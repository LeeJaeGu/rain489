package com.CFKorea.pbc.Activity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class FileUploadActivity extends Activity{
	
	String p;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		


        super.onCreate(savedInstanceState);

        //setContentView(R.layout.main);

        try {

            imageUpload(p);

        } catch (Exception e) {

            Log.e(e.getClass().getName(), e.getMessage());

        }

    }

    public static void imageUpload(String path)  {

        HttpURLConnection connection = null;

        DataOutputStream outputStream = null;

        DataInputStream inputStream = null;



        String pathToOurFile = path;

        String urlServer = "http://192.168.0.200:8888/Pyeonghwa/upload/upRecordVoice.jsp";

        String lineEnd = "\r\n";

        String twoHyphens = "--";

        String boundary =  "*****";



        int bytesRead, bytesAvailable, bufferSize;

        byte[] buffer;

        int maxBufferSize = 1*1024*1024;



        try

        {

        FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile) );



        URL url = new URL(urlServer);

        connection = (HttpURLConnection) url.openConnection();



        // Allow Inputs & Outputs

        connection.setDoInput(true);

        connection.setDoOutput(true);

        connection.setUseCaches(false);



        // Enable POST method

        connection.setRequestMethod("POST");



        connection.setRequestProperty("Connection", "Keep-Alive");

        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary");



        outputStream = new DataOutputStream( connection.getOutputStream() );

        outputStream.writeBytes(twoHyphens + boundary + lineEnd);

        outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + pathToOurFile );

        outputStream.writeBytes(lineEnd);



        bytesAvailable = fileInputStream.available();

        bufferSize = Math.min(bytesAvailable, maxBufferSize);

        buffer = new byte[bufferSize];



        // Read file

        bytesRead = fileInputStream.read(buffer, 0, bufferSize);



        while (bytesRead > 0)

        {

        	outputStream.write(buffer, 0, bufferSize);

        	bytesAvailable = fileInputStream.available();

        	bufferSize = Math.min(bytesAvailable, maxBufferSize);

        	bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        }



        outputStream.writeBytes(lineEnd);

        outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);



        // Responses from the server (code and message)

        int serverResponseCode = connection.getResponseCode();

         String serverResponseMessage = connection.getResponseMessage();

         Log.e("�꽌踰� �쓳�떟 肄붾뱶",""+serverResponseCode);

         Log.e("�꽌踰� �쓳�떟 硫붿떆吏�",""+serverResponseMessage);

        fileInputStream.close();

        outputStream.flush();

        outputStream.close();

        }

        catch (Exception ex)

        {

            ex.printStackTrace();

        }

    }

}