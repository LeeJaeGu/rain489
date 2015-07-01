package com.CFKorea.pbc.Adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Activity.MyStudioActivity;
import com.CFKorea.pbc.Activity.QnaActivity;
import com.CFKorea.pbc.Activity.VideoRecordingActivity;
import com.CFKorea.pbc.Activity.VoiceActivity;
import com.CFKorea.pbc.Utility.CustomDialog;
import com.CFKorea.pbc.ViewHolder.MainListViewHolder;

/** 
* @FileName      : MainListAdapter.java 
* @Project    	 : Halo_2nd_MC 
* @Date          : 2014. 11. 27. 
* @작성자        : jypark@cftown.co.kr 
* @변경이력      : 
*/
public class MainListAdapter extends BaseAdapter {

	/** Log Tag */
	private final String TAG = "MainListAdapter";
	
	private ArrayList<Integer> mArrayList = new ArrayList<Integer>();
	private Context mContext;
	private MainListViewHolder mViewHolder = null;
	private LayoutInflater mInflater = null;
	private CustomDialog mCustomDialog;
	
	public MainListAdapter(Context context, ArrayList<Integer> arrayList) {
		
		this.mContext = context;
		this.mArrayList = arrayList;
		this.mInflater = LayoutInflater.from(context);
	}
		
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayList.size();
	}

	@Override
	public Integer getItem(int position) {
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
        
        if(view == null) 
        {
        	mViewHolder = new MainListViewHolder();
        	view = mInflater.inflate(R.layout.list_musicalbum, null);
        	mViewHolder.btn_List = (com.CFKorea.pbc.Activity.SquareButton)view.findViewById(R.id.photogallery_Thumnail);
        	view.setPadding(20, 20, 20, 20);
        	view.setTag(mViewHolder);
             
        }
        else 
        {
        	mViewHolder = (MainListViewHolder)view.getTag();
        }
        
        mViewHolder.btn_List.setBackgroundResource(getItem(position));
        mViewHolder.btn_List.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					{
						try{
						mCustomDialog = new CustomDialog(mContext,null,mContext.getResources().getString(R.string.video),leftClickListener,rightClickListener);
						mCustomDialog.show();
						}
						catch(Exception e){
							Log.d(TAG,"========================================ERROR============================================");
							Log.d(TAG,"Exception = "  + e);
							android.os.Process.killProcess(android.os.Process.myPid());
						}
					}
					
					break;
				case 1:
					{
						try{
						Intent video = new Intent(mContext, VoiceActivity.class);
						mContext.startActivity(video);
						}
						catch(Exception e){
							Log.d(TAG,"========================================ERROR============================================");
							Log.d(TAG,"Exception = "  + e);
							android.os.Process.killProcess(android.os.Process.myPid());
						}
					}
					break;
				case 2:
					{
						try{
						Intent video = new Intent(mContext, MyStudioActivity.class);
						mContext.startActivity(video);
						}
						catch(Exception e){
							Log.d(TAG,"========================================ERROR============================================");
							Log.d(TAG,"Exception = "  + e);
							android.os.Process.killProcess(android.os.Process.myPid());
						}
					}
					
					break;
				case 3:
					{
						try{
						Intent video = new Intent(mContext, QnaActivity.class);
						mContext.startActivity(video);
						}
						catch(Exception e){
							Log.d(TAG,"========================================ERROR============================================");
							Log.d(TAG,"Exception = "  + e);
							android.os.Process.killProcess(android.os.Process.myPid());
						}
					}
					break;

				default:
					break;
				}
			
				
				
			}
		});
        //view.setOnClickListener(this);
		return view;
	}
	
	private View.OnClickListener leftClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mCustomDialog.dismiss();
			try{
			Intent video = new Intent(mContext, VideoRecordingActivity.class);
			mContext.startActivity(video);
			}
			catch(Exception e){
				Log.d(TAG,"========================================ERROR============================================");
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
