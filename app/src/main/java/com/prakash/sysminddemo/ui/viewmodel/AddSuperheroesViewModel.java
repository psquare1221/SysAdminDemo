package com.prakash.sysminddemo.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.prakash.sysminddemo.ui.listener.AddSuperheroesListener;

public class AddSuperheroesViewModel extends AndroidViewModel {

    public String productName,productDesc,regularPrice,salePrice;
    public AddSuperheroesListener addProductsListener ;

    public AddSuperheroesViewModel(@NonNull Application application) {
        super(application);
    }

    //to call from xml file
    public void onSaveButtonClick(View view){

    }

}
