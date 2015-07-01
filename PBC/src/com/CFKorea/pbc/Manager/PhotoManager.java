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
import com.CFKorea.pbc.ViewHolder.PhotoViewholder;

public class PhotoManager extends AsyncTask<String, Void, ArrayList<PhotoViewholder>> {

	//2015.1.15 JYP MusicVideoManager AsyncTask 구조 변경 [[
		/** Log Tag */
		private final String TAG = "[PhotoManager]";
		private final int DIALOG_DOWNLOAD_PROGRESS = 1;
		private Context mContext;
		
		private ArrayList<PhotoViewholder> mPhotoThumNailHolder;
		private MyProgressDialog mDialog; //2015.1.27 JYP Progress 구조 변경
		public static Bitmap mPhotoThumnailBm;		// Constructor
		
		public PhotoManager(Context context) {
			Log.d(TAG, "PhotoManager" );
			this.mContext = context; 
			//2015.1.27 JYP Progress 구조 변경
			//]]
		}
		
		
		    
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		@Override
		protected ArrayList<PhotoViewholder> doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.d(TAG, "doInBackground" + params );
			updatePhotoThumNailList(params); 
			return mPhotoThumNailHolder;
		}
		
		@Override
		protected void onPostExecute(ArrayList<PhotoViewholder> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
		
		//]]
		
		public ArrayList<PhotoViewholder> updatePhotoThumNailList(String[] filePath) {
			int cnt = 0;
			Cursor cur  = null;
			Log.d(TAG, "updatePhotoThumNailList ==========" + filePath[0].toString() );
			String [] selection_Args = new String[] {"%" + filePath[0].toString() +"%"};
			
			Log.d(TAG, "selection_Args = " + filePath);
			
			mPhotoThumNailHolder = new ArrayList<PhotoViewholder>();
			
			String[] mCursorCols = { 	MediaStore.Images.Media._ID,
										MediaStore.Images.Media.DATA,
										MediaStore.Images.Media.DISPLAY_NAME,
										MediaStore.Images.Media.SIZE,
										MediaStore.Images.Media.TITLE,};
		
	    	
	    	
	    	try 
	    	{
		    	// Define.SELECTION_ARGS_MUSIC_VIDEO
	    		
	    		//Cursor cur = mContext.getContentResolver().query(Define.MEDIA_URI_MUSIC_VIDEO, mCursorCols, null,null,null,null);
	    		cur = mContext.getContentResolver().query(Define.MEDIA_URI_PHOTO, mCursorCols, Define.SELECTION, selection_Args, Define.ORDER_BY, null);
	    		
		    	Log.d(TAG, "cur.getCount() = " + cur.getCount());
		    	PhotoViewholder[] photoViewHolder = new PhotoViewholder[cur.getCount()];
		    	
		    	if( cur != null && cur.moveToFirst() )
		    	{    		
		    		
		    		String id, data, display_Name, size, title;
		    		Bitmap thumnail;
		    		
		    		int idColumn = cur.getColumnIndex(MediaStore.Images.Media._ID);		
		    		int dataColumn = cur.getColumnIndex(MediaStore.Images.Media.DATA);	
		    		int display_NameColumn = cur.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);	 		
		    		int sizeColumn = cur.getColumnIndex(MediaStore.Images.Media.SIZE);
		    		int titleColumn = cur.getColumnIndex(MediaStore.Images.Media.TITLE);	
		    		
		    		do{
		    			
		    			id = cur.getString(idColumn);
		    			data = cur.getString(dataColumn);
		    			display_Name = cur.getString(display_NameColumn);
		    			size = cur.getString(sizeColumn);
		    			title = cur.getString(titleColumn);
		    			
		    			
		    			thumnail = mGetVideoThumnailImg(id);
		  
		    			photoViewHolder[cnt] = new PhotoViewholder(id, data, display_Name, size, title, thumnail);
		    			//Log.d(TAG, "artist, title, album" + artist + "  " + title + "  " +  album);
		    			mPhotoThumNailHolder.add(photoViewHolder[cnt]);
		    			cnt++;
		    		} while(cur.moveToNext());    		
		    	}
	    	}catch (Exception e) {
				Log.d(TAG, "updateMusicVideoList error");
			} finally {
				cur.close();
			}
	   	
	    	return mPhotoThumNailHolder;
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
			mPhotoThumnailBm = MediaStore.Images.Thumbnails.getThumbnail(mCrThumb, Long.valueOf(id), MediaStore.Video.Thumbnails.MINI_KIND, options);
			
			//halo_2nd 1.0.3 깨진파일 이미지 수정
			if(mPhotoThumnailBm == null)		
			{
				mPhotoThumnailBm = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_mystudio_error);
			}
			
			//Log.d(TAG , "동영상의 썸네일의 가로 :"+mPhotoThumnailBm.getWidth()+"입니다");
			//Log.d(TAG , "동영상의 썸네일의 세로 :"+mPhotoThumnailBm.getHeight()+"입니다");
			
			mCrThumb = null;
			options = null;
			Bitmap resized = Bitmap.createScaledBitmap(mPhotoThumnailBm, mPhotoThumnailBm.getWidth(), mPhotoThumnailBm.getHeight(), false);
			
			//Log.d(TAG , "동영상의 썸네일의 가로 :"+mPhotoThumnailBm.getWidth()+"입니다");
			//Log.d(TAG , "동영상의 썸네일의 세로 :"+mPhotoThumnailBm.getHeight()+"입니다");
			
			return resized; //resized;
		
		 
		
		}

}
