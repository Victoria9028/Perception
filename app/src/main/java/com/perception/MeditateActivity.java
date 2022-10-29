package com.perception;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.concurrent.TimeUnit;

// Meditate class
public class MeditateActivity extends BaseActivity {
    private ImageView imgBack;
    private Button btnStart;
    public int counter;

    // On create set content view
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meditate);
        setTitle("Meditate");
        initUI();
        initClickListner();
    }

    // Initialize UI
    @Override
    public void initUI() {
        btnStart = (Button) findViewById(R.id.btnStart);
        imgBack= (ImageView) findViewById(R.id.imgBack);
    }

    // Initialize on click listener for back button and start button. If start button pressed
    // play music and start countdown timer from 5 minutes
    @Override
    public void initClickListner() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressBackButton();
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeditateActivity.this, BackgroundSoundService.class);
                startService(intent);

                CountDownTimer  countDownTimer = new CountDownTimer(300000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        long millis = millisUntilFinished;
                        //Convert milliseconds into hour,minute and seconds
                        String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                        btnStart.setText(hms);//set text
                    }
                    public void onFinish() {
                        btnStart.setText("TIME'S UP!!"); //On finish change timer text

                        Intent myService = new Intent(MeditateActivity.this, BackgroundSoundService.class);
                        stopService(myService);
                    }
                }.start();

            }
        });
    }

    // When activity finishes, stop service
    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent myService = new Intent(MeditateActivity.this, BackgroundSoundService.class);
        stopService(myService);
    }
}


