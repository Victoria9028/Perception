package com.perception.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    Context mContext;
    SharedPreferences sharedPref;

    // Get shared preferences and and application context
    public SharedPrefs(Activity activity) {

        mContext=activity.getApplicationContext();
        sharedPref= mContext.getSharedPreferences("mPref", Context.MODE_PRIVATE);

    }

    // Add preference data function
    public void addPrefData(String key,String values)
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, values);
        editor.commit();
    }

    // Get preference data function
    public String getPrefData(String key)
    {

        return   sharedPref.getString(key, "");
    }

    // Delete Account preferences
    public void removeAll(){

        sharedPref.edit().remove("id").commit();
        sharedPref.edit().remove("name").commit();
        sharedPref.edit().remove("email").commit();
        sharedPref.edit().remove("password").commit();
        sharedPref.edit().remove("profile_pic").commit();




    }
}
