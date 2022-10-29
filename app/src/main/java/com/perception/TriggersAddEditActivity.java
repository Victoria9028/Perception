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

import com.perception.model.Triggers;
import com.perception.utility.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TriggersAddEditActivity extends BaseActivity {

    ImageView imgBack;

    private EditText edtDetails;

    private Button btnSubmit;

    String message;

    Triggers triggers;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trigers_add_edit);

        if (getIntent() != null) {
            FLAG = getIntent().getStringExtra(Constant.FLAG);
        }

        initUI();
        initClickListner();

        if (FLAG.matches(Constant.FLAG_ADD)) {
            setTitle("Add Triggers");
            btnSubmit.setText("Add");
        } else {
            triggers=gson.fromJson(getIntent().getStringExtra("gson"), Triggers.class);
            setTitle("Edit Triggers");
            btnSubmit.setText("Update");
            edtDetails.setText(triggers.getMessage());
        }

    }

    @Override
    public void initUI() {

        imgBack=(ImageView)findViewById(R.id.imgBack);
        edtDetails = (EditText)findViewById( R.id.edtDetails );

        btnSubmit = (Button)findViewById( R.id.btnSubmit );


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
                        new doAddTriggersEntry().execute();
                    }else
                    {
                        new doEditTriggersEntry().execute();
                    }

                }
            }
        });

    }


    private boolean validate() {
        message = edtDetails.getText().toString();
        if (message.matches("")  )
        {
            Toast.makeText(this, "All fields must be required", Toast.LENGTH_LONG).show();
            return false;
        }else {
            return true;
        }
    }



    public class doAddTriggersEntry extends AsyncTask<Void, Void, JSONObject> {

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
            inputParameter.put("message", message);


            inputParameter.put("type", FLAG);

            JSONObject json = jsonParser.doCallApiForData(Constant.API_TRIGGERS, inputParameter);

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
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Server Error Please try again!!!",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public class doEditTriggersEntry extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            appUtility.showProgressDialog();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            HashMap<String, String> inputParameter = new HashMap<>();
            inputParameter.put(Constant.SEQ_KEY_NAME, Constant.SEQ_KEY_VALUES);
            inputParameter.put("id",triggers.getId());
            inputParameter.put("user_id", getUserID());
            inputParameter.put("message", message);


            inputParameter.put("type",Constant.FLAG_EDIT);

            JSONObject json = jsonParser.doCallApiForData(Constant.API_TRIGGERS, inputParameter);

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
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Server Error Please try again!!!",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}


