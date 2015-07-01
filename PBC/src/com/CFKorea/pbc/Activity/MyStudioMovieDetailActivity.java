package com.CFKorea.pbc.Activity;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Adapter.MyStudioVideoAdapter;
import com.CFKorea.pbc.Adapter.MyStudioVoiceAdapter;
import com.CFKorea.pbc.Utility.AsyncSubmit;
import com.CFKorea.pbc.Utility.CustomDialog;
import com.CFKorea.pbc.Utility.MenuControll;
import com.CFKorea.pbc.ViewHolder.VideoViewHolder;

public class MyStudioMovieDetailActivity extends Activity implements OnClickListener{
	int index;
	ImageView movieimage;
	ImageView movieimage2;
	EditText title;
	EditText story;
	ImageButton submit;
	ImageButton cancel;
	String title1;
	String story1;
	private CustomDialog mCustomDialog;
	
	private final static String TAG = "[MyStudioMovieDetailActivity]";
	public static ArrayList<VideoViewHolder> mMusicVideoViewHolder;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetail);
        
        Intent intent = new Intent(getIntent());
		 index = intent.getIntExtra("index",1);
		 Log.d(TAG,"get intent index = " + index);
		 
		 movieimage = (ImageView)findViewById(R.id.movieimage);
		 movieimage2 = (ImageView)findViewById(R.id.movieimage2);
		 title = (EditText)findViewById(R.id.title);
		 story = (EditText)findViewById(R.id.story);
		 submit = (ImageButton) findViewById(R.id.submit);
	     cancel = (ImageButton) findViewById(R.id.cancel);
	     
	     
	     
	     submit.setOnClickListener(this);
	     cancel.setOnClickListener(this);
		 
		 movieimage.setOnClickListener(this);
		 movieimage2.setOnClickListener(this);
		 movieimage.setImageBitmap(MyStudioVideoAdapter.mMusicVideoViewHolder.get(index).getThumnail());
        
        
    }
	
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		
		if(v.getId()==R.id.story && actionId==EditorInfo.IME_ACTION_DONE)
		{
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.movieimage:
			InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(title.getWindowToken(),0);
			imm.hideSoftInputFromWindow(story.getWindowToken(),0);
			break;
			
		case R.id.movieimage2:
			Intent intent2 = new Intent(Intent.ACTION_VIEW);
	        Uri uri2 = Uri.parse("file://"  + MyStudioVideoAdapter.mMusicVideoViewHolder.get(index).getData());
	        intent2.setDataAndType(uri2, "video/*");
	        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        getApplicationContext().startActivity(intent2);
			break;
			
		case R.id.submit:
			
			title1 = title.getText().toString();
		    story1 = story.getText().toString();
		    mCustomDialog = new CustomDialog(MyStudioMovieDetailActivity.this,null,getResources().getText(R.string.send_message).toString(),leftClickListener,rightClickListener);
			mCustomDialog.show();
//			AlertDialog.Builder builder = new AlertDialog.Builder(MyStudioMovieDetailActivity.this);
//
//			builder.setTitle("전송 하시겠습니까?");
//			
//			builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
//			           public void onClick(DialogInterface dialog, int id) {
//			        	
//			           }
//			       });
//			builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
//			           public void onClick(DialogInterface dialog, int id) {
//			        	   
////			        	MenuControll.sending(MyStudioMovieDetailActivity.this);
////			   		    
////			   			Thread thread = new Thread(new Runnable() {
////			   		  		public void run() {
////			   		  			//HttpFileUpload("http://192.168.0.200:8888/Pyeonghwa/upload/upRecordVoice.jsp","",k);
////			   		  			//sendFileToServer(k,"http://192.168.0.200:8888/Pyeonghwa/upload/upRecordVoice.jsp");
////			   		  			//sendFileToServer(k,"http://115.68.182.113/Pyeonghwa/upload/upRecordMovie.jsp");
////			   		  			Log.d(TAG,"k = " + MyStudioVideoAdapter.mMusicVideoViewHolder.get(index).getData());
////			   		  			Log.d(TAG,"title1 = " + title1);
////			   		  			Log.d(TAG,"story1 = " + story1);
////			   		  			Log.d(TAG,"id = " + MainActivity.mainloginid);
////			   		  			UploadTest(MyStudioVideoAdapter.mMusicVideoViewHolder.get(index).getData(),title1,story1);
////			   		  			handler.sendEmptyMessage(0);
////			   		  			}
////			   				});
////			   			thread.start(); 
//			        	   Log.d(TAG,"path = " + MyStudioVideoAdapter.mMusicVideoViewHolder.get(index).getData());
//			        	   Log.d(TAG,"size = " + MyStudioVideoAdapter.mMusicVideoViewHolder.size());
//			        	   Log.d(TAG,"title1 = " + title1);
//			        	   Log.d(TAG,"story1 = " + story1);
//			        	new AsyncSubmit(MyStudioMovieDetailActivity.this,MyStudioVideoAdapter.mMusicVideoViewHolder.get(index).getData(),title1,story1).execute();
//			           }
//			       });
//			AlertDialog dialog = builder.create();
//			dialog.show();
			
			break;
			
		case R.id.cancel:
			finish();
			break;
		default:
			break;
		}
	}
	
	public Handler handler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	    	Log.d("handler","loagindDialog.dismiss()");
	    	if(MenuControll.loagindDialog.isShowing()){
	  			MenuControll.loagindDialog.dismiss();
    		}
	    	Toast.makeText(getApplicationContext(), getResources().getText(R.string.send_success).toString(), Toast.LENGTH_SHORT).show();
	    	finish();
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
        //String url = "http://192.168.0.200:8888/Pyeonghwa/upload/upRecordMovie.jsp";
        String url = "http://115.68.182.113/Pyeonghwa/upload/upRecordMovie.jsp?loginid="+MainActivity.mainloginid;
 
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
			new AsyncSubmit(MyStudioMovieDetailActivity.this,MyStudioVideoAdapter.mMusicVideoViewHolder.get(index).getData(),title1,story1).execute();
		}
	};

	private View.OnClickListener rightClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mCustomDialog.dismiss();
			
			
		}
	};

}
