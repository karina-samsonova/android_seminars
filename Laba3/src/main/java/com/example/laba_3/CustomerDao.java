package com.example.laba_3;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CustomerDao {

    @Query("SELECT * FROM customer")
    List<Customer> getAll();

    @Insert
    void insert(Customer customer);

    @Delete
    void delete(Customer customer);

    @Update
    void update(Customer customer);

    @Query("SELECT * FROM customer WHERE name=:str")
    List<Customer> getByName(String str);

    @Query("SELECT * FROM customer WHERE surname=:str")
    List<Customer> getBySurname(String str);
}
