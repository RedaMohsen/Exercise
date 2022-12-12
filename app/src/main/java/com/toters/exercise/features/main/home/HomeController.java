package com.toters.exercise.features.main.home;

import static com.toters.exercise.constants.AppConstants.ANIMATE_FADE_IN;
import static com.toters.exercise.constants.AppConstants.ANIMATE_FADE_OUT;
import static com.toters.exercise.constants.BundleConstants.BUNDLE_CHARACTER_MODEL;

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
import com.toters.exercise.features.main.details.DetailsController;
import com.toters.exercise.features.main.home.adapter.MarcelCharacterAdapter;
import com.toters.exercise.helper.ResourceHelper;
import com.toters.exercise.provider.AppProvider;
import com.toters.exercise.utils.AnimationUtils;

public class HomeController extends BaseRestoreController<HomeViewModel> {
    private ControllerHomeBinding binding;
    private ResourceHelper resourceHelper;

    boolean isLoading = false;
    private MarcelCharacterAdapter adapter;
    RecyclerView.OnScrollListener endOnScrollListener;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, Bundle savedViewState) {
        binding = ControllerHomeBinding.inflate(inflater);
        super.onCreateView(inflater, container, savedViewState);
        return binding.getRoot();


    }

    @Override
    protected HomeViewModel onCreateViewModel(AppProvider appProvider) {
        resourceHelper = appProvider.getResourceHelper();
        return new HomeViewModel(appProvider.getUserClient());
    }

    @Override
    protected void onBindView(Activity activity, AppProvider appProvider, HomeViewModel viewModel, Bundle savedViewState) {

        setupUI(activity);

        disposables.add(viewModel.getCharactersRelay().subscribe(charactersResponse -> {
            if (binding.shimmerGroup.getRoot().getVisibility() == View.VISIBLE) {
                AnimationUtils.animateViewVisibility(binding.shimmerGroup.shimmerCards, ANIMATE_FADE_OUT, 300);
                AnimationUtils.animateViewVisibility(binding.recyclerView, ANIMATE_FADE_IN, 100);
            }

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

    void setupUI(Activity activity) {
        adapter = new MarcelCharacterAdapter(resourceHelper, item -> {
            setRetainViewMode(RetainViewMode.RETAIN_DETACH);
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLE_CHARACTER_MODEL, item);
            openPage(new DetailsController(bundle));
        });
        binding.recyclerView.setAdapter(adapter);


        endOnScrollListener = new RecyclerView.OnScrollListener() {
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

        binding.shimmerGroup.getRoot().startShimmer();
    }
}
