package com.toters.exercise.features.main.details.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.toters.exercise.R;
import com.toters.exercise.databinding.RowItemDetailsBinding;
import com.toters.exercise.databinding.RowItemDetailsStoryBinding;
import com.toters.exercise.helper.ResourceHelper;
import com.toters.exercise.network.model.SeriesModel;
import com.toters.exercise.network.model.StoryModel;
import com.toters.exercise.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {

    private ResourceHelper resourceHelper;
    List<StoryModel> dataSet = new ArrayList<>();

    public StoriesAdapter(ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
    }


    public void setItems(List<StoryModel> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowItemDetailsStoryBinding binding = RowItemDetailsStoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StoriesAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RowItemDetailsStoryBinding binding;

        public ViewHolder(@NonNull RowItemDetailsStoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(StoryModel item) {
            binding.txtViewTitle.setText(item.getTitle());
            if (item.getDescription() != null)
                binding.txtViewDesc.setText(item.getDescription().isEmpty() ? resourceHelper.getString(R.string.description_not_found) : item.getDescription());
            else
                binding.txtViewDesc.setText(resourceHelper.getString(R.string.description_not_found));
        }
    }
}

