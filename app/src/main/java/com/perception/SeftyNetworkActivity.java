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

import com.perception.adapter.SeftyNetworksAdapter;
import com.perception.model.SeftyNetwork;
import com.perception.utility.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SeftyNetworkActivity extends BaseActivity {

    ImageView imgBack;

    private RecyclerView recyclerNetwork;

    ArrayList<SeftyNetwork> seftyNetworkArrayList;
    SeftyNetworksAdapter seftyNetworkAAdapter;

    int REQ_UPDATE_CODE;

    private Button btnAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sefynetwok);
        setTitle("Safety Network");
        initUI();
        initClickListner();
        doLoadData();
    }

    @Override
    public void initUI() {

        imgBack = (ImageView) findViewById(R.id.imgBack);
        recyclerNetwork = (RecyclerView) findViewById(R.id.recyclerNetwork);
        btnAdd = (Button) findViewById(R.id.btnAdd);


    }

    public void doLoadData() {
        new mDoSeftyNetworkData().execute();
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
                Intent intent = new Intent(SeftyNetworkActivity.this, SeftyNetworkAddEditActivity.class);
                intent.putExtra(Constant.FLAG, Constant.FLAG_ADD);
                startActivity(intent);
            }
        });
    }

    public void doEditData(SeftyNetwork seftyNetwork) {
        Intent intent = new Intent(SeftyNetworkActivity.this, SeftyNetworkAddEditActivity.class);
        intent.putExtra(Constant.FLAG, Constant.FLAG_EDIT);

        intent.putExtra("gson", gson.toJson(seftyNetwork));
        startActivityForResult(intent, REQ_UPDATE_CODE);

    }

    public void doDeleteData(String id) {

        new mDoDeleteData(id).execute();
    }

    public class mDoSeftyNetworkData extends AsyncTask<Void, Void, JSONObject> {

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
            seftyNetworkArrayList = new ArrayList<SeftyNetwork>();
            appUtility.hideProgressDialog();
            Log.e("RESULT", "---->" + result);

            try {
                if (result.getString("status").matches("YES")) {

                    JSONArray jsonArray = result.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        SeftyNetwork adsModel = new SeftyNetwork();
                        adsModel.setId(jsonObject.getString("id"));
                        adsModel.setName(jsonObject.getString("name"));
                        adsModel.setEmail(jsonObject.getString("email"));
                        adsModel.setMobile(jsonObject.getString("mobile"));
                        adsModel.setUser_id(jsonObject.getString("user_id"));
                        seftyNetworkArrayList.add(adsModel);

                    }

                    Log.e("Size", seftyNetworkArrayList.size() + "---");
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SeftyNetworkActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerNetwork.setLayoutManager(linearLayoutManager);
                    seftyNetworkAAdapter = new SeftyNetworksAdapter(SeftyNetworkActivity.this, seftyNetworkArrayList);
                    recyclerNetwork.setAdapter(seftyNetworkAAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), "No Data Available!!!", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public class mDoRefreshSeftyNetworkData extends AsyncTask<Void, Void, JSONObject> {

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
            seftyNetworkArrayList = new ArrayList<SeftyNetwork>();
            seftyNetworkArrayList.clear();
            appUtility.hideProgressDialog();
            Log.e("RESULT", "---->" + result);

            try {
                if (result.getString("status").matches("YES")) {

                    JSONArray jsonArray = result.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        SeftyNetwork adsModel = new SeftyNetwork();
                        adsModel.setId(jsonObject.getString("id"));
                        adsModel.setId(jsonObject.getString("id"));
                        adsModel.setName(jsonObject.getString("name"));
                        adsModel.setEmail(jsonObject.getString("email"));
                        adsModel.setMobile(jsonObject.getString("mobile"));
                        adsModel.setUser_id(jsonObject.getString("user_id"));
                        seftyNetworkArrayList.add(adsModel);

                    }


                    seftyNetworkAAdapter = new SeftyNetworksAdapter(SeftyNetworkActivity.this, seftyNetworkArrayList);
                    recyclerNetwork.setAdapter(seftyNetworkAAdapter);
                    recyclerNetwork.invalidate();
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

        public mDoDeleteData(String id) {
            super();
            this.id = id;
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
                    new mDoRefreshSeftyNetworkData().execute();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

