package com.mutwakilandroiddev.go4lunch.workmates_chat;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.mutwakilandroiddev.go4lunch.R;
import com.mutwakilandroiddev.go4lunch.api.MessageHelper;
import com.mutwakilandroiddev.go4lunch.api.UserHelper;
import com.mutwakilandroiddev.go4lunch.base.BaseActivity;
import com.mutwakilandroiddev.go4lunch.models.Message;
import com.mutwakilandroiddev.go4lunch.models.User;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ChatActivity extends BaseActivity implements WorkMatesChatAdapter.Listener {

    // Class name for Log tag
    public static final String TAG_LOG_CHAT = ChatActivity.class.getSimpleName();


    // FOR DESIGN
    @BindView(R.id.activity_chat_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.activity_chat_text_view_recycler_view_empty) TextView textViewRecyclerViewEmpty;
    @BindView(R.id.activity_chat_message_edit_text) TextInputEditText editTextMessage;
    @BindView(R.id.activity_chat_image_chosen_preview) ImageView imageViewPreview;

    // FOR DATA
    private WorkMatesChatAdapter workMatesChatAdapter;
    @Nullable private User modelCurrentUser;
    private String currentChatName;
    private Uri uriImageSelected;

    // STATIC DATA FOR CHAT
    private static final String CHAT_NAME_LUNCH = "lunch";
    private static final String CHAT_NAME_WORK = "work";
    private static final String CHAT_NAME_FUN = "fun";

    // STATIC DATA FOR PICTURE
    private static final String PERMS = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int RC_IMAGE_PERMS = 100;
    private static final int RC_CHOOSE_PHOTO = 200;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.configureRecyclerView(CHAT_NAME_LUNCH);
        this.getCurrentUserFromFirestore();
        //this.configureToolbar();

    }


    @Override
    public int getFragmentLayout() {
        return R.layout.activity_chat;
    }


    // --------------------
    // ACTIONS
    // --------------------



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //results for EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @OnClick(R.id.activity_chat_send_button)
    public void onClickSendMessage() {
        // 1 - Check if text field is not empty and current user properly downloaded from Firestore
        if (!TextUtils.isEmpty(editTextMessage.getText()) && modelCurrentUser != null){
            // 2 - Create a new Message to Fires tore
            MessageHelper.createMessageForChat(editTextMessage.getText().toString(), this.currentChatName, modelCurrentUser).addOnFailureListener(this.onFailureListener());
            // 3 - Reset text field
            this.editTextMessage.setText("");
        }
    }

    @OnClick({ R.id.activity_chat_lunch_chat_button, R.id.activity_chat_fun_chat_button, R.id.activity_chat_work_chat_button})
    public void onClickChatButtons(TextView textView) {
        // 8 - Re-Configure the RecyclerView depending chosen chat
        switch (Integer.valueOf(textView.getTag().toString())){
            case 10:
                this.configureRecyclerView(CHAT_NAME_LUNCH);
                break;
            case 20:
                this.configureRecyclerView(CHAT_NAME_FUN);
                break;
            case 30:
                this.configureRecyclerView(CHAT_NAME_WORK);
                break;
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Calling the appropriate method after activity result
        this.handleResponse(requestCode, resultCode, data);
    }



    @OnClick(R.id.activity_chat_add_file_button)
    @AfterPermissionGranted(RC_IMAGE_PERMS)
    public void onClickAddFile() { this.chooseImageFromPhone(); }



    private void chooseImageFromPhone() {
        if (!EasyPermissions.hasPermissions(this, PERMS)){
            EasyPermissions.requestPermissions(this,
                    getString(R.string.popup_title_permission_files_access),RC_IMAGE_PERMS,PERMS);
            return;
        }
        //Launch an "Selection Image" Activity
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RC_CHOOSE_PHOTO);
    }

    //  Handle activity response (after user has chosen or not a picture)

    private void handleResponse(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_CHOOSE_PHOTO){
            if (requestCode ==RC_CHOOSE_PHOTO){
                this.uriImageSelected = data.getData();
                Glide.with(this)
                        .load(this.uriImageSelected)
                        .apply(RequestOptions.centerCropTransform())
                        .into(this.imageViewPreview);
            } else {
                Toast.makeText(this, getString(R.string.toast_title_no_image_chosen),Toast.LENGTH_SHORT).show();
            }
        }
    }




    // --------------------
    // REST REQUESTS
    // --------------------
    // 4 - Get Current User from Firestore
    private void getCurrentUserFromFirestore(){
        UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> modelCurrentUser = documentSnapshot.toObject(User.class));
    }



    // --------------------
    // REST REQUESTS
    // --------------------

    private void configureRecyclerView(String chatName){
        //Track current chat name
        this.currentChatName = chatName;
        //Configure Adapter & RecyclerView
        this.workMatesChatAdapter = new WorkMatesChatAdapter(generateOptionsForAdapter(MessageHelper.getAllMessageForChat(this.currentChatName)), Glide.with(this), this, this.getCurrentUser().getUid());
        workMatesChatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                recyclerView.smoothScrollToPosition(workMatesChatAdapter.getItemCount()); // Scroll to bottom on new messages
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.workMatesChatAdapter);
    }

    //---Create options for RecyclerView from a Query
    private FirestoreRecyclerOptions<Message> generateOptionsForAdapter(Query query){
        return new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .setLifecycleOwner(this)
                .build();

    }




    @Override
    public void onDataChanged() {
      textViewRecyclerViewEmpty.setVisibility(this.workMatesChatAdapter.getItemCount() == 0 ?
              View.VISIBLE : View.GONE);
    }


}