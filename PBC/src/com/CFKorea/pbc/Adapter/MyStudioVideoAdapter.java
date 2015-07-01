package com.CFKorea.pbc.Adapter;

import java.util.ArrayList;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Activity.MainActivity;
import com.CFKorea.pbc.Activity.MemberShipLoginActivity;
import com.CFKorea.pbc.Activity.MyStudioMovieDetailActivity;
import com.CFKorea.pbc.Activity.PopUpActivity;
import com.CFKorea.pbc.Utility.CustomDialog;
import com.CFKorea.pbc.ViewHolder.VideoViewHolder;

public class MyStudioVideoAdapter extends BaseAdapter {

	/** Log Tag */
	private final String TAG = "[MusicVideoAdapter]";
	
	public static ArrayList<VideoViewHolder> mMusicVideoViewHolder;
	public static Context mContext;
	private ViewHolder mViewHolder;
	private LayoutInflater mInflater = null;
	private CustomDialog mCustomDialog;
	
	public MyStudioVideoAdapter(Context context, ArrayList<VideoViewHolder> holder) {
		
		Log.d(TAG,TAG);
		if(context != null){
		mContext = context;
		}
		mMusicVideoViewHolder = holder;
		this.mInflater = LayoutInflater.from(context);
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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.d("DEBUG","getView start");
		View view = convertView;
        if (view == null) 
        {
        	mViewHolder = new ViewHolder();
        	view = mInflater.inflate(R.layout.list_photogallery, null);
        	mViewHolder.thumnail_ImageView = (ImageView)view.findViewById(R.id.photogallery_Thumnail);
        	mViewHolder.thumnail_ImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        	mViewHolder.playtime = (TextView)view.findViewById(R.id.playtime);
        	view.setPadding(3, 3, 3, 3);
        	view.setTag(mViewHolder);
        }
        else 
        {
        	mViewHolder = (ViewHolder)view.getTag();
        }
	      int point =  Integer.parseInt(mMusicVideoViewHolder.get(position).getDuration());
	      Log.d(TAG,"point = " + point);
	      int maxMinPoint = point / 1000 / 60;
	      int maxSecPoint = (point / 1000) % 60;
	      String maxMinPointStr = "";
	      String maxSecPointStr = "";
	      
	      if (maxMinPoint < 10)
	        maxMinPointStr = "0" + maxMinPoint + ":";
	      else
	        maxMinPointStr = maxMinPoint + ":";
	      
	      if (maxSecPoint < 10)
	        maxSecPointStr = "0" + maxSecPoint;
	      else
	        maxSecPointStr = String.valueOf(maxSecPoint);
	      
	      mViewHolder.playtime.setText(maxMinPointStr + maxSecPointStr);
	      mViewHolder.thumnail_ImageView.setImageBitmap(mMusicVideoViewHolder.get(position).getThumnail());
        
        view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(Intent.ACTION_VIEW);
//				Log.d(TAG, "mMusicVideoViewHolder.get(position).getData() = " + mMusicVideoViewHolder.get(position).getData());
//		        Uri uri = Uri.parse("file://"  + mMusicVideoViewHolder.get(position).getData());
//		        intent.setDataAndType(uri, "video/*");
//		        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		        mContext.startActivity(intent);
				
				if(MainActivity.mLoginYN==null||MainActivity.mLoginYN.equals("N")){
//					AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//
//					builder.setTitle("로그인 먼저 해주십시오.\n로그인 창으로 이동하시겠습니까?");
//					
//					builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
//					           public void onClick(DialogInterface dialog, int id) {
//					        	
//					           }
//					       });
//					builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
//					           public void onClick(DialogInterface dialog, int id) {
//					        	   Intent Asynclogin = new Intent(mContext, MemberShipLoginActivity.class);
//					        	   mContext.startActivity(Asynclogin);
//					           }
//					       });
//					AlertDialog dialog = builder.create();
//					dialog.show();
					mCustomDialog = new CustomDialog(mContext,null,mContext.getResources().getString(R.string.mystudio_login),leftClickListener,rightClickListener);
					mCustomDialog.show();
					
				}
				
				else{
					Intent moviedetail = new Intent(mContext, MyStudioMovieDetailActivity.class);
					moviedetail.putExtra("index", position);
					Log.d(TAG,"intent position = " + position);
					mContext.startActivity(moviedetail);
				}
			}
        	
        });
        
        return view;
	}

	private View.OnClickListener leftClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mCustomDialog.dismiss();
			Intent Asynclogin = new Intent(mContext, MemberShipLoginActivity.class);
			mContext.startActivity(Asynclogin);
		}
	};

	private View.OnClickListener rightClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mCustomDialog.dismiss();
			
		}
	};
	
	public class ViewHolder {
		/** Log Tag */
		private final String TAG = "MainListViewHolder";
		private ImageView thumnail_ImageView;
		private TextView playtime;
	}
}
