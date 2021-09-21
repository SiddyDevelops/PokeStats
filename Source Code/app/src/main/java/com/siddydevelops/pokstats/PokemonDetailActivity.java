package com.siddydevelops.pokstats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.siddydevelops.pokstats.Models.PokeDetail;
import com.siddydevelops.pokstats.Models.PokeStats;
import com.siddydevelops.pokstats.PokeApi.PokeApiService;

import java.util.ArrayList;
import java.util.Collections;
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

    ConstraintLayout parentConstraintView;

    ProgressBar progressBar;

    ProgressBar donutChart;
    TextView statTextView1;
    ProgressBar donutChart1;
    TextView statTextView2;
    ProgressBar donutChart2;
    TextView statTextView3;
    ProgressBar donutChart3;
    TextView statTextView4;
    ProgressBar donutChart4;
    TextView statTextView5;
    ProgressBar donutChart5;
    TextView statTextView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //int colorCodeDark = Color.parseColor("#FF9800");
        window.setStatusBarColor(getResources().getColor(R.color.pokemonColor1));

        parentConstraintView = findViewById(R.id.parentConstraintView);
        parentConstraintView.setVisibility(View.INVISIBLE);

        progressBar = findViewById(R.id.spin_kit);
        Sprite fadingCircle = new FadingCircle();
        progressBar.setIndeterminateDrawable(fadingCircle);

        statTextView1 = findViewById(R.id.statTextView1);
        statTextView2 = findViewById(R.id.statTextView2);
        statTextView3 = findViewById(R.id.statTextView3);
        statTextView4 = findViewById(R.id.statTextView4);
        statTextView5 = findViewById(R.id.statTextView5);
        statTextView6 = findViewById(R.id.statTextView6);

        donutChart = findViewById(R.id.donutChart);
        donutChart1 = findViewById(R.id.donutChart1);
        donutChart2 = findViewById(R.id.donutChart2);
        donutChart3 = findViewById(R.id.donutChart3);
        donutChart4 = findViewById(R.id.donutChart4);
        donutChart5 = findViewById(R.id.donutChart5);

        donutChart.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.stats_HP), android.graphics.PorterDuff.Mode.ADD);
        donutChart1.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.stats_attack), android.graphics.PorterDuff.Mode.ADD);
        donutChart2.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.stats_defense), android.graphics.PorterDuff.Mode.ADD);
        donutChart3.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.stats_spAttack), android.graphics.PorterDuff.Mode.ADD);
        donutChart4.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.stats_spDefense), android.graphics.PorterDuff.Mode.ADD);
        donutChart5.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.stats_speed), PorterDuff.Mode.ADD);

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

                parentConstraintView.setVisibility(View.VISIBLE);

                nameOfPokemon.setText(pokeDetails.getName());
                heightTextView.setText(pokeDetails.getHeight() + " KG");
                weightTextView.setText(pokeDetails.getWeight() + " m");

                ObjectAnimator.ofInt(donutChart,"progress",Integer.parseInt(p.get(0).toString())).setDuration(1000).start();
                ObjectAnimator.ofInt(donutChart1,"progress",Integer.parseInt(p.get(1).toString())).setDuration(1000).start();
                ObjectAnimator.ofInt(donutChart2,"progress",Integer.parseInt(p.get(2).toString())).setDuration(1000).start();
                ObjectAnimator.ofInt(donutChart3,"progress",Integer.parseInt(p.get(3).toString())).setDuration(1000).start();
                ObjectAnimator.ofInt(donutChart4,"progress",Integer.parseInt(p.get(4).toString())).setDuration(1000).start();
                ObjectAnimator.ofInt(donutChart5,"progress",Integer.parseInt(p.get(5).toString())).setDuration(1000).start();

                statTextView1.setText(p.get(0).toString());
                statTextView2.setText(p.get(1).toString());
                statTextView3.setText(p.get(2).toString());
                statTextView4.setText(p.get(3).toString());
                statTextView5.setText(p.get(4).toString());
                statTextView6.setText(p.get(5).toString());

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<PokeDetail> call, Throwable t) {
                Log.i("ERROR", t.getMessage());
            }
        });

    }
}