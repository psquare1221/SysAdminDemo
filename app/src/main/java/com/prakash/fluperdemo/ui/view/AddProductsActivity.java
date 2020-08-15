package com.prakash.fluperdemo.ui.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.prakash.fluperdemo.R;
import com.prakash.fluperdemo.base.BaseActivity;
import com.prakash.fluperdemo.database.DatabaseClient;
import com.prakash.fluperdemo.databinding.ActivityAddProductsBinding;
import com.prakash.fluperdemo.model.ProductDetails;
import com.prakash.fluperdemo.ui.listener.AddProductsListener;
import com.prakash.fluperdemo.ui.viewmodel.AddProductsViewModel;

import java.io.IOException;

public class AddProductsActivity extends BaseActivity implements AddProductsListener {

    private ActivityAddProductsBinding binding;
    private AddProductsViewModel addProductsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_products);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_products);

        addProductsViewModel = ViewModelProviders.of(this).get(AddProductsViewModel.class);

        binding.setAddViewModel(addProductsViewModel);

        binding.buttonSave.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.add_products, 0);
        addProductsViewModel.addProductsListener = this;

        EnableRuntimePermission(AddProductsActivity.this);

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != uri) {
                    saveTask();
                } else {
                    Toast.makeText(AddProductsActivity.this, "Please Click Product's Picture first....",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        binding.imageViewAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickImageFromCamera(AddProductsActivity.this);
            }
        });

    }

    private void saveTask() {
        final String sProdName = binding.editTextProductName.getText().toString().trim();
        final String sProdDesc = binding.editTextProductDescription.getText().toString().trim();
        final String sRegularPrice = binding.editTextRegularPrice.getText().toString().trim();
        final String sSalePrice = binding.editTextSalePrice.getText().toString().trim();
        final String sImageURI = uri.toString();

        if (sProdName.isEmpty()) {
            binding.editTextProductName.setError("Product Name required");
            binding.editTextProductName.requestFocus();
            return;
        }

        if (sProdDesc.isEmpty()) {
            binding.editTextProductDescription.setError("Product Description required");
            binding.editTextProductDescription.requestFocus();
            return;
        }

        if (sRegularPrice.isEmpty()) {
            binding.editTextRegularPrice.setError("Product's Regular required");
            binding.editTextRegularPrice.requestFocus();
            return;
        }
        if (sSalePrice.isEmpty()) {
            binding.editTextSalePrice.setError("Product's Sale required");
            binding.editTextSalePrice.requestFocus();
            return;
        }
        if (Double.parseDouble(sSalePrice)>=Double.parseDouble(sRegularPrice) ){
            Toast.makeText(AddProductsActivity.this, "Sale Price should be less than Regular Price....!", Toast.LENGTH_LONG).show();
            return;
        }

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                ProductDetails productDetails = new ProductDetails();
                productDetails.setProd_name(sProdName);
                productDetails.setProd_desc(sProdDesc);
                productDetails.setRegular_price(sRegularPrice);
                productDetails.setSale_price(sSalePrice);
                productDetails.setPhoto_uri(sImageURI);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .productsDao()
                        .insert(productDetails);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
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
                    binding.imageViewAddProd.setImageBitmap(bitmap);
                } else {

                    uri = data.getData();

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        binding.imageViewAddProd.setImageBitmap(bitmap);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void showError() {

        Toast.makeText(AddProductsActivity.this,"All the fields are Mandatory...!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessfulSave() {
        finish();
        startActivity(new Intent(getApplication(), MainActivity.class));
        Toast.makeText(getApplication(), "Saved", Toast.LENGTH_LONG).show();
    }
}
