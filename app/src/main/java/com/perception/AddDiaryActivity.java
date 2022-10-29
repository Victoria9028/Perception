package com.perception;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.crystal.crystalrangeseekbar.widgets.BubbleThumbSeekbar;
import com.perception.utility.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class AddDiaryActivity extends BaseActivity {

    ImageView imgBack;

    private ImageView imgTarible;
    private ImageView imgCry;
    private ImageView imgNatural;
    private ImageView imgSmile;
    private ImageView imgHappy;
    private BubbleThumbSeekbar rangeIntense;
    private TextView txtDate;
    private TextView txtTime;
    private EditText edtLocation;
    private EditText edtHappened;
    private EditText edtNagative;
    private EditText edtAdvice;
    private Button btnSubmit;

    String feel = "", intense = "", date = "", time = "", location = "", happened = "", nagative = "", advice = "";

    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_entry_diary);
        if (getIntent() != null) {
            FLAG = getIntent().getStringExtra(Constant.FLAG);
        }
        initUI();
        initClickListner();

        if (FLAG.matches(Constant.FLAG_ADD)) {
            setTitle("Add Entry");
            btnSubmit.setText("Add");
        } else {
            setTitle("Edit Entry");
            btnSubmit.setText("Update");
        }

    }

    @Override
    public void initUI() {
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgTarible = (ImageView) findViewById(R.id.imgTarible);
        imgCry = (ImageView) findViewById(R.id.imgCry);
        imgNatural = (ImageView) findViewById(R.id.imgNatural);
        imgSmile = (ImageView) findViewById(R.id.imgSmile);
        imgHappy = (ImageView) findViewById(R.id.imgHappy);
        rangeIntense = (BubbleThumbSeekbar) findViewById(R.id.rangeIntense);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTime = (TextView) findViewById(R.id.txtTime);
        edtLocation = (EditText) findViewById(R.id.edtLocation);
        edtHappened = (EditText) findViewById(R.id.edtHappened);
        edtNagative = (EditText) findViewById(R.id.edtNagative);
        edtAdvice = (EditText) findViewById(R.id.edtAdvice);
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
        imgTarible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feel = "1";
            }
        });

        imgCry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feel = "2";
            }
        });

        imgNatural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feel = "3";
            }
        });
        imgSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feel = "4";
            }
        });
        imgHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feel = "5";
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate()) {

                    if (FLAG.matches(Constant.FLAG_ADD)) {
                        new mDoAddEntry().execute();
                    }

                }
            }
        });

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDiaryActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date = dayOfMonth + "/" + monthOfYear + "/" + year;
                                txtDate.setText(date);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddDiaryActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        time = selectedHour + ":" + selectedMinute;
                        txtTime.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }


    private boolean validate() {


        intense = rangeIntense.getSteps() + "";

        location = edtLocation.getText().toString();
        happened = edtHappened.getText().toString();
        nagative = edtNagative.getText().toString();
        advice = edtAdvice.getText().toString();


        if (location.matches("") || happened.matches("") || nagative.matches("") || advice.matches(""))
        {
            Toast.makeText(AddDiaryActivity.this, "All fields must be required", Toast.LENGTH_LONG).show();
            return false;
        } else if (date.matches("") || time.matches("")) {
            Toast.makeText(AddDiaryActivity.this, "Date and time must be required", Toast.LENGTH_LONG).show();
            return false;
        } else if (feel.matches("")) {
            Toast.makeText(AddDiaryActivity.this, "Please set your feel", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }




    public class mDoAddEntry extends AsyncTask<Void, Void, JSONObject> {

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
            inputParameter.put("feel", feel);
            inputParameter.put("intense", intense);
            inputParameter.put("date", date);
            inputParameter.put("time", time);
            inputParameter.put("location", location);
            inputParameter.put("heppened", happened);
            inputParameter.put("nagitive", nagative);
            inputParameter.put("advice", advice);

            inputParameter.put("type", FLAG);

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
                    Intent intent = new Intent(AddDiaryActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddDiaryActivity.this,"Server Error Please try again!!!",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}

