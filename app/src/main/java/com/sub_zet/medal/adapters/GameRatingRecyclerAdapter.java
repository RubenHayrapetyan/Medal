package com.sub_zet.medal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sub_zet.medal.databinding.RowGamesRatingBinding;
import com.sub_zet.medal.models.ProfileGameRatingModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class GameRatingRecyclerAdapter extends RecyclerView.Adapter<GameRatingRecyclerAdapter.MyViewHolder> {

    private ArrayList<ProfileGameRatingModel> gameRatingList = new ArrayList<>();

    public GameRatingRecyclerAdapter() {}

    @NonNull
    @Override
    public GameRatingRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowGamesRatingBinding binding = RowGamesRatingBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull GameRatingRecyclerAdapter.MyViewHolder holder, int position) {

        holder.bind(gameRatingList.get(position));
    }

    public void setData(ArrayList<ProfileGameRatingModel> profileGameRatingModel){
        gameRatingList.clear();
        gameRatingList.addAll(profileGameRatingModel);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return gameRatingList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private RowGamesRatingBinding binding;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        void bind(final ProfileGameRatingModel item) {
            binding.setGameRating(item);
        }
    }
}

