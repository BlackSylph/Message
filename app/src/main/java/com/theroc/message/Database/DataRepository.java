package com.theroc.message.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.theroc.message.Database.DAO.ConversationMessageDAO;
import com.theroc.message.Database.DAO.CredentialsDAO;
import com.theroc.message.Database.DAO.MessageDAO;
import com.theroc.message.Database.Entities.ConversationMessage;
import com.theroc.message.Database.Entities.Credentials;
import com.theroc.message.Database.Entities.Message;

import java.util.List;

public class DataRepository {

    private static RoomDatabase mDatabase;

    private CredentialsDAO mCredentialsDAO;
    private MessageDAO mMessageDAO;
    private ConversationMessageDAO mConversationMessageDAO;

    public DataRepository(Application application) {
        mDatabase = RoomDatabase.getDatabase(application);
    }

    //Credentials
    public LiveData<Credentials> getCredentials() {
        return mDatabase.credentialsDAO().getCredentials();
    }

    public void addCredentials(Credentials credentials) {
        RoomDatabase.databaseWriteExecutor.execute(() -> mCredentialsDAO.insert(credentials));
    }

    public void addCredentialList(List<Credentials> credentials) {
        RoomDatabase.databaseWriteExecutor.execute(() -> mCredentialsDAO.insertAll(credentials));
    }

    public void removeCredentials(Credentials credentials) {
        RoomDatabase.databaseWriteExecutor.execute(() -> mCredentialsDAO.delete(credentials));
    }

    public void removeAllCredentials() {
        RoomDatabase.databaseWriteExecutor.execute(() -> mCredentialsDAO.deleteAll());
    }

    //Message
    public LiveData<List<Message>> getAllMessages() {
        return mDatabase.messageDAO().getMessages();
    }

    public LiveData<List<Message>> getMessagesById(final List<Integer> idList) {
        return mDatabase.messageDAO().getMessagesById(idList);
    }

    public LiveData<Message> getMessage(final Integer id) {
        return mDatabase.messageDAO().getMessage(id);
    }

    public void addMessage(Message message) {
        RoomDatabase.databaseWriteExecutor.execute(() -> mMessageDAO.insert(message));
    }

    public void addMessages(List<Message> messages) {
        RoomDatabase.databaseWriteExecutor.execute(() -> mMessageDAO.insertAll(messages));
    }

    public void removeMessage(Message message) {
        RoomDatabase.databaseWriteExecutor.execute(() -> mMessageDAO.delete(message));
    }

    public void removeAllMessages() {
        RoomDatabase.databaseWriteExecutor.execute(() -> mMessageDAO.deleteAll());
    }

    //ConversationMessage
    public LiveData<List<ConversationMessage>> getAllConversationMessages() {
        return mDatabase.conversationMessageDAO().getAllConversationMessages();
    }

    public LiveData<List<ConversationMessage>> getConversationMessages(List<String> senderList) {
        return mDatabase.conversationMessageDAO().getConversationMessages(senderList);
    }

    public LiveData<ConversationMessage> getConversationMessage(final String conversationSender, final Integer messageId) {
        return mDatabase.conversationMessageDAO().getConversationMessage(conversationSender, messageId);
    }

    public LiveData<List<ConversationMessage>> getConversationMessagesBySender(final String conversationSender) {
        return mDatabase.conversationMessageDAO().getConversationMessagesBySender(conversationSender);
    }

    public void addConversationMessage(ConversationMessage conversationMessage) {
        RoomDatabase.databaseWriteExecutor.execute(() -> mConversationMessageDAO.insert(conversationMessage));
    }

    public void addConversationMessages(List<ConversationMessage> conversationMessages) {
        RoomDatabase.databaseWriteExecutor.execute(() -> mConversationMessageDAO.insertAll(conversationMessages));
    }

    public void removeConversationMessage(ConversationMessage conversationMessage) {
        RoomDatabase.databaseWriteExecutor.execute(() -> mConversationMessageDAO.delete(conversationMessage));
    }

    public void removeAllConversationMessages() {
        RoomDatabase.databaseWriteExecutor.execute(() -> mConversationMessageDAO.deleteAll());
    }
}
