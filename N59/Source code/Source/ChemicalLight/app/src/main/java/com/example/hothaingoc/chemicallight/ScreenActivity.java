package com.example.hothaingoc.chemicallight;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ScreenActivity extends AppCompatActivity {
    LinearLayout L1,L2;
    Animation uptodown, downtoup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        initScreen();
    }

    private void initScreen() {

        L1 = (LinearLayout) findViewById(R.id.l1) ;
        L2 = (LinearLayout) findViewById(R.id.l2) ;
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        L1.setAnimation(uptodown);
        L2.setAnimation(downtoup);

        //auto change activity
        CountDownTimer countDownTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                //Change screen
                Intent intent = new Intent(ScreenActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();

            }
        };
        countDownTimer.start();
    }
}
