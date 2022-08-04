package com.theroc.message.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theroc.message.Database.Entities.Message;
import com.theroc.message.R;

import java.util.List;

public class ConversationMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> messages;

    public ConversationMessageAdapter() {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == R.layout.item_message_send) {
            return new MessageSendViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
        } else {
            return new MessageReceiveViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message message = messages.get(position);

        if (holder instanceof MessageSendViewHolder) {

            if (messages != null) {
                ((MessageSendViewHolder) holder).bind(message);
            }
        } else {

            if (messages != null) {
                ((MessageReceiveViewHolder) holder).bind(message);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getSide() == 0) {
            return R.layout.item_message_receive;
        } else {
            return R.layout.item_message_send;
        }
    }

    @Override
    public int getItemCount() {

        if (messages != null)
            return messages.size();
        else {
            return 0;
        }
    }

    public void setMessages(List<Message> messages) {

        this.messages = messages;
        notifyDataSetChanged();
    }

    private static class MessageSendViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout messageSendLayout;
        private final TextView messageSendTimeTextView;
        private final TextView senderTextView;

        public MessageSendViewHolder(View itemView) {
            super(itemView);
            messageSendLayout = itemView.findViewById(R.id.messageSendLayout);
            messageSendTimeTextView = itemView.findViewById(R.id.messageSendTimeTextView);
            senderTextView = itemView.findViewById(R.id.messageSendTextTextView);
        }

        public void bind(Message message) {

            if (message != null) {
                messageSendTimeTextView.setText(message.getTime());
                senderTextView.setText(message.getText());
            } else {
                messageSendLayout.setVisibility(View.INVISIBLE);
            }
        }
    }

    private static class MessageReceiveViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout messageReceiveLayout;
        private final TextView receiverTextView;

        MessageReceiveViewHolder(View itemView) {
            super(itemView);
            messageReceiveLayout = itemView.findViewById(R.id.messageReceiveLayout);
            receiverTextView = itemView.findViewById(R.id.messageReceiveTextTextView);
        }

        public void bind(Message message) {

            if (message != null) {
                receiverTextView.setText(message.getText());
            } else {
                messageReceiveLayout.setVisibility(View.INVISIBLE);
            }
        }
    }
}
