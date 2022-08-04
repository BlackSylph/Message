package com.theroc.message.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.theroc.message.Database.DAO.ConversationMessageDAO;
import com.theroc.message.Database.DAO.CredentialsDAO;
import com.theroc.message.Database.DAO.MessageDAO;
import com.theroc.message.Database.Entities.ConversationMessage;
import com.theroc.message.Database.Entities.Credentials;
import com.theroc.message.Database.Entities.Message;
import com.theroc.message.Database.Entities.TrueMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Credentials.class, Message.class, ConversationMessage.class}, version = 1, exportSchema = false)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {

    public abstract CredentialsDAO credentialsDAO();
    public abstract MessageDAO messageDAO();
    public abstract ConversationMessageDAO conversationMessageDAO();

    private static volatile RoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static RoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabase.class, "database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {

                //Conversation
                ConversationMessageDAO conversationMessageDAO = INSTANCE.conversationMessageDAO();
                List<ConversationMessage> conversationMessages = new ArrayList<>();
                String sender;
                for (int i = 1; i < 17; i++) {
                    if (i < 10) {
                        sender = "13033";
                    } else if (i < 11) {
                        sender = "COSMOTE";
                    } else {
                        sender = "Γιώργος";
                    }
                    ConversationMessage conversationMessage = new ConversationMessage(sender, i);
                    conversationMessages.add(conversationMessage);
                }
                conversationMessageDAO.insertAll(conversationMessages);

                //Message
                MessageDAO messageDAO = INSTANCE.messageDAO();
                List<Message> messages = new ArrayList<>();
                TrueMessage trueMessage = new TrueMessage()
                for (int i = 1; i < 9; i++) {
                    Message message = new Message(i, );
                }
            });
        }
    };
}
