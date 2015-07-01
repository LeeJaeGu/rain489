package com.CFKorea.pbc.Adapter;

import java.util.ArrayList;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.ViewHolder.PhotoViewholder;

public class MyStudioPhotoDeleteAdpater extends BaseAdapter {
	
	/** Log Tag */
	private final String TAG = "[MyStudioPhotoDeleteAdpater]";
	public ArrayList<PhotoViewholder> mPhotoThumNailViewHolder;
	private PhotoViewholder mPhotoThumNailHolder;
	public Context mContext;
	public ViewHolder mViewHolder;
	public LayoutInflater mInflater = null;
	public ArrayList<String> mGallerylist;
	public static ArrayList<String> selectList;
	public static ArrayList<String> shareList;
	protected static ArrayList<Uri> shareUri;
	
	public MyStudioPhotoDeleteAdpater(){
		
	}
	
	public MyStudioPhotoDeleteAdpater(Context context, ArrayList<PhotoViewholder> holder) {
		Log.d("DEBUG","Adapter START");
		mGallerylist = new ArrayList<String>();
		mContext = context;
		mPhotoThumNailViewHolder = holder;
//		this.gallerylist = gallerylist;
		this.mInflater = LayoutInflater.from(mContext);
		selectList = new ArrayList<String>();
		shareList = new ArrayList<String>();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mPhotoThumNailViewHolder.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
//	2015.2.3 leejaegu 이미지 삭제 버그 수정 및 전체선택 추가[[
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//Log.d("","getViewgetViewgetViewgetViewgetViewgetViewgetViewgetViewgetViewgetViewgetView");
		View view = convertView;
        if (view == null) 
        {
        	mViewHolder = new ViewHolder();
        	view = mInflater.inflate(R.layout.list_select, null);
        	mViewHolder.thumnail_CheckBox = (CheckBox)view.findViewById(R.id.select_item);
        	mViewHolder.thumnail_ImageView = (ImageView)view.findViewById(R.id.photogallery_Thumnail);
        	mViewHolder.thumnail_ImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        	view.setPadding(3, 3, 3, 3);
        	mViewHolder.thumnail_CheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() 
    		{
    			@Override
    			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    				// TODO Auto-generated method stub
    				
    				if (isChecked == true )
    				{
    					mPhotoThumNailViewHolder.get(position).setCheckedState(true);
    					String j = mPhotoThumNailViewHolder.get(position).getId();
    					String k = mPhotoThumNailViewHolder.get(position).getData();
    					Log.d(TAG,"add id1 = " + j);
    					selectList.add(j);
    					shareList.add(k);
    					
    					 for (int i = 0; i < selectList.size(); i++) {
    					        String str1 = selectList.get(i);
    					        for (int g = 0; g < selectList.size(); g++) {
    					            if (i == g) continue;
    					            String str2 = selectList.get(g);
    					            if (str1.equals(str2)) {
    					            	Log.d(TAG,"i = " + i);
    					            	selectList.remove(i);
    					                break;
    					            }
    					        }
    					    }
    					 
    					 for (int i = 0; i < shareList.size(); i++) {
 					        String str3 = shareList.get(i);
 					        for (int g = 0; g < shareList.size(); g++) {
 					            if (i == g) continue;
 					            String str4 = shareList.get(g);
 					            if (str3.equals(str4)) {
 					            	Log.d(TAG,"i = " + i);
 					            	shareList.remove(i);
 					                break;
 					            }
 					        }
 					    }
    					 
    					 Log.d("","Count = " + selectList.size());
    					 
    				}
    				else
    				{
    					mPhotoThumNailViewHolder.get(position).setCheckedState(false);
    					String j = mPhotoThumNailViewHolder.get(position).getId();
    					String k = mPhotoThumNailViewHolder.get(position).getData();
    					Log.d(TAG,"remove id1 = " + j);
    					selectList.remove(j);
    					shareList.remove(k);
    					Log.d(TAG,"current selectList.Count = " + selectList.size());
    				}
    			}
    		});
        	view.setTag(mViewHolder);
        }
        else
        {
        	mViewHolder = (ViewHolder)view.getTag();
        	mViewHolder.thumnail_CheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() 
    		{
    			@Override
    			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    				// TODO Auto-generated method stub
    				
    				if (isChecked == true )
    				{
    					mPhotoThumNailViewHolder.get(position).setCheckedState(true);
    					String j = mPhotoThumNailViewHolder.get(position).getId();
    					String k = mPhotoThumNailViewHolder.get(position).getData();
    					Log.d(TAG,"add id2 = " + j);
    					selectList.add(j);
    					shareList.add(k);
    					
    					for (int i = 0; i < selectList.size(); i++) {
					        String str1 = selectList.get(i);
					        for (int g = 0; g < selectList.size(); g++) {
					            if (i == g) continue;
					            String str2 = selectList.get(g);
					            if (str1.equals(str2)) {
					            	Log.d(TAG,"i = " + i);
					            	selectList.remove(i);
					            	Log.d(TAG,"Count = " + selectList.size());
					                break;
					            }
					        }
					    }
    					
    					for (int i = 0; i < shareList.size(); i++) {
 					        String str3 = shareList.get(i);
 					        for (int g = 0; g < shareList.size(); g++) {
 					            if (i == g) continue;
 					            String str4 = shareList.get(g);
 					            if (str3.equals(str4)) {
 					            	Log.d("","i = " + i);
 					            	shareList.remove(i);
 					                break;
 					            }
 					        }
 					    }
    					
    					Log.d("","Count = " + selectList.size());
    				}
    				else
    				{
    					mPhotoThumNailViewHolder.get(position).setCheckedState(false);
    					String j = mPhotoThumNailViewHolder.get(position).getId();
    					String k = mPhotoThumNailViewHolder.get(position).getData();
    					Log.d(TAG,"remove id2 = " + j);
    					selectList.remove(j);
    					shareList.remove(k);
    					Log.d(TAG,"current selectList.Count = " + selectList.size());
    				}
    			}
    		});
        }
        
        mViewHolder.thumnail_ImageView.setImageBitmap(mPhotoThumNailViewHolder.get(position).getThumnail());
        mViewHolder.thumnail_CheckBox.setChecked(mPhotoThumNailViewHolder.get(position).getCheckedState());
        
        
        
        return view;
        //]]
	}
	public void dropall(){
		Log.d(TAG,"dropall()");
		selectList = new ArrayList<String>();
		shareList = new ArrayList<String>();
		for(int p=0; p<getCount();p++){
			mPhotoThumNailViewHolder.get(p).setCheckedState(true);
			String j = mPhotoThumNailViewHolder.get(p).getId();
			selectList.add(j);
		}
		Log.d(TAG,"current selectList.Count = " + selectList.size());
		notifyDataSetChanged();
	}
	public void dropallcancel(){
		selectList = new ArrayList<String>();
		shareList = new ArrayList<String>();
		for(int p=0; p<getCount();p++){
			mPhotoThumNailViewHolder.get(p).setCheckedState(false);
			String j = mPhotoThumNailViewHolder.get(p).getId();
			selectList.remove(j);
		}
		Log.d(TAG,"current selectList.Count = " + selectList.size());
		notifyDataSetChanged();
	}
	public ArrayList<String> getPhotoData()
	{
		for(int i = 0; i < mPhotoThumNailViewHolder.size(); i++)
		{
			mGallerylist.add(mPhotoThumNailViewHolder.get(i).getData());
			Log.d(TAG,"getdate = " + mPhotoThumNailViewHolder.get(i).getData());
		}
		
		return mGallerylist;
	}
	public class ViewHolder {
		public CheckBox thumnail_CheckBox;
		/** Log Tag */
		private ImageView thumnail_ImageView;
	}
	
	public void selected(String i)
	{
		
		selectList.add(i);
	}
	
}
