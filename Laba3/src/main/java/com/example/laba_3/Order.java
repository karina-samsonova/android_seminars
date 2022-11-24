package com.example.laba_3;

import android.content.ContentValues;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "orders")
public class Order implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = "id")
    private int id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "stuff")
    private int stuff;

    @ColumnInfo(name = "customer")
    private int customer;

    @ColumnInfo(name = "status")
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStuff() {
        return stuff;
    }

    public void setStuff(int stuff) {
        this.stuff = stuff;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
