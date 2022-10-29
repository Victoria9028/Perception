package com.perception;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.perception.utility.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AccountActivity extends BaseActivity {

    private ImageView profilePic;
    private Button btnUpload;
    private EditText edtName;
    private EditText edtEmail;
    private ImageButton imbChangePassword;
    private ImageButton imbNotification;
    private ImageButton imbLogout;
    private ImageButton imbDelete;
    private ImageView imgBack;

    // On create set content view layout
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
        initUI();
        initClickListner();
        initData();
    }

    // Initialize UI
    @Override
    public void initUI() {
        profilePic = (ImageView) findViewById(R.id.profilePic);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        imbChangePassword = (ImageButton) findViewById(R.id.imbChangePassword);
        imbNotification = (ImageButton) findViewById(R.id.imbNotification);
        imbLogout = (ImageButton) findViewById(R.id.imbLogout);
        imbDelete = (ImageButton) findViewById(R.id.imbDelete);
        imgBack = (ImageView) findViewById(R.id.imgBack);
    }

    // Initialize on click listener for back button, change password and notifications button
    @Override
    public void initClickListner() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressBackButton();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imbChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AccountActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        imbNotification.setOnClickListener(new View.OnClickListener() {

            // Set on click listener for logout and delete account buttons
            @Override
            public void onClick(View view) {
            }
        });
        imbLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSharedPrefs.removeAll();
                Intent intent=new Intent(AccountActivity.this,SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        imbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
                builder.setTitle("Confirm Account Delete!");
                builder.setMessage("You are about to delete all records of database. Do you really want to proceed ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new mDoDeleteAccount().execute();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();

            }
        });
    }

    // Initialize data
    public void initData(){
        edtName.setText(mSharedPrefs.getPrefData("name"));
        edtEmail.setText(mSharedPrefs.getPrefData("email"));
        String image=mSharedPrefs.getPrefData("profile_pic");
        Log.e("Image",image+"");

        Glide.with(AccountActivity.this).load(image).into(profilePic);

    }

    // Delete account function
    public class mDoDeleteAccount extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            appUtility.showProgressDialog();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {


            HashMap<String, String> inputParameter = new HashMap<>();
            inputParameter.put(Constant.SEQ_KEY_NAME, Constant.SEQ_KEY_VALUES);
            inputParameter.put("id", getUserID());
            inputParameter.put("type", Constant.FLAG_DELETE);
            JSONObject json = jsonParser.doCallApiForData(Constant.API_CHANGE_PASSOWRD, inputParameter);

            if (json == null) {
                json = new JSONObject();
                try {
                    json.put("status", "NO");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return json;

        }

        // If user deletes account ask them if they are sure, if they are then delete account
        @Override
        protected void onPostExecute(JSONObject result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            appUtility.hideProgressDialog();
            Log.e("RESULT", "---->" + result);

            try {
                if (result.getString("status").matches("YES")) {
                    Toast.makeText(AccountActivity.this,"Your account has been deleted",Toast.LENGTH_LONG).show();
                    mSharedPrefs.removeAll();
                    Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}



