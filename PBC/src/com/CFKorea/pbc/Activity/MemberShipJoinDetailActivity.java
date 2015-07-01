package com.CFKorea.pbc.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.CFKorea.pbc.R;

public class MemberShipJoinDetailActivity extends Activity implements OnClickListener{
	
	private ImageButton back;
	private ImageView cancel;
	private ImageView gojoin;
	private CheckBox check1;
	private CheckBox check2;
	private TextView notice1;
	private TextView notice2;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membershipjoindetail);
        
        back = (ImageButton) findViewById(R.id.btn_back);
        cancel = (ImageView) findViewById(R.id.cancel);
		gojoin = (ImageView) findViewById(R.id.gojoin);
		check1 = (CheckBox)findViewById(R.id.check1);
		check2 = (CheckBox)findViewById(R.id.check2);
		notice1 = (TextView)findViewById(R.id.notice1);
		notice2 = (TextView)findViewById(R.id.notice2);
		
		back.setOnClickListener(this);
		cancel.setOnClickListener(this);
		gojoin.setOnClickListener(this);
		
		//체크버튼 이미지 변경 [[
		check1.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Log.d("MemberShipActivity", "isChecked = " + isChecked);
				if (isChecked == true )
				{
					check1.setButtonDrawable(R.drawable.check02);
					
				}
				else
				{
					check1.setButtonDrawable(R.drawable.check01);
					
					
				}
			}
		});
		check2.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
			{
				// TODO Auto-generated method stub
				Log.d("MemberShipActivity", "isChecked = " + isChecked);
				if (isChecked == true )
				{
					check2.setButtonDrawable(R.drawable.check02);
					
				}
				else
				{
					check2.setButtonDrawable(R.drawable.check01);
					
					
				}
			}
		});
		//]]
		
		
		
		
		
		
	}
	
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.cancel:
			finish();
			break;
		case R.id.gojoin:
			if(check1.isChecked() == false)
			{
				Toast.makeText(this,getResources().getString(R.string.service1check),Toast.LENGTH_SHORT).show();
			}
			else if(check2.isChecked() ==false){
				Toast.makeText(this,getResources().getString(R.string.service2check),Toast.LENGTH_SHORT).show();
			}
			else
			{
				Log.d("DEBUG","gobutton");
				Intent intent = new Intent(getApplicationContext(), MemberShipJoinActivity.class);
				startActivity(intent);
			}
			break;
			
		}
	}
}
