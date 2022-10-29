package com.perception;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.perception.helper.SharedPrefs;
import com.perception.utility.AppUtility;
import com.perception.utility.Constant;
import com.perception.utility.JSONParser;

public abstract class BaseActivity extends AppCompatActivity {


    public AppUtility appUtility;
    JSONParser jsonParser=new JSONParser();
    public abstract void initUI();
    public abstract void initClickListner();
    SharedPrefs mSharedPrefs;
    String FLAG;
    Gson gson=new Gson();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appUtility=new AppUtility(this);
        mSharedPrefs=new SharedPrefs(this);
    }

    public void pressBackButton(){

        finish();
    }

    public void setTitle(String title)
    {

        TextView txtTitle=(TextView)findViewById(R.id.txtTitle);
        txtTitle.setText(title);
    }

    public String getUserID()
    {
        return mSharedPrefs.getPrefData(Constant.USER_ID);

    }
}
