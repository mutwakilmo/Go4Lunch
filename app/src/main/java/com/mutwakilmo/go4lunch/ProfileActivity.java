package com.mutwakilmo.go4lunch;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends BaseActivity {

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
    // --------------------
    @OnClick(R.id.profile_activity_button_update)
    public void onClickUpdateButton() { }

    @OnClick(R.id.profile_activity_button_sign_out)
    public void onClickSignOutButton() { }

    @OnClick(R.id.profile_activity_button_delete)
    public void onClickDeleteButton() { }
}
