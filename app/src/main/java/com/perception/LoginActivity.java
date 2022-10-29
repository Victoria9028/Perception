package com.perception;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.perception.utility.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

// Login class
public class LoginActivity extends BaseActivity {
    private EditText edtEmail;
    private EditText edtPassword;
    private TextView txtForgotPassword;
    private Button btnSignIn;
    private TextView txtSignUp;

    String email, password;

    // On create set content view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initUI();
        initClickListner();
    }

    // Initialzie UI
    @Override
    public void initUI() {

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        txtSignUp = (TextView) findViewById(R.id.txtSignUp);
    }

    // Initialize on click listener
    @Override
    public void initClickListner() {

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // sign in button
                if (validate()) {

                    if (appUtility.isConnectingToInternet()) { // connecting to database
                        new mLoadLoginData().execute();
                    }


                }
            }
        });
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent); // sign up button
            }
        });
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent); // forgot password button
            }
        });
    }


    // Field validation
    private boolean validate() {

        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.equals("") || password.equals("")) {
            Toast.makeText(LoginActivity.this, "Email and password not be empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(LoginActivity.this, "Password is to short required minimum 6 char long", Toast.LENGTH_LONG).show();
            return false;
        } else if (!email.trim().matches(emailPattern)) {

            Toast.makeText(LoginActivity.this, "Enter valid Email Id", Toast.LENGTH_LONG).show();
            return false;
        } else {

            return true;
        }

    }

    // Load login data
    public class mLoadLoginData extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            appUtility.showProgressDialog();
        }

        // In background
        @Override
        protected JSONObject doInBackground(Void... params) {
            HashMap<String, String> inputParameter = new HashMap<>();
            inputParameter.put(Constant.SEQ_KEY_NAME, Constant.SEQ_KEY_VALUES);
            inputParameter.put("email", email);
            inputParameter.put("password", password);
            JSONObject json = jsonParser.doCallApiForData(Constant.API_SIGN_IN, inputParameter);

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

        // If data correct, login, else tell user invalid email and password
        @Override
        protected void onPostExecute(JSONObject result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            appUtility.hideProgressDialog();
            Log.e("RESULT", "---->" + result);

            try {
                if (result.getString("status").matches("YES")) {
                    JSONObject jsonData=result.getJSONArray("data").getJSONObject(0);
                    mSharedPrefs.addPrefData("id",jsonData.getString("id"));
                    mSharedPrefs.addPrefData("name",jsonData.getString("name"));
                    mSharedPrefs.addPrefData("email",jsonData.getString("email"));
                    mSharedPrefs.addPrefData("password",jsonData.getString("password"));
                    mSharedPrefs.addPrefData("profile_pic",jsonData.getString("profile_pic"));

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,"Invalide email and password!!!",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}



