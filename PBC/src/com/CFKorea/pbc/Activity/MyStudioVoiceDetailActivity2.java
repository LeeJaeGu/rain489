package com.CFKorea.pbc.Activity;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Adapter.MyStudioVoiceAdapter;
import com.CFKorea.pbc.Utility.CustomDialog;
import com.CFKorea.pbc.Utility.MenuControll;

public class MyStudioVoiceDetailActivity2 extends Activity implements OnClickListener,OnCompletionListener{
	int index;
	EditText title;
	EditText story;
	String indextitle;
	TextView voicetitle;
	TextView tvPlayStartPoint;
	ImageButton submit;
	ImageButton cancel;
	String title1;
	String story1;
	int point;
	LinearLayout linearlayoutmain;
	static boolean sub = true;
	
	//미리재생[[
	private static final int REC_STOP = 0;
	private static final int RECORDING = 1;
	private static final int PLAY_STOP = 0;
	private static final int PLAYING = 1;
	private static final int PLAY_PAUSE = 2;
	private MediaPlayer mPlayer = null;
	private int mRecState = REC_STOP;
	private int mPlayerState = PLAY_STOP;
	private SeekBar mPlayProgressBar;
	private ImageButton mBtnStartPlay, mBtnStopPlay;
	private TextView mTvPlayMaxPoint;
	private CustomDialog mCustomDialog;
	private static Handler mHandler = new Handler();
	//]]
	private final static String TAG = "[MyStudioVoiceDetailActivity]";
	
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voicedetail);
        
        Intent intent = new Intent(getIntent());
		 index = intent.getIntExtra("index",1);
		 Log.d(TAG,"get intent index = " + index);
		 
		 indextitle = MyStudioVoiceAdapter.mVoiceRecordingViewHolder.get(index).getTitle()+".mp3";
		 point =  Integer.parseInt(MyStudioVoiceAdapter.mVoiceRecordingViewHolder.get(index).getDuration());
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
		 
		 
		 title = (EditText)findViewById(R.id.title);
		 story = (EditText)findViewById(R.id.story);
		 voicetitle = (TextView)findViewById(R.id.voicetitle);
	     mBtnStartPlay = (ImageButton) findViewById(R.id.btnStartPlay);
	     mBtnStopPlay = (ImageButton) findViewById(R.id.btnStopPlay);
	     mPlayProgressBar = (SeekBar) findViewById(R.id.playProgressBar);
	     
	     
	     mTvPlayMaxPoint = (TextView) findViewById(R.id.tvPlayMaxPoint);
	     mTvPlayMaxPoint.setText(maxMinPointStr + maxSecPointStr);
	     
	     
	     submit = (ImageButton) findViewById(R.id.submit);
	     tvPlayStartPoint = (TextView)findViewById(R.id.tvPlayStartPoint);
	     cancel = (ImageButton) findViewById(R.id.cancel);
	     linearlayoutmain = (LinearLayout) findViewById(R.id.linearlayoutmain);
	     linearlayoutmain.setOnClickListener(this);
	     
	     
	     
	     submit.setOnClickListener(this);
	     cancel.setOnClickListener(this);
	     mBtnStartPlay.setOnClickListener(this);
		 mBtnStopPlay.setOnClickListener(this);
		 
		 voicetitle.setText(MyStudioVoiceAdapter.mVoiceRecordingViewHolder.get(index).getTitle());
		 
    }
	
	public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);        
    }	
	
	public int progressToTimer(int progress, int totalDuration) {
		int currentDuration = 0;
		totalDuration = (int) (totalDuration / 1000);
		currentDuration = (int) ((((double)progress) / 100) * totalDuration);
		
		// return current duration in milliseconds
		return currentDuration * 1000;
	}
	
	private Runnable mUpdateTimeTask = new Runnable() {
		   public void run() 
		   {
			   try
			   {
				   
				   tvPlayStartPoint.setText(getTime(mPlayer.getCurrentPosition()));
				   int progress = (int)(getProgressPercentage(mPlayer.getCurrentPosition(), point));
				   mPlayProgressBar.setProgress(progress);
			       mHandler.postDelayed(this, 100);
			   }
			   catch(Exception e) 
			   {
			   }
		   }
		};
		
	public int getProgressPercentage(long currentDuration, long totalDuration){
		Double percentage = (double) 0;
		
		long currentSeconds = (int) (currentDuration / 1000);
		long totalSeconds = (int) (totalDuration / 1000);
		
		// calculating percentage
		percentage =(((double)currentSeconds)/totalSeconds)*100;
		
		// return percentage
		return percentage.intValue();
	}
		

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
	    {
	    	case R.id.linearlayoutmain:
	    		InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(title.getWindowToken(),0);
				imm.hideSoftInputFromWindow(story.getWindowToken(),0);
	    		break;
	        
	      case R.id.btnStartPlay:
	        mBtnStartPlayOnClick();
	        break;
	        
	      case R.id.btnStopPlay:
	        mBtnStopPlayOnClick();
	        break;
	        
	      case R.id.submit:
	    	 title1 = title.getText().toString();
	 	     story1 = story.getText().toString();
	 	    mCustomDialog = new CustomDialog(MyStudioVoiceDetailActivity2.this,null,"방송국으로 전송 하시겠습니까?",leftClickListener,rightClickListener);
			mCustomDialog.show();
	 	     
	    	break;
	    	
	      case R.id.cancel:
	    	  mBtnStopPlayOnClick();
	    	  finish();
		    break;	
	    	
	      default:
	        break;
	    }
	}
	
	@Override
    public void onBackPressed() {
		mBtnStopPlayOnClick();
		finish();
		
	}
	
	 private void mBtnStartPlayOnClick()
	  {
	    if (mPlayerState == PLAY_STOP)
	    {
	      mPlayerState = PLAYING;
	      initMediaPlayer();
	      startPlay();
	      updateUI();
	    }
	    else if (mPlayerState == PLAYING)
	    {
	      mPlayerState = PLAY_PAUSE;
	      pausePlay();
	      updateUI();
	    }
	    else if (mPlayerState == PLAY_PAUSE)
	    {
	      mPlayerState = PLAYING;
	      startPlay();
	      updateUI();
	    }
	  }
	 
	 private void mBtnStopPlayOnClick()
	  {
	    if (mPlayerState == PLAYING || mPlayerState == PLAY_PAUSE)
	    {
	      mPlayerState = PLAY_STOP;
	      stopPlay();
	      releaseMediaPlayer();
	      updateUI();      
	      tvPlayStartPoint.setText("00:00");
	    }
	  }
	 
	 private void initMediaPlayer()
	  {
	    // 미디어 플레이어 생성
	    if (mPlayer == null)
	      mPlayer = new MediaPlayer();
	    else
	      mPlayer.reset();
	    
	    mPlayer.setOnCompletionListener(this);
	    String fullFilePath = Environment.getExternalStorageDirectory().toString()+"/CFKorea/PyungHwa/Voice/"+indextitle;
	    
	    try
	    {
	      mPlayer.setDataSource(fullFilePath);
	      mPlayer.prepare();   
	      int point = mPlayer.getDuration();
	      mPlayProgressBar.setMax(point);
	      
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
	      
	      //mTvPlayMaxPoint.setText(maxMinPointStr + maxSecPointStr);
	      
	      mPlayProgressBar.setProgress(0);
	    }
	    catch(Exception e)
	    {
	      Log.v("ProgressRecorder", "미디어 플레이어 Prepare Error ==========> " + e);
	    }
	  }
	  
	  // 재생 시작
	  private void startPlay()
	  {
	    Log.v("ProgressRecorder", "startPlay().....");
	    
	    try
	    {
	      mPlayer.start();
	      
	      // SeekBar의 상태를 0.1초마다 체크      
	      mProgressHandler2.sendEmptyMessageDelayed(0, 100);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      Toast.makeText(this, "error : " + e.getMessage(), 0).show();
	    }
	  }
	  
	  private void pausePlay()
	  {
	    Log.v("ProgressRecorder", "pausePlay().....");
	    
	    // 재생을 일시 정지하고
	    mPlayer.pause();
	    
	    // 재생이 일시정지되면 즉시 SeekBar 메세지 핸들러를 호출한다.
	    mProgressHandler2.sendEmptyMessageDelayed(0, 0);
	  }
	  
	  private void stopPlay()
	  {
	    Log.v("ProgressRecorder", "stopPlay().....");
	    
	    // 재생을 중지하고
	    mPlayer.stop();
	    
	    // 즉시 SeekBar 메세지 핸들러를 호출한다. 
	    mProgressHandler2.sendEmptyMessageDelayed(0, 0);
	  }
	  
	  private void releaseMediaPlayer()
	  {
	    Log.v("ProgressRecorder", "releaseMediaPlayer().....");
	    mPlayer.release();
	    mPlayer = null;
	    mPlayProgressBar.setProgress(0);
	  }

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mPlayerState = PLAY_STOP; // 재생이 종료됨

	    // 재생이 종료되면 즉시 SeekBar 메세지 핸들러를 호출한다.
	    mProgressHandler2.sendEmptyMessageDelayed(0, 0);
	    
	    updateUI();
		
	}
	
	Handler mProgressHandler2 = new Handler()
	  {
	    public void handleMessage(Message msg)
	    {
	      if (mPlayer == null) return;
	      
	      try
	      {
	        if (mPlayer.isPlaying())
	        {
	          mPlayProgressBar.setProgress(mPlayer.getCurrentPosition());
	          Log.d("getCurrentPosition = ",getTime(mPlayer.getCurrentPosition()));
	          tvPlayStartPoint.setText(getTime(mPlayer.getCurrentPosition()));
	          mProgressHandler2.sendEmptyMessageDelayed(0, 100);
	        }
	      }
	      catch (IllegalStateException e)
	      {}
	      catch (Exception e)
	      {}
	    }
	  };
	  
	  private String getTime(int n) {
		  int hour, minute, second;
		  int time = n / 1000;
		  String timer = null;
		 
		    hour = time / 3600;
		    minute = time %3600;
		    second = minute % 60;
		    minute = minute / 60;
		    //timer = String.format("%2s:%2s:%2s", addZero(hour), addZero(minute), addZero(second));
		    timer = String.format("%2s:%2s",addZero(minute), addZero(second));
		  return timer;
		 }
	  
	  private String addZero(int n) {
		  // TODO Auto-generated method stub
		  if(n < 10) return "0"+n;
		  else return String.valueOf(n);
		 }
	  
	  private void updateUI()
	  {
	    if (mRecState == REC_STOP) 
	    {
	    }
	    else if (mRecState == RECORDING)
	    
	    if (mPlayerState == PLAY_STOP)
	    {
	      mPlayProgressBar.setProgress(0);
	    }
	    else if (mPlayerState == PLAYING){}
	    else if (mPlayerState == PLAY_PAUSE){}
	  } 
	
	  public Handler handler = new Handler() {
		    @Override
		    public void handleMessage(Message msg) {
		    	Log.d("handler","loagindDialog.dismiss()");
		    	if(MenuControll.loagindDialog.isShowing()){
		  			MenuControll.loagindDialog.dismiss();
	    		}
		    	if(sub==true){
		    	Toast.makeText(getApplicationContext(), "전송이 완료되었습니다.", Toast.LENGTH_SHORT).show();
		    	finish();
		    	}
		    	else{
		    		Toast.makeText(getApplicationContext(), "Wifi연결 강도를 확인해 주세요", Toast.LENGTH_SHORT).show();
		    	}
		    	//finish();
		    }
		};
		
		public static void UploadTest(String k,String title, String story) {
	        try {
	        	
	            upload(k,title,story);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	 
	    public static void upload(String filePath,String title, String story) throws IOException {
	    	try{
	    	//String url = "http://192.168.0.200:8888/Pyeonghwa/upload/upRecordVoice.jsp";
	        String url = "http://115.68.182.113/Pyeonghwa/upload/upRecordVoice.jsp?loginid="+MainActivity.mainloginid;
	 
	        // 데이터 구분문자. 아무거나 정해도 상관없지만 꼭 나타날 수 없는 형태의 문자열로 정한다.
	        String boundary = "^******^";
	 
	        // 데이터 경계선
	        String delimiter = "\r\n--" + boundary + "\r\n";
	 
	        StringBuffer postDataBuilder = new StringBuffer();
	 
	        // 추가하고 싶은 Key & Value 추가
	        // key & value를 추가한 후 꼭 경계선을 삽입해줘야 데이터를 구분할 수 있다.
	        postDataBuilder.append(delimiter);
	        postDataBuilder.append(setValue("title", title));
	        postDataBuilder.append(delimiter);
	        postDataBuilder.append(setValue("story", story));
	        postDataBuilder.append(delimiter);
	        postDataBuilder.append(setValue("id", MainActivity.mainloginid));
	        postDataBuilder.append(delimiter);
	 
	        // 파일 첨부
	        postDataBuilder.append(setFile("uploadedFile", "temp.jpg"));
	        postDataBuilder.append("\r\n");
	 
	        // 커넥션 생성 및 설정
	        HttpURLConnection conn = (HttpURLConnection) new URL(url)
	                .openConnection();
	        conn.setDoInput(true);
	        conn.setDoOutput(true);
	        conn.setUseCaches(false);
	        conn.setChunkedStreamingMode(1024*1024);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Connection", "Keep-Alive");
	        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="
	                + boundary);
	        sub =true;
	        // 전송 작업 시작
	        FileInputStream in = new FileInputStream(filePath);
	        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
	                conn.getOutputStream()));
	 
	        // 위에서 작성한 메타데이터를 먼저 전송한다. (한글이 포함되어 있으므로 UTF-8 메소드 사용)
	        out.writeUTF(postDataBuilder.toString());
	 
	        // 파일 복사 작업 시작
	        int maxBufferSize = 1024*1024;
	        Log.d(TAG,"maxBufferSize = " + maxBufferSize);
	        int bufferSize = Math.min(in.available(), maxBufferSize);
	        Log.d(TAG,"bufferSize = " + bufferSize);
	        byte[] buffer = new byte[bufferSize];
	 
	        // 버퍼 크기만큼 파일로부터 바이트 데이터를 읽는다.
	        int byteRead = in.read(buffer, 0, bufferSize);
	        Log.d(TAG,"byteRead = " + byteRead);
	 
	        // 전송
	        /*
	        while (byteRead > 0) {
	            out.write(buffer);
	            bufferSize = Math.min(in.available(), maxBufferSize);
	            byteRead = in.read(buffer, 0, bufferSize);
	        }
	         */
	        
	        try
	        {
	            while (byteRead > 0)
	            {
	            	
	                try
	                {
	                    out.write(buffer, 0, bufferSize);
	                }
	                catch (OutOfMemoryError e)
	                {
	                    e.printStackTrace();
	                    Log.d("TTT", "Exception 1");
	                }
	                
	                bufferSize = Math.min(in.available(), maxBufferSize);
	                Log.d(TAG,"bufferSize = " + bufferSize);
	                byteRead = in.read(buffer, 0, bufferSize);
	                Log.d(TAG,"byteRead = " + byteRead);
	            }
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	            Log.d("TTT", "Exception 2");
	            
	        }
	        out.writeBytes(delimiter); // 반드시 작성해야 한다.
	        out.flush();
	        out.close();
	        in.close();
	        out = null;
	        in = null;
	 
	        // 결과 반환 (HTTP RES CODE)
	        conn.getInputStream();
	        conn.disconnect();
	    	}
	    	catch(Exception e){
	    		sub =false;
	    	}
	    }
	 
	    /**
	     * Map 형식으로 Key와 Value를 셋팅한다.
	     * @param key : 서버에서 사용할 변수명
	     * @param value : 변수명에 해당하는 실제 값
	     * @return
	     */
	    public static String setValue(String key, String value) {
	        return "Content-Disposition: form-data; name=\"" + key + "\"r\n\r\n"
	                + value;
	    }
	 
	    /**
	     * 업로드할 파일에 대한 메타 데이터를 설정한다.
	     * @param key : 서버에서 사용할 파일 변수명
	     * @param fileName : 서버에서 저장될 파일명
	     * @return
	     */
	    public static String setFile(String key, String fileName) {
	        return "Content-Disposition: form-data; name=\"" + key
	                + "\";filename=\"" + fileName + "\"\r\n";
	    }
	    
	    private View.OnClickListener leftClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCustomDialog.dismiss();
				MenuControll.sending(MyStudioVoiceDetailActivity2.this);
	   		    
	   			Thread thread = new Thread(new Runnable() {
	   		  		public void run() {
	   		  			//HttpFileUpload("http://192.168.0.200:8888/Pyeonghwa/upload/upRecordVoice.jsp","",k);
	   		  			//sendFileToServer(k,"http://192.168.0.200:8888/Pyeonghwa/upload/upRecordVoice.jsp");
	   		  			//sendFileToServer(k,"http://115.68.182.113/Pyeonghwa/upload/upRecordMovie.jsp");
	   		  			Log.d(TAG,"k = " + MyStudioVoiceAdapter.mVoiceRecordingViewHolder.get(index).getData());
	   		  			Log.d(TAG,"title1 = " + title1);
	   		  			Log.d(TAG,"story1 = " + story1);
	   		  			Log.d(TAG,"id = " + MainActivity.mainloginid);
	   		  			UploadTest(MyStudioVoiceAdapter.mVoiceRecordingViewHolder.get(index).getData(),title1,story1);
	   		  			handler.sendEmptyMessage(0);
	   		  			}
	   				});
	   			thread.start();
			}
		};

		private View.OnClickListener rightClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCustomDialog.dismiss();
				
			}
		};
		
//		public void onStartTrackingTouch(SeekBar seekBar) {
////			mHandler.removeCallbacks(mUpdateTimeTask);
//		}
//		
//		@Override
//		public void onStopTrackingTouch(SeekBar seekBar) {
////			mHandler.removeCallbacks(mUpdateTimeTask);
////			int totalDuration = mMediaPlayer.getDuration();
////			int currentPosition = mUtils.progressToTimer(seekBar.getProgress(), totalDuration);
////			
////			mMediaPlayer.seekTo(currentPosition);
////			updateProgressBar();
//		}


}
