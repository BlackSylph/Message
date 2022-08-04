package com.theroc.message.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theroc.message.Database.Entities.ConversationMessage;
import com.theroc.message.Database.Entities.Message;
import com.theroc.message.Interface.ItemClickListener;
import com.theroc.message.R;

import java.util.List;

public class ConversationPreviewAdapter extends RecyclerView.Adapter<ConversationPreviewAdapter.MessagePreviewViewHolder> {

    private List<ConversationMessage> conversationMessages;
    private List<Message> messages;
    private ItemClickListener clickListener;

    public ConversationPreviewAdapter() {

    }

    @NonNull
    @Override
    public MessagePreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MessagePreviewViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_conversation_preview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessagePreviewViewHolder holder, int position) {

        if (conversationMessages != null) {
            ConversationMessage conversationMessage = conversationMessages.get(position);
            Message message = messages.get(position);

            //Set sender
            holder.conversationPreviewSenderTextView.setText(conversationMessage.getConversationSender());

            //Set time
            holder.conversationPreviewTimeTextView.setText(message.getTime());

            //Set text
            String messagePreview = message.getText();
            if (messagePreview.length() >= 30) {
                String messagePreviewTrim = messagePreview.substring(0, 27) + "...";
                holder.conversationPreviewTextView.setText(messagePreviewTrim);
            } else {
                holder.conversationPreviewTextView.setText(messagePreview);
            }
        } else {
            holder.conversationPreviewLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {

        if (conversationMessages != null)
            return conversationMessages.size();
        else {
            return 0;
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public void setConversationMessages(List<ConversationMessage> conversationMessages) {

        this.conversationMessages = conversationMessages;
        notifyDataSetChanged();
    }

    public void setMessages(List<Message> messages) {

        this.messages = messages;
        notifyDataSetChanged();
    }

    public class MessagePreviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RelativeLayout conversationPreviewLayout;
        private final TextView conversationPreviewTextView;
        private final TextView conversationPreviewSenderTextView;
        private final TextView conversationPreviewTimeTextView;

        MessagePreviewViewHolder(View itemView) {
            super(itemView);
            conversationPreviewLayout = itemView.findViewById(R.id.conversationPreviewLayout);
            conversationPreviewTextView = itemView.findViewById(R.id.conversationPreviewTextView);
            conversationPreviewSenderTextView = itemView.findViewById(R.id.conversationPreviewSenderTextView);
            conversationPreviewTimeTextView = itemView.findViewById(R.id.conversationPreviewTimeTextView);
            conversationPreviewLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onClick(view, getAdapterPosition());
            }
        }
    }
}
