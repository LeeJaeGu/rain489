package com.CFKorea.pbc.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Utility.AsyncMemberShipLogin;

public class IntroActivity extends Activity{
	private final String TAG = "[IntroActivity]";
	String userid;
	String userpass;
	Boolean box;
	String m_PhoneDeviceid;
	TelephonyManager m_TelephonyManager;
	Boolean auto = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		
		SharedPreferences prefs = getSharedPreferences("autologin", MODE_PRIVATE);
		box = prefs.getBoolean("autobox",false);
		userid = prefs.getString("autouserid","");   // Boolean
		userpass = prefs.getString("autouserpass","");
		m_TelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		m_PhoneDeviceid = m_TelephonyManager.getDeviceId();
		
		onInit();
	}
	
	public void onInit(){
		Handler mHandler = new Handler(Looper.getMainLooper());
		mHandler.postDelayed(new Runnable() 
		{
			@Override
			public void run() 
			{
				if(box == true)
				{
					new AsyncMemberShipLogin(IntroActivity.this).setCallable(m_PhoneDeviceid, userid, userpass, auto).execute();
				}
				else{
					startActivity(new Intent(getApplicationContext(), MainActivity.class));
//					Intent intent = new Intent();
//					intent.setClass(getApplicationContext(), MainActivity.class);
//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					getApplicationContext().startActivity(intent);
				}
				
			    finish();      
			}
		}, 1000);
	}
	
}
