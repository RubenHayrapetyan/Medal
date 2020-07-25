package com.sub_zet.medal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sub_zet.medal.databinding.RowGamesBinding;
import com.sub_zet.medal.interfaces.GamesClickListener;
import com.sub_zet.medal.models.GamesModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class GamesListRecyclerAdapter extends RecyclerView.Adapter<GamesListRecyclerAdapter.MyViewHolder> {

    private ArrayList<GamesModel> gamesListArray = new ArrayList<>();
    private GamesClickListener listener;

    public GamesListRecyclerAdapter() {}
    public void setListener(GamesClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public GamesListRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowGamesBinding binding = RowGamesBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull GamesListRecyclerAdapter.MyViewHolder holder, int position) {

        holder.bind(gamesListArray.get(position));
    }

    public void setData(ArrayList<GamesModel> gamesModel){
        gamesListArray.clear();
        gamesListArray.addAll(gamesModel);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return gamesListArray.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private RowGamesBinding binding;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        void bind(final GamesModel item) {
            binding.setGamesRow(item);
            itemView.setOnClickListener(v -> {
                listener.onGameItemClick(item);
                for (int i = 0; i < gamesListArray.size(); i ++){
                    gamesListArray.get(i).setSelected(false);
                }
                item.setSelected(true);
            });
        }
    }
}
