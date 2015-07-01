package com.CFKorea.pbc.Activity;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Adapter.MyStudioVideoAdapter;
import com.CFKorea.pbc.Adapter.MyStudioVideoDeleteAdapter;
import com.CFKorea.pbc.Utility.MenuControll;
import com.CFKorea.pbc.ViewHolder.VideoViewHolder;

public class MyStudioVideoActivity extends Fragment implements OnClickListener{

	private final String TAG = "[MyStudioVideoActivity]";
	public static ArrayList<VideoViewHolder> mMusicVideoViewHolder;
	public GridView mGridview;
	public CheckBox select;
	private ImageButton delete;
	private ImageView share;
	public static CheckBox all;
	public static Context mContext;
	private MyStudioVideoDeleteAdapter m;
	public static ArrayList<String> selectList;
	public static ArrayList<String> shareList;
	public static ArrayList<Uri> shareUri;
	public static String deleteNotify = "N";
	public static String k;
	File videoFileToShare;
	
	public MyStudioVideoActivity(){
		m = new MyStudioVideoDeleteAdapter(mContext, mMusicVideoViewHolder);
	}
	
	public MyStudioVideoActivity(Context applicationContext, ArrayList<VideoViewHolder> holder) {
		mContext = applicationContext;
		//2015.1.16 leejaegu mystudio의 배열을 holder로 받아옴
		mMusicVideoViewHolder = holder;
		m = new MyStudioVideoDeleteAdapter(mContext, mMusicVideoViewHolder);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.activity_mystudiovideo, container, false);
		
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
		mGridview = (GridView) view.findViewById(R.id.videogridview);
		
		mGridview.setAdapter(new MyStudioVideoAdapter(mContext, mMusicVideoViewHolder));
		//2015.1.22 leejaegu 삭제기능 추가[[
		select.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked == false )
				{
					mGridview.setAdapter(new MyStudioVideoAdapter(mContext, mMusicVideoViewHolder));
					//select.setButtonDrawable(R.drawable.btn_mystudio_select);
					select.setText(getResources().getString(R.string.select));
					delete.setVisibility(View.INVISIBLE);
					share.setVisibility(View.INVISIBLE);
					all.setVisibility(View.INVISIBLE);
					for(int i=0;i < mMusicVideoViewHolder.size();i++){
						mMusicVideoViewHolder.get(i).setCheckedState(false);
					}
					MyStudioVideoDeleteAdapter.selectList = new ArrayList<String>();
					MyStudioVideoDeleteAdapter.shareList = new ArrayList<String>();
					all.setChecked(false);
				}
				else
				{
					mGridview.setAdapter(m);
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
			shareUri = new ArrayList<Uri>();
			selectList = m.selectList;
			
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
			        	delete(selectList);
			        	deleteNotify = "Y";
			   			Intent intent = new Intent(mContext, MyStudioActivity.class);
			   			intent.putExtra("video", 2);
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
		case R.id.share:
			shareList = new ArrayList<String>();
			shareUri = new ArrayList<Uri>();
			shareList = m.shareList;
			
			for(int o = 0;o<shareList.size(); o++){
				k = shareList.get(o);
				Log.d(TAG,"SHARE = " + k);
			
				videoFileToShare = new File(k);
				Uri uri = Uri.fromFile(videoFileToShare);
				shareUri.add(uri);
			}
			if(shareList.size()!=0){
				Intent intentSend  = new Intent(Intent.ACTION_SEND_MULTIPLE);
				intentSend.setType("video/*");
				intentSend.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intentSend.putExtra(Intent.EXTRA_STREAM, shareUri);
				
				mContext.startActivity(Intent.createChooser(intentSend, "공유"));
			}
			else{
				Toast.makeText(mContext, mContext.getResources().getString(R.string.choice), Toast.LENGTH_SHORT).show();
			}			break;
		}
	}
	
	
	
	
	
	public String sendFileToServer(String filename, String targetUrl) {
	    String response = "error";
	    Log.e("Image filename", filename);
	    Log.e("url", targetUrl);
	    HttpURLConnection connection = null;
	    DataOutputStream outputStream = null;
	    // DataInputStream inputStream = null;

	    String pathToOurFile = filename;
	    String urlServer = targetUrl;
	    String lineEnd = "\r\n";
	    String twoHyphens = "--";
	    String boundary = "*****";
	    DateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss");

	    int bytesRead, bytesAvailable, bufferSize;
	    byte[] buffer;
	    int maxBufferSize = 1 * 1024;
	    try {
	        FileInputStream fileInputStream = new FileInputStream(new File(
	                pathToOurFile));

	        URL url = new URL(urlServer);
	        connection = (HttpURLConnection) url.openConnection();

	        // Allow Inputs & Outputs
	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        connection.setUseCaches(false);
	        connection.setChunkedStreamingMode(1024);
	        // Enable POST method
	        connection.setRequestMethod("POST");

	        connection.setRequestProperty("Connection", "Keep-Alive");
	        connection.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);

	        outputStream = new DataOutputStream(connection.getOutputStream());
	        outputStream.writeBytes(twoHyphens + boundary + lineEnd);

	        String connstr = null;
	        connstr = "Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
	                + pathToOurFile + "\"" + lineEnd;
	        Log.i("Connstr", connstr);

	        outputStream.writeBytes(connstr);
	        outputStream.writeBytes(lineEnd);

	        bytesAvailable = fileInputStream.available();
	        bufferSize = Math.min(bytesAvailable, maxBufferSize);
	        buffer = new byte[bufferSize];

	        // Read file
	        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
	        Log.e("Image length", bytesAvailable + "");
	        try {
	            while (bytesRead > 0) {
	                try {
	                    outputStream.write(buffer, 0, bufferSize);
	                } catch (OutOfMemoryError e) {
	                    e.printStackTrace();
	                    response = "outofmemoryerror";
	                    return response;
	                }
	                bytesAvailable = fileInputStream.available();
	                bufferSize = Math.min(bytesAvailable, maxBufferSize);
	                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            response = "error";
	            return response;
	        }
	        outputStream.writeBytes(lineEnd);
	        outputStream.writeBytes(twoHyphens + boundary + twoHyphens
	                + lineEnd);

	        // Responses from the server (code and message)
	        int serverResponseCode = connection.getResponseCode();
	        String serverResponseMessage = connection.getResponseMessage();
	        Log.i("Server Response Code ", "" + serverResponseCode);
	        Log.i("Server Response Message", serverResponseMessage);

	        if (serverResponseCode == 200) {
	            response = "true";
	        }

	        String CDate = null;
	        Date serverTime = new Date(connection.getDate());
	        try {
	            CDate = df.format(serverTime);
	        } catch (Exception e) {
	            e.printStackTrace();
	            Log.e("Date Exception", e.getMessage() + " Parse Exception");
	        }
	        Log.i("Server Response Time", CDate + "");

	        filename = CDate
	                + filename.substring(filename.lastIndexOf("."),
	                        filename.length());
	        Log.i("File Name in Server : ", filename);

	        fileInputStream.close();
	        outputStream.flush();
	        outputStream.close();
	        outputStream = null;
	    } catch (Exception ex) {
	        // Exception handling
	        response = "error";
	        Log.e("Send file Exception", ex.getMessage() + "");
	        ex.printStackTrace();
	    }
	    return response;
	}
	
	public void delete(ArrayList<String> array)
	{
		for(int i = 0;  i<array.size(); i++){
			String id = array.get(i);
			mContext.getContentResolver().delete(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "_ID='"+ id +"'", null);
		}
		
		
	}
	
}
