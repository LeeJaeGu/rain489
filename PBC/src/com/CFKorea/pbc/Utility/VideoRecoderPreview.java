package com.CFKorea.pbc.Utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Activity.VideoRecordingActivity;
import com.CFKorea.pbc.Activity.VoiceActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;



public class VideoRecoderPreview extends SurfaceView implements SurfaceHolder.Callback {
	
	public final int MEDIA_TYPE_VIDEO = 2;
	private SurfaceHolder mHolder;
    public Camera mCamera;
    private Context mContext;
    private final String TAG = "DEBUG_CameraPreview";
    private int mCameraFacing;
    private String mCameraFlash; // 전면 or 후면 카메라 상태 저장
    
    public MediaRecorder mMediaRecorder;
    private String mPath = "";//PhoneStateCheckListener phoneCheckListener;
    private File m_MediaFile;
    private Parameters mParameters;
    public static String savevideo;
    public VideoRecoderPreview(Context context, int cameraFacing, String cameraFlash) {
        super(context);
        
        mContext = context;
        
        mHolder = getHolder();
        mHolder.addCallback(this);
        
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
        mCameraFacing = cameraFacing;
        mCameraFlash = cameraFlash;
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    	 mCamera.startPreview();
    }
   
    public void surfaceCreated(SurfaceHolder holder) {
    	//mHolder = holder;
    	
    	try 
    	{
    		//카메라 전후면 설정
    		mCamera = getCameraInstance();
    		mParameters = mCamera.getParameters();
    		mParameters.setFlashMode(mCameraFlash);
    		
    		mCamera.setParameters(mParameters);
    		mCamera.setPreviewDisplay(mHolder);
      	
	    } 
    	catch (Exception e) 
    	{
    		mCamera.release();
            mCamera = null;
          	
            e.printStackTrace(System.out);
	     
	    }
    	
        
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    
    /**
     * <pre>
     * 1. 개요 : 
     * 2. 처리내용 : 미디어 레코더 초기화설정
     * 3. 파라미터 : 
     *
     * @Method Name : prepareVideoRecorder
     * @return type : boolean
     * </pre>
     */
    public boolean prepareVideoRecorder() {
    	
    	if ( mCamera == null) 
    	{
    		mCamera = getCameraInstance();
    	}
    	
        mMediaRecorder = new MediaRecorder();

        // Step 1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);

        // Step 2: Set sources
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_480P));

        // Step 4: Set output file
        mMediaRecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).toString());

        // Step 5: Set the preview output
        mMediaRecorder.setPreviewDisplay(getHolder().getSurface());

        // Step 6: Prepare configured MediaRecorder
        try {
            mMediaRecorder.prepare();
           	Thread.sleep(1000);
            //mMediaRecorder.start(); 
        } catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            onRecStop();
            return false;
        } catch (IOException e) {
            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            onRecStop();
            return false;
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return true;
    }
    
    public void onRecStart(){
    	//Toast.makeText(mContext, mContext.getResources().getString(R.string.video_start), Toast.LENGTH_SHORT).show();
    	mMediaRecorder.start();
    	VideoRecordingActivity.timer.setVisibility(View.VISIBLE);
    	VideoRecordingActivity.timer.setBase(SystemClock.elapsedRealtime());
    	VideoRecordingActivity.timer.start();
    	
    }
    
    public void onRecStop(){
    	VideoRecordingActivity.timer.stop();
    	VideoRecordingActivity.timer.setVisibility(View.GONE);
    	if (mMediaRecorder != null) 
    	{
    		Toast.makeText(mContext, mContext.getResources().getString(R.string.video_end) + "\n" + mPath, Toast.LENGTH_LONG).show();
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }
    	
    	if ( mPath != null) 
    	{
    		mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+ mPath)));
    	}
    	//finish();  
    }
    public void releaseMediaRecorder(){
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }
    }
    public Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(mCameraFacing); //openFrontFacingCamera();
        }
        catch (Exception e){
        	Log.d(TAG, "getCameraInstance is null" + e);
        }
        return c; // returns null if camera is unavailable
    }
    
    /** Create a File for saving an image or video */
    private File getOutputMediaFile(int type) {
    	
    	//file create
    	File file = fileCreate(Environment.getExternalStorageDirectory().toString() + "/CFKorea/PyungHwa/Video");
        
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        
        if(type == MEDIA_TYPE_VIDEO) 
        {
        	mPath = file.getPath() + File.separator + "VID_"+ timeStamp + ".mp4";
            m_MediaFile = new File(mPath);
            savevideo="Y";
        } 
        else 
        {
            return null;
        }
        return m_MediaFile;
    }
    
    public File fileCreate (String filePath) {
		File file = new File(filePath);
		
		// 원하는 경로에 폴더가 있는지 확인
		if( !file.exists() )
		{
			file.mkdirs();
		}
		
		return file;
		
	}
  
}
