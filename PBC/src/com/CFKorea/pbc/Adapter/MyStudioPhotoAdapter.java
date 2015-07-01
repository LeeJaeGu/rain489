package com.CFKorea.pbc.Adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Activity.MyStudioPhotoDetailActivity;
import com.CFKorea.pbc.ViewHolder.PhotoViewholder;

public class MyStudioPhotoAdapter extends BaseAdapter {
	
	/** Log Tag */
	private final String TAG = "MyStudioPhotoAdapter";
	
	public static ArrayList<PhotoViewholder> mPhotoThumNailViewHolder;
	public static Context mContext;
	public ViewHolder mViewHolder;
	public LayoutInflater mInflater = null;
	public static ArrayList<String> mGallerylist;
	public static int r=0;
	
	public MyStudioPhotoAdapter(Context context, ArrayList<PhotoViewholder> holder) {
		Log.d(TAG,"Adapter START");
		mGallerylist = new ArrayList<String>();
		if(context != null){
		mContext = context;
		}
		mPhotoThumNailViewHolder = holder;
//		this.gallerylist = gallerylist;
		this.mInflater = LayoutInflater.from(mContext);
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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
        if (view == null) 
        {
        	mViewHolder = new ViewHolder();
        	view = mInflater.inflate(R.layout.list_photogallery, null);
        	mViewHolder.thumnail_ImageView = (ImageView)view.findViewById(R.id.photogallery_Thumnail);
        	mViewHolder.thumnail_ImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        	view.setTag(mViewHolder);
        	view.setPadding(3, 3, 3, 3);
        }
        else
        {
        	mViewHolder = (ViewHolder)view.getTag();
        }
        
        mViewHolder.thumnail_ImageView.setImageBitmap(mPhotoThumNailViewHolder.get(position).getThumnail());
        //Log.d("DEBUG","position = " + position);
        view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				r=1;
				Intent i = new Intent(mContext, MyStudioPhotoDetailActivity.class);
				//Log.d("DEBUG","mPhotoThumNailViewHolder data" + mPhotoThumNailViewHolder.get(position).getData());
				i.putExtra("gallerylist", getPhotoData());
				i.putExtra("selectedIndex", position);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(i);
			}
        	
        });
        
        return view;
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
		/** Log Tag */
		private ImageView thumnail_ImageView;
	}
}
