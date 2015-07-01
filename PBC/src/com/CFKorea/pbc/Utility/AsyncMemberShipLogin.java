package com.CFKorea.pbc.Utility;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Activity.MainActivity;

public class AsyncMemberShipLogin extends AsyncTask<Void, Void, Void> {

	private String m_PhoneDeviceid;
	private String m_loginid;
	private String m_loginpass;
	boolean m_auto;
	
	private Context m_Context;
	private ArrayList<XMLValuesModel> m_XmlValues;
	private static XMLValuesModel data = null; 
	private final String TAG = "AsyncPost_DEBUG";
	
	public AsyncMemberShipLogin(Context context) {
		m_Context = context;
	}
	
	public AsyncMemberShipLogin setCallable( String PhoneDeviceid, String loginid, String Ioginpass,boolean auto) {
		this.m_PhoneDeviceid = PhoneDeviceid;
		this.m_loginid = loginid;
		this.m_loginpass = Ioginpass;
		this.m_auto= auto;
		return this;
	}
	
	public void downloadIntent() {
		Log.d("DEBUG",  "downloadIntent");
		
		Log.d("DEBUG", "AsyncPostm_loginid=" + m_loginid + "AsyncPostm_loginpass=" + m_loginpass);
		
		Intent intent = new Intent();
		intent.setClass(m_Context, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("mainloginid", m_loginid);
		intent.putExtra("mainloginpass", m_loginpass);
		intent.putExtra("loginYN", "Y");
		intent.putExtra("message", "Y");
		intent.putExtra("admin", false);
		intent.putExtra("general", true);
		//MenuControll.Toastshow(m_Context);
		m_Context.startActivity(intent);
		Log.d(TAG,m_loginid + "님 로그인");
		
	}
	
	public void AdminIntent() {
		Intent intent = new Intent();
		intent.setClass(m_Context, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("mainloginid", m_loginid);
		intent.putExtra("mainloginpass", m_loginpass);
		intent.putExtra("loginYN", "Y");
		intent.putExtra("message", "Y");
		intent.putExtra("admin", true);
		intent.putExtra("general", false);
		m_Context.startActivity(intent);
		
	}

//	private AsyncCallback<File> fileDownCallback = new AsyncCallback.Base<File>() {
//		@Override
//		public void onResult(File result) {
//			
//		}
//
//		@Override
//		public void exceptionOccured(Exception e) {
//			
//		}
//	};
	
	@Override
	protected Void doInBackground(Void... p) {
		// TODO Auto-generated method stub
		m_XmlValues = new ArrayList<XMLValuesModel>();
         try
         {
        	 HttpParams param = new BasicHttpParams();
        	 HttpConnectionParams.setConnectionTimeout(param, 5000);

        	 DefaultHttpClient client = new DefaultHttpClient(param); 
        	 
        	 HttpPost post =  new HttpPost(Define.LOGINURL);
        	 
        	 
        	 Log.d("DEBUG", "post = " + post);
        	 
        	       	 
			 List<NameValuePair> params = new ArrayList<NameValuePair>();
			 params.add(new BasicNameValuePair("model_key", m_PhoneDeviceid));
			 params.add(new BasicNameValuePair("loginid", m_loginid));
			 params.add(new BasicNameValuePair("password", m_loginpass));

   		  	 UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
	   		 post.setEntity(ent);
	   		 
   		  	 Log.d("DEBUG", "ent = " + ent);
   		  	 HttpResponse responsePOST = client.execute(post);  
   		  	 Log.d("DEBUG", "responsePOST = " + responsePOST);
   		  	 
   		  	 HttpEntity resEntity = responsePOST.getEntity();
   		  	 
   		  	 Log.d("DEBUG", "resEntity = " + resEntity);
   		  	 
   		  	 BufferedReader in = new BufferedReader(new InputStreamReader(resEntity.getContent(), HTTP.UTF_8));
   		 
	   		 String line = "";
	   		 StringBuffer sb= new StringBuffer("");
	   		 String NL = System.getProperty("line.separator");
	   		 while ((line = in.readLine()) != null) {
	   			 sb.append(line);
					
	   		 }
	   		  
	   		 in.close();
   		 
	   		 SAXParserFactory saxPF = SAXParserFactory.newInstance(); 
	         SAXParser saxP = saxPF.newSAXParser();
	         XMLReader xmlR = saxP.getXMLReader();
	   		  
	         XMLParser myXMLParser = new XMLParser();
	         xmlR.setContentHandler(myXMLParser);
	           
	         InputStream istream = new ByteArrayInputStream(sb.toString().getBytes("utf-8"));
	         Log.d("DEBUG", sb.toString() + "");
	         xmlR.parse(new InputSource(istream)); 
	          
	           
	         m_XmlValues = myXMLParser.getData();
	         Log.d(TAG, "m_XmlValues.get(0).getmError() = " + m_XmlValues.get(0).getmError());
	         Log.d(TAG, "m_Context = " + m_Context + "");
      }
      catch (Exception e)
      {
        Log.d("DEBUG_Security", "Exception = " +  e);
        e.printStackTrace();
      }
         return null;
	}

	
	@Override
	protected void onPostExecute(Void result) {
		 super.onPostExecute(result);
		 try{
			 if ( m_XmlValues.get(0).getmError() != "") 
	         {
				Log.d(TAG,"==================================ERROR============================================="); 
	        	 Toast.makeText(m_Context, m_XmlValues.get(0).getmError(), Toast.LENGTH_SHORT).show();
	        	 data = new XMLValuesModel();
	        	 data.setmError("");
				 
	         }
			 else if (m_XmlValues.get(0).getmAdmin() != ""){
				 Log.d(TAG,"================================ADMIN LOGIN=========================================");
				 if(m_auto == true)
				 {
					 MainActivity.autologin(m_Context);
				 }
				 AdminIntent();
			 }
			 
			 else
			 {
				 Log.d(TAG,"=========================================GENERAL LOGIN==================================");
				 if(m_auto == true)
				 {
					 MainActivity.autologin(m_Context);
				 }
				 downloadIntent();
			 }
		 }
		 catch(Exception e){
		Toast.makeText(m_Context, m_Context.getResources().getText(R.string.Wifi_Error).toString(), Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();
		intent.setClass(m_Context, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		m_Context.startActivity(intent);
			}
	}

}
