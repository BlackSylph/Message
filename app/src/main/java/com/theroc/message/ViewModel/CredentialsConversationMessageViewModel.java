package com.theroc.message.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.theroc.message.Database.DataRepository;
import com.theroc.message.Database.Entities.ConversationMessage;
import com.theroc.message.Database.Entities.Credentials;
import com.theroc.message.Database.Entities.Message;

import java.util.ArrayList;
import java.util.List;

public class CredentialsConversationMessageViewModel extends AndroidViewModel {

    private DataRepository mRepository;
    private LiveData<List<ConversationMessage>> mAllConversationMessages;
    private LiveData<List<ConversationMessage>> mConversationMessages = Transformations.switchMap(mAllConversationMessages, conversationMessages -> {
        List<ConversationMessage> conversationMessageList = new ArrayList<>();
        for (ConversationMessage conversationMessage : conversationMessages) {
            if (!conversationMessageList.contains(conversationMessage.getConversationSender())) {
                conversationMessageList.add(conversationMessage);
            }
        }
        List<String> senderList = new ArrayList<>();
        for (ConversationMessage conversationMessage : conversationMessageList) {
            senderList.add(conversationMessage.getConversationSender());
        }
        return mRepository.getConversationMessages(senderList);
    });
    private LiveData<List<Message>> mMessages = Transformations.switchMap(mConversationMessages, conversationMessages -> {
        List<Integer> idList = new ArrayList<>();
        for (ConversationMessage conversationMessage : conversationMessages) {
            idList.add(conversationMessage.getMessageId());
        }
        return mRepository.getMessagesById(idList);
    });

    public CredentialsConversationMessageViewModel(Application application) {
        super(application);
        mRepository = new DataRepository(application);
        mAllConversationMessages = mRepository.getAllConversationMessages();
    }

    //Credentials
    public LiveData<Credentials> getCredentials() {
        return mRepository.getCredentials();
    }

    //ConversationMessage
    public LiveData<List<ConversationMessage>> getConversationMessages() {
        return mConversationMessages;
    }

    //Messages
    public LiveData<List<Message>> getMessages() {
        return mMessages;
    }
}
