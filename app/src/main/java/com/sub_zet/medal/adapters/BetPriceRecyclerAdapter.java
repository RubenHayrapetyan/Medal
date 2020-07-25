package com.sub_zet.medal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sub_zet.medal.databinding.RowBetPriceBinding;
import com.sub_zet.medal.interfaces.BetPriceClickListener;
import com.sub_zet.medal.models.BetPriceModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BetPriceRecyclerAdapter extends RecyclerView.Adapter<BetPriceRecyclerAdapter.MyViewHolder> {

    private final ArrayList<BetPriceModel> priceArray = new ArrayList<>();
    private BetPriceClickListener listener;

    public BetPriceRecyclerAdapter() {}

    public void setListener(BetPriceClickListener listener) {
        this.listener = listener;
    }

    public void setData(ArrayList<BetPriceModel> priceModels) {
        priceArray.clear();
        priceArray.addAll(priceModels);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BetPriceRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowBetPriceBinding binding = RowBetPriceBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull BetPriceRecyclerAdapter.MyViewHolder holder, int position) {

        holder.bind(priceArray.get(position));
    }

    @Override
    public int getItemCount() {
        return priceArray.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private RowBetPriceBinding betPriceItemsBinding;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            betPriceItemsBinding = DataBindingUtil.findBinding(itemView);
        }

        void bind(final BetPriceModel item) {
            betPriceItemsBinding.setBetPrice(item);
            itemView.setOnClickListener(v -> {

                listener.priceClickListener(item);
            });
        }
    }
}
