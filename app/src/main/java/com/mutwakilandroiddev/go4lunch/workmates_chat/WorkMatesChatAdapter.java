package com.mutwakilandroiddev.go4lunch.workmates_chat;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.RequestManager;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.mutwakilandroiddev.go4lunch.R;
import com.mutwakilandroiddev.go4lunch.api.Message;

public class WorkMatesChatAdapter extends FirestoreRecyclerAdapter<Message, MessageViewHolder> {

    //--------------------------------------------------------------------------------------------
    //Explanations: We have created an Adapter here that looks fairly simple at first. However,
    //it inherits from FirestoreRecyclerAdapter, an object available in the library FirebaseUI.
    //--------------------------------------------------------------------------------------------
    public interface Listener {
        void onDataChanged();
    }

    //For DATA
    private final RequestManager glide;
    private final String idCurrentUser;

    //For COMMUNICATION
    private Listener callback;

    public WorkMatesChatAdapter(@NonNull FirestoreRecyclerOptions<Message> options, RequestManager glide, Listener callback, String idCurrentUser) {
        super(options);
        this.glide = glide;
        this.callback = callback;
        this.idCurrentUser = idCurrentUser;
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
