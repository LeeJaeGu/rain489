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
import android.widget.TextView;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.ViewHolder.VoiceViewholder;

public class MyStudioVoiceDeleteAdapter extends BaseAdapter {

	/** Log Tag */
	private final String TAG = "MusicVideoAdapter";
	
	private ArrayList<VoiceViewholder> mVoiceRecordingViewHolder;
	private Context mContext;
	private ViewHolder mViewHolder;
	private LayoutInflater mInflater = null;
	public static ArrayList<String> selectList;
	//2015.1.27 share기능 추가
	public static ArrayList<String> shareList;
	
	public MyStudioVoiceDeleteAdapter(Context context, ArrayList<VoiceViewholder> holder) {
		
		Log.d("DEBUG","MyStudioVideoAdapter");
		
		mContext = context;
		mVoiceRecordingViewHolder = holder;
		this.mInflater = LayoutInflater.from(context);
		selectList = new ArrayList<String>();
		shareList = new ArrayList<String>();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mVoiceRecordingViewHolder.size();
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
        	view = mInflater.inflate(R.layout.list_vselect, null);
        	mViewHolder.thumnail_CheckBox = (CheckBox)view.findViewById(R.id.select_item);
        	mViewHolder.vselect = (TextView)view.findViewById(R.id.vselect);
        	mViewHolder.thumnail_CheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() 
    		{
    			@Override
    			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    				// TODO Auto-generated method stub
    				
    				if (isChecked == true )
    				{
    					mVoiceRecordingViewHolder.get(position).setCheckedState(true);
    					String j = mVoiceRecordingViewHolder.get(position).getId();
    					String k = mVoiceRecordingViewHolder.get(position).getData();
    					Log.d("DEBUG","j = " + j);
    					selectList.add(j);
    					shareList.add(k);

    					for (int i = 0; i < selectList.size(); i++) {
					        String str1 = selectList.get(i);
					        for (int g = 0; g < selectList.size(); g++) {
					            if (i == g) continue;
					            String str2 = selectList.get(g);
					            if (str1.equals(str2)) {
					            	Log.d("","i = " + i);
					            	selectList.remove(i);
					            	Log.d("","Count = " + selectList.size());
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
    					mVoiceRecordingViewHolder.get(position).setCheckedState(false);
    					String j = mVoiceRecordingViewHolder.get(position).getId();
    					String k = mVoiceRecordingViewHolder.get(position).getData();
    					Log.d("DEBUG","j = " + j);
    					selectList.remove(j);
    					shareList.remove(k);
    					Log.d("","current selectList.Count = " + selectList.size());
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
    					mVoiceRecordingViewHolder.get(position).setCheckedState(true);
    					String j = mVoiceRecordingViewHolder.get(position).getId();
    					String k = mVoiceRecordingViewHolder.get(position).getData();
    					Log.d("DEBUG","j = " + j);
    					selectList.add(j);
    					shareList.add(k);
    					
    					for (int i = 0; i < selectList.size(); i++) {
					        String str1 = selectList.get(i);
					        for (int g = 0; g < selectList.size(); g++) {
					            if (i == g) continue;
					            String str2 = selectList.get(g);
					            if (str1.equals(str2)) {
					            	Log.d("","i = " + i);
					            	selectList.remove(i);
					            	Log.d("","Count = " + selectList.size());
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
    					mVoiceRecordingViewHolder.get(position).setCheckedState(false);
    					String j = mVoiceRecordingViewHolder.get(position).getId();
    					String k = mVoiceRecordingViewHolder.get(position).getData();
    					Log.d("DEBUG","j = " + j);
    					selectList.remove(j);
    					shareList.remove(k);
    					Log.d("","current selectList.Count = " + selectList.size());
    				}
    			}
    		});
        }
        mViewHolder.vselect.setText(mVoiceRecordingViewHolder.get(position).getTitle());
        mViewHolder.thumnail_CheckBox.setChecked(mVoiceRecordingViewHolder.get(position).getCheckedState());
        
        return view;
        //]]
	}
	public void dropall(){
		selectList = new ArrayList<String>();
		shareList = new ArrayList<String>();
		for(int p=0; p<getCount();p++){
			mVoiceRecordingViewHolder.get(p).setCheckedState(true);
			String j = mVoiceRecordingViewHolder.get(p).getId();
			String k = mVoiceRecordingViewHolder.get(p).getData();
			shareList.add(k);
			selectList.add(j);
			Log.d("","add k= " + k);
		}
		Log.d("","current selectList.Count = " + selectList.size());
		notifyDataSetChanged();
	}
	public void dropallcancel(){
		selectList = new ArrayList<String>();
		shareList = new ArrayList<String>();
		for(int p=0; p<getCount();p++){
			mVoiceRecordingViewHolder.get(p).setCheckedState(false);
			String j = mVoiceRecordingViewHolder.get(p).getId();
			String k = mVoiceRecordingViewHolder.get(p).getData();
			selectList.remove(j);
			shareList.remove(k);
			Log.d("","remove k= " + k);
		}
		Log.d("","current selectList.Count = " + selectList.size());
		notifyDataSetChanged();
	}
	public class ViewHolder {
		public CheckBox thumnail_CheckBox;
		/** Log Tag */
		private final String TAG = "MainListViewHolder";
		private TextView vselect;
	}
	


}
