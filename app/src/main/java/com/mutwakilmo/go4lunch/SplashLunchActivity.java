package com.mutwakilmo.go4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashLunchActivity extends AppCompatActivity {


    public static final String LOG_TAG_SPLASH  = SplashLunchActivity.class.getSimpleName();

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

        //Animation Hooks
        sideAnim = AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        //Set Animation
        backgroundImage.setAnimation(sideAnim);
        LaunchNext();
    }



    private void LaunchNext(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashLunchActivity.this, OnBoardingActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMER);
    }
}
