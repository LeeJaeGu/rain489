package com.CFKorea.pbc.Activity;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Adapter.MyStudioVoiceAdapter;
import com.CFKorea.pbc.Adapter.MyStudioVoiceDeleteAdapter;
import com.CFKorea.pbc.Utility.MenuControll;
import com.CFKorea.pbc.ViewHolder.VoiceViewholder;

public class MyStudioVoiceActivity extends Fragment implements OnClickListener{

	private final String TAG = "MyStudioVoiceFragment";
	public static ArrayList<VoiceViewholder> mVoiceRecordingViewHolder;
	public ListView mListdview;
	public CheckBox select;
	private ImageButton delete;
	private ImageView share;
	public static CheckBox all;
	public static Context mContext;
	private MyStudioVoiceDeleteAdapter m;
	public ArrayList<String> selectList;
	public ArrayList<String> shareList;
	public ArrayList<Uri> shareUri;
	public static String deleteNotify = "N";
	public static String k;
	File videoFileToShare;
	URLConnection con;
	String result;
	MultipartEntityBuilder builder;
	
	public MyStudioVoiceActivity(){
		m = new MyStudioVoiceDeleteAdapter(mContext, mVoiceRecordingViewHolder);
	}
	
	public MyStudioVoiceActivity(Context applicationContext, ArrayList<VoiceViewholder> holder) {
		mContext = applicationContext;
		//2015.1.16 leejaegu mystudio의 배열을 holder로 받아옴
		mVoiceRecordingViewHolder = holder;
		m = new MyStudioVoiceDeleteAdapter(mContext, mVoiceRecordingViewHolder);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.activity_mystudiovoice, container, false);
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		.detectDiskReads() 
		.detectDiskWrites()
		.detectNetwork() 
		.penaltyLog().build()); 
		
		select = (CheckBox) view.findViewById(R.id.select);
		delete = (ImageButton) view.findViewById(R.id.delete);
		delete.setOnClickListener(this);
		share = (ImageView) view.findViewById(R.id.share);
		share.setOnClickListener(this);
		all = (CheckBox) view.findViewById(R.id.all);
		mListdview = (ListView) view.findViewById(R.id.voicelist);
		
		mListdview.setAdapter(new MyStudioVoiceAdapter(mContext, mVoiceRecordingViewHolder));
		//2015.1.22 leejaegu 삭제기능 추가[[
		select.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked == false )
				{
					mListdview.setAdapter(new MyStudioVoiceAdapter(mContext, mVoiceRecordingViewHolder));
					//select.setButtonDrawable(R.drawable.btn_mystudio_select);
					select.setText(getResources().getString(R.string.select));
					delete.setVisibility(View.INVISIBLE);
					share.setVisibility(View.INVISIBLE);
					all.setVisibility(View.INVISIBLE);
					for(int i=0;i < mVoiceRecordingViewHolder.size();i++){
						mVoiceRecordingViewHolder.get(i).setCheckedState(false);
					}
					MyStudioVoiceDeleteAdapter.selectList = new ArrayList<String>();
					MyStudioVoiceDeleteAdapter.shareList = new ArrayList<String>();
					all.setChecked(false);
				}
				else
				{
					mListdview.setAdapter(m);
					//select.setButtonDrawable(R.drawable.btn_mystudio_cancel_pressed);
					select.setText(getResources().getString(R.string.cancel));
					delete.setVisibility(View.VISIBLE);
					share.setVisibility(View.VISIBLE);
					all.setVisibility(View.VISIBLE);
					
					
				}
			}
		});
		//2015.2.3 leejaegu 전체선택 추가[[
		all.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked == true )
				{
					all.setButtonDrawable(R.drawable.btn_mystudio_all_pressed);
					m.dropall();
				}
				else
				{
					all.setButtonDrawable(R.drawable.btn_mystudio_all);
					m.dropallcancel();
				}
			}
		});
		//]]
		return view;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.delete:
			selectList = new ArrayList<String>();
			shareList = new ArrayList<String>();
			shareUri = new ArrayList<Uri>();
			selectList = m.selectList;
			shareList = m.shareList;
			
			for(int i = 0;  i<selectList.size(); i++)
			{
				Log.d(TAG,"size = " +selectList.size());
				Log.d(TAG,"selectList"+ selectList.get(i));
			}
			if(selectList.size()!=0){
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

			builder.setTitle(mContext.getResources().getString(R.string.delete_asking));
			
			builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	
			           }
			       });
			builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	delete(selectList,shareList);
			        	deleteNotify = "Y";
			   			Intent intent = new Intent(mContext, MyStudioActivity.class);
			   			intent.putExtra("video", 3);
			   			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			   			mContext.startActivity(intent);
			           }
			       });
			
			

			AlertDialog dialog = builder.create();
			dialog.show();
			}
			else{
				Toast.makeText(mContext, mContext.getResources().getString(R.string.choice), Toast.LENGTH_SHORT).show();
			}
			break;
			
			//2015.1.27 share기능 추가
//		case R.id.share:
//			shareList = new ArrayList<String>();
//			shareUri = new ArrayList<Uri>();
//			shareList = m.shareList;
//			
//			for(int o = 0;o<shareList.size(); o++){
//				String k = shareList.get(o);
//				Log.d(TAG,"SHARE = " + k);
//			
//				File videoFileToShare = new File(k);
//				Uri uri = Uri.fromFile(videoFileToShare);
//				shareUri.add(uri);
//			}
//			if(shareList.size()!=0){
//			 Intent intentSend  = new Intent(Intent.ACTION_SEND_MULTIPLE);
//			 intentSend.setType("video/*");
//			 intentSend.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			 intentSend.putExtra(Intent.EXTRA_STREAM, shareUri);
//			 mContext.startActivity(Intent.createChooser(intentSend, "공유"));
//			}
//			else{
//				Toast.makeText(mContext, "선택한 파일이 없습니다.", Toast.LENGTH_SHORT).show();
//			}
//			break;
		case R.id.share:
			shareList = new ArrayList<String>();
			shareUri = new ArrayList<Uri>();
			shareList = m.shareList;
			
			for(int o = 0;o<shareList.size(); o++){
				k = shareList.get(o);
				Log.d(TAG,"k = " + k);
			
				videoFileToShare = new File(k);
				Uri uri = Uri.fromFile(videoFileToShare);
				shareUri.add(uri);
			}
			if(shareList.size()!=0){
				Intent intentSend  = new Intent(Intent.ACTION_SEND_MULTIPLE);
				intentSend.setType("audio/*");
				intentSend.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intentSend.putExtra(Intent.EXTRA_STREAM, shareUri);
				
				mContext.startActivity(Intent.createChooser(intentSend, mContext.getResources().getString(R.string.share)));
			}
			else{
				Toast.makeText(mContext, mContext.getResources().getString(R.string.choice), Toast.LENGTH_SHORT).show();
			}
			
			break;
		}
	}
	
	
	
	
	
	
	public void delete(ArrayList<String> array,ArrayList<String> array2)
	{
		Log.d(TAG,"array.size = " + array.size());
		Log.d(TAG,"array2.size = " + array2.size());
		for(int i = 0;  i<array.size(); i++){
			String id = array.get(i);
			String data = array2.get(i);
			Log.d(TAG,"data = " + data);
			File file = new File(data);
			Log.d(TAG,"id = " + id);
			mContext.getContentResolver().delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "_ID='"+ id +"'", null);
			mContext.getContentResolver().delete(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "_ID='"+ id +"'", null);
			file.delete();
		}
		
		
	}
	
}
