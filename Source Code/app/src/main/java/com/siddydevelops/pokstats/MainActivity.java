package com.siddydevelops.pokstats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;

    RecyclerView pokemonListRV;
    PokemonListAdapter pokemonListAdapter;

    private int offset;
    private boolean pageChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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