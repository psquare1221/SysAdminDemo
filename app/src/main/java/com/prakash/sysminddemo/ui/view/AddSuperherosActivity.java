package com.prakash.sysminddemo.ui.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.prakash.sysminddemo.R;
import com.prakash.sysminddemo.base.BaseActivity;
import com.prakash.sysminddemo.database.DatabaseClient;
import com.prakash.sysminddemo.databinding.ActivityAddCharactersBinding;
import com.prakash.sysminddemo.model.SuperheroDetails;
import com.prakash.sysminddemo.ui.listener.AddSuperheroesListener;
import com.prakash.sysminddemo.ui.viewmodel.AddSuperheroesViewModel;

import java.io.IOException;

public class AddSuperherosActivity extends BaseActivity implements AddSuperheroesListener {

    private ActivityAddCharactersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_characters);

        AddSuperheroesViewModel addSuperheroesViewModel = ViewModelProviders.of(this).get(AddSuperheroesViewModel.class);

        binding.setAddViewModel(addSuperheroesViewModel);

        binding.buttonSave.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_save, 0);
        addSuperheroesViewModel.addProductsListener = this;

        EnableRuntimePermission(AddSuperherosActivity.this);

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != uri) {
                    saveTask();
                } else {
                    Toast.makeText(AddSuperherosActivity.this, "Please Upload Superheroes Picture first....",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        binding.imageViewAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickImageFromCamera(AddSuperherosActivity.this);
            }
        });

    }

    private void saveTask() {
        final String sSuperheroName = binding.editTextCharacterName.getText().toString().trim();
        final String sSuperheroDesc = binding.editTextCharacterDescription.getText().toString().trim();
        final String sImageURI = uri.toString();

        Log.d("Prakash : ", "Image URI : " + sImageURI);
        if (sSuperheroName.isEmpty()) {
            binding.editTextCharacterName.setError("Superheroe's Name required");
            binding.editTextCharacterDescription.requestFocus();
            return;
        }

        if (sSuperheroDesc.isEmpty()) {
            binding.editTextCharacterDescription.setError("Superheroe's Description required");
            binding.editTextCharacterDescription.requestFocus();
            return;
        }


        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                SuperheroDetails superheroDetails = new SuperheroDetails();
                superheroDetails.setCharacter_name(sSuperheroName);
                superheroDetails.setCharacter_desc(sSuperheroDesc);
                superheroDetails.setPhoto_uri(sImageURI);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .superheroesDetailsDao()
                        .insert(superheroDetails);
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
                Bitmap bitmap = null;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        bitmap = extras.getParcelable("data");
                    }
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

        Toast.makeText(AddSuperherosActivity.this, "All the fields are Mandatory...!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessfulSave() {
        finish();
        startActivity(new Intent(getApplication(), MainActivity.class));
        Toast.makeText(getApplication(), "Saved", Toast.LENGTH_LONG).show();
    }
}
