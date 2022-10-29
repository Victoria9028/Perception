package com.perception;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

// Mind Hack class
public class MindHackActivity extends BaseActivity {
    private ImageView imgBack;

    // On create set content view
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mindhack);
        setTitle("Psychological Mind Hacks");
        initUI();
        initClickListner();
    }

    // Initialize UI
    @Override
    public void initUI() {
        imgBack= (ImageView) findViewById(R.id.imgBack);
    }

    // Initialize on click listener
    @Override
    public void initClickListner() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressBackButton();
            }
        });
    }
}
