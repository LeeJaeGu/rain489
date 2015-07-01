package com.CFKorea.pbc.Activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import ca.uol.aig.fftpack.RealDoubleFFT;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.DB.IndexDB;
import com.CFKorea.pbc.Utility.Define;
import com.CFKorea.pbc.Utility.VoiceRecordingUtils;

public class VoiceActivity extends Activity implements OnClickListener{
	
	private static final String TAG = "[VoiceActivity]";
	VoiceRecordingUtils VoiceRecordingUtils;
	public static ImageView btnRec;//녹음버튼
	public static Chronometer timer;
	ImageButton btnback;
	
	//SoundEffect
	int frequency = 8000;
    int channelConfiguration = AudioFormat.CHANNEL_IN_MONO;
    int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    private RealDoubleFFT transformer;
    int blockSize = 256;
    static Button startStopButton;
    public static boolean started = false;
    ImageView imageView;
    Bitmap bitmap;
    Canvas canvas;
    AudioRecord audioRecord;
    Paint paint;
    static RecordAudio recordTask;
    int BufferElements2Rec = 1024; // want to play 2048 (2K) since 2 bytes we use only 1024
	int BytesPerElement = 2; // 2 bytes in 16bit format	
	static Cursor cursor;
	static SQLiteDatabase sqlite;
	static String timeStamp;
	static int num;
	public static LinearLayout linearmain;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voice);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		timer = (Chronometer) findViewById(R.id.timer);
		btnback = (ImageButton) findViewById(R.id.btn_back);
		btnRec = (ImageView) findViewById(R.id.btnRec);
		linearmain = (LinearLayout) findViewById(R.id.linearmain);
		btnRec.setOnClickListener(this);
		btnback.setOnClickListener(this);
		//SoundEffect
        // RealDoubleFFT 클래스 컨스트럭터는 한번에 처리할 샘플들의 수를 받는다. 그리고 출력될 주파수 범위들의 수를 나타낸다.
        transformer = new RealDoubleFFT(blockSize);
        
        // ImageView 및 관련 객체 설정 부분
        imageView = (ImageView)findViewById(R.id.ImageView01);
        bitmap = Bitmap.createBitmap((int)256, (int)100, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(Color.GREEN);
        imageView.setImageBitmap(bitmap);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_back:
				
				if (VoiceRecordingUtils.mRecState == VoiceRecordingUtils.RECORDING)
			    {
					try{
						VoiceRecordingUtils.mRecState = VoiceRecordingUtils.REC_STOP;
						VoiceRecordingUtils.stopRec();
						VoiceActivity.btnRec.setImageResource(R.drawable.btn_rec);
					}
					catch(Exception e){
						Log.d(TAG,"========================================ERROR============================================");
						Log.d(TAG,"Exception = " + e);
					}
			    }
				
				if(MainActivity.mLoginYN==null||MainActivity.mLoginYN.equals("N")){
					try{
						Intent intent = new Intent();
						intent.setClass(this, MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						this.startActivity(intent);
					}
					catch(Exception e){
						Log.d(TAG,"========================================ERROR============================================");
						Log.d(TAG,"Exception = " + e);
					}
				}
				else{
					try{
						Intent intent = new Intent();
						intent.setClass(this, MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.putExtra("loginYN", "Y");
						intent.putExtra("message", "N");
						Log.d(TAG,"back admin =" + MainActivity.admin);
						Log.d(TAG,"back general = " + MainActivity.general);
						if(MainActivity.admin==true){
							intent.putExtra("admin", true);
						}
						else if(MainActivity.general==true){
							intent.putExtra("general", true);
						}
						intent.putExtra("mainloginid", MainActivity.mainloginid);
						this.startActivity(intent);
						Log.d(TAG,"mainloginid = " + MainActivity.mainloginid);
					}
					catch(Exception e){
						Log.d(TAG,"========================================ERROR============================================");
						Log.d(TAG,"Exception = " + e);
					}
				}
				break;
				
			case R.id.btnRec:
				VoiceRecordingUtils = new VoiceRecordingUtils(getApplicationContext());
//				if(started){
//				    started = false;
//				    recordTask.cancel(true);
//				    }else{
//				    started = true;
//				    recordTask = new RecordAudio();
//				    recordTask.execute();
//				    }
				break;
		}
	}
	
	@Override
    public void onBackPressed() {
		
		if (VoiceRecordingUtils.mRecState == VoiceRecordingUtils.RECORDING)
	    {
			VoiceRecordingUtils.mRecState = VoiceRecordingUtils.REC_STOP;
			VoiceRecordingUtils.stopRec();
			VoiceActivity.btnRec.setImageResource(R.drawable.btn_rec);
	    }
		//recycleView(findViewById(R.id.linearmain));
		if(MainActivity.mLoginYN==null||MainActivity.mLoginYN.equals("N")){
			try{
			Intent intent = new Intent();
			intent.setClass(this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(intent);
			}
			catch(Exception e){
				Log.d(TAG,"========================================ERROR============================================");
				Log.d(TAG,"Exception = " + e);
			}
		}
		else{
			Intent intent = new Intent();
			intent.setClass(this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("loginYN", "Y");
			intent.putExtra("message", "N");
			Log.d(TAG,"back admin =" + MainActivity.admin);
			Log.d(TAG,"back general = " + MainActivity.general);
			if(MainActivity.admin==true){
				intent.putExtra("admin", true);
			}
			else if(MainActivity.general==true){
				intent.putExtra("general", true);
			}
			intent.putExtra("mainloginid", MainActivity.mainloginid);
			this.startActivity(intent);
			Log.d(TAG,"mainloginid = " + MainActivity.mainloginid);
		}
	
    }
	
	private void writeAudioDataToFile() {
	    // Write the output audio in byte
		File file = fileCreate(Define.MY_STUDIO_VOICE);
		
	    String filePath = Define.MY_STUDIO_VOICE + "PyungHwa"+ num + ".mp3";
	    short sData[] = new short[BufferElements2Rec];

	    FileOutputStream os = null;
	    try {
	        os = new FileOutputStream(filePath);
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }

	    while (started) {
	        // gets the voice output from microphone to byte format

	    	audioRecord.read(sData, 0, BufferElements2Rec);
	        System.out.println("Short wirting to file" + sData.toString());
	        try {
	            // // writes the data to file from buffer
	            // // stores the voice buffer
	            byte bData[] = short2byte(sData);
	            os.write(bData, 0, BufferElements2Rec * BytesPerElement);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    try {
	        os.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private class RecordAudio extends AsyncTask<Void, double[], Void>{
	    @Override
	    protected Void doInBackground(Void... params) {
	    try{
	    int bufferSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding);
	   
	    audioRecord = new AudioRecord( MediaRecorder.AudioSource.MIC, frequency, channelConfiguration, audioEncoding, bufferSize);
	    short[] buffer = new short[blockSize];
	    double[] toTransform = new double[blockSize];
	   
	    audioRecord.startRecording();
	    
	    Thread recordingThread = new Thread(new Runnable() {
	        public void run() {
	            writeAudioDataToFile();
	        }
	    }, "AudioRecorder Thread");
	    recordingThread.start();
	    
	    while(started){
	    int bufferReadResult = audioRecord.read(buffer, 0, blockSize);
	   
	    for(int i = 0; i < blockSize && i < bufferReadResult; i++)
	    {
	    toTransform[i] = (double)buffer[i] / Short.MAX_VALUE; // 부호 있는 16비트
	    }
	    	transformer.ft(toTransform);
	    	publishProgress(toTransform);
	    }
	   
	    audioRecord.stop();
	    }catch(Throwable t){
	    	Log.d(TAG,"========================================ERROR============================================");
	    	Log.e("AudioRecord", "Recording Failed");
	    }
	   
	    return null;
	    }
	    @Override
	    protected void onProgressUpdate(double[]... toTransform) {
	    canvas.drawColor(Color.BLACK);
	   
	    for(int i = 0; i < toTransform[0].length; i++){
	    int x = i;
	    int downy = (int) (100 - (toTransform[0][i] * 10));
	    int upy = 100;
	   
	    canvas.drawLine(x, downy, x, upy, paint);
	    }
	    imageView.invalidate();
	    }
	    }
	
	private byte[] short2byte(short[] sData) {
	    int shortArrsize = sData.length;
	    byte[] bytes = new byte[shortArrsize * 2];
	    for (int i = 0; i < shortArrsize; i++) {
	        bytes[i * 2] = (byte) (sData[i] & 0x00FF);
	        bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
	        sData[i] = 0;
	    }
	    return bytes;

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
	
	public void number(){
		
		IndexDB db = new IndexDB(VoiceActivity.this);
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
	

	private void recycleView(View view) {
			if(view != null) {
				Drawable bg = view.getBackground();
				if(bg != null) {
					bg.setCallback(null);
					((BitmapDrawable)bg).getBitmap().recycle();
					view.setBackgroundDrawable(null);
				}	
			}
	    }
	
}
