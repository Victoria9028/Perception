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

import com.perception.utility.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

// Class Gratitude Activity
public class GratitudeActivity extends BaseActivity {
    private ImageView imgBack;
    private Button btnStart;
    private EditText edt1;
    private EditText edt2;
    private EditText edt3;
    private EditText edt4;
    private EditText edt5;

    String MODE = Constant.FLAG_EDIT;//Option for Add
    String ID = "";
    String details;

    String message1, message2, message3, message4, message5;

    // On create set content view
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gratitude);
        setTitle("Gratitude");
        initUI();
        initClickListner();
        initData();
    }

    // Initialize UI
    @Override
    public void initUI() {
        btnStart = (Button) findViewById(R.id.btnStart);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        edt1 = (EditText) findViewById(R.id.edt1);
        edt2 = (EditText) findViewById(R.id.edt2);
        edt3 = (EditText) findViewById(R.id.edt3);
        edt4 = (EditText) findViewById(R.id.edt4);
        edt5 = (EditText) findViewById(R.id.edt5);
    }

    // Initialize on click listener
    @Override
    public void initClickListner() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressBackButton();
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate())
                {
                    details=message1+","+message2+","+message3+","+message4+","+message5;
                    if (MODE==Constant.FLAG_ADD)
                    {
                        new mDoADDData().execute();
                    }else
                    {
                        new mDoEditData().execute();
                    }
                }
            }
        });
    }

    // Validation of gratitude fields
    private boolean validate() {
        message1 = edt1.getText().toString();
        message2 = edt2.getText().toString();
        message3 = edt3.getText().toString();
        message4 = edt4.getText().toString();
        message5 = edt5.getText().toString();

        if (message1.matches("") || message2.matches("") || message3.matches("") || message4.matches("") || message5.matches("")) {
            Toast.makeText(this, "All fields must be required", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public void initData() {
        new mDoGetGratitudeData().execute();
    }

    // If there is gratitude data in database, then get and display to user
    public class mDoGetGratitudeData extends AsyncTask<Void, Void, JSONObject> {

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
            inputParameter.put("type", Constant.FLAG_VIEW);
            JSONObject json = jsonParser.doCallApiForData(Constant.API_GRATITUDE, inputParameter);

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

        // If new gratitude list is created
        @Override
        protected void onPostExecute(JSONObject result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            appUtility.hideProgressDialog();
            Log.e("RESULT", "---->" + result);

            try {
                if (result.getString("status").matches("YES")) {
                    JSONObject jsonData = result.getJSONArray("data").getJSONObject(0);
                    ID = jsonData.getString("id");
                    details = jsonData.getString("message");
                    String[] messages = details.split(",");
                    edt1.setText(messages[0]);
                    edt2.setText(messages[1]);
                    edt3.setText(messages[2]);
                    edt4.setText(messages[3]);
                    edt5.setText(messages[4]);
                    MODE = Constant.FLAG_EDIT;

                } else {
                    MODE = Constant.FLAG_ADD;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    // Add gratitude list to database
    public class mDoADDData extends AsyncTask<Void, Void, JSONObject> {

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
            inputParameter.put("message", details);
            inputParameter.put("type", Constant.FLAG_ADD);
            JSONObject json = jsonParser.doCallApiForData(Constant.API_GRATITUDE, inputParameter);

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

        // Add Edit data
        @Override
        protected void onPostExecute(JSONObject result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            appUtility.hideProgressDialog();
            Log.e("RESULT", "---->" + result);

            try {
                if (result.getString("status").matches("YES")) {

                    ID=result.getString("id");
                 MODE=Constant.FLAG_EDIT;
                } else {
                    MODE=Constant.FLAG_ADD;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    // Edit gratitude data
    public class mDoEditData extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            appUtility.showProgressDialog();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            HashMap<String, String> inputParameter = new HashMap<>();
            inputParameter.put(Constant.SEQ_KEY_NAME, Constant.SEQ_KEY_VALUES);
            inputParameter.put("id", ID);
            inputParameter.put("user_id", getUserID());
            inputParameter.put("message", details);
            inputParameter.put("type", Constant.FLAG_EDIT);
            JSONObject json = jsonParser.doCallApiForData(Constant.API_GRATITUDE, inputParameter);

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

        // Update gratitude data
        @Override
        protected void onPostExecute(JSONObject result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            appUtility.hideProgressDialog();
            Log.e("RESULT", "---->" + result);

            try {
                if (result.getString("status").matches("YES")) {
                    MODE=Constant.FLAG_EDIT;
                } else {
                    MODE=Constant.FLAG_EDIT;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}