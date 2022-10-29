package com.perception;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.perception.adapter.TriggersAdapter;
import com.perception.model.Triggers;
import com.perception.utility.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TriggersActivity extends BaseActivity {

    ImageView imgBack;

    private RecyclerView recyclerTriggers;
    private Button btnAdd;


    ArrayList<Triggers> triggersArrayList;
    TriggersAdapter triggersAdapter;
    int REQ_UPDATE_CODE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trigers);
        setTitle("Triggers");
        initUI();
        initClickListner();
        doLoadData();

    }

    @Override
    public void initUI() {

        imgBack = (ImageView) findViewById(R.id.imgBack);
        recyclerTriggers = (RecyclerView) findViewById(R.id.recyclerTriggers);
        btnAdd = (Button) findViewById(R.id.btnAdd);


    }

    @Override
    public void initClickListner() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressBackButton();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TriggersActivity.this, TriggersAddEditActivity.class);
                intent.putExtra(Constant.FLAG, Constant.FLAG_ADD);
                startActivity(intent);
            }
        });
    }


    public void doLoadData() {
        new mDoGetTriggersData().execute();
    }

    public void doDeleteData(String id){

        new mDoDeleteData(id).execute();
    }

    public void doEditData(Triggers triggers)
    {
        Intent intent = new Intent(TriggersActivity.this, TriggersAddEditActivity.class);
        intent.putExtra(Constant.FLAG, Constant.FLAG_EDIT);

        intent.putExtra("gson", gson.toJson(triggers));
        startActivityForResult(intent,REQ_UPDATE_CODE);

    }

    public class mDoGetTriggersData extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            appUtility.showProgressDialog();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            HashMap<String, String> inputParameter = new HashMap<>();
            inputParameter.put(Constant.SEQ_KEY_NAME, Constant.SEQ_KEY_VALUES);
            inputParameter.put("type", Constant.FLAG_VIEW);
            inputParameter.put("user_id", getUserID());
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
            triggersArrayList = new ArrayList<Triggers>();
            appUtility.hideProgressDialog();
            Log.e("RESULT", "---->" + result);

            try {
                if (result.getString("status").matches("YES")) {

                    JSONArray jsonArray = result.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Triggers adsModel = new Triggers();
                        adsModel.setId(jsonObject.getString("id"));
                        adsModel.setUser_id(jsonObject.getString("user_id"));
                        adsModel.setMessage(jsonObject.getString("message"));
                        triggersArrayList.add(adsModel);

                    }

                    Log.e("Size", triggersArrayList.size() + "---");
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TriggersActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerTriggers.setLayoutManager(linearLayoutManager);
                    triggersAdapter = new TriggersAdapter(TriggersActivity.this, triggersArrayList);
                    recyclerTriggers.setAdapter(triggersAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), "No Data Available!!!", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public class mDoRefressTriggersData extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            HashMap<String, String> inputParameter = new HashMap<>();
            inputParameter.put(Constant.SEQ_KEY_NAME, Constant.SEQ_KEY_VALUES);
            inputParameter.put("type", Constant.FLAG_VIEW);
            inputParameter.put("user_id", getUserID());
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
            triggersArrayList = new ArrayList<Triggers>();
            triggersArrayList.clear();
            try {
                if (result.getString("status").matches("YES")) {

                    JSONArray jsonArray = result.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Triggers adsModel = new Triggers();
                        adsModel.setId(jsonObject.getString("id"));
                        adsModel.setUser_id(jsonObject.getString("user_id"));
                        adsModel.setMessage(jsonObject.getString("message"));
                        triggersArrayList.add(adsModel);

                    }
                    triggersAdapter = new TriggersAdapter(TriggersActivity.this, triggersArrayList);
                    recyclerTriggers.setAdapter(triggersAdapter);
                    recyclerTriggers.invalidate();

                } else {
                    Toast.makeText(getApplicationContext(), "No Data Available!!!", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }



    public class mDoDeleteData extends AsyncTask<Void, Void, JSONObject> {

        String id;
        public mDoDeleteData(String id){
            super();
            this.id=id;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            appUtility.showProgressDialog();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            HashMap<String, String> inputParameter = new HashMap<>();
            inputParameter.put(Constant.SEQ_KEY_NAME, Constant.SEQ_KEY_VALUES);
            inputParameter.put("type", Constant.FLAG_DELETE);
            inputParameter.put("id", id);

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
            triggersArrayList = new ArrayList<Triggers>();
            appUtility.hideProgressDialog();
            Log.e("RESULT", "---->" + result);

            try {
                if (result.getString("status").matches("YES")) {
                new mDoRefressTriggersData().execute();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK)
        {
            if(requestCode==REQ_UPDATE_CODE)
            {
                new mDoRefressTriggersData().execute();
                Log.e("RESULT","Updated");
            }
        }
    }
}

