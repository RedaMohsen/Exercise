package com.toters.exercise.features.main.details.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.toters.exercise.R;
import com.toters.exercise.databinding.RowItemDetailsBinding;
import com.toters.exercise.helper.ResourceHelper;
import com.toters.exercise.network.model.ComicModel;
import com.toters.exercise.network.model.EventModel;
import com.toters.exercise.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private ResourceHelper resourceHelper;
    List<EventModel> dataSet = new ArrayList<>();

    public EventsAdapter(ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
    }


    public void setItems(List<EventModel> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowItemDetailsBinding binding = RowItemDetailsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EventsAdapter.ViewHolder(binding);
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

        private RowItemDetailsBinding binding;

        public ViewHolder(@NonNull RowItemDetailsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(EventModel item) {
            ImageUtils.loadImage(String.format("%s.%s", item.getThumbNail().getPath(), item.getThumbNail().getExtension()), binding.imgView);
            binding.txtViewTitle.setText(item.getTitle());

            if (item.getDescription() != null)
                binding.txtViewDesc.setText(item.getDescription().isEmpty() ? resourceHelper.getString(R.string.description_not_found) : item.getDescription());
            else
                binding.txtViewDesc.setText(resourceHelper.getString(R.string.description_not_found));
        }
    }
}

