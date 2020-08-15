package com.prakash.fluperdemo.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.prakash.fluperdemo.BuildConfig;
import com.prakash.fluperdemo.R;

import java.io.File;

public class BaseActivity extends AppCompatActivity {

    private Intent CamIntent, CropIntent;
    public static final int RequestPermissionCode = 1;
    private File file;
    public Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public void EnableRuntimePermission(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);
        }
    }

    public void ClickImageFromCamera(Activity activity) {
        CamIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(getApplicationContext().getExternalCacheDir(),
                String.valueOf(System.currentTimeMillis()) + ".jpg");
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(getApplicationContext(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    file);
        } else {
            uri = Uri.fromFile(file);
        }
        CamIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        CamIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        CamIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        CamIntent.putExtra("return-data", true);
        startActivityForResult(CamIntent, 0);

    }

    public void ImageCropFunction() {
        try {
            CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(uri, "image/*");
            CropIntent.putExtra("crop", true);
            CropIntent.putExtra("outputX", 180);
            CropIntent.putExtra("outputY", 180);
            CropIntent.putExtra("aspectX", 3);
            CropIntent.putExtra("aspectY", 4);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);
            CropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            CropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            CropIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(CropIntent, 1);


        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT).show();
        }
    }


}