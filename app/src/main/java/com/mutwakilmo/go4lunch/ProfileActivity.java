package com.mutwakilmo.go4lunch;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mutwakilmo.go4lunch.api.UserHelper;
import com.mutwakilmo.go4lunch.models.User;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends BaseActivity {
    // Creating identifier to identify REST REQUEST (Update username)
    public static final int UPDATE_USERNAME = 30;

    //Identify each HTTP Request
    public static final int SING_OUT_TASK = 10;
    public static final int DELETE_USER_TASK = 20;

    @BindView(R.id.profile_activity_imageview_profile)
    ImageView imageViewProfile;
    @BindView(R.id.profile_activity_edit_text_username)
    TextInputEditText textInputEditTextUsername;
    @BindView(R.id.profile_activity_text_view_email)
    TextView textViewEmail;
    @BindView(R.id.profile_activity_progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        //Update UI
        this.UpdateUIWhenCreating();

    }



    @Override
    public int getFragmentLayout() {
        return R.layout.activity_profile;
    }





    // --------------------
    // ACTIONS
    //Adding request to button listener
    // --------------------

    @OnClick(R.id.profile_activity_button_sign_out)
    public void onClickSignOutButton() {this.singOutUserFromFirebase(); }

    @OnClick(R.id.profile_activity_button_delete)
    public void onClickDeleteButton() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.popup_message_confirmation_delete_account)
                .setPositiveButton(R.string.popup_message_choice_yes, new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                deleteUserFromFirebase();
                            }
                        })
                .setNegativeButton(R.string.popup_message_choice_no, null)
                .show();
    }

    @OnClick(R.id.profile_activity_button_update)
    public void onClickUpdateButton() {this.updateUserNameInFirebase(); }

    //update userName
    private void updateUserNameInFirebase() {
        this.progressBar.setVisibility(View.VISIBLE);
        String username = this.textInputEditTextUsername.getText().toString();

        if (this.getCurrentUser() != null){
            if (username.isEmpty() && username.equals(getString(R.string.info_no_username_found))){
                UserHelper.updateUsername(username, this.getCurrentUser().getUid()).
                        addOnFailureListener(this.onFailureListener()).addOnSuccessListener
                        (this.updateUIAfterRESTRequestsCompleted(UPDATE_USERNAME));
            }
        }
    }


    // --------------------
    // REST REQUESTS
    // --------------------
    // 1 - Create http requests (SignOut & Delete)

    private void singOutUserFromFirebase(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(SING_OUT_TASK));
    }


    private void deleteUserFromFirebase(){
        if (this.getCurrentUser() != null){
            AuthUI.getInstance()
                    .delete(this)
                    .addOnSuccessListener(this,this.updateUIAfterRESTRequestsCompleted(DELETE_USER_TASK));
        }
    }


    // --------------------
    // UI
    // --------------------

    private void UpdateUIWhenCreating() {
       if (this.getCurrentUser() != null){
           if (this.getCurrentUser().getPhotoUrl() != null){
               Glide.with(this)
                       .load(this.getCurrentUser().getPhotoUrl())
                       .apply(RequestOptions.circleCropTransform())
                       .into(imageViewProfile);
           }

           String email = TextUtils.isEmpty(this.getCurrentUser().getEmail()) ?
                   getString(R.string.info_no_email_found) : this.getCurrentUser().getEmail();
           this.textViewEmail.setText(email);

           //Get additional data from FireStore (UserName)
           UserHelper.getUser(this.getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
               @Override
               public void onSuccess(DocumentSnapshot documentSnapshot) {
                   User currentUser = documentSnapshot.toObject(User.class);
                   String username = TextUtils.isEmpty(currentUser.getUsername()) ?
                           getString(R.string.info_no_username_found) : currentUser.getUsername();
                   textInputEditTextUsername.setText(username);
               }
           });
       }
    }



    //Create OnCompleteListener called after tasks ended
    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(final int origin){
        return new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                switch (origin){
                    case UPDATE_USERNAME:
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                    case SING_OUT_TASK:
                    finish();
                    break;
                    case DELETE_USER_TASK:
                        finish();
                        break;
                    default:
                        break;
                }
            }
        };
    }
}
