package com.theroc.message.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.io.Serializable;

@Entity(tableName = "conversationMessage_table", primaryKeys = {"conversation_sender", "message_id"})
public class ConversationMessage implements Serializable {

    @NonNull
    @ColumnInfo(name = "conversation_sender")
    private String conversationSender;

    @NonNull
    @ColumnInfo(name = "message_id")
    private Integer messageId;

    @Ignore
    public ConversationMessage() {
    }

    public ConversationMessage(@NonNull String conversationSender, @NonNull Integer messageId) {
        this.conversationSender = conversationSender;
        this.messageId = messageId;
    }

    @NonNull
    public String getConversationSender() {
        return conversationSender;
    }

    public void setConversationSender(@NonNull String conversationSender) {
        this.conversationSender = conversationSender;
    }

    @NonNull
    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(@NonNull Integer messageId) {
        this.messageId = messageId;
    }
}
