package com.CFKorea.pbc.Activity;

import java.io.File;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toast;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Adapter.MyStudioPhotoAdapter;
import com.CFKorea.pbc.Adapter.MyStudioPhotoDeleteAdpater;
import com.CFKorea.pbc.ViewHolder.PhotoViewholder;

public class MyStudioPhotoActivity extends Fragment implements OnClickListener{

	private final String TAG = "[MyStudioPhotoActivity]";
	public static ArrayList<PhotoViewholder> mPhotoThumNailViewHolder;
	public static ArrayList<String> gallerylist = new ArrayList<String>();
	
	public GridView mGridview;
	public CheckBox select;
	private ImageButton delete;
	private ImageButton share;
	public static CheckBox all;
	public static Context mContext;
	public MyStudioPhotoDeleteAdpater m;
	public ArrayList<String> selectList;
	public ArrayList<String> shareList;
	public ArrayList<Uri> shareUri;
	public static String deleteNotify = "N";
	
	
	public MyStudioPhotoActivity(){
		m = new MyStudioPhotoDeleteAdpater(mContext, mPhotoThumNailViewHolder);
	}
	
	public MyStudioPhotoActivity(Context applicationContext, ArrayList<PhotoViewholder> holder) {
		Log.d(TAG,TAG);
		mContext = applicationContext;
		mPhotoThumNailViewHolder = holder;
		m = new MyStudioPhotoDeleteAdpater(mContext, mPhotoThumNailViewHolder);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		
		View view = inflater.inflate(R.layout.activity_mystudiophoto, container, false);
		select = (CheckBox) view.findViewById(R.id.select);
		delete = (ImageButton) view.findViewById(R.id.delete);
		delete.setOnClickListener(this);
		share = (ImageButton) view.findViewById(R.id.share);
		share.setOnClickListener(this);
		all = (CheckBox) view.findViewById(R.id.all);
		mGridview = (GridView) view.findViewById(R.id.GridView);
		
		mGridview.setAdapter(new MyStudioPhotoAdapter(mContext, mPhotoThumNailViewHolder));
		//2015.1.22 leejaegu 삭제기능 추가[[
		select.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked == false )
				{
					mGridview.setAdapter(new MyStudioPhotoAdapter(mContext, mPhotoThumNailViewHolder));
					select.setButtonDrawable(R.drawable.btn_mystudio_select);
					delete.setVisibility(View.INVISIBLE);
					share.setVisibility(View.INVISIBLE);
					all.setVisibility(View.INVISIBLE);
					for(int i=0;i < mPhotoThumNailViewHolder.size();i++){
						mPhotoThumNailViewHolder.get(i).setCheckedState(false);
					}
					MyStudioPhotoDeleteAdpater.selectList = new ArrayList<String>();
					MyStudioPhotoDeleteAdpater.shareList = new ArrayList<String>();
					all.setChecked(false);
				}
				else
				{
					//m = new MyStudioPhotoDeleteAdapter(mContext, mPhotoThumNailViewHolder);
					mGridview.setAdapter(m);
					select.setButtonDrawable(R.drawable.btn_mystudio_cancel_pressed);
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
			selectList = m.selectList;
			Log.d("","current selectList.Count = " + selectList.size());
			for(int i = 0;  i<selectList.size(); i++)
			{
				Log.d(TAG,"size = " +selectList.size());
				Log.d(TAG,"selectList"+ selectList.get(i));
			}
			if(selectList.size()!=0){
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

			builder.setTitle("삭제 하시겠습니까?");
			
			builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	
			           }
			       });
			builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	delete(selectList);
			        	deleteNotify = "Y";
			   			Intent intent = new Intent(mContext, MyStudioActivity.class);
			   			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			   			mContext.startActivity(intent);
			   			
			           }
			       });
			

			AlertDialog dialog = builder.create();
			dialog.show();
			}
			else{
				Toast.makeText(mContext, "선택한 이미지가 없습니다.", Toast.LENGTH_SHORT).show();
			}
			break;
			
			//2015.1.27 share기능 추가
		case R.id.share:
			shareList = new ArrayList<String>();
			shareUri = new ArrayList<Uri>();
			shareList = m.shareList;
			
				for(int a = 0; a<shareList.size(); a++){
				String k = shareList.get(a);
				Log.d(TAG,"SHARE = " + k);
				
				File imageFileToShare = new File(k);
				Uri uri = Uri.fromFile(imageFileToShare);
				shareUri.add(uri);
				}
				if(shareList.size()!=0){
					Intent intentSend  = new Intent(Intent.ACTION_SEND_MULTIPLE);
					intentSend.setType("image/*");
					intentSend.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intentSend.putExtra(Intent.EXTRA_STREAM, shareUri);
					
					mContext.startActivity(Intent.createChooser(intentSend, "공유"));
				}
				else{
					Toast.makeText(mContext, "선택한 이미지가 없습니다.", Toast.LENGTH_SHORT).show();
				}
				
				
				//mContext.startActivity(intentSend);
			break;
		}
	}
	
	public void delete(ArrayList<String> array)
	{
		for(int i = 0;  i<array.size(); i++){
			String id = array.get(i);
			Log.d(TAG,"delete id = " + id);
			mContext.getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "_ID='"+ id +"'", null);
		}
		
	}
}
