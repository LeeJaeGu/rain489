package com.CFKorea.pbc.Utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Activity.VoiceActivity;
import com.CFKorea.pbc.DB.IndexDB;


/** 
* @FileName      : RecordingActivity.java 
* @Project    	 : Halo_2nd_MC 
* @Date          : 2014. 11. 26. 
* @작성자        : jypark@cftown.co.kr 
* @변경이력      : 
*/
public class VoiceRecordingUtils{
	
	private final static String TAG="[VoiceRecordingUtils]";
	public static MediaRecorder mRecorder = null;
	private static String mFilePath;
	private static String mFileName;
	public static int mRecState = 0;
	public static final int REC_STOP = 0;
	public static final int RECORDING = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private static String mPath = "";
	private static File m_MediaFile;
	public static Context mContext;
	static Cursor cursor;
	static SQLiteDatabase sqlite;
	static String timeStamp;
	static int num;
	static String filename;
	public static String savevoice;
	
	public VoiceRecordingUtils(Context context){
		mContext = context;
		mBtnStartRecOnClick();
		
	}
	
	
	public static void mBtnStartRecOnClick()
	  {
		
	    if (mRecState == REC_STOP)
	    {
	      mRecState = RECORDING;
	      //MenuControll.Toastshow4(mContext);
	      startRec();
	      
	      VoiceActivity.btnRec.setImageResource(R.drawable.btn_mic_rec_pressed);
	      Handler hd = new Handler();
	      hd.postDelayed(new Runnable() 
			{
				@Override
				public void run() 
				{
					if (mRecState == RECORDING){
					mRecState = REC_STOP;
				    MenuControll.Toastshow6(mContext);
				    stopRec();
				    VoiceActivity.btnRec.setImageResource(R.drawable.btn_rec);
					 }
					Log.d(TAG,"10000000000000000000000000000000");
				}
				
			}, 181000);
	    }
	    else if (mRecState == RECORDING)
	    {
	      mRecState = REC_STOP;
	      stopRec();
	      VoiceActivity.btnRec.setImageResource(R.drawable.btn_rec);
	    }
	  }
	public static void startRec()
	  {
//		mFilePath = Define.MY_STUDIO_VOICE;
//		SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//		mFileName = "/WJ"+ timeStampFormat.format(new Date()).toString()+ "Rec.mp4";
		
		//PlayerActivity.timer.setVisibility(View.VISIBLE);
		VoiceActivity.timer.setBase(SystemClock.elapsedRealtime());
		VoiceActivity.timer.start();
		MenuControll.Toastshow4(mContext);
		
		
		//VoiceActivity.linearmain.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_mic_background_light)));
		
		//VoiceActivity.linearmain.setBackgroundResource(R.drawable.img_mic_background_light);
	    if (mRecorder == null)
	    {
	      mRecorder = new MediaRecorder();
	      mRecorder.reset();
	    }
	    else
	    {
	      mRecorder.reset();
	    }
	    //오디오 파일 생성
	      mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	      	if (Build.VERSION.SDK_INT >= 10) 
	      	{
	      		Log.d("","SDK_INT > 9");
	    	    mRecorder.setAudioSamplingRate(44100);
	    	    mRecorder.setAudioEncodingBitRate(96000);
	    	    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
	    	    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
	    	} 
	      	else 
	    	{
	      		Log.d("","SDK_INT < 10");
	    	    // older version of Android, use crappy sounding voice codec
	    		mRecorder.setAudioSamplingRate(8000);
	    		mRecorder.setAudioEncodingBitRate(12200);
	    		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	    		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	    	}
//	      mRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
//	      mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
	      mRecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).toString());
	      mRecorder.setMaxDuration(180000);
	    try
	    {
	      mRecorder.prepare();
	      Thread.sleep(0);
	      mRecorder.start();
	    } catch (IllegalStateException e) {
            stopRec();
        } catch (IOException e) {
            stopRec();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    }
	  }
	
	public static void stopRec()
	  {
		VoiceActivity.timer.stop();
		mRecState = REC_STOP;
		VoiceActivity.linearmain.setBackgroundResource(R.drawable.img_mic_background);		
	    try
	    {
	    	//Thread.sleep(200);
	    	MenuControll.Toastshow5(mContext);
	    	mRecorder.stop();
		      if ( mPath != null) 
		    	{
		    	  Log.d("","mPath = " + mPath);
		    	  	MenuControll.mediaScanner(mContext, mPath);
		    	}
	    }
	    catch(Exception e){
	    	Log.d("","Exception = " + e);
	    	MenuControll.mediaScanner(mContext, mPath);
	    	MenuControll.Toastshow5(mContext);
	    }
	    finally
	    {
	      mRecorder.release();
	      mRecorder = null;
	    }
	  }
	private static File getOutputMediaFile(int type) {
    	
    	//file create
    	File file = fileCreate(Define.MY_STUDIO_VOICE);
        // Create a media file name
        timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(new Date());
        //"/WJ"+ timeStampFormat.format(new Date()).toString()+ "Rec.mp4";
        
        //number();
        
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
        Log.d("","num = " + num);
        if(type == MEDIA_TYPE_VIDEO) 
        {
        	//filename = "PyungHwa"+ num + ".mp3";
        	filename = timeStamp+".mp3";
        	mPath = file.getPath() + File.separator + filename;
            m_MediaFile = new File(mPath);
            savevoice="Y";
        } 
        else 
        {
            return null;
        }
        return m_MediaFile;
    }
	
	public static File fileCreate (String filePath) {
		File file = new File(filePath);
		Log.d("","filePath = " + filePath);
		// 원하는 경로에 폴더가 있는지 확인
		if( !file.exists() )
		{
			file.mkdirs();
		}
		
		return file;
		
	}
	public static void number(){
		
		IndexDB db = new IndexDB(mContext);
		Log.d("","number() start");
		try
		{
			sqlite = db.getWritableDatabase();
		}
		catch (SQLiteException ex)
		{
			sqlite = db.getReadableDatabase();
		}
		
		sqlite.execSQL("insert INTO " + db.TABLE_NAME + "(" + db.savefile + ")" + " VALUES(" + "'" + timeStamp + "'" +");");
			//Log.d("","insert INTO " + db.TABLE_NAME + "(" + db.savefile + ")" + " VALUES(" + "'" + timeStamp + "'" +");");
			cursor = sqlite.rawQuery("select " + db.idx + " from " + db.TABLE_NAME +" where savefile =" + "'" + timeStamp +"'" + ";", null);
			//Log.d("","select " + db.idx + " from " + db.TABLE_NAME +" where savefile =" + "'" + timeStamp +"'" + ";");
			
			cursor.moveToFirst();
			num = cursor.getInt(cursor.getColumnIndex(db.idx));
	}
	
}
