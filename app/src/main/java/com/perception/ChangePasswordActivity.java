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

public class ChangePasswordActivity extends BaseActivity {


    private ImageView imgBack;

    private EditText edtPassword;
    private EditText edtCPpassword;
    private Button btnSubmit;



    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        initUI();
        initClickListner();
        setTitle("Change Password");
    }


    @Override
    public void initUI() {
        imgBack = (ImageView) findViewById(R.id.imgBack);

        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtCPpassword = (EditText) findViewById(R.id.edtCPpassword);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);


    }

    @Override
    public void initClickListner() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate()) {
                  new mDoChangePassword().execute();
                }
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean validate() {

        password = edtPassword.getText().toString();
        String cpPassword = edtCPpassword.getText().toString();


       if (password.length() < 6) {
            Toast.makeText(ChangePasswordActivity.this, "Password is to short required minimum 6 char long", Toast.LENGTH_LONG).show();
            return false;
        } else if (!password.equals(cpPassword)) {
            Toast.makeText(ChangePasswordActivity.this, "Password and Confirm password must be same", Toast.LENGTH_LONG).show();
            return false;
        }  else {

            return true;
        }

    }



    public class mDoChangePassword extends AsyncTask<Void, Void, JSONObject> {

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
            inputParameter.put("password", password);
            inputParameter.put("type", Constant.FLAG_EDIT);
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

        @Override
        protected void onPostExecute(JSONObject result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            appUtility.hideProgressDialog();
            Log.e("RESULT", "---->" + result);

            try {
                if (result.getString("status").matches("YES")) {
                    Toast.makeText(ChangePasswordActivity.this,"Your Password has been changed login with new password",Toast.LENGTH_LONG).show();
                    mSharedPrefs.removeAll();
                    Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
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



