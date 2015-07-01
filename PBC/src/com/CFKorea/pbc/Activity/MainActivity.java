package com.CFKorea.pbc.Activity;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Adapter.MainListAdapter;
import com.CFKorea.pbc.Adapter.MainNaviAdapter;
import com.CFKorea.pbc.Utility.MenuControll;


public class MainActivity extends Activity implements OnClickListener{

	
	private final String TAG="MainActivity";
	public static TextView user;
	public static TextView user3;
	static TextView logoutuser;
	ImageView logo;
	TextView pyunghwa;
	TextView lan;
	String language;
	public static String mLoginYN;
	public static String mainloginid;
	public String mainloginpass;
	public String sLoginChek="Y";
	public static String logout = "N";
	private long backKeyPressedTime = 0;
	private Toast toast;
	private LinearLayout mLeft_layout;
	private ListView mLeft_ListView;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
	private View drawer;
	private ArrayList<Integer> mMainListArray = new ArrayList<Integer>();
	private ArrayList<String> mMainNaviListArray = new ArrayList<String>();
	private ActionBarDrawerToggle mLeft_Toggle;
	GridView maingrid;
	Locale locale;
	public static ImageView loginimage;
	private AdapterViewFlipper flipper;
	public static ArrayList<String> SlideImage = new ArrayList<String>();
	String sublogin;
	String sublogout;
	String sublanguage;
	private ArrayList<String> submenu1;
	private ArrayList<String> submenu2;
	public static boolean admin = false;
	public static boolean general = false;
	
	private String[] slide = {
			"id:CFKorea ---> 안녕하세요~~~~~~", 
			"id:CFKorea ---> 평화방송 라디오 입니다~~~~~~~ ", 
			"id:CFKorea ---> 나만의 사연을 전송~~~~~",
			"id:CFKorea ---> 잘부탁 드립니다~~~~~~~~~~~~~~~~"};
	
	private int[] resResources = { 	
			R.drawable.videorecording,  
			R.drawable.voicerecording,  
			R.drawable.mystudio,  
			R.drawable.pod
			};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        //서브 메뉴 리스트 제목 추가
        
        
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerLayout.setBackgroundResource(R.drawable.animation);
        AnimationDrawable mAnimationDrawable_1 = (AnimationDrawable)mDrawerLayout.getBackground();
        mAnimationDrawable_1.start();
        user = (TextView)findViewById(R.id.user);
        user3 = (TextView)findViewById(R.id.user3);
        lan = (TextView)findViewById(R.id.lan);
        loginimage = (ImageView)findViewById(R.id.loginimage);
        user3.setOnClickListener(this);
        lan.setOnClickListener(this);
        maingrid = (GridView)findViewById(R.id.maingrid);
        for (int i = 0; i < resResources.length; i++) 
		{
        	mMainListArray.add(resResources[i]);
		}
        MainListAdapter mainListAdapter = new MainListAdapter(this, mMainListArray);
        maingrid.setAdapter(mainListAdapter);
        
        for (int i = 0; i < slide.length; i++) 
		{
        	SlideImage.add(slide[i]);
		}
        
//        flipper = (AdapterViewFlipper)findViewById(R.id.viewFlipper);
//        flipper.setAdapter(new SlideAdapter(MainActivity.this));
//			flipper.startFlipping();
        locale = getResources().getConfiguration().locale;
        language = locale.getLanguage();
        Log.d(TAG,"language = " + language);

        getlogin();
        leftmenu();
        
        Log.d(TAG,"onCreate mLoginYN = " + mLoginYN);
    }
    
    public void leftmenu(){
    	
    	mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer = (View) findViewById(R.id.drawer);
		mLeft_ListView = (ListView)findViewById(R.id.left_ListView);
		mTitle = mDrawerTitle = getTitle();
		getActionBar().setIcon(R.drawable.img_main_top);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
		
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, R.drawable.sidebar, R.string.drawer_open, R.string.drawer_close) 
        {
            public void onDrawerClosed(View view) 
            {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) 
            {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        //sublogin = getResources().getString(R.string.main_sub_login);
        sublogin = getResources().getText(R.string.main_sub_login).toString();
        sublogout = getResources().getText(R.string.main_sub_logout).toString();
        sublanguage = getResources().getText(R.string.language).toString();
        Log.d(TAG,"sublogin = " + sublogin);
        submenu1 = new ArrayList<String>();
        submenu1.add(sublogin);
        submenu1.add(sublanguage);
        submenu2 = new ArrayList<String>();
        submenu2.add(sublogout);
        submenu2.add(sublanguage);
        
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        MainNaviAdapter mainNaviAdapter = new MainNaviAdapter(this, mMainNaviListArray);
        if("Y".equals(mLoginYN))
		{
			for ( int i = 0; i < submenu2.size(); i++) 
			{
				mMainNaviListArray.add(submenu2.get(i));
			}
			mainNaviAdapter.setsLoginChek(sLoginChek);
			Log.d(TAG,"SET sLoginChek = " + sLoginChek);
		}	
        
		else
		{
			for ( int i = 0; i < submenu1.size(); i++) 
			{
				mMainNaviListArray.add(submenu1.get(i));
			}
			Log.d(TAG,"SET sLoginChek = " + sLoginChek);
		}
        
        if(admin==true){
        	mMainNaviListArray.add(getResources().getText(R.string.admin).toString());
        }
        if(general==true){
        	mMainNaviListArray.add(getResources().getText(R.string.general).toString());
        }
        
        mLeft_ListView.setAdapter(mainNaviAdapter);
        
    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    
		if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
		
	    switch(item.getItemId()){
	    default:
	    	return super.onOptionsItemSelected(item);
	    }
	    
	}
    
    public void getlogin(){
    	
    	Intent intent = new Intent(getIntent());
		mLoginYN = intent.getStringExtra("loginYN");
		mainloginid = intent.getStringExtra("mainloginid");
		mainloginpass = intent.getStringExtra("mainloginpass");
		String message = intent.getStringExtra("message");
		admin = intent.getBooleanExtra("admin", false);
		general = intent.getBooleanExtra("general", false);
		
		MenuControll menuControll = new MenuControll();
		menuControll.setmLoginYN(mLoginYN);
		menuControll.setLoginId(mainloginid);
		menuControll.setLoginPass(mainloginpass);
		
		if("N".equals(mLoginYN))
		{
			SharedPreferences prefs = getSharedPreferences("autologin", MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putBoolean("autobox", false);
			editor.commit();
			boolean box = prefs.getBoolean("autobox",false);
			Log.d("DEBUG","logout!! autobox = " + box);
		}
		else if("Y".equals(mLoginYN)){
			user3.setVisibility(View.GONE);
			user.setVisibility(View.VISIBLE);
			user.setText(Html.fromHtml("<u>" + mainloginid + "</u>"));
			//user.setText(mainloginid);
			//loginimage.setVisibility(View.VISIBLE);
			if(message.equals("Y")){
				MenuControll.Toastshow(getApplicationContext());
			}
		}
    }
    
    public static void autologin(Context context) 
    {
    	Log.d("DEBUG","autologin call");
    	SharedPreferences prefs = context.getSharedPreferences("autologin", MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();	
		editor.putBoolean("autobox", true);    // Boolean
		editor.commit();
    }

    @Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user3:
			Intent intent = new Intent(getApplicationContext(), MemberShipLoginActivity.class);
			getApplicationContext().startActivity(intent);
			Log.d(TAG,"user3");
			break;
		case R.id.lan:
			if(language.equals("ko")){
				Locale en = Locale.ENGLISH;
				Configuration config = new Configuration();
				config.locale = en;
				getResources().updateConfiguration(config, getResources().getDisplayMetrics());
				
				
				Intent intent2 = new Intent(getApplicationContext(), IntroActivity.class);
				intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getApplicationContext().startActivity(intent2);
			}
			else{
				Locale ko = Locale.KOREA;
				Configuration config2 = new Configuration();
				config2.locale = ko;
				getResources().updateConfiguration(config2, getResources().getDisplayMetrics());
				
				Intent intent3 = new Intent(getApplicationContext(), IntroActivity.class);
				intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getApplicationContext().startActivity(intent3);
			}
			break;
		}
	}
    
	public class SlideAdapter extends BaseAdapter {
		private final Context mContext;
		LayoutInflater inflater;
		
		public SlideAdapter(Context c){
			mContext = c;
			inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		}
		
		public int getCount(){
			return SlideImage.size();
		}
		
		@Override
		public Object getItem(int position) {
			return position;
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null){
				convertView = inflater.inflate(R.layout.slideitem, parent, false);
				
			}
			TextView slidetext = (TextView) convertView.findViewById(R.id.slideview);
			slidetext.setText(SlideImage.get(position));
			
			return convertView;
			
		}
	}
	
    
	public void onBackPressed() {
			
	        if (System.currentTimeMillis() > backKeyPressedTime + 2000) 
	        {
	            backKeyPressedTime = System.currentTimeMillis();
	            toast = Toast.makeText(this,getResources().getString(R.string.exit), Toast.LENGTH_SHORT);
	            toast.show();
	            return;
	        }
	        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) 
	        {
	        	moveTaskToBack(true);
	        	android.os.Process.killProcess(android.os.Process.myPid());
	            toast.cancel();
	        }
	    }

}
