package com.sub_zet.medal.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sub_zet.medal.databinding.RowPlatformBinding;
import com.sub_zet.medal.helpers.MySavedData;
import com.sub_zet.medal.interfaces.PlatformsClickListener;
import com.sub_zet.medal.models.PlatformsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class PlatformRecyclerAdapter extends RecyclerView.Adapter<PlatformRecyclerAdapter.MyViewHolder> {
    private ArrayList<PlatformsModel> platformArray = new ArrayList<>();
    private PlatformsClickListener listener;
    private boolean isSelected = false;

    public PlatformRecyclerAdapter() {
    }

    @NonNull
    @Override
    public PlatformRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowPlatformBinding binding = RowPlatformBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull PlatformRecyclerAdapter.MyViewHolder holder, int position) {
        Log.i("imgUrl", platformArray.get(position).getPlatformIconUrl());

        holder.bind(platformArray.get(position));
    }

    @Override
    public int getItemCount() {
        return platformArray.size();
    }

    public void setData(ArrayList<PlatformsModel> platformsModels) {
        platformArray.clear();
        platformArray.addAll(platformsModels);
        notifyDataSetChanged();
    }

    public void setListener(PlatformsClickListener listener) {
        this.listener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private RowPlatformBinding itemPlatformsBinding;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemPlatformsBinding = DataBindingUtil.bind(itemView);
        }

        void bind(final PlatformsModel item) {

            if (!isSelected) {
                for (int i = 0; i < platformArray.size(); i++) {
                    if (platformArray.get(i).getPlatformName().equals(MySavedData.getChoosenPlatform())) {
                        platformArray.get(i).setSelected(true);
                    }
                }
            }

            itemView.setOnClickListener(v -> {
                isSelected = true;
                // senc nenc enq anum, vor menak 1 platform yntrvac mna, mnacacy hani
                for (int i = 0; i < platformArray.size(); i++) {
                    platformArray.get(i).setSelected(false);
                }
                item.setSelected(true);
                listener.onPlatformItemClick(item);
                MySavedData.saveChoosenPlatform(item.getPlatformName());
            });
            for (int i = 0; i < platformArray.size(); i++) {
                if (platformArray.get(i).isSelected())
                    Log.i("sdvavda", platformArray.get(i).getPlatformName() + " = selected");
            }
            itemPlatformsBinding.setPlatformModel(item);
        }
    }
}
