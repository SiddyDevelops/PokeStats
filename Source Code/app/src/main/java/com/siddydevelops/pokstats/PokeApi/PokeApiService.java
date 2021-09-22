package com.siddydevelops.pokstats.PokeApi;

import com.siddydevelops.pokstats.Models.PokeDetail;
import com.siddydevelops.pokstats.Models.PokemonRequests;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApiService {

    @GET("pokemon")
    Call<PokemonRequests> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{poke_num}")
    Call<PokeDetail> getPokeDetails(@Path("poke_num") int poke_num);

    @GET("pokemon/{poke_name}")
    Call<PokeDetail> getPokeDetailsV(@Path("poke_name") String poke_name);

}
