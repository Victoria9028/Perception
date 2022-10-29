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

public class ForgotPasswordActivity extends BaseActivity {


    private ImageView imgBack;

    private EditText edtEmail;

    private Button btnSend;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        initUI();
        initClickListner();
    }


    @Override
    public void initUI() {
        imgBack = (ImageView) findViewById(R.id.imgBack);

        edtEmail = (EditText) findViewById(R.id.edtEmail);

        btnSend = (Button) findViewById(R.id.btnSend);


    }

    @Override
    public void initClickListner() {

        btnSend.setOnClickListener(new View.OnClickListener() {
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


        email = edtEmail.getText().toString();


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.equals("") ) {
            Toast.makeText(ForgotPasswordActivity.this, "Email must be required", Toast.LENGTH_LONG).show();
            return false;
        }  else if (!email.trim().matches(emailPattern)) {

            Toast.makeText(ForgotPasswordActivity.this, "Enter valid Email Id", Toast.LENGTH_LONG).show();
            return false;
        } else {

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
            inputParameter.put("email", email);
            inputParameter.put("type", Constant.FLAG_EMAIL);
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
                    Toast.makeText(ForgotPasswordActivity.this,"Email send sucessfully!!!",Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(ForgotPasswordActivity.this,LoginActivity.class);
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



