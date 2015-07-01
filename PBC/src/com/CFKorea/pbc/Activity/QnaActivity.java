package com.CFKorea.pbc.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.CFKorea.pbc.R;

public class QnaActivity extends Activity implements OnClickListener{
	
	private String TAG = "[QnaActivity]";
	private WebView mWebView;
	private ImageView btn_back;
	private ValueCallback<Uri> mUploadMessage;
	
	private final static int FILECHOOSER_RESULTCODE = 1;
	
	 @Override
	 protected  void onActivityResult(int requestCode, int resultCode,
	         Intent intent) {
	     if (requestCode == FILECHOOSER_RESULTCODE) {
	         if (null == mUploadMessage)
	             return;
	         Uri result = intent == null || resultCode != RESULT_OK ? null
	                 : intent.getData();
	         mUploadMessage.onReceiveValue(result);
	         mUploadMessage = null;
	     }
	 }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qna);
		
		mWebView = (WebView) findViewById(R.id.webview);
		btn_back = (ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setUseWideViewPort(true);//must be true
		mWebView.getSettings().setRenderPriority(RenderPriority.HIGH);
		mWebView.setInitialScale(1);
		mWebView.getSettings().setPluginState(PluginState.ON);
	    mWebView.setWebChromeClient(new WebChromeClient()
	    {
	         public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
	               mUploadMessage = uploadMsg;
	               Intent i = new Intent(Intent.ACTION_GET_CONTENT);
	               i.addCategory(Intent.CATEGORY_OPENABLE);
	               i.setType("*/*");
	               QnaActivity.this.startActivityForResult(
	                       Intent.createChooser(i, "사진을 선택하세요"),
	                       FILECHOOSER_RESULTCODE);
	           }
	     });
	    
	    mWebView.loadUrl("http://115.68.182.113/Pyeonghwa/podcast/potcast_list.jsp");
	    mWebView.setWebViewClient(new WebViewClientClass());
		
	}
	
	@Override 
    public boolean onKeyDown(int keyCode, KeyEvent event) { 
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) { 
            mWebView.goBack(); 
            return true; 
        } 
        return super.onKeyDown(keyCode, event);
    }
    
    private class WebViewClientClass extends WebViewClient { 
        @Override 
        public boolean shouldOverrideUrlLoading(WebView view, String url) { 
            view.loadUrl(url); 
            return true; 
        } 
    }
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
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
	

}
