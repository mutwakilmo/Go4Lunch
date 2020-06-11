package com.mutwakilmo.go4lunch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.mutwakilmo.go4lunch.api.UserHelper;

import java.util.Arrays;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    //Getting login button
    @BindView(R.id.main_activity_button_login)
    Button buttonLogin;
    public static final String MAIN_TAG = MainActivity.class.getSimpleName();
    //For data
    // 1 - Identifier for Sing-In Activity
    public static final int RC_SIGN_IN = 101;

    //FOR DESIGN
    // 1 - Get Coordinator Layout

    @BindView(R.id.main_activity_coordinator_layout)
    CoordinatorLayout coordinatorLayout;


    @Override
    public int getFragmentLayout() {
        return R.layout.activity_main;
    }


    @OnClick(R.id.main_activity_button_login)
    public void onClickLoginButton() {
        //Start appropriate activity
        if (this.isCurrentUserLogged()) {
            this.mainScreenActivity();
        } else {
            this.startSignInActivity();
        }
    }

    // --------------------
    // REST REQUEST
    // --------------------
    private void createUserInFirestore(){
        if (this.getCurrentUser() != null){
            String urlPicture = (this.getCurrentUser().getPhotoUrl() != null) ?
                    this.getCurrentUser().getPhotoUrl().toString() : null;
            String uid = this.getCurrentUser().getUid();
            String username = this.getCurrentUser().getDisplayName();

            UserHelper.createUser(uid, username, urlPicture).addOnFailureListener(this.onFailureListener());
        }
    }


    // --------------------
    // NAVIGATION
    // --------------------

    //Launching
    private void mainScreenActivity() {
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
    }

//**********************************************************************************************
//    When the user clicks on the "Login" button managed by the method  onClickLoginButton()  (3),
//    we'll run the method  startSignInActivity()   (2). That method will launch a
//    login/registration activity automatically generated by the Firebase-UI library!
// **********************************************************************************************

    @OnClick(R.id.main_activity_button_login)
    public void onClickLoginButton(View view) {
        //3 launch sing-in activity when user clicked on login button
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.side_anim);
        view.setAnimation(animation);
        this.startSignInActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 5 - Update UI when activity is resuming
        this.updateUIWhenResuming();
    }


    // --------------------
    // NAVIGATION
    // --------------------

    // 2 - Launch Sign-In Activity
    private void startSignInActivity() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build(),
                                        new AuthUI.IdpConfig.EmailBuilder().build(),
                                        new AuthUI.IdpConfig.GitHubBuilder().build(),
                                        new AuthUI.IdpConfig.FacebookBuilder().build()))
                        //setIsSmartLockEnabled()in order to automatically offer the user a list of
                        // available emails to make it easier to register.
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.ic_logo_auth)
                        .build(),
                RC_SIGN_IN);
    }


    // --------------------
    // OnActivityResult
    // --------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Handle SingIn Activity response on activity result
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }


    // --------------------
    // UI
    // --------------------

    //show snack bar with a message
    private void showSnackBar(CoordinatorLayout coordinatorLayout, String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    private void updateUIWhenResuming(){
        this.buttonLogin.setText(this.isCurrentUserLogged() ?
                getString(R.string.button_login_text_logged) : getString(R.string.button_login_text_not_logged));
    }


    // --------------------
    // UTILS
    // --------------------

    //  Method that handles response after SignIn Activity close
    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data) {

        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) { // SUCCESS
                this.createUserInFirestore();

                showSnackBar(this.coordinatorLayout, getString(R.string.connection_succeed));
            } else { // ERRORS
                if (response == null) {
                    showSnackBar(this.coordinatorLayout, getString(R.string.error_authentication_canceled));
                } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackBar(this.coordinatorLayout, getString(R.string.error_no_internet));
                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackBar(this.coordinatorLayout, getString(R.string.error_unknown_error));
                }
            }
        }
    }


}




