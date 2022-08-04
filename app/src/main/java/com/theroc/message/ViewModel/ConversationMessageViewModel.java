package com.theroc.message.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.theroc.message.Database.DataRepository;
import com.theroc.message.Database.Entities.ConversationMessage;
import com.theroc.message.Database.Entities.Message;

import java.util.ArrayList;
import java.util.List;

public class ConversationMessageViewModel extends AndroidViewModel {

    private DataRepository mRepository;

    private MutableLiveData<String> mSender = new MutableLiveData<>();
    private LiveData<List<ConversationMessage>> mConversationMessages = Transformations.switchMap(mSender, sender -> mRepository.getConversationMessagesBySender(sender));
    private LiveData<List<Message>> mMessages = Transformations.switchMap(mConversationMessages, conversationMessages -> {
        List<Integer> idList = new ArrayList<>();
        for (ConversationMessage conversationMessage : conversationMessages) {
            idList.add(conversationMessage.getMessageId());
        }
        return mRepository.getMessagesById(idList);
    });

    public ConversationMessageViewModel(Application application) {
        super(application);
        mRepository = new DataRepository(application);
    }

    //Conversation sender
    public void setConversationSender(String sender) {
        mSender.setValue(sender);
    }

    //Message
    public LiveData<List<Message>> getMessages() {
        return mMessages;
    }

    public LiveData<Message> getMessage(Integer id) {
        return mRepository.getMessage(id);
    }

    public void addMessage(Message message) {
        mRepository.addMessage(message);
    }

    public void addMessages(List<Message> players) {
        mRepository.addMessages(players);
    }

    //ConversationMessage
    public LiveData<List<ConversationMessage>> getConversationMessagesByConversation() {
        return mConversationMessages;
    }

    public void addConversationMessage(ConversationMessage conversationMessage) {
        mRepository.addConversationMessage(conversationMessage);
    }

    public void addConversationMessages(List<ConversationMessage> conversationMessages) {
        mRepository.addConversationMessages(conversationMessages);
    }

    public void removeConversationMessage(ConversationMessage conversationMessage) {
        mRepository.removeConversationMessage(conversationMessage);
    }

    public void removeConversationMessages(List<ConversationMessage> conversationMessages) {
        for (ConversationMessage conversationMessage : conversationMessages) {
            mRepository.removeConversationMessage(conversationMessage);
        }
    }
}
