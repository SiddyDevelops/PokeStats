package com.siddydevelops.pokstats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.siddydevelops.pokstats.Models.PokeDetail;
import com.siddydevelops.pokstats.Models.PokeStats;
import com.siddydevelops.pokstats.PokeApi.PokeApiService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonDetailActivity extends AppCompatActivity {

    //https://pokeapi.co/api/v2/pokemon/{poke_num}  --> DETAIL API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        Intent intent = getIntent();
        int pokeNum = intent.getIntExtra("pokeNum",1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokeApiService pokeApiService = retrofit.create(PokeApiService.class);

        Call<PokeDetail> call = pokeApiService.getPokeDetails(pokeNum);

        call.enqueue(new Callback<PokeDetail>() {
            @Override
            public void onResponse(Call<PokeDetail> call, Response<PokeDetail> response) {
                if(!response.isSuccessful()){
                    Log.i("ERROR", "onResponse: " + response.code());
                }

                PokeDetail pokeDetails = response.body();
                Log.i("NAME", pokeDetails.getName());
                Log.i("Height", pokeDetails.getHeight());
                Log.i("Weight", pokeDetails.getWeight());
                List<PokeStats> p = pokeDetails.getStats();
                Log.i("ListDATA", p.toString());

            }

            @Override
            public void onFailure(Call<PokeDetail> call, Throwable t) {
                Log.i("ERROR", t.getMessage());
            }
        });

    }
}