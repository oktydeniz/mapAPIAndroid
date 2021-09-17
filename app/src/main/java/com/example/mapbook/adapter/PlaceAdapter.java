package com.example.mapbook.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapbook.databinding.ItemRecyclerViewBinding;
import com.example.mapbook.model.Place;
import com.example.mapbook.util.RecyclerViewClickInterface;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder>{

    public List<Place> placeList;
    RecyclerViewClickInterface clickInterface;

    public PlaceAdapter(List<Place> places, RecyclerViewClickInterface clickInterface) {
        this.placeList = places;
        this.clickInterface = clickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRecyclerViewBinding viewBinding = ItemRecyclerViewBinding.inflate(inflater);
        return new ViewHolder(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = placeList.get(position);
        holder.binding.setPlace(place);
        holder.binding.executePendingBindings();
        holder.binding.getLocation.setOnClickListener(v -> clickInterface.itemOnClick(place));
    }



    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemRecyclerViewBinding binding;

        public ViewHolder(@NonNull ItemRecyclerViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
