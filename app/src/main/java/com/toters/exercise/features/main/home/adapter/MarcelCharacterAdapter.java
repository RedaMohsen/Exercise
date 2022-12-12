package com.toters.exercise.features.main.home.adapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.toters.exercise.R;
import com.toters.exercise.databinding.RowItemCharacterBinding;
import com.toters.exercise.helper.ResourceHelper;
import com.toters.exercise.network.model.responseBody.CharacterModel;
import com.toters.exercise.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NotifyDataSetChanged")
public class MarcelCharacterAdapter extends RecyclerView.Adapter<MarcelCharacterAdapter.ViewHolder> {

    private final List<CharacterModel> items = new ArrayList<>();
    private final OnClickListener listener;
    private ResourceHelper resourceHelper;

    public MarcelCharacterAdapter(ResourceHelper resourceHelper, OnClickListener listener) {
        this.resourceHelper = resourceHelper;
        this.listener = listener;
    }

    public void setItems(List<CharacterModel> list) {
        int prevPosition = items.size();

        items.addAll(list);
        notifyItemRangeChanged(prevPosition, items.size() - 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowItemCharacterBinding binding = RowItemCharacterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnClickListener {
        void onClick(CharacterModel item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RowItemCharacterBinding binding;

        public ViewHolder(@NonNull RowItemCharacterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(CharacterModel item) {
            ImageUtils.loadImage(String.format("%s.%s", item.getThumbNail().getPath(), item.getThumbNail().getExtension()), binding.imgView);
            binding.txtViewName.setText(item.getName());
            binding.txtViewDesc.setText(TextUtils.isEmpty(item.getDescription()) ? resourceHelper.getString(R.string.hero_has_no_desc) : item.getDescription());

            binding.getRoot().setOnClickListener(view -> listener.onClick(item));
        }
    }
}
