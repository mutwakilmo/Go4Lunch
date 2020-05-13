package com.mutwakilmo.go4lunch;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnBoardingActivity extends AppCompatActivity {


    @BindView(R.id.slider)
    ViewPager viewPager;
    @BindView(R.id.get_started_btn)
    Button getStartedBtn;
    @BindView(R.id.dots)
    LinearLayout dots;
    @BindView(R.id.next_btn)
    Button nextBtn;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;


    SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        //Initialize
        ButterKnife.bind(this);
        //call adapter
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

    }


}
