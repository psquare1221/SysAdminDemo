package com.prakash.sysminddemo.ui.view;

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

import com.prakash.sysminddemo.R;
import com.prakash.sysminddemo.base.BaseActivity;
import com.prakash.sysminddemo.database.DatabaseClient;
import com.prakash.sysminddemo.databinding.ActivityDeleteTaskBinding;
import com.prakash.sysminddemo.model.SuperheroDetails;

public class UpdateTaskActivity extends BaseActivity {

    private ActivityDeleteTaskBinding activityUpdateTaskBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_task);

        activityUpdateTaskBinding = DataBindingUtil.setContentView(this, R.layout.activity_delete_task);

        final SuperheroDetails superheroDetails = (SuperheroDetails) getIntent().getSerializableExtra("superheroes");

        if (superheroDetails != null) {
            loadListOfProducts(superheroDetails);
        }

        activityUpdateTaskBinding.buttonDelete.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_delete, 0);

//        activityUpdateTaskBinding.imageViewAddProd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(getApplicationContext(), FullImageViewActivity.class);
//                intent.putExtra("superheroes", superheroDetails);
//                startActivityForResult(intent, 100);
//            }
//        });

        activityUpdateTaskBinding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTaskActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteTask(superheroDetails);
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

    private void loadListOfProducts(SuperheroDetails superheroDetails) {

        activityUpdateTaskBinding.editTextProductName.setText(superheroDetails.getCharacter_name());
        activityUpdateTaskBinding.editTextProductDescription.setText(superheroDetails.getCharacter_desc());

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),
                    Uri.parse(superheroDetails.getPhoto_uri()));
        } catch (Exception e) {
            //handle exception
        }

        activityUpdateTaskBinding.imageViewAddProd.setImageBitmap(bitmap);

    }

    private void deleteTask(final SuperheroDetails productDetails) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .superheroesDetailsDao()
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
