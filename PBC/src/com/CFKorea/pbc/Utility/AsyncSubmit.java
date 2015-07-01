package com.CFKorea.pbc.Utility;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Activity.MainActivity;
import com.CFKorea.pbc.Activity.MyStudioMovieDetailActivity;

public class AsyncSubmit extends AsyncTask<Integer, String, String>{
	
	private final static String TAG = "[AsyncSubmit]";
	private Context mContext;
	private ProgressDialog m_Dlg;
	static String one;
	static String two;
	static String three;
	HttpURLConnection conn;
	FileInputStream in;
	DataOutputStream out;
	String exit="N";
	boolean sub = true;
	public static boolean Success = false;
	private ProgressDialog loagindDialog;
	
	public AsyncSubmit(Context context,String k,String title, String story) {
		Log.d(TAG,"AsyncSubmit Start");
		mContext = context;
		one=k;
        two=title;
        three=story;
	}
	
	@Override
	protected void onProgressUpdate(String... progress) {
		//Log.d(TAG,"AsyncSubmit onProgressUpdate");
		if (progress[0].equals("progress")) {
			m_Dlg.setProgress(Integer.parseInt(progress[1]));
			m_Dlg.setMessage(progress[2]);
		}
			else if (progress[0].equals("max")) {
			//Log.d(TAG, "max = "+ Integer.parseInt(progress[1]));
			m_Dlg.setMax(Integer.parseInt(progress[1]));
		}
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Log.d(TAG,"AsyncSubmit onPreExecute");
		m_Dlg = new ProgressDialog(mContext);
		m_Dlg.setButton(mContext.getResources().getText(R.string.cancel).toString(), new OnClickListener() {
			 
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				m_Dlg.dismiss();
				exit="Y";
				createThreadAndDialog();
			}

		});
		m_Dlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		m_Dlg.setMessage("전송 준비중...");
		m_Dlg.setCancelable(false);
		m_Dlg.setCanceledOnTouchOutside(false);
		m_Dlg.show();
	}
	private void createThreadAndDialog() {
		  // TODO Auto-generated method stub
		loagindDialog = ProgressDialog.show(mContext,"",mContext.getResources().getText(R.string.Canceling).toString(), true);
		  
			  	Thread thread = new Thread(new Runnable() {
			  		public void run() {
			  			try{
							cancel(true);
							out.close();
					        in.close();
					        out = null;
					        in = null;
					        conn.disconnect();
						}
						catch(Exception e){
							e.printStackTrace();
						}
				   		handler.sendEmptyMessage(0);
			  		}	
			  	});
		  	thread.start();
	}
	public Handler handler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	    	Log.d(TAG,"loagindDialog.dismiss()");
	    		if(loagindDialog.isShowing()){
	    			loagindDialog.dismiss();
	    			Toast.makeText(mContext,mContext.getResources().getText(R.string.send_cancel).toString(), Toast.LENGTH_SHORT).show();
	    		}
	    	 
	    }
	};
	
	@Override
	protected String doInBackground(Integer... params) {
		// TODO Auto-generated method stub
		try {
			Log.d(TAG,"AsyncSubmit doInBackground");
			String filePath=one;
			String title=two;
			String story=three;
			File maxsize = new File(filePath);
			String tt = maxsize.getName();
			
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
     
            Log.d(TAG,"success1");
            // 커넥션 생성 및 설정
            conn = (HttpURLConnection) new URL(url)
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
            Log.d(TAG,"success2");
            // 전송 작업 시작
            in = new FileInputStream(filePath);
            out = new DataOutputStream(new BufferedOutputStream(
                    conn.getOutputStream()));
            Log.d(TAG,"success3");
            // 위에서 작성한 메타데이터를 먼저 전송한다. (한글이 포함되어 있으므로 UTF-8 메소드 사용)
            out.writeUTF(postDataBuilder.toString());
            Log.d(TAG,"success4");
            // 파일 복사 작업 시작
            int maxBufferSize = 1024;
            Log.d(TAG,"maxBufferSize = " + maxBufferSize);
            int bufferSize = Math.min(in.available(), maxBufferSize);
            Log.d(TAG,"bufferSize = " + bufferSize);
            byte[] buffer = new byte[bufferSize];
     
            // 버퍼 크기만큼 파일로부터 바이트 데이터를 읽는다.
            int byteRead = in.read(buffer, 0, bufferSize);
            Log.d(TAG,"byteRead = " + byteRead);
            Log.d(TAG,"success4");
            
            publishProgress("max",String.valueOf(maxsize.length()) );
            
            
            Log.d(TAG,"success5");
            int bufferLength = 0; //used to store a temporary size of the buffer
            int downloadedSize = 0;
            int i =Integer.parseInt(String.valueOf(maxsize.length()));
            Log.d(TAG," i  = " + i);
            Log.d(TAG,"downloadsize = " + downloadedSize);
            
            try
	        {
	            while (byteRead > 0)
	            {
	            	
	                try
	                {
	                	//Log.d("TAG", "=========================================================s");
	                    out.write(buffer, 0, bufferSize);
	                    //bufferLength = in.read(buffer);
	                    bufferLength = byteRead;
	                    //Log.d(TAG,"byteRead = " + byteRead);
	                    downloadedSize += bufferLength;
	                    //Log.d(TAG,"downloadedSize = " + downloadedSize);
	                    publishProgress("progress", downloadedSize + "",tt+" sending...");
	                    //Log.d("TAG", "=========================================================e");
	                }
	                catch (OutOfMemoryError e)
	                {
	                    e.printStackTrace();
	                    Log.d("TTT", "Exception 1");
	                }
	                
	                bufferSize = Math.min(in.available(), maxBufferSize);
	                //Log.d(TAG,"bufferSize = " + bufferSize);
	                byteRead = in.read(buffer, 0, bufferSize);
	                //Log.d(TAG,"byteRead = " + byteRead);
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
            
//            while ( (bufferLength = in.read(buffer)) > 0) {
//            	Log.d("TAG", "=========================================================s");
//            	Log.d(TAG,"in.read(buffer) = " + in.read(buffer));
//            	 //out.write(buffer, 0, bufferLength); // < -- 엄청 오래 걸린다
//            	 Log.d(TAG,"success6");
//            	 
//            	 //File file = null;
//            	 
//                    downloadedSize += bufferLength;
//                    
//                    Log.d(TAG,"bufferLength = " + bufferLength);
//                    Log.d(TAG,"downloadedSize = " + downloadedSize);
//                    Log.d(TAG,"max = " + String.valueOf(maxsize.length()));
//                    publishProgress("progress", downloadedSize + "",tt+" 전송중");
//                    Log.d(TAG,"update");
//                    Log.d("TAG", "=========================================================e");
//            }
            Log.d(TAG,"success7");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"e = " + e);
            sub = false;
            
        }
		
		
		return null;
		
		
	}
	
	
	@Override
	protected void onPostExecute(String result) {
		m_Dlg.dismiss();
		Log.d(TAG,"AsyncSubmit onPostExecute");
		if(exit.equals("Y")){
			Toast.makeText(mContext,"Error", Toast.LENGTH_SHORT).show();
			exit="N";
		}
		else if(sub==false){
			Toast.makeText(mContext,mContext.getResources().getText(R.string.Wifi_Error).toString(), Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(mContext,mContext.getResources().getText(R.string.send_success).toString(), Toast.LENGTH_SHORT).show();
			((MyStudioMovieDetailActivity)mContext).finish();
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

    
}
