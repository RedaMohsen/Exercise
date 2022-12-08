package com.toters.exercise.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.bluelinelabs.conductor.Controller;
import com.toters.exercise.R;
import com.toters.exercise.constants.IntentConstants;

import java.io.File;

public class IntentHelper {
    private Controller controller;
    private ResourceHelper resourceHelper;

    public IntentHelper(Controller controller, ResourceHelper resourceHelper) {
        this.controller = controller;
        this.resourceHelper = resourceHelper;
    }
}
