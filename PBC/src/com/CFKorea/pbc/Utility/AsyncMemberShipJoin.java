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
import android.widget.Toast;

import com.CFKorea.pbc.R;
import com.CFKorea.pbc.Activity.MemberShipLoginActivity;

public class AsyncMemberShipJoin extends AsyncTask<Void, Void, Void> {

	private String loginid;
	private String password;
	private String username;
	private String phonenumber;
	private String model;
	
	private Context m_Context;
	private ArrayList<XMLValuesModel> m_XmlValues;
	private static XMLValuesModel data = null; 
	private final String TAG = "AsyncPost_DEBUG";
	
	public AsyncMemberShipJoin(Context context) {
		m_Context = context;
	}
	
	public AsyncMemberShipJoin setCallable( String jid, String jpass, String jname, String jtel, String model) {
		//this.callable = callable;
		
		Log.d("DEBUG",  "jid = " + jid + "jpass = " + jpass + "jname = " + jname + "jtel = " + jtel);
		
		this.loginid = jid;
		this.password = jpass;
		this.username = jname;
		this.phonenumber = jtel;
		this.model = model;
		return this;
	}
	
	public void downloadIntent() {
		Log.d("DEBUG",  "downloadIntent");
		
		Intent intent = new Intent();
		intent.setClass(m_Context, MemberShipLoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Toast.makeText(m_Context, m_Context.getResources().getString(R.string.join_success), Toast.LENGTH_SHORT).show();
		m_Context.startActivity(intent);
		
	}
	
	@Override
	protected Void doInBackground(Void... p) {
		// TODO Auto-generated method stub
		m_XmlValues = new ArrayList<XMLValuesModel>();
         try
         {
        	 HttpParams param = new BasicHttpParams();
        	 HttpConnectionParams.setConnectionTimeout(param, 5000);

        	 DefaultHttpClient client = new DefaultHttpClient(param); 
        	 
        	 HttpPost post =  new HttpPost(Define.JOINURL);
        	 
        	 
        	 Log.d("DEBUG", "post = " + post);
        	 
        	 Log.d("DEBUG",  "paramsjid = " + loginid + "paramsjpass = " + password + "paramsjname = " + username + "paramsjtel = " + phonenumber);
        	       	 
			 List<NameValuePair> params = new ArrayList<NameValuePair>();
			 params.add(new BasicNameValuePair("loginid", loginid));
			 params.add(new BasicNameValuePair("password", password));
			 params.add(new BasicNameValuePair("username", username));
			 params.add(new BasicNameValuePair("phonenumber", phonenumber));
			 params.add(new BasicNameValuePair("model_key", model));
			 Log.d("DEBUG","param.loginid = " + loginid );
			 
			 

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
					//sb.append(line + NL);
	   			 sb.append(line);
					
	   		 }
	   		  
	   		 in.close();
   		 
	   		 SAXParserFactory saxPF = SAXParserFactory.newInstance(); 
	         SAXParser saxP = saxPF.newSAXParser();
	         XMLReader xmlR = saxP.getXMLReader();
	   		  
	         XMLParser myXMLParser = new XMLParser();
	         xmlR.setContentHandler(myXMLParser);
	           
	         InputStream istream = new ByteArrayInputStream(sb.toString().getBytes("utf-8"));
	         //reader.parse(new InputSource(istream));
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
	// TODO Auto-generated method stub
		 super.onPostExecute(result);
		 if ( m_XmlValues.get(0).getmError() != "") 
         {
        	 Toast.makeText(m_Context, m_XmlValues.get(0).getmError(), Toast.LENGTH_SHORT).show();
        	 data = new XMLValuesModel();
        	 data.setmError("");
         }
		 else
		 {
			 downloadIntent();
		 }
	}

}
