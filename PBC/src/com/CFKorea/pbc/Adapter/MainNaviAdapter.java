package com.CFKorea.pbc.Adapter;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Activity.IntroActivity;
import com.CFKorea.pbc.Activity.MainActivity;
import com.CFKorea.pbc.Activity.MemberShipLoginActivity;
import com.CFKorea.pbc.Activity.MyUploadActivity;
import com.CFKorea.pbc.Activity.QnaActivity;
import com.CFKorea.pbc.Utility.CustomDialog;
import com.CFKorea.pbc.ViewHolder.MainNaviViewHolder;

/** 
* @FileName      : MainNaviAdapter.java 
* @Project    	 : Halo_2nd_MC 
* @Date          : 2014. 11. 28. 
* @작성자        : jypark@cftown.co.kr 
* @변경이력      : 
*/
public class MainNaviAdapter extends BaseAdapter {

	
	/** Log Tag */
	private final String TAG = "MainNaviAdapter";
	private ArrayList<String> mArrayList = new ArrayList<String>();
	private Context mContext;
	private MainNaviViewHolder mViewHolder = null;
	private LayoutInflater mInflater = null;
	private CustomDialog mCustomDialog;
	Locale locale;
	String language;
	
	private String sLoginChek = "";
	
	public String getsLoginChek() {
		return sLoginChek;
	}

	public void setsLoginChek(String sLoginChek) {
		this.sLoginChek = sLoginChek;
	}
	
	
//	
//	if(position==0)
//	{
//		SharedPreferences prefs = getSharedPreferences("autologin", MODE_PRIVATE);
//		SharedPreferences.Editor editor = prefs.edit();
//		String login = prefs.getString("login", "");
//		if(login == "Y"){
//        	startActivity(new Intent(getApplicationContext() , MemberShipInfo.class));
//			}
//			else{
//				startActivity(new Intent(getApplicationContext() , MemberShipActivity.class));	
//			}
//	}
	
	private Class<?>[] activitys = {
			QnaActivity.class,
			QnaActivity.class,
			QnaActivity.class,
			QnaActivity.class};
	//*로그인후*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private Class<?>[] activitys2 = {
			MainActivity.class};
	
	private int[] navItemsResources = { 	
			R.drawable.btn_navigation_login,  
			R.drawable.btn_navigation_card,  
			R.drawable.btn_navigation_qna,  
			R.drawable.btn_navigation_infomaition
			};
	
	public MainNaviAdapter(Context context, ArrayList<String> arrayList) {
		this.mContext = context;
		this.mArrayList = arrayList;
		this.mInflater = LayoutInflater.from(context);
		locale = mContext.getResources().getConfiguration().locale;
        language = locale.getLanguage();
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mArrayList.get( position );
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
        Log.d("getView", "Navi Dapter");
        if(view == null) 
        {
        	mViewHolder = new MainNaviViewHolder();
        	view = mInflater.inflate(R.layout.list_main_navi_item, null);
        	
        	mViewHolder.imageView = (ImageView)view.findViewById(R.id.imageView);
        	mViewHolder.textView = (TextView)view.findViewById(R.id.textview);
        	
        	view.setTag(mViewHolder);
             
        }
        else 
        {
        	mViewHolder = (MainNaviViewHolder)view.getTag();
        }
        mViewHolder.imageView.setBackgroundResource(navItemsResources[position]);
        mViewHolder.textView.setText(getItem(position).toString());
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
				if (0 == position)
				{
					if("Y".equals(sLoginChek))
					{ 
//						Log.d(TAG,"sLoginChek = " + sLoginChek);
//						AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//
//						builder.setTitle("로그아웃 하시겠습니까?");
//						
//						builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
//						           public void onClick(DialogInterface dialog, int id) {
//						        	
//						           }
//						       });
//						builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
//						           public void onClick(DialogInterface dialog, int id) {
//						        	   Intent intent = new Intent(mContext, MainActivity.class);
//										intent.putExtra("loginYN", "N");
//										intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//										mContext.startActivity(intent);
//										Toast.makeText(mContext,"로그아웃 되었습니다",Toast.LENGTH_SHORT).show();
//										MainActivity.user.setVisibility(View.GONE);
//										MainActivity.user2.setVisibility(View.GONE);
//						           }
//						       });
//						AlertDialog dialog = builder.create();
//						dialog.show();
						try{
						mCustomDialog = new CustomDialog(mContext,null,mContext.getResources().getString(R.string.logout_asking),leftClickListener,rightClickListener);
						mCustomDialog.show();
						}
						catch(Exception e){
							Log.d(TAG,"========================================ERROR============================================");
							Log.d(TAG,"Exception = "  + e);
							android.os.Process.killProcess(android.os.Process.myPid());
						}
						
					}
					else
					{
						try{
						Intent intent = new Intent(mContext, MemberShipLoginActivity.class);
						mContext.startActivity(intent);
						}
						catch(Exception e){
							Log.d(TAG,"Exception = "  + e);
							android.os.Process.killProcess(android.os.Process.myPid());
						}
					}
				}
				else if(1 == position){
						if(language.equals("ko")){
							try{
								Locale en = Locale.ENGLISH;
								Configuration config = new Configuration();
								config.locale = en;
								mContext.getResources().updateConfiguration(config, mContext.getResources().getDisplayMetrics());
								
								
								Intent intent2 = new Intent(mContext, IntroActivity.class);
								intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								mContext.startActivity(intent2);
							}
							catch(Exception e){
								Log.d(TAG,"========================================ERROR============================================");
								Log.d(TAG,"Exception = "  + e);
								android.os.Process.killProcess(android.os.Process.myPid());
							}
						}
						else{
							try{
								Locale ko = Locale.KOREA;
								Configuration config2 = new Configuration();
								config2.locale = ko;
								mContext.getResources().updateConfiguration(config2, mContext.getResources().getDisplayMetrics());
								
								Intent intent3 = new Intent(mContext, IntroActivity.class);
								intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								mContext.startActivity(intent3);
							}
							catch(Exception e){
								Log.d(TAG,"========================================ERROR============================================");
								Log.d(TAG,"Exception = "  + e);
								android.os.Process.killProcess(android.os.Process.myPid());
							}
						}
					
				}
				
				else if(2 == position){
					try{
					Intent intent = new Intent(mContext, MyUploadActivity.class);
					mContext.startActivity(intent);
					}
					catch(Exception e){
						Log.d(TAG,"========================================ERROR============================================");
						Log.d(TAG,"Exception = "  + e);
						android.os.Process.killProcess(android.os.Process.myPid());
					}
				}
				
//				if (position == 1)
//				{
//					Intent intent = new Intent(mContext, activitys[1]);
//					mContext.startActivity(intent);
//				}
//				if (position == 2)
//				{
//					//Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://115.68.182.113/MusicCard/board/qnaBoardList.jsp"));
//					Intent intent = new Intent(mContext, activitys[2]);
//					mContext.startActivity(intent);
//				}
//				if (position == 3)
//				{
//					
//					//MenuControll.Toastshow2(mContext);
//					Intent intent = new Intent(mContext, activitys[3]);
//					mContext.startActivity(intent);
//				}
            }
        });
    	
		return view;
	}
	
	private View.OnClickListener leftClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			try{
				mCustomDialog.dismiss();
				Intent intent = new Intent(mContext, MainActivity.class);
				intent.putExtra("loginYN", "N");
				intent.putExtra("admin", false);
				intent.putExtra("general", false);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				mContext.startActivity(intent);
				Toast.makeText(mContext,mContext.getResources().getString(R.string.logout_success),Toast.LENGTH_SHORT).show();
				MainActivity.user.setVisibility(View.GONE);
				MainActivity.loginimage.setVisibility(View.GONE);
				MainActivity.user3.setVisibility(View.VISIBLE);
			}
			catch(Exception e){
				Log.d(TAG,"Exception = "  + e);
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		}
	};

	private View.OnClickListener rightClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mCustomDialog.dismiss();
			
		}
	};
	

	
}
