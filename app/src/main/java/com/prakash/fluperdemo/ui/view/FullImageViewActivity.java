package com.prakash.fluperdemo.ui.view;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.prakash.fluperdemo.R;
import com.prakash.fluperdemo.base.BaseActivity;
import com.prakash.fluperdemo.databinding.ActivityFullImageViewBinding;
import com.prakash.fluperdemo.model.ProductDetails;

import java.io.IOException;

public class FullImageViewActivity extends BaseActivity {

    private ActivityFullImageViewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_full_image_view);

        final ProductDetails productDetails = (ProductDetails) getIntent().getSerializableExtra("products");

        Log.d("Prakash PhotoURI 1 : ",productDetails.getPhoto_uri());

        Bitmap bitmap = null;
        try
        {
            bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver() ,
                    Uri.parse(productDetails.getPhoto_uri()));
        }
        catch (Exception e)
        {
            //handle exception
        }

        binding.imgFullPic.setImageBitmap(bitmap);

//        binding.buttonUpdateImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ClickImageFromCamera(FullImageViewActivity.this);
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            ImageCropFunction();
        } else if (requestCode == 2) {
            if (data != null) {
                uri = data.getData();

                ImageCropFunction();
            }
        } else if (requestCode == 1) {
            if (data != null) {

                Bundle bundle = data.getExtras();
                Bitmap bitmap;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    Bundle extras = data.getExtras();
                    bitmap = extras.getParcelable("data");
//                    activityUpdateTaskBinding.imageViewAddProd.setImageBitmap(bitmap);
                } else {

                    uri = data.getData();

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//                        activityUpdateTaskBinding.imageViewAddProd.setImageBitmap(bitmap);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}