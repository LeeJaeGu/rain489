package com.CFKorea.pbc.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import com.CFKorea.pbc.R;

public class PopUpActivity extends Activity implements OnClickListener{
	
	Button button1;//ok
	Button button2;//취소
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
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.activity_popup);
	        
	        mWebView = (WebView) findViewById(R.id.webview);
			btn_back = (ImageView)findViewById(R.id.btn_back);
			btn_back.setOnClickListener(this);
			mWebView.getSettings().setJavaScriptEnabled(true);
			mWebView.getSettings().setUseWideViewPort(true);//must be true
			mWebView.getSettings().setRenderPriority(RenderPriority.HIGH);
			mWebView.getSettings().setBuiltInZoomControls(true);
			mWebView.getSettings().setSupportZoom(true);
			mWebView.setInitialScale(1);
			mWebView.getSettings().setPluginState(PluginState.ON);
		    mWebView.setWebChromeClient(new WebChromeClient()
		    {
		         public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
		               mUploadMessage = uploadMsg;
		               Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		               i.addCategory(Intent.CATEGORY_OPENABLE);
		               i.setType("*/*");
		               PopUpActivity.this.startActivityForResult(
		                       Intent.createChooser(i, "사진을 선택하세요"),
		                       FILECHOOSER_RESULTCODE);
		           }
		     });
		    
		    mWebView.loadUrl("http://web.pbc.co.kr/radio");
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
				finish();
			}
			
		}
}
