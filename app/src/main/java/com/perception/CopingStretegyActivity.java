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

import com.perception.adapter.CopingStrategyAdapter;
import com.perception.adapter.TriggersAdapter;
import com.perception.model.CopingStrategy;
import com.perception.model.Triggers;
import com.perception.utility.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CopingStretegyActivity extends BaseActivity {

    ImageView imgBack;

    private Button btnAdd;

    private RecyclerView recyclerStrategy;
    ArrayList<CopingStrategy> copingStrategyArrayList;
    CopingStrategyAdapter copingStrategyAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coping_startegy);
        setTitle("Coping Strategies");
        initUI();
        initClickListner();
        doLoadData();
    }

    @Override
    public void initUI() {

        imgBack=(ImageView)findViewById(R.id.imgBack);
        recyclerStrategy = (RecyclerView)findViewById( R.id.recyclerStrategy );
        btnAdd = (Button)findViewById( R.id.btnAdd );


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
                Intent intent=new Intent(CopingStretegyActivity.this,CopingStrategyAddEditActivity.class);
                intent.putExtra(Constant.FLAG,Constant.FLAG_ADD);
                startActivity(intent);
            }
        });
    }

    public void doLoadData() {
        new mDoGetCopingStrategyData().execute();
    }

    public class mDoGetCopingStrategyData extends AsyncTask<Void, Void, JSONObject> {

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
            JSONObject json = jsonParser.doCallApiForData(Constant.API_COPING_STRATEGY, inputParameter);

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
            copingStrategyArrayList = new ArrayList<CopingStrategy>();
            appUtility.hideProgressDialog();
            Log.e("RESULT", "---->" + result);

            try {
                if (result.getString("status").matches("YES")) {

                    JSONArray jsonArray = result.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        CopingStrategy adsModel = new CopingStrategy();
                        adsModel.setId(jsonObject.getString("id"));
                        adsModel.setUser_id(jsonObject.getString("user_id"));
                        adsModel.setMessage(jsonObject.getString("message"));
                        copingStrategyArrayList.add(adsModel);

                    }

                    Log.e("Size", copingStrategyArrayList.size() + "---");
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CopingStretegyActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerStrategy.setLayoutManager(linearLayoutManager);
                    copingStrategyAdapter = new CopingStrategyAdapter(CopingStretegyActivity.this, copingStrategyArrayList);
                    recyclerStrategy.setAdapter(copingStrategyAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), "No Data Available!!!", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

