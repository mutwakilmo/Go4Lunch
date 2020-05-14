package com.mutwakilmo.go4lunch;

import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    TextView[] dotsL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        //Initialize
        ButterKnife.bind(this);
        //call adapter
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        //for dots
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);

    }

    private void addDots(int position){
        dotsL = new TextView[4];
        dots.removeAllViews();

        for (int i= 0 ; i <dotsL.length; i++){
            dotsL[i] = new TextView(this);
            dotsL[i].setText(Html.fromHtml("&#8226"));
            dotsL[i].setTextSize(35);
            dots.addView(dotsL[i]);
        }

        if (dotsL.length > 0){
            dotsL[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }


    //view dots
    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
           addDots(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
