package com.siddydevelops.pokstats.PokeApi;

import com.siddydevelops.pokstats.Models.PokemonRequests;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeApiService {

    @GET("pokemon")
    Call<PokemonRequests> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);

}
