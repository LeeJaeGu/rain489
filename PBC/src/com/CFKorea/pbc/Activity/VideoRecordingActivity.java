package com.CFKorea.pbc.Activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Utility.VideoRecoderPreview;

public class VideoRecordingActivity extends Activity implements OnClickListener{

	
	/** Log Tag */
	private final String TAG = "RecordingActivity";
	
	private VideoRecoderPreview mPreview; //미리보기
	public static Camera mCamera;//카메라
	private int mCameraFacing; // 전면 or 후면 카메라 상태 저장
	private String mCameraFlash; // 전면 or 후면 카메라 상태 저장
	
	private ImageButton mCaptureButton;
	private ImageButton mFlashButton;
	private ImageButton mListButton;
    private ImageButton mFacingButton;
    public static Chronometer timer;

    private Parameters mParameters;
    public static MediaPlayer mMediaPlayer = null;
    private static ArrayList<String> mSongPath;
    
    private String path = "";//PhoneStateCheckListener phoneCheckListener;
    private boolean isRecording = false;
    public int mSongIndex = -1;
    
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // 카메라 화면 유지 꺼지지 않도록 설정
		mCameraFacing  = Camera.CameraInfo.CAMERA_FACING_BACK; // 최초 카메라 상태는 후면카메라로 설정
		mCameraFlash = Parameters.FLASH_MODE_OFF; // 최초 카메라 플래쉬는 off
		
		//fileCreate(Define.MY_STUDIO_VIDEO);
		onInit();
	}
	
	
	public void onInit() {
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// Create our Preview view and set it as the content of our activity.
        mPreview = new VideoRecoderPreview(this, mCameraFacing, mCameraFlash);
        
        setContentView(R.layout.activity_videorecording);
        
        
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
        	LayoutParams.WRAP_CONTENT);
        params.height = getWindowManager().getDefaultDisplay().getHeight(); // 원하는 높이
        params.width = (int) (params.height * 4.0 / 2.88); // 원하는 너비
        params.gravity = Gravity.CENTER; // 정렬

        preview.addView(mPreview,params);
        
        mFlashButton = (ImageButton) findViewById(R.id.button_flash);
        mCaptureButton = (ImageButton) findViewById(R.id.button_capture);
        mFacingButton = (ImageButton) findViewById(R.id.button_facing);
        timer = (Chronometer) findViewById(R.id.timer);
        
        mCaptureButton.setOnClickListener(this);
        mFlashButton.setOnClickListener(this);
        mFacingButton.setOnClickListener(this);
        
        if ( mCameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT )
        {
        	mFlashButton.setVisibility(View.GONE);
        }
        else
        {
        	mFlashButton.setVisibility(View.VISIBLE);
	        if ( mCameraFlash.equals(Parameters.FLASH_MODE_TORCH ))
	        {
	        	mFlashButton.setImageResource(R.drawable.btn_camera_light);
	        }
	        else
	        {
	        	mFlashButton.setImageResource(R.drawable.btn_camera_light_pressed);
	        }
        }
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) 
		{
			case R.id.button_capture:
				setRecod();
				break;
				
			case R.id.button_flash:
				setFlash();
				break;
				
			case R.id.button_facing:
				getChangeFacing();
				break;
			default:
				break;
		}
	}
	
	public void setRecod() {
		
		if (isRecording) 
		{
            // stop recording and release camera
			mFacingButton.setClickable(true);
        	mFlashButton.setClickable(true);
        	
        	mFacingButton.setVisibility(View.VISIBLE);
        	mFlashButton.setVisibility(View.VISIBLE);
        	
			mCaptureButton.setImageResource(R.drawable.btn_gallery_recoding_play);
			
        	mSongIndex = -1;
        	stopService();
        	mPreview.onRecStop();
        	
        	this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+ path)));
            isRecording = false;
          
        } 
		else 
		{
			//2015.1.12 JYP 1.0.2 구조 변경 다운로드 된 음악이 없어도 촬영이 가능하도록 수정 [[
			
        	if (mPreview.prepareVideoRecorder()) 
        		{
                    // Camera is available and unlocked, MediaRecorder is prepared,
                    // now you can start recording
                	mCaptureButton.setImageResource(R.drawable.btn_gallery_recoding_play_pressd);
                	Log.d("RecoderActivity", "playSong(mSongIndex); = " + mSongIndex);
                	
                	mFacingButton.setClickable(false);
                	mFlashButton.setClickable(false);
                	
                	mFacingButton.setVisibility(View.GONE);
                	mFlashButton.setVisibility(View.GONE);
                	
                	mPreview.onRecStart();	
                    // inform the user that recording has started
                	
                    isRecording = true;
                    
                    Handler hd = new Handler();
          	      	hd.postDelayed(new Runnable() 
          			{
          				@Override
          				public void run() 
          				{
          					mFacingButton.setClickable(true);
          	        	mFlashButton.setClickable(true);
          	        	
          	        	mFacingButton.setVisibility(View.VISIBLE);
          	        	mFlashButton.setVisibility(View.VISIBLE);
          	        	
          				mCaptureButton.setImageResource(R.drawable.btn_gallery_recoding_play);
          				
          	        	mSongIndex = -1;
          	        	stopService();
          	        	mPreview.onRecStop();
          	        	Toast.makeText(getApplicationContext(), getResources().getString(R.string.video_start), Toast.LENGTH_SHORT).show();
          	        	getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+ path)));
          	            isRecording = false;
          	            }
          				
          			}
          	      	, 121000);
                    
                } 
        		else 
        		{
                    // prepare didn't work, release the camera
                    mPreview.releaseMediaRecorder();
                    // inform user
                }
        }
            
	}

	
	public void stopService() {
		if ( mMediaPlayer != null)
		{
		  mMediaPlayer.stop();  
          mMediaPlayer.release();  
          mMediaPlayer = null;
		}
	}
	
	
	public void setFlash() {
		Log.d(TAG, "mCameraFlash = " + mCameraFlash);
		
		if ( mCameraFlash.equals(Parameters.FLASH_MODE_TORCH ))
		{
			
			mCameraFlash = Parameters.FLASH_MODE_OFF;
			
		}
		else
		{
			
			mCameraFlash = Parameters.FLASH_MODE_TORCH;
			
		}
		
		Log.d(TAG, "mCameraFlash = " + mCameraFlash);
		mPreview = new VideoRecoderPreview(this, mCameraFacing, mCameraFlash);
		onInit();
	}
	public void getChangeFacing() {
		// 전면 -> 후면 or 후면 -> 전면으로 카메라 상태 전환
		mCameraFacing = (mCameraFacing==Camera.CameraInfo.CAMERA_FACING_BACK) ? 
		                 Camera.CameraInfo.CAMERA_FACING_FRONT 
		                         : Camera.CameraInfo.CAMERA_FACING_BACK;
			
        // 변경된 방향으로 새로운 카메라 View 생성
		mPreview = new VideoRecoderPreview(this, mCameraFacing, mCameraFlash);
		
		
        // ContentView, Listener 재설정
		onInit();
	}
	
	
	
	/**
	 * 뒤로가기버튼
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch(keyCode) {
			case KeyEvent.KEYCODE_BACK:
				mPreview.onRecStop();
				stopService();
				//sendIntent(MainActivity.class);
			break;
			case KeyEvent.KEYCODE_HOME:
				
				Log.d("Recoder", "KeyEvent.KEYCODE_HOME" + KeyEvent.KEYCODE_HOME);
				mPreview.onRecStop();
				stopService();
				//sendIntent(MainActivity.class);
			break;
			case KeyEvent.KEYCODE_MENU:
				Log.d("Recoder", "KeyEvent.KEYCODE_MENU" + KeyEvent.KEYCODE_MENU);
				mPreview.onRecStop();
				stopService();
				
				//sendIntent(MainActivity.class);
			break;
			case KeyEvent.KEYCODE_POWER:
				Log.d("Recoder", "KeyEvent.KEYCODE_POWER" + KeyEvent.KEYCODE_MENU);
				mPreview.onRecStop();
				stopService();
				
				//sendIntent(MainActivity.class);
			break;
		}
		return super.onKeyDown(keyCode, event);
		
		
	}
	/**
	 * 
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v(TAG, "onResume" );
    
		
		
	}
	@Override 
	public void onPause(){
	    super.onPause();
	    Log.v(TAG, "onPause" );
	    //2015.1.9 JYP 1.0.2 카메라 촬영시 슬립 및 홈버튼 눌렀을시 영상이 계속 촬영되던 버그 수정 [[
	    //Log.v(TAG, "onPause = PlayerService.mMediaPlayer" + PlayerService.mMediaPlayer );
	    if ( mMediaPlayer != null )
	    {
	    	mPreview.onRecStop();
	    	stopService();
	    }
	    //]]
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		 Log.v(TAG, "onDestroy" );
	  
	}

	/**
	 * intent 정의
	 */
	public void sendIntent(Class anyClass) {
		Intent intent = new Intent(getApplicationContext(), anyClass);
		intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

}
