package com.example.laba_3;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Customer.class, Stuff.class, Order.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CustomerDao customerDao();
    public abstract StuffDao stuffDao();
    public abstract OrderDao orderDao();
}
