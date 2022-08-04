package com.theroc.message.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.theroc.message.Database.Entities.Credentials;

import java.util.List;

@Dao
public interface CredentialsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Credentials credentials);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Credentials> credentials);

    @Update
    void update(Credentials credentials);

    @Delete
    void delete(Credentials credentials);

    @Query("SELECT * FROM credentials_table")
    LiveData<Credentials> getCredentials();

    @Query("DELETE FROM credentials_table")
    void deleteAll();
}
