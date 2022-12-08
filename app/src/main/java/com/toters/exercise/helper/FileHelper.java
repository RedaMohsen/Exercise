package com.toters.exercise.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.core.content.FileProvider;


import com.toters.exercise.constants.AppConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.UUID;


public class FileHelper {
    private Context context;

    public FileHelper(Context context) {
        this.context = context;
    }

    public File createImageUploadFile() {
        return createUploadFile(createRandomString() + ".jpg");
    }

    public File createImageUploadFileExtension(Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        String mimeType = contentResolver.getType(uri) != null ? contentResolver.getType(uri) : "application/octet-stream";

        return createUploadFile(createRandomString() + "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType));
    }

    public File createAudioUploadFile() {
        return createUploadFile(createRandomString() + ".mp4");
    }

    public File createPDFUploadFile(){return createUploadFile(createRandomString() + ".pdf");}

    private File createUploadFile(String filename) {
        File dir = new File(context.getCacheDir(), "uploads");
        dir.mkdir();
        return new File(dir, filename);
    }

    private String createRandomString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public Uri fileToPublicUri(File file) {
        return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
    }

    public File copyToStorage(byte[] bytes) {
        String root = context.getFilesDir().toString();
        File myDir = new File(root + "/spa_bills_pdf");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Spa-" + n + ".pdf";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(bytes);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }


    public String createTempFile(Uri url) {
        ContentResolver contentResolver = context.getContentResolver();
        try {
            InputStream inputStream = contentResolver.openInputStream(url);
            File file = createImageUploadFileExtension(url);
            try (OutputStream output = new FileOutputStream(file)) {
                byte[] buffer = new byte[4 * 1024]; // or other buffer size
                int read;
                if (inputStream != null) {
                    while ((read = inputStream.read(buffer)) != -1) {
                        output.write(buffer, 0, read);
                    }
                }
                output.flush();
                return file.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public  byte[] getFileDataFromDrawable(Uri drawable) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),drawable);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] getBytesFromUri(File file) {

        byte[] bytesArray = new byte[(int) file.length()];

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            Log.e(AppConstants.ERROR_TAG,"FileNotFoundException: ",e);
            /*e.printStackTrace();*/
        }
        try {
            if (fis != null) {
                fis.read(bytesArray);
                fis.close();
            }
        } catch (IOException e) {
            Log.e(AppConstants.ERROR_TAG,"IOException: ",e);
//            e.printStackTrace();
        }

        return bytesArray;
    }

    public String getDuration(File file) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
        String durationStr = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        return durationStr;
    }
}

