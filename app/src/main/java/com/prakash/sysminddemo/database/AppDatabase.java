package com.prakash.sysminddemo.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.prakash.sysminddemo.dao.SuperheroesDetailsDao;
import com.prakash.sysminddemo.model.SuperheroDetails;

@Database(entities = {SuperheroDetails.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SuperheroesDetailsDao superheroesDetailsDao();
}
