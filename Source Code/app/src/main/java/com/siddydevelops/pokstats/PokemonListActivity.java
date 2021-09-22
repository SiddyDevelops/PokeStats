package com.siddydevelops.pokstats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.roger.catloadinglibrary.CatLoadingView;
import com.siddydevelops.pokstats.Models.PokeDetail;
import com.siddydevelops.pokstats.Models.Pokemon;
import com.siddydevelops.pokstats.Models.PokemonRequests;
import com.siddydevelops.pokstats.PokeApi.PokeApiService;
import com.siddydevelops.pokstats.RecyclerAdapters.PokemonListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonListActivity extends AppCompatActivity {

    private Retrofit retrofit;

    EditText searchEditText;
    ImageView pokeBallImageView;

    RecyclerView pokemonListRV;
    PokemonListAdapter pokemonListAdapter;

    CatLoadingView catLoadingView;

    private int offset;
    private boolean pageChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        catLoadingView = new CatLoadingView();
        catLoadingView.show(getSupportFragmentManager(), "");

        searchEditText = findViewById(R.id.searchEditText);
        pokeBallImageView = findViewById(R.id.pokeBallImageView);

        pokeBallImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pokemonName = searchEditText.getText().toString().toLowerCase().replace(" ","");
                catLoadingView.show(getSupportFragmentManager(), "");
                getPokemonDetailsBySearch(pokemonName);
            }
        });

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_GO) {
                    Log.i("INFO-->",searchEditText.getText().toString());
                    String pokemonName = searchEditText.getText().toString().toLowerCase().replace(" ","");
                    catLoadingView.show(getSupportFragmentManager(), "");
                    getPokemonDetailsBySearch(pokemonName);
                }
                return false;
            }
        });

        pokemonListRV = findViewById(R.id.pokemonListRV);
        pokemonListAdapter = new PokemonListAdapter();
        pokemonListRV.setAdapter(pokemonListAdapter);
        pokemonListRV.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        pokemonListRV.setLayoutManager(layoutManager);
        pokemonListRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItem = layoutManager.findFirstVisibleItemPosition();

                    if(pageChange){
                        if((visibleItemCount + pastVisibleItem) >= totalItemCount){
                            catLoadingView.show(getSupportFragmentManager(), "");
                            Log.i("PAGE-->", "PageChange");
                            pageChange = false;
                            offset += 20;
                            getData(offset);
                        }
                    }

                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        pageChange = true;
        offset = 0;
        getData(offset);
    }

    private void getPokemonDetailsBySearch(String pokemonName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PokeApiService pokeApiService = retrofit.create(PokeApiService.class);

        Call<PokeDetail> call = pokeApiService.getPokeDetailsV(pokemonName);

        call.enqueue(new Callback<PokeDetail>() {
            @Override
            public void onResponse(Call<PokeDetail> call, Response<PokeDetail> response) {
                if(!response.isSuccessful()){
                    Log.i("ERROR", "onResponse: " + response.code());
                }
                PokeDetail pokeDetails = response.body();
                if(pokeDetails == null)
                {
                    Toast.makeText(PokemonListActivity.this, "No such Pokémon Exists!", Toast.LENGTH_SHORT).show();
                    catLoadingView.dismiss();
                    searchEditText.setText("");
                    recreate();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), PokemonDetailActivity.class);
                    intent.putExtra("pokeNum", pokeDetails.getId());
                    catLoadingView.dismiss();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<PokeDetail> call, Throwable t) {
                Log.i("ERROR", t.getMessage());
            }
        });

    }

    private void getData(int offset) {
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<PokemonRequests> pokemonRequestsCall =  service.getPokemonList(20,offset);

        pokemonRequestsCall.enqueue(new Callback<PokemonRequests>() {
            @Override
            public void onResponse(@NonNull Call<PokemonRequests> call, @NonNull Response<PokemonRequests> response) {
                pageChange = true;
                if(response.isSuccessful())
                {
                    PokemonRequests pokemonRequests = response.body();
                    ArrayList<Pokemon> listOfPokemons = pokemonRequests.getResults();
//                    for(int i=0; i<listOfPokemons.size(); i++)
//                    {
//                        Pokemon pokemon = listOfPokemons.get(i);
//                        Log.i("POKEMON: ", pokemon.getName());
//                        Log.i("POKEMON_URL: ", pokemon.getUrl());
//                    }
                    pokemonListAdapter.addPokemonToList(listOfPokemons);
                    catLoadingView.dismiss();

                } else {
                    Log.i("Error","onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonRequests> call, Throwable t) {
                pageChange = true;
                Log.i("Error","onFailure: " + t.getMessage());
            }
        });
    }

}