package com.theroc.message.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.theroc.message.Database.Entities.Message;

import java.util.List;

@Dao
public interface MessageDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Message message);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Message> message);

    @Update
    void update(Message message);

    @Delete
    void delete(Message message);

    @Query("SELECT * FROM message_table")
    LiveData<List<Message>> getMessages();

    @Query("SELECT * FROM message_table where _id IN (:idList)")
    LiveData<List<Message>> getMessagesById(List<Integer> idList);

    @Query("SELECT * FROM message_table WHERE _id = :id")
    LiveData<Message> getMessage(Integer id);

    @Query("DELETE FROM message_table")
    void deleteAll();
}
