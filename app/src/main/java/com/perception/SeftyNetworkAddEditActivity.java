package com.perception;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.perception.model.SeftyNetwork;
import com.perception.model.Triggers;
import com.perception.utility.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SeftyNetworkAddEditActivity extends BaseActivity {

    ImageView imgBack;

    private EditText edtName;
    private EditText edtEmail;
    private EditText edtMobile;
    private Button btnSubmit;


    String name, email, mobile;

    SeftyNetwork seftyNetwork;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sefynetwok_add_edit);
        if (getIntent() != null) {
            FLAG = getIntent().getStringExtra(Constant.FLAG);
        }

        initUI();
        initClickListner();

        if (FLAG.matches(Constant.FLAG_ADD)) {
            setTitle("Add Safety Network");
            btnSubmit.setText("Add");
        } else {

            setTitle("Edit Safety Network");
            btnSubmit.setText("Update");
            seftyNetwork = gson.fromJson(getIntent().getStringExtra("gson"), SeftyNetwork.class);

            edtName.setText(seftyNetwork.getName());
            edtMobile.setText(seftyNetwork.getMobile());
            edtEmail.setText(seftyNetwork.getEmail());
        }
    }

    @Override
    public void initUI() {

        imgBack = (ImageView) findViewById(R.id.imgBack);
        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtMobile = (EditText) findViewById(R.id.edtMobile);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);


    }

    @Override
    public void initClickListner() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressBackButton();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {

                    if (FLAG.matches(Constant.FLAG_ADD)) {
                        new doAddNetworkEntry().execute();
                    } else if (FLAG.matches(Constant.FLAG_EDIT)) {
                        new doEditNetworkEntry().execute();
                    }

                }
            }
        });

    }

    private boolean validate() {
        name = edtName.getText().toString();
        email = edtEmail.getText().toString();
        mobile = edtMobile.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (name.matches("") || email.matches("") || mobile.matches("")) {
            Toast.makeText(SeftyNetworkAddEditActivity.this, "All fields must be required", Toast.LENGTH_LONG).show();
            return false;
        } else if (!email.trim().matches(emailPattern)) {

            Toast.makeText(SeftyNetworkAddEditActivity.this, "Enter valid Email Id", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }


    public class doAddNetworkEntry extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            appUtility.showProgressDialog();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            HashMap<String, String> inputParameter = new HashMap<>();
            inputParameter.put(Constant.SEQ_KEY_NAME, Constant.SEQ_KEY_VALUES);
            inputParameter.put("user_id", getUserID());
            inputParameter.put("name", name);
            inputParameter.put("email", email);
            inputParameter.put("mobile", mobile);

            inputParameter.put("type", FLAG);

            JSONObject json = jsonParser.doCallApiForData(Constant.API_SAFETY_NETWORK, inputParameter);

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
                    Intent intent = new Intent(SeftyNetworkAddEditActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SeftyNetworkAddEditActivity.this, "Server Error Please try again!!!", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    public class doEditNetworkEntry extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            appUtility.showProgressDialog();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            HashMap<String, String> inputParameter = new HashMap<>();
            inputParameter.put(Constant.SEQ_KEY_NAME, Constant.SEQ_KEY_VALUES);
            inputParameter.put("id", seftyNetwork.getId());

            inputParameter.put("user_id", getUserID());
            inputParameter.put("name", name);
            inputParameter.put("email", email);
            inputParameter.put("mobile", mobile);

            inputParameter.put("type", FLAG);

            JSONObject json = jsonParser.doCallApiForData(Constant.API_SAFETY_NETWORK, inputParameter);

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
                    Intent intent = new Intent(SeftyNetworkAddEditActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SeftyNetworkAddEditActivity.this, "Server Error Please try again!!!", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}


