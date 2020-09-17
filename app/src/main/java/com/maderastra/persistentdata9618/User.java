package com.maderastra.persistentdata9618;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "full_name")
    public String fullName;

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getFullName() {return fullName;}

    public void setFullName(String fullName) {this.fullName = fullName;}

}
