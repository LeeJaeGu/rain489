package com.CFKorea.pbc.Activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Utility.AsyncMemberShipJoin;

public class MemberShipJoinActivity extends Activity implements OnClickListener, OnEditorActionListener{

	private EditText joinid;
	private EditText joinpass;
	private EditText joinpass2;
	private EditText joinname;
	private EditText jointel;
	private EditText jointel2;
	private EditText jointel3;
	private ImageButton btncancel;
	private ImageButton btnok;
	private ImageButton back;
	private TelephonyManager TelephonyManager;
	private String model;
	private RelativeLayout joinmain;
	private String jid;
	private String jpass;
	private String jpass2;
	private String jname;
	private String jtel;
	
	Pattern pattern;
    Matcher matcher;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membershipjoin);
        
        joinid = (EditText)findViewById(R.id.joinid);
        joinpass = (EditText)findViewById(R.id.joinpass);
        joinpass2 = (EditText)findViewById(R.id.joinpass2);
        joinname = (EditText)findViewById(R.id.joinname);
        jointel = (EditText)findViewById(R.id.jointel);
        jointel2 = (EditText)findViewById(R.id.jointel2);
        jointel3 = (EditText)findViewById(R.id.jointel3);
        joinmain = (RelativeLayout)findViewById(R.id.joinmain);
        
        btncancel = (ImageButton) findViewById(R.id.btncancel);
        btnok = (ImageButton) findViewById(R.id.btnok);
        back = (ImageButton) findViewById(R.id.btn_back);
		
        btncancel.setOnClickListener(this);
        joinmain.setOnClickListener(this);
        btnok.setOnClickListener(this);
        back.setOnClickListener(this);
        joinid.setOnEditorActionListener(this);
        joinpass.setOnEditorActionListener(this);
        joinpass2.setOnEditorActionListener(this);
        joinname.setOnEditorActionListener(this);
        jointel3.setOnEditorActionListener(this);
        
        setFocusMove();
	}
	
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if(v.getId()==R.id.jointel3 && actionId==EditorInfo.IME_ACTION_DONE)
		{
		}
		return false;
		}
	
	@Override
	public void onClick(View v) 
	{
		boolean isValueNotNull = true;
		jid = joinid.getText().toString();
		jpass = joinpass.getText().toString() + "";
		jpass2 = joinpass2.getText().toString() + "";
		jname = joinname.getText().toString() + "";
		jtel = jointel.getText().toString() + jointel2.getText().toString() + jointel3.getText().toString();
		
		switch (v.getId()) 
		{
		
		case R.id.btncancel:
			finish();
			break;
		case R.id.joinmain:
			InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(joinid.getWindowToken(),0);
			imm.hideSoftInputFromWindow(joinpass.getWindowToken(),0);
			imm.hideSoftInputFromWindow(joinpass2.getWindowToken(),0);
			imm.hideSoftInputFromWindow(joinname.getWindowToken(),0);
			imm.hideSoftInputFromWindow(jointel.getWindowToken(),0);
			imm.hideSoftInputFromWindow(jointel2.getWindowToken(),0);
			imm.hideSoftInputFromWindow(jointel3.getWindowToken(),0);
			break;
		case R.id.btnok:
			
			if(!jpass.equals(jpass2))
			{
				Toast.makeText(this,"鍮꾨�踰덊샇 �솗�씤",Toast.LENGTH_SHORT).show();
				isValueNotNull = false;
			}
			else if(jid == null || "".equals(jid))
			{
				Toast.makeText(this,"�븘�씠�뵒瑜� �엯�젰�빐 二쇱떗�떆�삤.",Toast.LENGTH_SHORT).show();
				isValueNotNull = false;
			}
			// 1/13 leejaegu �븘�씠�뵒 �쑀�슚�꽦 寃��궗 異붽�
			else if(jid.length() < 15 || jid.length() > 64)
			{
				Toast.makeText(this,"�븘�씠�뵒�뒗 15�옄 �씠�긽 64�옄 �씠�븯�엯�땲�떎.",Toast.LENGTH_SHORT).show();
				isValueNotNull = false;
			}
			else if(jname == null || "".equals(jname))
			{
				Toast.makeText(this,"�씠由꾩쓣 �엯�젰�빐 二쇱떗�떆�삤.",Toast.LENGTH_SHORT).show();
				isValueNotNull = false;
			}
			// 1/13 leejaegu �씠由� �쑀�슚�꽦 寃��궗 異붽�
			else if(jname.length() > 10)
			{
				Toast.makeText(this,"�씠由꾩씠 �꼫臾� 源곷땲�떎.",Toast.LENGTH_SHORT).show();
				isValueNotNull = false;
			}
			else if(jtel == null || "".equals(jtel))
			{
				Toast.makeText(this,"�쟾�솕踰덊샇瑜� �엯�젰�빐 二쇱떗�떆�삤.",Toast.LENGTH_SHORT).show();
				isValueNotNull = false;
			}
			else if(jpass == null || "".equals(jpass) || jpass2 == null || "".equals(jpass2))
			{
				Toast.makeText(this,"鍮꾨�踰덊샇瑜� �엯�젰�빐 二쇱떗�떆�삤.",Toast.LENGTH_SHORT).show();
				isValueNotNull = false;
			}
			// 1/13 leejaegu 鍮꾨�踰덊샇 �쑀�슚�꽦 寃��궗 異붽�
			else if(jpass.length() < 8 || jpass.length() >20)
			{
				Toast.makeText(this,"鍮꾨�踰덊샇�뒗 8�옄 �씠�긽 20�옄由� �씠�븯濡� �엯�젰�빐二쇱떗�떆�삤.",Toast.LENGTH_SHORT).show();
				isValueNotNull = false;
			}
			else if(isEmail(jid) == false) 
			{
				Toast.makeText(this,"�씠硫붿씪 �삎�떇�쑝濡� �옉�꽦�빐 二쇱떗�떆�삤. ",Toast.LENGTH_SHORT).show();
				isValueNotNull = false;
			}
			else if(jointel.length() < 2 || jointel2.length() < 3 || jointel3.length() < 4) 
			{
				Toast.makeText(this,"�쟾�솕踰덊샇瑜� �솗�씤�빐 二쇱떗�떆�삤. ",Toast.LENGTH_SHORT).show();
				isValueNotNull = false;
			}
			
			getJoin(isValueNotNull);
			
			break;
			
		case R.id.btn_back:
			finish();
			break;
		}
	}
	
	public void setFocusMove() {
		
		jointel.addTextChangedListener(new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			Log.d("DEBUG", "setFocusMove =====" +jointel.length() );
			if(jointel.length() == 3) {  // edit1  媛믪쓽 �젣�븳媛믪쓣 4�씠�씪怨� 媛��젙�뻽�쓣�븣
				jointel2.requestFocus(); // �몢踰덉㎏EditText 濡� �룷而ㅼ뒪媛� �꽆�뼱媛�寃� �맗�땲�떎
			}
		}
		@Override
		public void afterTextChanged(Editable arg0) {}
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
	
		});
		
		
		jointel2.addTextChangedListener(new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if(jointel2.length() == 4) {  // edit1  媛믪쓽 �젣�븳媛믪쓣 6�씠�씪怨� 媛��젙�뻽�쓣�븣
				jointel3.requestFocus(); // �몢踰덉㎏EditText 濡� �룷而ㅼ뒪媛� �꽆�뼱媛�寃� �맗�땲�떎
			}
		}
		@Override
		public void afterTextChanged(Editable arg0) {}
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
		});
	}
	
	public static boolean isEmail(String jid) 
	{
        if (jid==null) return false;
        boolean b = Pattern.matches(
            "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", 
            jid.trim());
        return b;
    }
	
	public void getJoin(boolean isValueNotNull) 
	{
		if(isValueNotNull)
		{
			TelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			Log.d("getJoin",  "joinid = " + jid + "joinpass = " + jpass + "joinpass2 = " + jpass2 + "joinname = " + jname + "jointel = " + jtel);
			model = TelephonyManager.getDeviceId();
			new AsyncMemberShipJoin(MemberShipJoinActivity.this).setCallable(jid,jpass,jname,jtel,model).execute();
		}
	}
}
