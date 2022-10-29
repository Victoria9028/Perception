package com.perception;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

// Good deed class
public class GoodDeedsActivity extends BaseActivity {
    private ImageView imgBack;

    // On create set content view
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.good_did);
        setTitle("Good Deeds");
        initUI();
        initClickListner();
    }

    // Initialize UI
    @Override
    public void initUI() {

        imgBack=(ImageView)findViewById(R.id.imgBack);
    }

    // Initialize On click listener for back button
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
