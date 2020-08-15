package com.prakash.fluperdemo.ui.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.prakash.fluperdemo.database.DatabaseClient;
import com.prakash.fluperdemo.model.ProductDetails;
import com.prakash.fluperdemo.ui.listener.AddProductsListener;
import com.prakash.fluperdemo.ui.view.MainActivity;

public class AddProductsViewModel extends AndroidViewModel {

    public String productName,productDesc,regularPrice,salePrice;
    public AddProductsListener addProductsListener ;

    public AddProductsViewModel(@NonNull Application application) {
        super(application);
    }

    //to call from xml file
    public void onSaveButtonClick(View view){

    }

}
