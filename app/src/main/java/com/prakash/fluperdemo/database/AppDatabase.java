package com.prakash.fluperdemo.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.prakash.fluperdemo.dao.ProductDetailsDao;
import com.prakash.fluperdemo.model.ProductDetails;

@Database(entities = {ProductDetails.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDetailsDao productsDao();
}
