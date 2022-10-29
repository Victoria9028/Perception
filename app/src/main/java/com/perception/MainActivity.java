package com.perception;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

public class MainActivity extends BaseActivity {
    private ImageButton imbDiary;
    private ImageButton imbInspiration;
    private ImageButton imbSeftyPlan;
    private ImageButton imbGoodDeed;
    private ImageButton imbMeditation;
    private ImageButton imbGratitude;
    private ImageButton imbAccount;
    private ImageButton imbMindHack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initUI();
        initClickListner();
    }

    @Override
    public void initUI() {
        imbDiary = (ImageButton) findViewById(R.id.imbDiary);
        imbInspiration = (ImageButton) findViewById(R.id.imbInspiration);
        imbSeftyPlan = (ImageButton) findViewById(R.id.imbSeftyPlan);
        imbGoodDeed = (ImageButton) findViewById(R.id.imbGoodDeed);
        imbMeditation = (ImageButton) findViewById(R.id.imbMeditation);
        imbGratitude = (ImageButton) findViewById(R.id.imbGratitude);
        imbAccount = (ImageButton) findViewById(R.id.imbAccount);
        imbMindHack = (ImageButton) findViewById(R.id.imbMindHack);
    }

    @Override
    public void initClickListner() {

        imbDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DiaryActivity.class);
                startActivity(intent);
            }
        });
        imbInspiration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InspirationActivity.class);
                startActivity(intent);
            }
        });
        imbSeftyPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SeftyPlanActivity.class);
                startActivity(intent);
            }
        });
        imbGoodDeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, GoodDeedsActivity.class);
                startActivity(intent);
            }
        });
        imbMeditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MeditateActivity.class);
                startActivity(intent);
            }
        });
        imbGratitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GratitudeActivity.class);
                startActivity(intent);
            }
        });
        imbAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
        imbMindHack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, MindHackActivity.class);
                startActivity(intent);
            }
        });
    }
}

