package com.sub_zet.medal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sub_zet.medal.databinding.RowGamesTutorialBinding;
import com.sub_zet.medal.models.GameTutorialModel;
import com.sub_zet.medal.models.HelpModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class HelpRecyclerAdapter extends RecyclerView.Adapter<HelpRecyclerAdapter.MyViewHolder> {
    private final List<HelpModel> gamesTutorialArray = new ArrayList<>();

    public HelpRecyclerAdapter() {}

    public void setData(ArrayList<HelpModel> helpModels){
        gamesTutorialArray.clear();
        gamesTutorialArray.addAll(helpModels);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HelpRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowGamesTutorialBinding binding = RowGamesTutorialBinding.inflate(inflater, parent, false);
        return new HelpRecyclerAdapter.MyViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HelpRecyclerAdapter.MyViewHolder holder, int position) {

        holder.bind(gamesTutorialArray.get(position));
    }

    @Override
    public int getItemCount() {
        return gamesTutorialArray.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private RowGamesTutorialBinding gamesTutorialRowBinding;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            gamesTutorialRowBinding = DataBindingUtil.bind(itemView);
        }

        void bind(final HelpModel item) {
            gamesTutorialRowBinding.setTutorial(item);
        }
    }
}
