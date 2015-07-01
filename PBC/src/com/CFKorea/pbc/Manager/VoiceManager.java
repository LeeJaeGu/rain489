package com.CFKorea.pbc.Manager;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import com.CFKorea.pbc.Utility.Define;
import com.CFKorea.pbc.ViewHolder.VoiceViewholder;

public class VoiceManager extends AsyncTask<String, Void, ArrayList<VoiceViewholder>>{
	//2015.1.15 JYP MusicVideoManager AsyncTask 구조 변경 [[
	
	private final String TAG = "[VoiceManager]";
	/** Log Tag */
	private final int DIALOG_DOWNLOAD_PROGRESS = 1;
	private Context mContext;
	
	private ArrayList<VoiceViewholder> mVoiceRecordingViewHolder;
	// Constructor
	
	public VoiceManager(Context context) {
		Log.d(TAG,"VoiceManager");
		this.mContext = context; 
		//2015.1.27 JYP Progress 구조 변경 [[
		//]]
	}
	
	
	    
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	@Override
	protected ArrayList<VoiceViewholder> doInBackground(String... params) {
		// TODO Auto-generated method stub
		updateMusicVideoList(params); 
		return mVoiceRecordingViewHolder;
	}
	
	@Override
	protected void onPostExecute(ArrayList<VoiceViewholder> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	
	//]]
	
	public ArrayList<VoiceViewholder> updateMusicVideoList(String[] filePath) {
		int cnt = 0;
		Cursor cur  = null;
		String [] selection_Args = new String[] {"%" + filePath[0].toString() +"%"};
		
		
		mVoiceRecordingViewHolder = new ArrayList<VoiceViewholder>();
		
		String[] mCursorCols = new String[]
						{ 	MediaStore.Audio.Media._ID,
			    			MediaStore.Audio.Media.DATA,
			    			MediaStore.Audio.Media.DISPLAY_NAME,
			    			MediaStore.Audio.Media.SIZE ,
			    			MediaStore.Audio.Media.TITLE,
			    			MediaStore.Audio.Media.DURATION,
			    			MediaStore.Audio.Media.DATE_ADDED,
			    			};
    	
    	
    	try 
    	{
	    	// Define.SELECTION_ARGS_MUSIC_VIDEO
    		
    		//Cursor cur = mContext.getContentResolver().query(Define.MEDIA_URI_MUSIC_VIDEO, mCursorCols, null,null,null,null);
    		cur = mContext.getContentResolver().query(Define.MEDIA_URI_MUSIC_VOICE, mCursorCols, Define.SELECTION, selection_Args, Define.ORDER_BY, null);
    		
    		VoiceViewholder[] music = new VoiceViewholder[cur.getCount()];
	    	
	    	if( cur != null && cur.moveToFirst() )
	    	{    		
	    		
	    		String id, data, display_Name, size, title, duration, date_added, resolution;
	    		Bitmap thumnail;
	    		
	    		int idColumn = cur.getColumnIndex(MediaStore.Audio.Media._ID);		
	    		int dataColumn = cur.getColumnIndex(MediaStore.Audio.Media.DATA);	
	    		int display_NameColumn = cur.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);	 		
	    		int sizeColumn = cur.getColumnIndex(MediaStore.Audio.Media.SIZE);
	    		int titleColumn = cur.getColumnIndex(MediaStore.Audio.Media.TITLE);		
	    		int durationColumn = cur.getColumnIndex(MediaStore.Audio.Media.DURATION);	
	    		int date_addedColumn = cur.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED);	 		
	    		
	    		do{
	    			
	    			id = cur.getString(idColumn);
	    			data = cur.getString(dataColumn);
	    			display_Name = cur.getString(display_NameColumn);
	    			size = cur.getString(sizeColumn);
	    			title = cur.getString(titleColumn);
	    			duration = cur.getString(durationColumn);
	    			date_added = cur.getString(date_addedColumn);
	    			
	    			
	  
	    			music[cnt] = new VoiceViewholder(id, data, display_Name, size, title, duration, date_added);
	    			//Log.d(TAG, "artist, title, album" + artist + "  " + title + "  " +  album);
	    			mVoiceRecordingViewHolder.add(music[cnt]);
	    			cnt++;
	    		} while(cur.moveToNext());    		
	    	}
    	}catch (Exception e) {
		} finally {
			cur.close();
		}
   	
    	return mVoiceRecordingViewHolder;
	}


}
