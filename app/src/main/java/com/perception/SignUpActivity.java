package com.perception;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.perception.utility.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignUpActivity extends BaseActivity {


    private ImageView imgBack;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtCPpassword;
    private Button btnSignUp;
    private TextView txtSignIN;


    String name ,email,password,profile_pic="profile_pic";

    // On create set content view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        initUI();
        initClickListner();
    }

    // Initialize UI
    @Override
    public void initUI() {
        imgBack = (ImageView) findViewById(R.id.imgBack);
        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtCPpassword = (EditText) findViewById(R.id.edtCPpassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        txtSignIN = (TextView) findViewById(R.id.txtSignIN);

    }

    // Initialize on click listener for sign up, sign in and back button
    @Override
    public void initClickListner() {

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate()) { // If fields validated
                  new mDoSignUp().execute(); // sign up
                }
            }
        });

        txtSignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // Field validation
    private boolean validate() {

         name = edtName.getText().toString();
        email = edtEmail.getText().toString();

        password = edtPassword.getText().toString();
        String cpPassword = edtCPpassword.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (name.equals("") || password.equals("") || email.equals("")) {
            Toast.makeText(SignUpActivity.this, "all fields must be required", Toast.LENGTH_LONG).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(SignUpActivity.this, "Password is to short required minimum 6 char long", Toast.LENGTH_LONG).show();
            return false;
        } else if (!password.equals(cpPassword)) {
            Toast.makeText(SignUpActivity.this, "Password and Confirm password must be same", Toast.LENGTH_LONG).show();
            return false;
        } else if (!email.trim().matches(emailPattern)) {

            Toast.makeText(SignUpActivity.this, "Enter valid Email Id", Toast.LENGTH_LONG).show();
            return false;
        } else {

            return true;
        }

    }

    // Add sign up data to database
    public class mDoSignUp extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            appUtility.showProgressDialog();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {


            HashMap<String, String> inputParameter = new HashMap<>();
            inputParameter.put(Constant.SEQ_KEY_NAME, Constant.SEQ_KEY_VALUES);
            inputParameter.put("email", email);
            inputParameter.put("password", password);
            inputParameter.put("profile_pic", profile_pic);

            JSONObject json = jsonParser.doCallApiForData(Constant.API_SIGN_UP, inputParameter);

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

        // If sign in details correct execute sign up else tell user to try another email
        @Override
        protected void onPostExecute(JSONObject result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            appUtility.hideProgressDialog();
            Log.e("RESULT", "---->" + result);

            try {
                if (result.getString("status").matches("YES")) {

                    mSharedPrefs.addPrefData("id",result.getString("user_id"));
                    mSharedPrefs.addPrefData("name",name);
                    mSharedPrefs.addPrefData("email",email);
                    mSharedPrefs.addPrefData("password",password);
                    mSharedPrefs.addPrefData("profile_pic",profile_pic);

                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this,"Email id already exist try another email id",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}



