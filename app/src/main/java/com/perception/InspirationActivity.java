package com.perception;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class InspirationActivity extends BaseActivity {

    ImageView imgBack;

    private ImageView imgQuotes;
    private ImageView imgPeoples;
    private ImageView imgMindfulnessRelaxation;
    private ImageView imgMusic;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inspiration);
        setTitle("Inspiration");
        initUI();
        initClickListner();
    }

    @Override
    public void initUI() {

        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgQuotes = (ImageView)findViewById( R.id.imgQuotes );
        imgPeoples = (ImageView)findViewById( R.id.imgPeoples );
        imgMindfulnessRelaxation = (ImageView)findViewById( R.id.imgMindfulnessRelaxation );
        imgMusic = (ImageView)findViewById( R.id.imgMusic );
    }

    @Override
    public void initClickListner() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressBackButton();
            }
        });

        imgQuotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchYoutubeVideo("r6Y7Nbu9LUs");
            }
        });
        imgPeoples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchYoutubeVideo("Oi36yMik6jo");
            }
        });
        imgMindfulnessRelaxation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchYoutubeVideo("qFZKK7K52uQ");
            }
        });
        imgMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchYoutubeVideo("u_izSzis3Mw");
            }
        });
    }

    public  void watchYoutubeVideo(String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}
