package com.CFKorea.pbc.Manager;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Utility.Define;
import com.CFKorea.pbc.Utility.MyProgressDialog;
import com.CFKorea.pbc.ViewHolder.VideoViewHolder;

public class VideoManager extends AsyncTask<String, Void, ArrayList<VideoViewHolder>>{
	/** Log Tag */
	private final String TAG = "VideoManager";
	private final int DIALOG_DOWNLOAD_PROGRESS = 1;
	private Context mContext;
	
	private ArrayList<VideoViewHolder> mMusicVideoVieHolder;
	private MyProgressDialog mDialog; 
	public static Bitmap mVideoThumnailBm;
	
	public VideoManager(Context context) {
		Log.d(TAG, "VideoManager" );
		this.mContext = context; 
		//2015.1.27 JYP Progress 구조 변경 [[
		//]]
	}
	
	
	    
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	@Override
	protected ArrayList<VideoViewHolder> doInBackground(String... params) {
		// TODO Auto-generated method stub
		Log.d(TAG, "doInBackground" + params );
		updateMusicVideoList(params); 
		return mMusicVideoVieHolder;
	}
	
	@Override
	protected void onPostExecute(ArrayList<VideoViewHolder> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	
	//]]
	
	public ArrayList<VideoViewHolder> updateMusicVideoList(String[] filePath) {
		int cnt = 0;
		Cursor cur  = null;
		Log.d(TAG, "updateMusicVideoList ==========" + filePath[0].toString() );
		String [] selection_Args = new String[] {"%" + filePath[0].toString() +"%"};
		
		Log.d(TAG, "selection_Args = " + filePath);
		
		mMusicVideoVieHolder = new ArrayList<VideoViewHolder>();
		
		String[] mCursorCols = new String[]
						{ 	MediaStore.Video.Media._ID,
			    			MediaStore.Video.Media.DATA,
			    			MediaStore.Video.Media.DISPLAY_NAME,
			    			MediaStore.Video.Media.SIZE ,
			    			MediaStore.Video.Media.TITLE,
			    			MediaStore.Video.Media.DURATION,
			    			MediaStore.Video.Media.DATE_ADDED,
			    			MediaStore.Video.Media.RESOLUTION };
    	
    	
    	try 
    	{
	    	// Define.SELECTION_ARGS_MUSIC_VIDEO
    		
    		//Cursor cur = mContext.getContentResolver().query(Define.MEDIA_URI_MUSIC_VIDEO, mCursorCols, null,null,null,null);
    		cur = mContext.getContentResolver().query(Define.MEDIA_URI_MUSIC_VIDEO, mCursorCols, Define.SELECTION, selection_Args, Define.ORDER_BY, null);
    		
	    	Log.d(TAG, "cur.getCount() = " + cur.getCount());
	    	VideoViewHolder[] music = new VideoViewHolder[cur.getCount()];
	    	
	    	if( cur != null && cur.moveToFirst() )
	    	{    		
	    		
	    		String id, data, display_Name, size, title, duration, date_added, resolution;
	    		Bitmap thumnail;
	    		
	    		int idColumn = cur.getColumnIndex(MediaStore.Video.Media._ID);		
	    		int dataColumn = cur.getColumnIndex(MediaStore.Video.Media.DATA);	
	    		int display_NameColumn = cur.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME);	 		
	    		int sizeColumn = cur.getColumnIndex(MediaStore.Video.Media.SIZE);
	    		int titleColumn = cur.getColumnIndex(MediaStore.Video.Media.TITLE);		
	    		int durationColumn = cur.getColumnIndex(MediaStore.Video.Media.DURATION);	
	    		int date_addedColumn = cur.getColumnIndex(MediaStore.Video.Media.DATE_ADDED);	 		
	    		int resolutionColumn = cur.getColumnIndex(MediaStore.Video.Media.RESOLUTION);
	    		
	    		do{
	    			
	    			id = cur.getString(idColumn);
	    			data = cur.getString(dataColumn);
	    			display_Name = cur.getString(display_NameColumn);
	    			size = cur.getString(sizeColumn);
	    			title = cur.getString(titleColumn);
	    			duration = cur.getString(durationColumn);
	    			Log.d(TAG,"duration = " + duration);
	    			date_added = cur.getString(date_addedColumn);
	    			resolution = cur.getString(resolutionColumn);
	    			
	    			
	    			thumnail = mGetVideoThumnailImg(id);
	  
	    			music[cnt] = new VideoViewHolder(id, data, display_Name, size, title, duration, date_added, resolution, thumnail);
	    			//Log.d(TAG, "artist, title, album" + artist + "  " + title + "  " +  album);
	    			mMusicVideoVieHolder.add(music[cnt]);
	    			cnt++;
	    		} while(cur.moveToNext());    		
	    	}
    	}catch (Exception e) {
			Log.d(TAG, "updateMusicVideoList error");
		} finally {
			cur.close();
		}
   	
    	return mMusicVideoVieHolder;
	}
	
	
	/**
	* 비디오 썸네일 이미지 가져오기
	* @param id 비디오 아이디
	*/
	public Bitmap mGetVideoThumnailImg(String id) {
		
		ContentResolver mCrThumb = mContext.getContentResolver();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;
	 
		//	MICRO_KIND :작은이미지(정사각형) MINI_KIND (중간이미지)
		mVideoThumnailBm = MediaStore.Video.Thumbnails.getThumbnail(mCrThumb, Long.valueOf(id), MediaStore.Video.Thumbnails.MINI_KIND, options);
		
		//halo_2nd 1.0.3 깨진파일 이미지 수정
		if(mVideoThumnailBm == null)		
		{
			mVideoThumnailBm = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_mystudio_error);
		}
		
		//Log.d(TAG , "동영상의 썸네일의 가로 :"+mVideoThumnailBm.getWidth()+"입니다");
		//Log.d(TAG , "동영상의 썸네일의 세로 :"+mVideoThumnailBm.getHeight()+"입니다");
		
		mCrThumb = null;
		options = null;
		Bitmap resized = Bitmap.createScaledBitmap(mVideoThumnailBm, mVideoThumnailBm.getWidth(), mVideoThumnailBm.getHeight(), false);
		
		//Log.d(TAG , "동영상의 썸네일의 가로 :"+mVideoThumnailBm.getWidth()+"입니다");
		//Log.d(TAG , "동영상의 썸네일의 세로 :"+mVideoThumnailBm.getHeight()+"입니다");
		
		return resized; //resized;
	
	 
	
	}
}
