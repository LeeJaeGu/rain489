package com.CFKorea.pbc.Utility;

import java.io.File;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.CFKorea.pbc.R;

public class MenuControll {
	/** Log Tag */
	private final static String TAG = "MenuControll";
	static MediaScannerConnection mediaScanner; // 변수 선언.
	String path;
	static String mService_name = "com.CFKorea.Halo_2nd_MC.Utility.PlayerService";
	private static ProgressDialog mLoagindDialog;
	private static String LoginId = "";
	private static String LoginPass = "";
	private static String mLoginYN = "";
	public static ProgressDialog loagindDialog;
	
	public static String getLoginId() {
		return LoginId;
	}

	public static void setLoginId(String loginId) {
		LoginId = loginId;
	}

	public static String getLoginPass() {
		return LoginPass;
	}
	
	public static void setLoginPass(String loginPass) {
		LoginPass = loginPass;
	}

	public static String getmLoginYN() {
		Log.d("DEBUG","getmLoginYN");
		return mLoginYN;
	}

	public static void setmLoginYN(String mLoginYN) {
		Log.d("DEBUG","mLoginYN = " + mLoginYN);
		MenuControll.mLoginYN = mLoginYN;
	}
	
	
	public static boolean isService(Context context) {
    	
		
		ActivityManager manager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
    	for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) 
    	{
    	    if (mService_name.equals(service.service.getClassName())) 
    	    {
    	        return true;
    	    }
    	}
    	return false;
	}
	
	public static void mediaScanner (Context context, String path) {
		Log.d(TAG, "mediaScanner path = "  + path );
		Log.d("","*****************************************save******************************************");
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+ path)));
	}
	
	public static void Toastshow(Context context)
	{
		Toast.makeText(context,context.getResources().getString(R.string.login_success),Toast.LENGTH_SHORT).show();
		//mLoagindDialog = ProgressDialog.show(context, null, "로딩중...", true, true);
	}
	public static void Toastshow3(Context context)
	{
		Toast.makeText(context,context.getResources().getString(R.string.logout_success),Toast.LENGTH_SHORT).show();
	}
	public static void Toastshow4(Context context)
	{
		Toast.makeText(context,context.getResources().getString(R.string.voice_start),Toast.LENGTH_SHORT).show();
	}
	public static void Toastshow5(Context context)
	{
		Toast.makeText(context,VoiceRecordingUtils.filename + "\nMY STUDIO > VOICE" + context.getResources().getString(R.string.voice_save) ,Toast.LENGTH_LONG).show();
	}
	public static void Toastshow6(Context context)
	{
		Toast.makeText(context,context.getResources().getString(R.string.voice_max),Toast.LENGTH_SHORT).show();
	}
	public static void error(Context context)
	{
		Toast.makeText(context,VoiceRecordingUtils.filename + "\n" + context.getResources().getString(R.string.error),Toast.LENGTH_LONG).show();
	}
	
	public static void sending(Context context)
	{
		loagindDialog = ProgressDialog.show(context,"",context.getResources().getString(R.string.voice_send), true);
		//new ProgressDlgTest(context).execute(100);
	}
	
	public static void PreviousActivity (Activity activity) {
		activity.finish();
	}
	public static void twominute(Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle(context.getResources().getString(R.string.video_start));
		
		builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   
		           }
		       });

		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	
}
