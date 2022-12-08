package com.toters.exercise.base;

import static com.toters.exercise.constants.AppConstants.ROUTER_TAG_COMMON;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.toters.exercise.helper.PrefHelper;

public abstract class BaseViewBindingRestoreController extends Controller {

    public BaseViewBindingRestoreController() {
    }

    public BaseViewBindingRestoreController(Bundle bundle) {
        super(bundle);
    }

    protected abstract int layoutRes();

    public abstract void onViewCreated(View view, Bundle savedViewState);

    public abstract void onToolbarBind(View view);

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @Nullable Bundle savedViewState) {
        View view = onInflateView(layoutRes(), inflater, container);
        onViewPreCreated(view);
        onViewCreated(view, savedViewState);
        bindToolbar(view);
        return view;
    }

    protected void onViewPreCreated(View view) {

    }

    protected void bindToolbar(View view) {
        onToolbarBind(view);
    }

    protected View onInflateView(int layoutRes, LayoutInflater inflater, ViewGroup container){
        return inflater.inflate(layoutRes, container, false);
    }

    protected void setUpToolbar(View view, int titleId, int imageId) {
      /*  ImageView toolbarIcon = view.findViewById(R.id.imageToolbar);
        TextView toolbarTitle = view.findViewById(R.id.titleToolbar);

        toolbarIcon.setImageResource(imageId);
        toolbarTitle.setText(titleId);*/
    }

    protected void setUpToolbar(View view, String title, String imageUrl) {
      /*  ImageView toolbarIcon = view.findViewById(R.id.imageToolbar);
        TextView toolbarTitle = view.findViewById(R.id.titleToolbar);

        ImageUtils.loadImage(BuildConfig.BASE_URL + imageUrl, toolbarIcon);
        toolbarTitle.setText(title);*/
    }

    protected   void openPage(Controller controller) {
        try{
            getRouter().pushController(RouterTransaction.with(controller).tag(ROUTER_TAG_COMMON)
                    .popChangeHandler(new HorizontalChangeHandler())
                    .pushChangeHandler(new HorizontalChangeHandler()));
        }catch (Exception used){
            Log.e("THE_KODE","open page exception",used);
        }


    }


    protected void openPageInChildRouter(Controller controller) {

        try {
            getParentController().getRouter().pushController(RouterTransaction.with(controller).tag(ROUTER_TAG_COMMON)
                    .pushChangeHandler(new HorizontalChangeHandler())
                    .popChangeHandler(new HorizontalChangeHandler()));
        } catch (Exception ex) {
            if (getRouter()!=null)
                getRouter().pushController(RouterTransaction.with(controller).tag(ROUTER_TAG_COMMON)
                        .pushChangeHandler(new HorizontalChangeHandler())
                        .popChangeHandler(new HorizontalChangeHandler()));
        }

    }

    protected void pop() {
        if (getRouter().getBackstack().size() > 0)
            getRouter().popCurrentController();
    }

    protected void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
