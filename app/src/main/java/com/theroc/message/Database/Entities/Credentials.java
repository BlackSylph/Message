package com.theroc.message.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "credentials_table")
public class Credentials implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "_id")
    private Integer _id;

    @NonNull
    @ColumnInfo(name = "first_name")
    private String firstName;

    @NonNull
    @ColumnInfo(name = "last_name")
    private String lastName;

    @NonNull
    @ColumnInfo(name = "reason")
    private String reason;

    @NonNull
    @ColumnInfo(name = "address")
    private String address;

    @NonNull
    @ColumnInfo(name = "time")
    private Integer time;

    @Ignore
    public Credentials() {
    }

    public Credentials(@NonNull Integer _id, @NonNull String firstName, @NonNull String lastName, @NonNull String reason, @NonNull String address, @NonNull Integer time) {
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.reason = reason;
        this.address = address;
        this.time = time;
    }

    @NonNull
    public Integer get_id() {
        return _id;
    }

    public void set_id(@NonNull Integer _id) {
        this._id = _id;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public String getReason() {
        return reason;
    }

    public void setReason(@NonNull String reason) {
        this.reason = reason;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @NonNull
    public Integer getTime() {
        return time;
    }

    public void setTime(@NonNull Integer time) {
        this.time = time;
    }
}
