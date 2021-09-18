package com.siddydevelops.pokstats.RecyclerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.siddydevelops.pokstats.Models.Pokemon;
import com.siddydevelops.pokstats.R;

import java.util.ArrayList;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder> {

    private ArrayList<Pokemon> dataset;
    Context context;

    public PokemonListAdapter(){
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pokemon_list_item, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemon = dataset.get(position);
        holder.pokemonName.setText(pokemon.getName());
        Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+ pokemon.getNumber() +".png").into(holder.pokemonImageView);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addPokemonToList(ArrayList<Pokemon> listOfPokemons) {
        dataset.addAll(listOfPokemons);
        notifyDataSetChanged();
    }


    public class PokemonViewHolder extends RecyclerView.ViewHolder {

        TextView pokemonName;
        ImageView pokemonImageView;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);

            pokemonName = itemView.findViewById(R.id.pokemonName);
            pokemonImageView = itemView.findViewById(R.id.pokemonImageView);
            context = itemView.getContext();

        }
    }

}
