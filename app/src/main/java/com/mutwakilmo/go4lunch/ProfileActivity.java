package com.mutwakilmo.go4lunch;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends BaseActivity {


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
        this.UpdateWhenCreating();

    }



    @Override
    public int getFragmentLayout() {
        return R.layout.activity_profile;
    }


    // --------------------
    // UI
    // --------------------

    private void UpdateWhenCreating() {
        if (this.getCurrentUser() != null){
            Glide.with(this)
                    .load(this.getCurrentUser().getPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageViewProfile);
        }
        //Get email @ userName from Firebase
        String email = TextUtils.isEmpty(this.getCurrentUser().getEmail())?
                getString(R.string.info_no_email_found) : this.getCurrentUser().getEmail();

        String username = TextUtils.isEmpty(this.getCurrentUser().getDisplayName()) ?
                getString(R.string.info_no_username_found) : this.getCurrentUser().getDisplayName();

        //Update views with data
        this.textInputEditTextUsername.setText(username);
        this.textViewEmail.setText(email);
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
    public void onClickUpdateButton() { }


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

    //Create OnCompleteListener called after tasks ended
    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(final int origin){
        return new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                switch (origin){
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
