package com.toters.exercise.features.main.details;

import static com.toters.exercise.constants.BundleConstants.BUNDLE_CHARACTER_MODEL;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.toters.exercise.R;
import com.toters.exercise.base.BaseRestoreController;
import com.toters.exercise.databinding.ControllerDetailsBinding;
import com.toters.exercise.features.main.details.adapter.ComicsAdapter;
import com.toters.exercise.features.main.details.adapter.EventsAdapter;
import com.toters.exercise.features.main.details.adapter.SeriesAdapter;
import com.toters.exercise.features.main.details.adapter.StoriesAdapter;
import com.toters.exercise.helper.ResourceHelper;
import com.toters.exercise.network.model.responseBody.CharacterModel;
import com.toters.exercise.provider.AppProvider;
import com.toters.exercise.utils.ImageUtils;

public class DetailsController extends BaseRestoreController<DetailsViewModel> {
    private ControllerDetailsBinding binding;
    private ResourceHelper resourceHelper;
    private CharacterModel characterModel;
    private DividerItemDecoration itemDecorator;
    /*******
     * Comics adapter
     * ****************/
    ComicsAdapter comicsAdapter;


    /*******
     * Events adapter
     * ****************/
    EventsAdapter eventsAdapter;


    /*******
     * Series adapter
     * ****************/
    SeriesAdapter seriesAdapter;


    /*******
     * Stories adapter
     * ****************/
    StoriesAdapter storiesAdapter;

    public DetailsController(Bundle bundle) {
        super(bundle);
        characterModel = (CharacterModel) bundle.getSerializable(BUNDLE_CHARACTER_MODEL);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, Bundle savedViewState) {
        binding = ControllerDetailsBinding.inflate(inflater);
        super.onCreateView(inflater, container, savedViewState);
        return binding.getRoot();


    }

    @Override
    protected DetailsViewModel onCreateViewModel(AppProvider appProvider) {
        resourceHelper = appProvider.getResourceHelper();
        return new DetailsViewModel(appProvider.getUserClient());
    }

    @Override
    protected void onBindView(Activity activity, AppProvider appProvider, DetailsViewModel viewModel, Bundle savedViewState) {
        setupUI(activity);
    }

    @Override
    protected void cleanView() {

    }

    @Override
    protected int layoutRes() {
        return R.layout.controller_details;
    }

    @Override
    public void onViewCreated(View view, Bundle savedViewState) {

    }

    @Override
    public void onToolbarBind(View view) {

    }


    void setupUI(Activity activity) {
        if (characterModel == null)
            return;

        /*setup details passed in the bundel model*/
        {
            binding.toolBar.backImageView.setOnClickListener(view -> pop());
            ImageUtils.loadImage(String.format("%s.%s", characterModel.getThumbNail().getPath(), characterModel.getThumbNail().getExtension()), binding.headerGroup.imgView);
            binding.headerGroup.txtViewName.setText(String.format("%s\n%s: %s", characterModel.getName(), resourceHelper.getString(R.string.hero_id), characterModel.getId()));
            binding.headerGroup.txtViewDesc.setText(TextUtils.isEmpty(characterModel.getDescription()) ? resourceHelper.getString(R.string.hero_has_no_desc) : characterModel.getDescription());

        }

        /*setup item decoration ref*/
        {
            itemDecorator = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
            itemDecorator.setDrawable(AppCompatResources.getDrawable(activity, R.drawable.divider_transparent));
        }


        /*initialize comics adapter and add dividers to recycler view*/
        {
            comicsAdapter = new ComicsAdapter(resourceHelper);
            binding.comicsGroup.recyclerView.setAdapter(comicsAdapter);

            binding.comicsGroup.recyclerView.addItemDecoration(itemDecorator);
            binding.comicsGroup.shimmerGroup.getRoot().startShimmer();
        }



        /*initialize events adapter*/
        {
            eventsAdapter = new EventsAdapter(resourceHelper);
            binding.eventsGroup.recyclerView.setAdapter(eventsAdapter);
            binding.eventsGroup.shimmerGroup.getRoot().startShimmer();
        }


        /*initialize series adapter*/
        {
            seriesAdapter = new SeriesAdapter(resourceHelper);
            binding.seriesGroup.recyclerView.setAdapter(comicsAdapter);
            binding.seriesGroup.shimmerGroup.getRoot().startShimmer();
        }

        /*initialize stories adapter*/
        {
            storiesAdapter = new StoriesAdapter(resourceHelper);
            binding.storiesGroup.recyclerView.setAdapter(storiesAdapter);
            binding.storiesGroup.shimmerGroup.getRoot().startShimmer();
        }



        /*add disposables*/
        {
            disposables.add(viewModel.getComicsRelay().subscribe(response -> {

                binding.comicsGroup.shimmerGroup.getRoot().stopShimmer();
                binding.comicsGroup.shimmerGroup.shimmerCards.setVisibility(View.GONE);

                assert response != null;
                comicsAdapter.setItems(response.getResults());

                binding.comicsGroup.txtViewNoItem.setVisibility(comicsAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
            }, Throwable::printStackTrace));

            disposables.add(viewModel.getEventsRelay().subscribe(response -> {

                binding.eventsGroup.shimmerGroup.getRoot().stopShimmer();
                binding.eventsGroup.shimmerGroup.shimmerCards.setVisibility(View.GONE);

                assert response != null;
                eventsAdapter.setItems(response.getResults());

                binding.eventsGroup.txtViewNoItem.setVisibility(eventsAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
            }, Throwable::printStackTrace));


            disposables.add(viewModel.getSeriesRelay().subscribe(response -> {

                binding.seriesGroup.shimmerGroup.getRoot().stopShimmer();
                binding.seriesGroup.shimmerGroup.shimmerCards.setVisibility(View.GONE);

                assert response != null;
                seriesAdapter.setItems(response.getResults());

                binding.seriesGroup.txtViewNoItem.setVisibility(seriesAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
            }, Throwable::printStackTrace));


            disposables.add(viewModel.getStoriesRelay().subscribe(response -> {
                binding.storiesGroup.shimmerGroup.getRoot().stopShimmer();
                binding.storiesGroup.shimmerGroup.shimmerCards.setVisibility(View.GONE);

                assert response != null;
                storiesAdapter.setItems(response.getResults());

                binding.storiesGroup.txtViewNoItem.setVisibility(storiesAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
            }, Throwable::printStackTrace));
        }


        viewModel.getComics(characterModel.getId());
        viewModel.getEvents(characterModel.getId());
        viewModel.getSeries(characterModel.getId());
        viewModel.getStories(characterModel.getId());

    }
}
