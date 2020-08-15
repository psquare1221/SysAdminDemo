package com.prakash.fluperdemo.ui.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.prakash.fluperdemo.R;
import com.prakash.fluperdemo.base.BaseActivity;
import com.prakash.fluperdemo.database.DatabaseClient;
import com.prakash.fluperdemo.databinding.ActivityUpdateTaskBinding;
import com.prakash.fluperdemo.model.ProductDetails;

public class UpdateTaskActivity extends BaseActivity {

    private ActivityUpdateTaskBinding activityUpdateTaskBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        activityUpdateTaskBinding = DataBindingUtil.setContentView(this, R.layout.activity_update_task);

        final ProductDetails productDetails = (ProductDetails) getIntent().getSerializableExtra("products");

        activityUpdateTaskBinding.buttonDelete.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete_products, 0);

        activityUpdateTaskBinding.buttonUpdate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.update_product, 0);

        loadListOfProducts(productDetails);

        activityUpdateTaskBinding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTask(productDetails);
            }
        });

        activityUpdateTaskBinding.imageViewAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), FullImageViewActivity.class);
                intent.putExtra("products", productDetails);
                startActivity(intent);
            }
        });


        activityUpdateTaskBinding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTaskActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteTask(productDetails);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });

    }

    private void loadListOfProducts(ProductDetails productDetails) {

        activityUpdateTaskBinding.editTextProductName.setText(productDetails.getProd_name());
        activityUpdateTaskBinding.editTextProductDescription.setText(productDetails.getProd_desc());
        activityUpdateTaskBinding.editTextRegularPrice.setText(productDetails.getRegular_price());
        activityUpdateTaskBinding.editTextSalePrice.setText(productDetails.getSale_price());
        Uri myUri = Uri.parse(productDetails.getPhoto_uri());

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

        activityUpdateTaskBinding.imageViewAddProd.setImageBitmap(bitmap);

    }

    private void updateTask(final ProductDetails productDetails) {
        final String sProdName = activityUpdateTaskBinding.editTextProductName.getText().toString().trim();
        final String sProdDesc = activityUpdateTaskBinding.editTextProductDescription.getText().toString().trim();
        final String sRegularPrice = activityUpdateTaskBinding.editTextRegularPrice.getText().toString().trim();
        final String sSalePrice = activityUpdateTaskBinding.editTextSalePrice.getText().toString().trim();

        if (sProdName.isEmpty()) {
            activityUpdateTaskBinding.editTextProductName.setError("Product Name required");
            activityUpdateTaskBinding.editTextProductName.requestFocus();
            return;
        }

        if (sProdDesc.isEmpty()) {
            activityUpdateTaskBinding.editTextProductDescription.setError("Product Description required");
            activityUpdateTaskBinding.editTextProductDescription.requestFocus();
            return;
        }

        if (sRegularPrice.isEmpty()) {
            activityUpdateTaskBinding.editTextRegularPrice.setError("Product's Regular required");
            activityUpdateTaskBinding.editTextRegularPrice.requestFocus();
            return;
        }
        if (sSalePrice.isEmpty()) {
            activityUpdateTaskBinding.editTextSalePrice.setError("Product's Sale required");
            activityUpdateTaskBinding.editTextSalePrice.requestFocus();
            return;
        }

        class UpdateTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                productDetails.setProd_name(sProdName);
                productDetails.setProd_desc(sProdDesc);
                productDetails.setRegular_price(sRegularPrice);
                productDetails.setSale_price(sSalePrice);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .productsDao()
                        .update(productDetails);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateTaskActivity.this, MainActivity.class));
            }
        }

        UpdateTask ut = new UpdateTask();
        ut.execute();
    }

    private void deleteTask(final ProductDetails productDetails) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .productsDao()
                        .delete(productDetails);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateTaskActivity.this, MainActivity.class));
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();

    }

}
