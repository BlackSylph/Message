package com.theroc.message.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "message_table")
public class Message implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "_id")
    private Integer _id;

    @NonNull
    @ColumnInfo(name = "text")
    private String text;

    @NonNull
    @ColumnInfo(name = "time")
    private String time;

    @NonNull
    @ColumnInfo(name = "side")
    private Integer side;

    @Ignore
    public Message() {
    }

    @Ignore
    public Message(@NonNull String text, @NonNull String time, @NonNull Integer side) {
        this.text = text;
        this.time = time;
        this.side = side;
    }

    public Message(@NonNull Integer _id, @NonNull String text, @NonNull String time, @NonNull Integer side) {
        this._id = _id;
        this.text = text;
        this.time = time;
        this.side = side;
    }

    @NonNull
    public Integer get_id() {
        return _id;
    }

    public void set_id(@NonNull Integer _id) {
        this._id = _id;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    public void setTime(@NonNull String time) {
        this.time = time;
    }

    @NonNull
    public Integer getSide() {
        return side;
    }

    public void setSide(@NonNull Integer side) {
        this.side = side;
    }

    @NonNull
    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", time='" + time + '\'' +
                ", side=" + side +
                '}';
    }
}
