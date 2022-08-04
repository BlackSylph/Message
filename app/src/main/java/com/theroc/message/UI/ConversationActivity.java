package com.theroc.message.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.theroc.message.Adapter.ConversationMessageAdapter;
import com.theroc.message.R;
import com.theroc.message.ViewModel.ConversationMessageViewModel;

public class ConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        // Bundle info
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String conversationSender = bundle.getString("conversationSender");

        TextView senderTextView = findViewById(R.id.messageSenderHeaderTextView);
        senderTextView.setText(conversationSender);

        ConversationMessageViewModel viewModel = new ViewModelProvider(this).get(ConversationMessageViewModel.class);
        viewModel.setConversationSender(conversationSender);

        RecyclerView recyclerView = findViewById(R.id.messageRecyclerView);
        ConversationMessageAdapter adapter = new ConversationMessageAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        viewModel.getMessages().observe(this, adapter::setMessages);

        //Back button
        ImageButton backImageButton = findViewById(R.id.messageBackImageButton);
        backImageButton.setOnClickListener((View v) -> openMainActivity());
    }

    private void openMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}