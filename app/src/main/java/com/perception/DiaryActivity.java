package com.perception;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.perception.utility.Constant;

public class DiaryActivity extends BaseActivity {
    private ImageView imgBack;


    private ImageButton imbAddEntry;
    private ImageButton imbViewEntry;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary);
        setTitle("Diary");
        initUI();
        initClickListner();
    }

    @Override
    public void initUI() {

        imgBack= (ImageView) findViewById(R.id.imgBack);
        imbAddEntry = (ImageButton) findViewById( R.id.imbAddEntry );
        imbViewEntry = (ImageButton)findViewById( R.id.imbViewEntry );
    }

    @Override
    public void initClickListner() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressBackButton();
            }
        });

        imbAddEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(DiaryActivity.this,AddDiaryActivity.class);
                intent.putExtra(Constant.FLAG,Constant.FLAG_ADD);
                startActivity(intent);
            }
        });

        imbViewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DiaryActivity.this,DiaryViewActivity.class);
                startActivity(intent);
            }
        });

    }
}



