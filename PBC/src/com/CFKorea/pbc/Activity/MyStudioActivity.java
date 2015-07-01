package com.CFKorea.pbc.Activity;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.R.drawable;
import com.CFKorea.pbc.Adapter.MyStudioPhotoDeleteAdpater;
import com.CFKorea.pbc.Adapter.MyStudioVideoDeleteAdapter;
import com.CFKorea.pbc.Adapter.MyStudioVoiceDeleteAdapter;
import com.CFKorea.pbc.Manager.PhotoManager;
import com.CFKorea.pbc.Manager.VideoManager;
import com.CFKorea.pbc.Manager.VoiceManager;
import com.CFKorea.pbc.Utility.Define;
import com.CFKorea.pbc.Utility.VideoRecoderPreview;
import com.CFKorea.pbc.Utility.VoiceRecordingUtils;
import com.CFKorea.pbc.ViewHolder.PhotoViewholder;
import com.CFKorea.pbc.ViewHolder.VideoViewHolder;
import com.CFKorea.pbc.ViewHolder.VoiceViewholder;

public class MyStudioActivity extends FragmentActivity implements OnClickListener {

	private final String TAG = "[MyStudioActivity]";
	private int mCurrentFragmentIndex;
	public final static int FRAGMENT_ONE = 0;
	public final static int FRAGMENT_TWO = 1;
	public final static int FRAGMENT_THREE = 2;
	private int video;
	private ImageView bt_oneFragment;
	private Button bt_twoFragment;
	private Button bt_threeFragment;
	private ImageButton btn_back;
	private String black = "#000000";
	private String white = "#ffffff";
	private String gray="#e1e1e1";
	private int r =0;
	private ProgressDialog loagindDialog;
	public static ArrayList<PhotoViewholder> mPhotoThumNailViewHolder;
	public static ArrayList<VideoViewHolder> mMusicVideoViewHolder;
	public static ArrayList<VoiceViewholder> mVoiceRecordingViewHolder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mystudio);
		
		Intent i = getIntent();
		video = i.getIntExtra("video", 1);
		Log.d(TAG,"video = " + video);
		createThreadAndDialog();
		//onInit();
	}
	
	public void onInit(){
		bt_oneFragment = (ImageView) findViewById(R.id.bt_oneFragment);
		bt_oneFragment.setOnClickListener(this);
		bt_twoFragment = (Button) findViewById(R.id.bt_twoFragment);
		bt_twoFragment.setOnClickListener(this);
		bt_threeFragment = (Button) findViewById(R.id.bt_threeFragment);
		bt_threeFragment.setOnClickListener(this);
		btn_back = (ImageButton) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		
		String [] selection_Args = new String[] {"%" + Define.MY_STUDIO_VIDEO.toString() +"%"};
		Cursor cur = this.getContentResolver().query(Define.MEDIA_URI_MUSIC_VIDEO, null, Define.SELECTION, selection_Args, Define.ORDER_BY, null);
		Log.d(TAG,"count = " + cur.getCount());
		cur.close();
	}
	
	public void ManagerExcute(){
		Log.d(TAG,"======================MEMORY CHECK MYSTUDIO RELOAD!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		try
		{
			mPhotoThumNailViewHolder = new PhotoManager(this).execute(Define.MY_STUDIO_PHOTO).get();
			mMusicVideoViewHolder = new VideoManager(this).execute(Define.MY_STUDIO_VIDEO).get();
			mVoiceRecordingViewHolder = new VoiceManager(this).execute(Define.MY_STUDIO_VOICE).get();
			Log.d(TAG,"voice size = " + mVoiceRecordingViewHolder.size());
			
		}
		catch(Exception e)
		{		
			Log.d(TAG,"========================================ERROR============================================");
			Log.d(TAG,"e.printStackTrace() =" + e);
		}
		
	}
	
	private void createThreadAndDialog() {
		  // TODO Auto-generated method stub
		if(MyStudioPhotoActivity.deleteNotify == "Y" 
					|| MyStudioVideoActivity.deleteNotify == "Y" || MyStudioVoiceActivity.deleteNotify =="Y"){
			loagindDialog = ProgressDialog.show(this,"","삭제중...", true);
		}
		else{
			loagindDialog = ProgressDialog.show(this,"","Loding...", true);
		}
		  
			  	Thread thread = new Thread(new Runnable() {
			  		public void run() {
			  			Log.d(TAG,"createThreadAndDialog()");
			  			Log.d(TAG,"MyStudioPhotoActivity.deleteNotify = " + MyStudioPhotoActivity.deleteNotify);
			  			if(mPhotoThumNailViewHolder == null || MyStudioPhotoActivity.deleteNotify == "Y" 
			  					|| MyStudioVideoActivity.deleteNotify == "Y" || MyStudioVoiceActivity.deleteNotify =="Y" ||
			  					VoiceRecordingUtils.savevoice=="Y"||VideoRecoderPreview.savevideo=="Y"){
			  				MyStudioPhotoActivity.deleteNotify = "N";
			  				MyStudioVideoActivity.deleteNotify = "N";
			  				MyStudioVoiceActivity.deleteNotify = "N";
			  				VoiceRecordingUtils.savevoice="N";
			  				VideoRecoderPreview.savevideo="N";
			  				ManagerExcute();
			  			}
			  			
			  			onInit();
				   		handler.sendEmptyMessage(0);
			  		}	
			  	});
		  	thread.start();
	}
		
		
	///handler
	public Handler handler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	    	Log.d(TAG,"loagindDialog.dismiss()");
	    		if(loagindDialog.isShowing()){
	    			loagindDialog.dismiss();
	    		}
	    		if(video == 2){
    				mCurrentFragmentIndex = FRAGMENT_TWO;
    				bt_oneFragment.setImageResource(drawable.btn_mystudio_photo);
    				bt_twoFragment.setBackgroundResource(drawable.btn_mystudio_video);
    				bt_threeFragment.setBackgroundResource(drawable.btn_mystudio_voice_pressed);
    			}
    			else if(video == 3){
    				mCurrentFragmentIndex = FRAGMENT_THREE;
    				bt_oneFragment.setImageResource(drawable.btn_mystudio_photo);
    				bt_twoFragment.setBackgroundResource(drawable.btn_mystudio_video_pressed);
    				bt_threeFragment.setBackgroundResource(drawable.btn_mystudio_voice);
    			}
    			else
    			{
    			mCurrentFragmentIndex = FRAGMENT_TWO;
    			}
	    		fragmentReplace(mCurrentFragmentIndex);
	    	 
	    }
	};

	public void fragmentReplace(int reqNewFragmentIndex) {

		Fragment newFragment = null;

		Log.d(TAG, "fragmentReplace " + reqNewFragmentIndex);

		newFragment = getFragment(reqNewFragmentIndex);

		// replace fragment
		final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

		transaction.replace(R.id.ll_fragment, newFragment);

		// Commit the transaction
		transaction.commit();

	}

	private Fragment getFragment(int idx) {
		Fragment newFragment = null;

		switch (idx) {
		case FRAGMENT_ONE:
			newFragment = new MyStudioPhotoActivity(this, mPhotoThumNailViewHolder);
			
			//photo select item 초기화
			if(MyStudioPhotoActivity.all != null){
				for(int i=0;i < mPhotoThumNailViewHolder.size();i++){
					mPhotoThumNailViewHolder.get(i).setCheckedState(false);
				}
				MyStudioPhotoDeleteAdpater.selectList = new ArrayList<String>();
				MyStudioPhotoDeleteAdpater.shareList = new ArrayList<String>();
				MyStudioPhotoActivity.all.setChecked(false);
			}
			//video select item 초기화
			if(MyStudioVideoActivity.all != null){
				for(int i=0;i < mMusicVideoViewHolder.size();i++){
					mMusicVideoViewHolder.get(i).setCheckedState(false);
				}
				MyStudioVideoDeleteAdapter.selectList = new ArrayList<String>();
				MyStudioVideoDeleteAdapter.shareList = new ArrayList<String>();
				MyStudioVideoActivity.all.setChecked(false);
			}
			//voice select item 초기화
			if(MyStudioVoiceActivity.all != null){
				for(int i=0;i < mVoiceRecordingViewHolder.size();i++){
					mVoiceRecordingViewHolder.get(i).setCheckedState(false);
				}
				MyStudioVoiceDeleteAdapter.selectList = new ArrayList<String>();
				MyStudioVoiceDeleteAdapter.shareList = new ArrayList<String>();
				MyStudioVoiceActivity.all.setChecked(false);
			}
			break;
		case FRAGMENT_TWO:
			newFragment = new MyStudioVideoActivity(this, mMusicVideoViewHolder);
			
			//photo select item 초기화
			if(MyStudioPhotoActivity.all != null){
				for(int i=0;i < mPhotoThumNailViewHolder.size();i++){
					mPhotoThumNailViewHolder.get(i).setCheckedState(false);
				}
				MyStudioPhotoDeleteAdpater.selectList = new ArrayList<String>();
				MyStudioPhotoDeleteAdpater.shareList = new ArrayList<String>();
				MyStudioPhotoActivity.all.setChecked(false);
			}
			//video select item 초기화
			if(MyStudioVideoActivity.all != null){
				for(int i=0;i < mMusicVideoViewHolder.size();i++){
					mMusicVideoViewHolder.get(i).setCheckedState(false);
				}
				MyStudioVideoDeleteAdapter.selectList = new ArrayList<String>();
				MyStudioVideoDeleteAdapter.shareList = new ArrayList<String>();
				MyStudioVideoActivity.all.setChecked(false);
			}
			//voice select item 초기화
			if(MyStudioVoiceActivity.all != null){
				for(int i=0;i < mVoiceRecordingViewHolder.size();i++){
					mVoiceRecordingViewHolder.get(i).setCheckedState(false);
				}
				MyStudioVoiceDeleteAdapter.selectList = new ArrayList<String>();
				MyStudioVoiceDeleteAdapter.shareList = new ArrayList<String>();
				MyStudioVoiceActivity.all.setChecked(false);
			}
			
			break;
		case FRAGMENT_THREE:
			newFragment = new MyStudioVoiceActivity(this, mVoiceRecordingViewHolder);
			
			if(MyStudioPhotoActivity.all != null){
				for(int i=0;i < mPhotoThumNailViewHolder.size();i++){
					mPhotoThumNailViewHolder.get(i).setCheckedState(false);
				}
				MyStudioPhotoDeleteAdpater.selectList = new ArrayList<String>();
				MyStudioPhotoDeleteAdpater.shareList = new ArrayList<String>();
				MyStudioPhotoActivity.all.setChecked(false);
			}
			if(MyStudioVideoActivity.all != null){
				for(int i=0;i < mMusicVideoViewHolder.size();i++){
					mMusicVideoViewHolder.get(i).setCheckedState(false);
				}
				MyStudioVideoDeleteAdapter.selectList = new ArrayList<String>();
				MyStudioVideoDeleteAdapter.shareList = new ArrayList<String>();
				MyStudioVideoActivity.all.setChecked(false);
			}
			if(MyStudioVoiceActivity.all != null){
				for(int i=0;i < mVoiceRecordingViewHolder.size();i++){
					mVoiceRecordingViewHolder.get(i).setCheckedState(false);
				}
				MyStudioVoiceDeleteAdapter.selectList = new ArrayList<String>();
				MyStudioVoiceDeleteAdapter.shareList = new ArrayList<String>();
				MyStudioVoiceActivity.all.setChecked(false);
			}
			
			break;

		default:
			Log.d(TAG, "Unhandle case");
			break;
		}

		return newFragment;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.bt_oneFragment:
			//MenuControll.Toastshow(this);
			mCurrentFragmentIndex = FRAGMENT_ONE;
			fragmentReplace(mCurrentFragmentIndex);
			
			bt_oneFragment.setImageResource(drawable.btn_mystudio_photo_pressed);
			bt_twoFragment.setBackgroundResource(drawable.btn_mystudio_video);
			bt_threeFragment.setBackgroundResource(drawable.btn_mystudio_voice);
			break;
		case R.id.bt_twoFragment:
			r=1;
			mCurrentFragmentIndex = FRAGMENT_TWO;
			fragmentReplace(mCurrentFragmentIndex);
			
			bt_oneFragment.setImageResource(drawable.btn_mystudio_photo);
			bt_twoFragment.setBackgroundResource(drawable.btn_mystudio_video);
			bt_threeFragment.setBackgroundResource(drawable.btn_mystudio_voice_pressed);
			break;
		case R.id.bt_threeFragment:
			mCurrentFragmentIndex = FRAGMENT_THREE;
			fragmentReplace(mCurrentFragmentIndex);
			bt_oneFragment.setImageResource(drawable.btn_mystudio_photo);
			bt_twoFragment.setBackgroundResource(drawable.btn_mystudio_video_pressed);
			bt_threeFragment.setBackgroundResource(drawable.btn_mystudio_voice);
			break;
		
		case R.id.btn_back:
			
			if(MainActivity.mLoginYN==null||MainActivity.mLoginYN.equals("N")){
				Intent intent = new Intent();
				intent.setClass(this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				this.startActivity(intent);
				
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
			break;
		}

	}
	
	
	@Override
    public void onBackPressed() {
		if(MainActivity.mLoginYN==null||MainActivity.mLoginYN.equals("N")){
			Intent intent = new Intent();
			intent.setClass(this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(intent);
		}
		else{
			Intent intent = new Intent();
			intent.setClass(this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("loginYN", "Y");
			intent.putExtra("mainloginid", MainActivity.mainloginid);
			intent.putExtra("message", "N");
			Log.d(TAG,"back admin =" + MainActivity.admin);
			Log.d(TAG,"back general = " + MainActivity.general);
			if(MainActivity.admin==true){
				intent.putExtra("admin", true);
			}
			else if(MainActivity.general==true){
				intent.putExtra("general", true);
			}
			this.startActivity(intent);
			Log.d(TAG,"mainloginid = " + MainActivity.mainloginid);
		}
	
    }
	
	
//	@Override
//	public void onDestroy(){
//		Log.d(TAG,"onDestroy()");
//		if(PhotoManager.mPhotoThumnailBm != null){
//		PhotoManager.mPhotoThumnailBm.recycle();
//		PhotoManager.mPhotoThumnailBm = null;
//		}
//		if(VideoManager.mVideoThumnailBm != null){
//		VideoManager.mVideoThumnailBm.recycle();
//		VideoManager.mVideoThumnailBm = null;
//		}
//	super.onDestroy();
//	}
	
}
