package com.toters.exercise.base;

import static android.provider.Settings.ACTION_WIFI_SETTINGS;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.toters.exercise.base.dialogController.InternetDialogController;
import com.toters.exercise.features.splash.SplashController;
import com.toters.exercise.network.clients.volley.VolleyApiClient;
import com.toters.exercise.provider.AppProvider;
import com.toters.exercise.utils.DialogUtils;
import com.toters.exercise.utils.ErrorHandler;
import com.toters.exercise.utils.Utils;

import java.util.Set;

import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseRestoreController<T extends ViewModel> extends BaseViewBindingRestoreController implements InternetDialogController.InternetDialogListener {

    public BaseRestoreController() {}

    public BaseRestoreController(Bundle bundle) {
        super(bundle);
    }

    protected CompositeDisposable disposables = new CompositeDisposable();
    private AlertDialog loadingDialog;
    private AlertDialog infoDialog;
    protected T viewModel;

    protected abstract T onCreateViewModel(AppProvider appProvider);

    protected abstract void onBindView(Activity activity, AppProvider appProvider, T viewModel, Bundle savedViewState);

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, Bundle savedViewState) {
        View view = super.onCreateView(inflater, container, savedViewState);
        AppProvider appProvider = new AppProvider(getActivity());
        if (viewModel == null) {
            viewModel = onCreateViewModel(appProvider);
        }
        onBindView(getActivity(), appProvider, viewModel, savedViewState);

        disposables.add(viewModel.getLoadingObs.subscribe(this::showLoading));
        disposables.add(viewModel.getInfoObs.subscribe(this::showInfoDialog));
        disposables.add(appProvider.getErrorHandler().getInternetRelay().subscribe(request -> {
            showLoading(false);
            showInternetDialog();
//            if (!(viewModel instanceof HomeViewModel ) && !(viewModel instanceof CartViewModel) && !(viewModel instanceof ProductsViewModel) &&
//                    !(viewModel instanceof NotificationsViewModel) && !(viewModel instanceof MenuViewModel) && !(viewModel instanceof ParentNavigationViewModel) && !(viewModel instanceof SplashViewModel))
//            showInternetDialog();


        }));
        disposables.add(appProvider.getErrorHandler().getErrorRelay().subscribe(error -> {
            Log.e("API_ERROR", error.getMessage());

            Utils.showSnackBar(container, error.getMessage());
        }));

        disposables.add(appProvider.getErrorHandler().getLogoutRelay().subscribe(requests -> {
            showLoading(false);
            Router router = getParentController() != null ? getParentController().getRouter() : getRouter();
            router.setRoot(RouterTransaction.with(new SplashController(getArgs())));
        }));

        disposables.add(viewModel.getLogoutRelay().subscribe(aBoolean -> {



        }));
        return view;
    }

    public void showInternetDialog() {
        Router router = getParentController() != null ? getParentController().getRouter() : getRouter();
        new InternetDialogController(this).show(router, "internetDialog");
    }

    @Override
    public void onConfigClicked() {
        Intent intent = new Intent();
        intent.setAction(ACTION_WIFI_SETTINGS);
        startActivity(intent);
    }

    @Override
    public void onRetryClicked() {
        Set requests = ErrorHandler.internetRelay.getValue();
        Log.d("REQUESTS: ", String.valueOf(requests));
        for (int i = 0; i < requests.size(); i++) {
            VolleyApiClient.getInstance(getActivity()).getRequestQueue().add((Request) requests.toArray()[i]);
        }
        requests.clear();
    }

    private void showLoading(Boolean show) {
        if (loadingDialog == null) loadingDialog = DialogUtils.createLoadingDialog(getActivity());
        if (show && !loadingDialog.isShowing()) {
            loadingDialog.show();
        } else {
            if (!show && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        }
    }

    private void showInfoDialog(String message) {
        if (infoDialog == null) infoDialog = DialogUtils.createInfoDialog(getActivity(), message);
        else infoDialog.setMessage(message);


        infoDialog.show();
    }

    protected abstract void cleanView();

    @Override
    protected void onDestroyView(@NonNull View view) {
        disposables.clear();
        loadingDialog = null;
        super.onDestroyView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanView();
        if (viewModel != null) {
            viewModel.onCleared();
        }
    }

}
