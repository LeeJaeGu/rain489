package com.CFKorea.pbc.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Utility.AsyncMemberShipLogin;

public class MemberShipLoginActivity extends Activity implements OnClickListener, OnEditorActionListener {
	 
	private EditText id;
	private EditText pass;
	private String idtext;
	private String passtext;
	private String m_loginid;
	private String m_loginpass;
	private String m_PhoneDeviceid;
	boolean m_auto = false;
	private ImageButton loginbutton;
	private ImageButton loginjoin;
	private ImageButton back;
	private TelephonyManager m_TelephonyManager;
	static CheckBox auto;
	private RelativeLayout loginmain;
	//boolean autobox;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_membershiplogin);
		
		

		onInit();
	}

	public void onInit() {
		
		id = (EditText) findViewById(R.id.id);
		pass = (EditText) findViewById(R.id.pass);
		loginbutton = (ImageButton) findViewById(R.id.loginbutton);
		loginjoin = (ImageButton) findViewById(R.id.loginjoin);
		back = (ImageButton) findViewById(R.id.btn_back);
		auto = (CheckBox)findViewById(R.id.auto);
		loginmain = (RelativeLayout)findViewById(R.id.loginmain);
		id.setOnClickListener(this);
		pass.setOnClickListener(this);
		loginmain.setOnClickListener(this);
		loginbutton.setOnClickListener(this);
		loginjoin.setOnClickListener(this);
		back.setOnClickListener(this);
		id.setOnEditorActionListener(this);
		pass.setOnEditorActionListener(this);
		
		auto.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Log.d("MemberShipActivity", "isChecked = " + isChecked);
				if (isChecked == true )
				{
					auto.setButtonDrawable(R.drawable.check02);
				}
				else
				{
					auto.setButtonDrawable(R.drawable.check01);
				}
			}
		});
		
		m_TelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		
	}
	
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		
		if(v.getId()==R.id.pass && actionId==EditorInfo.IME_ACTION_DONE)
		{
		}
		return false;
		}

	public String getloginid() 
	{

		m_loginid = id.getText().toString();

		return m_loginid;
	}
	
	public String getloginpass() 
	{

		m_loginpass = pass.getText().toString();

		return m_loginpass;
	}

	public String getAndroidId() 
	{
		m_PhoneDeviceid = m_TelephonyManager.getDeviceId(); // model_key
		
		return m_PhoneDeviceid;
	}


	public void getlogin(boolean isValueNotNull) 
	{
		
		m_PhoneDeviceid = getAndroidId(); // model_key
		m_loginid = getloginid(); // license_key
		m_loginpass = getloginpass();
		
		if(isValueNotNull)
		{
			if(auto.isChecked() == true)
			{
				SharedPreferences prefs = getSharedPreferences("autologin", MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString("autouserid",m_loginid);
				editor.putString("autouserpass",m_loginpass);// String
				editor.commit();
				m_auto = true;
			}
		new AsyncMemberShipLogin(MemberShipLoginActivity.this).setCallable(m_PhoneDeviceid, m_loginid, m_loginpass, m_auto).execute();
		}
	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		
		idtext = id.getText().toString() + "";
		passtext = pass.getText().toString() + "";
		boolean isValueNotNull = true;
		
		
		switch (v.getId()) {
		case R.id.loginbutton:
			if(idtext == null || "".equals(idtext))
			{
				Toast.makeText(this,"아이디를 입력해 주십시오.",Toast.LENGTH_LONG).show();
				isValueNotNull = false;
			}
			else if(passtext == null || "".equals(passtext))
			{
				Toast.makeText(this,"비밀번호를 입력해 주십시오.",Toast.LENGTH_LONG).show();
				isValueNotNull = false;
			}
			
			getlogin(isValueNotNull);
			break;
		case R.id.loginmain:
			InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(id.getWindowToken(),0);
			imm.hideSoftInputFromWindow(pass.getWindowToken(),0);
			break;
		case R.id.loginjoin:
			Intent intent = new Intent(getApplicationContext(), MemberShipJoinDetailActivity.class);
			startActivity(intent);
			
			break;
		case R.id.btn_back:
			Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
        	intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
 			startActivity(intent2);
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		       switch(keyCode) {
		         case KeyEvent.KEYCODE_BACK:
		        	Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 			startActivity(intent);
		          default:
		            return false;
		      }
		  }
}
