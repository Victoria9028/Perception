package com.perception;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class SeftyPlanActivity extends BaseActivity {

    ImageView imgBack;

    private ImageButton imbTrigger;
    private ImageButton imbSafetyNetwork;
    private ImageButton imbCopingStategies;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sefty_plan);
        setTitle("Safety Plan");
        initUI();
        initClickListner();
    }

    @Override
    public void initUI() {

        imgBack=(ImageView)findViewById(R.id.imgBack);

        imbTrigger = (ImageButton)findViewById( R.id.imbTrigger );
        imbSafetyNetwork = (ImageButton)findViewById( R.id.imbSafetyNetwork );
        imbCopingStategies = (ImageButton) findViewById( R.id.imbCopingStategies );


    }

    @Override
    public void initClickListner() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressBackButton();
            }
        });

        imbTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SeftyPlanActivity.this,TriggersActivity.class);
                startActivity(intent);
            }
        });
        imbSafetyNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SeftyPlanActivity.this,SeftyNetworkActivity.class);
                startActivity(intent);
            }
        });
        imbCopingStategies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SeftyPlanActivity.this,CopingStretegyActivity.class);
                startActivity(intent);
            }
        });
    }
}

