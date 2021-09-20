package com.siddydevelops.pokstats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

    TextView nameOfPokemon;
    ImageView pokemonImageView;
    TextView heightTextView;
    TextView weightTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //int colorCodeDark = Color.parseColor("#FF9800");
        window.setStatusBarColor(getResources().getColor(R.color.pokemonColor1));

        Intent intent = getIntent();
        int pokeNum = intent.getIntExtra("pokeNum",1);

        nameOfPokemon = findViewById(R.id.nameOfPokemon);
        pokemonImageView = findViewById(R.id.pokemonImageView);
        heightTextView = findViewById(R.id.heightTextView);
        weightTextView = findViewById(R.id.weightTextView);

        Glide.with(this).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+ pokeNum +".png").into(pokemonImageView);


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

                nameOfPokemon.setText(pokeDetails.getName());
                heightTextView.setText(pokeDetails.getHeight() + " KG");
                weightTextView.setText(pokeDetails.getWeight() + " m");

            }

            @Override
            public void onFailure(Call<PokeDetail> call, Throwable t) {
                Log.i("ERROR", t.getMessage());
            }
        });

    }
}