package com.example.laba_3;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM orders")
    List<Order> getAll();

    @Query(
            "SELECT orders.date, stuff.name, stuff.price, orders.status FROM orders " +
            "INNER JOIN stuff ON stuff.id=orders.stuff WHERE orders.customer=:ID"
    )
    List<History> getHistory(int ID);

    @Insert
    void insert(Order orders);

    @Delete
    void delete(Order orders);

    @Update
    void update(Order orders);

    @Query("SELECT * FROM orders WHERE status=:str")
    List<Order> getByStatus(String str);

    @Query("SELECT * FROM orders WHERE stuff=:str")
    List<Order> getByStuff(int str);

    @Query("SELECT * FROM orders WHERE customer=:str")
    List<Order> getByCustomer(int str);
}