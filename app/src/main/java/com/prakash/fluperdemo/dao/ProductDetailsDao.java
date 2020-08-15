package com.prakash.fluperdemo.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.prakash.fluperdemo.model.ProductDetails;

import java.util.List;

@Dao
public interface ProductDetailsDao {

    @Query("SELECT * FROM ProductDetails")
    List<ProductDetails> getAll();

    @Insert
    void insert(ProductDetails productDetails);

    @Delete
    void delete(ProductDetails productDetails);

    @Update
    void update(ProductDetails productDetails);

}
