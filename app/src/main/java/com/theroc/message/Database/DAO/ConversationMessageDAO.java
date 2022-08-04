package com.theroc.message.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.theroc.message.Database.Entities.ConversationMessage;

import java.util.List;

@Dao
public interface ConversationMessageDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ConversationMessage conversationMessage);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ConversationMessage> conversationMessage);

    @Update
    void update(ConversationMessage conversationMessage);

    @Delete
    void delete(ConversationMessage conversationMessage);

    @Query("SELECT * FROM conversationMessage_table")
    LiveData<List<ConversationMessage>> getAllConversationMessages();

    @Query("SELECT * FROM conversationMessage_table where conversation_sender IN (:senderList)")
    LiveData<List<ConversationMessage>> getConversationMessages(List<String> senderList);

    @Query("SELECT * FROM conversationMessage_table WHERE conversation_sender = :conversationSender AND message_id = :messageId")
    LiveData<ConversationMessage> getConversationMessage(String conversationSender, Integer messageId);

    @Query("SELECT * FROM conversationMessage_table WHERE conversation_sender = :conversationSender")
    LiveData<List<ConversationMessage>> getConversationMessagesBySender(String conversationSender);

    @Query("DELETE FROM conversationMessage_table")
    void deleteAll();
}
