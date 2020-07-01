package com.mutwakilandroiddev.go4lunch;


import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.animation.Animation;

import android.view.animation.AnimationUtils;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;


import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashLunchActivity extends AppCompatActivity {


    public static final String LOG_TAG_SPLASH = SplashLunchActivity.class.getSimpleName();

    SharedPreferences onBoardingScreen;

    //Duration
    private final int SPLASH_TIMER = 5000;
    //Variables
    @BindView(R.id.background_image)
    ImageView backgroundImage;

    //Animation
    Animation sideAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_lunch);
        ButterKnife.bind(this);
        Log.d(LOG_TAG_SPLASH, "onCreate: splash screen");
        //Animation Hooks
        sideAnim = AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        //Set Animation
        backgroundImage.setAnimation(sideAnim);
        //Generate Hash-key for facebook sdk
     /*   try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/


        new Handler().postDelayed(new Runnable() {
            public void run() {
                onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
                boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);
                if (isFirstTime) {
                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), OnBoardingLunchActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, SPLASH_TIMER);


    }


}






