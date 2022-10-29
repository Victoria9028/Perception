package com.perception.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class AppUtility {

	Context mContext;
	Activity activity;
	ProgressDialog pDialog;

	
	public AppUtility(Activity activity) {
		this.activity = activity;
		this.mContext = this.activity.getApplicationContext();
		pDialog=new ProgressDialog(activity);
	}

	// Show progress dialog utility
	public void showProgressDialog(){
		pDialog.setCancelable(true);
		pDialog.setMessage("Please wait...");
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.show();
	}

	// Show ads progress dialog utility
	public void showAdsProgressDialog(){
		if(pDialog==null)
		{
			pDialog=new ProgressDialog(activity);
		}
		pDialog.setCancelable(false);
		pDialog.setMessage("Ads Display. Please wait...");
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.show();
	}

	// Hide progress dialog utility
	public void hideProgressDialog(){
		if(pDialog!=null)
		{
			pDialog.dismiss();
		}
	}
	public static int getScreenWidth() {
		return Resources.getSystem().getDisplayMetrics().widthPixels;
	}

	public static int getScreenHeight() {
		return Resources.getSystem().getDisplayMetrics().heightPixels;
	}

	// Camera Utility
	public boolean isDeviceSupportCamera() {
		if (activity.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
		}
	}

	// Array Utility
	public ArrayList<String> getTextStyle(){
		ArrayList<String> list=new ArrayList<>();

		for(int i=1;i<31;i++)
		{
			list.add("fonts/"+i+".ttf");

			
		}
		return list;
	}

	// Upload account image utility
	public void addImageGallery( File file ) {
	    ContentValues values = new ContentValues();
	    values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
	    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg"); // setar isso
	    mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	}


	// Get current time utility
	public String getCurruntTimeStamp()
	{
		SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
		String format = s.format(new Date());
		return format;
	}


	public static  int getAppRandomDownload(){
		Random r = new Random();
		int i1 = r.nextInt(500000 - 20000) + 20000;
		return i1;
	}
	public static  String getAppRandomRating(){
		float min=4.0f;
		float max=5.0f;
		Random rand = new Random();
		float random = min + rand.nextFloat() * (max - min);

		return String.format("%.1f", random);
	}
	public static  String getAppRandomSize(){
		float min=2.0f;
		float max=5.0f;
		Random rand = new Random();
		float random = min + rand.nextFloat() * (max - min);

		return String.format("%.1f", random)+" MB";
	}

	// Create alert dialog utility
	public void createAlertDialog(String title,String message,String btnName) {
		 AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		   
		   builder.setTitle(title);
		   builder.setMessage(message);
		   builder.setPositiveButton(btnName, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		  
		   builder.create();
		   builder.show();
	}

	// Set ads utility
	public void setAdsJson(String data) {
		SharedPreferences.Editor editor = this.activity.getSharedPreferences(
				"jdata", 0).edit();
		editor.putString("json", data);

		editor.commit();
	}

	// Get ads utility
	public String getAdsJson() {
		SharedPreferences prefs = this.activity.getSharedPreferences("jdata", 0);
		String json = prefs.getString("json", "");
		return json;
	}

	// Set diary stickers utility
	public void setStickerJson(String data) {
		SharedPreferences.Editor editor = this.activity.getSharedPreferences(
				"jdata", 0).edit();
		editor.putString("stickers", data);

		editor.commit();
	}

	// Get diary sticker utility
	public String getStickerJson() {
		SharedPreferences prefs = this.activity.getSharedPreferences("jdata", 0);
		String json = prefs.getString("stickers", "");
		return json;
	}

	// Set template utility
	public void setTemplateJson(String data) {
		SharedPreferences.Editor editor = this.activity.getSharedPreferences("adsData", 0).edit();
		editor.putString("jsonTemplate", data);

		editor.commit();
	}

	// Get template for utility
	public String getTemplateJson() {
		SharedPreferences prefs = this.activity.getSharedPreferences("adsData", 0);
		String json = prefs.getString("jsonTemplate", "");
		return json;
	}

	// Internet connection utility
	public boolean isConnectingToInternet() {
		ConnectivityManager connectivityManager 
        = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
  NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
  return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	// Column layout utility
	public  int calculateNoOfColumns(Context context,int width) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
		int noOfColumns = (int) (dpWidth / width);
		return noOfColumns;
	}

}
