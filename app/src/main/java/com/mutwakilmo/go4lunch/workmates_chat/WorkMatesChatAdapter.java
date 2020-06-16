package com.mutwakilmo.go4lunch.workmates_chat;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.RequestManager;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.mutwakilmo.go4lunch.R;
import com.mutwakilmo.go4lunch.models.Message;

public class WorkMatesChatAdapter extends FirestoreRecyclerAdapter<Message, MessageViewHolder> {


    public interface Listener{
        void onDataChanged();
    }

    //For DATA
    private final RequestManager glide;
    private final String idCurrentUser;

    //For COMMUNICATION
    private Listener callback;

    public WorkMatesChatAdapter(@NonNull FirestoreRecyclerOptions<Message> options, RequestManager glide, String idCurrentUser, Listener callback) {
        super(options);
        this.glide = glide;
        this.idCurrentUser = idCurrentUser;
        this.callback = callback;
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull Message model) {
        holder.updateWithMessage(model, this.idCurrentUser, this.glide);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_workmates_chat_item, parent, false));
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        this.callback.onDataChanged();
    }
}
