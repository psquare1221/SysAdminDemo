package com.prakash.sysminddemo.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.prakash.sysminddemo.model.SuperheroDetails;

import java.util.List;

@Dao
public interface SuperheroesDetailsDao {

    @Query("SELECT * FROM SuperheroDetails")
    List<SuperheroDetails> getAll();

    @Insert
    void insert(SuperheroDetails superheroDetails);

    @Delete
    void delete(SuperheroDetails superheroDetails);

    @Update
    void update(SuperheroDetails superheroDetails);

}
