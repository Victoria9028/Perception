package com.perception;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.perception.adapter.DiaryAdapter;
import com.perception.adapter.TriggersAdapter;
import com.perception.model.Diary;
import com.perception.model.Triggers;
import com.perception.utility.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DiaryViewActivity extends BaseActivity {

    ImageView imgBack;

    private RecyclerView recyclerDiary;


    ArrayList<Diary> diaryArrayList;
    DiaryAdapter diaryAdapter;
    int REQ_UPDATE_CODE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diaryview);
        setTitle("View Diary");
        initUI();
        initClickListner();
        doLoadData();

    }

    @Override
    public void initUI() {

        imgBack = (ImageView) findViewById(R.id.imgBack);
        recyclerDiary = (RecyclerView) findViewById(R.id.recyclerDiary);


    }

    @Override
    public void initClickListner() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressBackButton();
            }
        });

    }


    public void doLoadData() {
        new mDoGetDiaryData().execute();
    }

    public void doDeleteData(String id) {

        new mDoDeleteData(id).execute();
    }

    public void doEditData(Triggers triggers) {
        Intent intent = new Intent(DiaryViewActivity.this, TriggersAddEditActivity.class);
        intent.putExtra(Constant.FLAG, Constant.FLAG_EDIT);

        intent.putExtra("gson", gson.toJson(triggers));
        startActivityForResult(intent, REQ_UPDATE_CODE);

    }

    public class mDoGetDiaryData extends AsyncTask<Void, Void, JSONObject> {

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
            JSONObject json = jsonParser.doCallApiForData(Constant.API_DIARY, inputParameter);

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
            diaryArrayList = new ArrayList<Diary>();
            appUtility.hideProgressDialog();
            Log.e("RESULT", "---->" + result);

            try {
                if (result.getString("status").matches("YES")) {

                    JSONArray jsonArray = result.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Diary adsModel = new Diary();
                        adsModel.setId(jsonObject.getString("id"));
                        adsModel.setUser_id(jsonObject.getString("user_id"));
                        adsModel.setFeel(jsonObject.getString("feel"));
                        adsModel.setIntense(jsonObject.getString("intense"));
                        adsModel.setDate(jsonObject.getString("date"));
                        adsModel.setTime(jsonObject.getString("time"));
                        adsModel.setLocation(jsonObject.getString("location"));

                        adsModel.setHappened(jsonObject.getString("heppened"));
                        adsModel.setNegative(jsonObject.getString("nagitive"));
                        adsModel.setAdvice(jsonObject.getString("advice"));

                        diaryArrayList.add(adsModel);

                    }

                    Log.e("Size", diaryArrayList.size() + "---");
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DiaryViewActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerDiary.setLayoutManager(linearLayoutManager);
                    diaryAdapter = new DiaryAdapter(DiaryViewActivity.this, diaryArrayList);
                    recyclerDiary.setAdapter(diaryAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), "No Data Available!!!", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public class mDoRefreshDiaryData extends AsyncTask<Void, Void, JSONObject> {

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
            JSONObject json = jsonParser.doCallApiForData(Constant.API_DIARY, inputParameter);

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
            diaryArrayList = new ArrayList<Diary>();
            diaryArrayList.clear();
            try {
                if (result.getString("status").matches("YES")) {

                    JSONArray jsonArray = result.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Diary adsModel = new Diary();
                        adsModel.setId(jsonObject.getString("id"));
                        adsModel.setUser_id(jsonObject.getString("user_id"));
                        adsModel.setFeel(jsonObject.getString("feel"));
                        adsModel.setIntense(jsonObject.getString("intense"));
                        adsModel.setDate(jsonObject.getString("date"));
                        adsModel.setTime(jsonObject.getString("time"));
                        adsModel.setLocation(jsonObject.getString("location"));

                        adsModel.setHappened(jsonObject.getString("heppened"));
                        adsModel.setNegative(jsonObject.getString("nagitive"));
                        adsModel.setAdvice(jsonObject.getString("advice"));

                        diaryArrayList.add(adsModel);

                    }
                    diaryAdapter = new DiaryAdapter(DiaryViewActivity.this, diaryArrayList);
                    recyclerDiary.setAdapter(diaryAdapter);
                    recyclerDiary.invalidate();

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

            JSONObject json = jsonParser.doCallApiForData(Constant.API_DIARY, inputParameter);

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
                    new mDoRefreshDiaryData().execute();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_UPDATE_CODE) {

            }
        }
    }
}

