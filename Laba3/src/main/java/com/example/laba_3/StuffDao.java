package com.example.laba_3;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StuffDao {

    @Query("SELECT * FROM stuff")
    List<Stuff> getAll();

    @Insert
    void insert(Stuff stuff);

    @Delete
    void delete(Stuff stuff);

    @Update
    void update(Stuff stuff);

    @Query("SELECT * FROM stuff WHERE stock=:str")
    List<Stuff> getByStock(String str);

    @Query("SELECT * FROM stuff WHERE name=:str")
    List<Stuff> getByName(String str);
}
