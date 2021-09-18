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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getData();

        pokemonListRV = findViewById(R.id.pokemonListRV);
        pokemonListAdapter = new PokemonListAdapter();
        pokemonListRV.setAdapter(pokemonListAdapter);
        pokemonListRV.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        pokemonListRV.setLayoutManager(layoutManager);
    }

    private void getData() {
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<PokemonRequests> pokemonRequestsCall =  service.getPokemonList();

        pokemonRequestsCall.enqueue(new Callback<PokemonRequests>() {
            @Override
            public void onResponse(@NonNull Call<PokemonRequests> call, @NonNull Response<PokemonRequests> response) {
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
                Log.i("Error","onFailure: " + t.getMessage());
            }
        });

    }
}