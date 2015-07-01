package com.CFKorea.pbc.Adapter;

import java.util.ArrayList;

import android.content.Context;
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
import com.CFKorea.pbc.ViewHolder.VideoViewHolder;

public class MyStudioVideoDeleteAdapter extends BaseAdapter {

	/** Log Tag */
	private final String TAG = "[MyStudioVideoDeleteAdapter]";
	
	public static ArrayList<VideoViewHolder> mMusicVideoViewHolder;
	public static Context mContext;
	private ViewHolder mViewHolder;
	private LayoutInflater mInflater = null;
	public static ArrayList<String> selectList;
	//2015.1.27 share기능 추가
	public static ArrayList<String> shareList;
	
	public MyStudioVideoDeleteAdapter(Context context, ArrayList<VideoViewHolder> holder) {
		
		Log.d(TAG,TAG);
		if(context != null){
		mContext = context;
		}
		mMusicVideoViewHolder = holder;
		this.mInflater = LayoutInflater.from(context);
		selectList = new ArrayList<String>();
		shareList = new ArrayList<String>();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mMusicVideoViewHolder.size();
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
//	2015.2.3 leejaegu 동영상 삭제 버그 수정 및 전체선택 추가 [[
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
//		Log.d("DEBUG","getView start");
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
    					mMusicVideoViewHolder.get(position).setCheckedState(true);
    					String j = mMusicVideoViewHolder.get(position).getId();
    					String k = mMusicVideoViewHolder.get(position).getData();
    					Log.d(TAG,"j = " + j);
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
    					mMusicVideoViewHolder.get(position).setCheckedState(false);
    					String j = mMusicVideoViewHolder.get(position).getId();
    					String k = mMusicVideoViewHolder.get(position).getData();
    					Log.d(TAG,"j = " + j);
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
    					mMusicVideoViewHolder.get(position).setCheckedState(true);
    					String j = mMusicVideoViewHolder.get(position).getId();
    					String k = mMusicVideoViewHolder.get(position).getData();
    					Log.d(TAG,"j = " + j);
    					selectList.add(j+"");
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
    					mMusicVideoViewHolder.get(position).setCheckedState(false);
    					String j = mMusicVideoViewHolder.get(position).getId();
    					String k = mMusicVideoViewHolder.get(position).getData();
    					Log.d(TAG,"j = " + j);
    					selectList.remove(j+"");
    					shareList.remove(k);
    					Log.d(TAG,"current selectList.Count = " + selectList.size());
    				}
    			}
    		});
        }
        mViewHolder.thumnail_ImageView.setImageBitmap(mMusicVideoViewHolder.get(position).getThumnail());
        mViewHolder.thumnail_CheckBox.setChecked(mMusicVideoViewHolder.get(position).getCheckedState());
        
        return view;
        //]]
	}
	public void dropall(){
		selectList = new ArrayList<String>();
		shareList = new ArrayList<String>();
		for(int p=0; p<getCount();p++){
			mMusicVideoViewHolder.get(p).setCheckedState(true);
			String j = mMusicVideoViewHolder.get(p).getId();
			Log.d(TAG,"mMusicVideoViewHolder.get(p).getId() = " + j);
			selectList.add(j+"");
		}
		Log.d(TAG,"current selectList.Count = " + selectList.size());
		notifyDataSetChanged();
	}
	public void dropallcancel(){
		selectList = new ArrayList<String>();
		Log.d(TAG,"new selectList count = " + selectList.size());
		shareList = new ArrayList<String>();
		for(int p=0; p<getCount();p++){
			mMusicVideoViewHolder.get(p).setCheckedState(false);
			String j = mMusicVideoViewHolder.get(p).getId();
			selectList.remove(j);
		}
		Log.d(TAG,"current selectList.Count = " + selectList.size());
		notifyDataSetChanged();
	}
	
	public class ViewHolder {
		public CheckBox thumnail_CheckBox;
		private ImageView thumnail_ImageView;
	}
}
