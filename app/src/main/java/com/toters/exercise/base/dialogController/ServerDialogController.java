package com.toters.exercise.base.dialogController;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

public class ServerDialogController extends DialogController {
    public InternetDialogListener internetDialogListener;

    public interface InternetDialogListener {
        void onConfigClicked();

        void onRetryClicked();
    }

    public ServerDialogController(InternetDialogListener internetDialogListener) {
        super();
        this.internetDialogListener = internetDialogListener;
    }

    public ServerDialogController(Bundle bundle) {
        super(bundle);
    }

    @Override
    public Dialog onCreateDialog() {
//        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.controller_no_internet, null, false);
//        initView(dialogView);
//        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(dialogView).create();
//        setCancelable(false);
//        dialog.requestWindowFeature(STYLE_NO_TITLE);
        return dialog;
    }

    private void initView(View dialogView) {
//        dialogView.findViewById(R.id.checkInternetButton).setOnClickListener(view -> {
//            internetDialogListener.onConfigClicked();
//        });
//        dialogView.findViewById(R.id.repeatButton).setOnClickListener(view -> {
//            internetDialogListener.onRetryClicked();
//            this.dismiss();
//        });
    }

}
