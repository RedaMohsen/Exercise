package com.toters.exercise.features.main.home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.toters.exercise.R;
import com.toters.exercise.base.BaseRestoreController;
import com.toters.exercise.databinding.ControllerHomeBinding;
import com.toters.exercise.features.main.home.adapter.MarcelCharacterAdapter;
import com.toters.exercise.provider.AppProvider;

public class HomeController extends BaseRestoreController<HomeViewModel> {
    private ControllerHomeBinding binding;


    boolean isLoading = false;
    private MarcelCharacterAdapter adapter;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, Bundle savedViewState) {
        binding = ControllerHomeBinding.inflate(inflater);
        super.onCreateView(inflater, container, savedViewState);
        return binding.getRoot();


    }

    @Override
    protected HomeViewModel onCreateViewModel(AppProvider appProvider) {
        return new HomeViewModel(appProvider.getUserClient());
    }

    @Override
    protected void onBindView(Activity activity, AppProvider appProvider, HomeViewModel viewModel, Bundle savedViewState) {

        adapter = new MarcelCharacterAdapter(item -> {

        });
        binding.recyclerView.setAdapter(adapter);


        RecyclerView.OnScrollListener endOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (isLastItemDisplaying(recyclerView)) {
                    if (!isLoading) {
                        Log.e("Reached end: ", "Load more");
                        isLoading = true;
                        viewModel.getCharacters(adapter.getItemCount());
                    }
                }
            }
        };

        binding.recyclerView.addOnScrollListener(endOnScrollListener);

        disposables.add(viewModel.getCharactersRelay().subscribe(charactersResponse -> {

            adapter.setItems(charactersResponse.getResults());
            isLoading = false;
        }, Throwable::printStackTrace));

        viewModel.getCharacters(0);
    }


    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        //Check if the adapter item count is greater than 0
        if (recyclerView.getAdapter().getItemCount() != 0) {
            //get the last visible item on screen using the layout manager
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();

            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1) {
                return true;
            }

        }
        return false;

    }


    @Override
    protected void cleanView() {

    }

    @Override
    protected int layoutRes() {
        return R.layout.controller_home;
    }

    @Override
    public void onViewCreated(View view, Bundle savedViewState) {

    }

    @Override
    public void onToolbarBind(View view) {

    }
}
