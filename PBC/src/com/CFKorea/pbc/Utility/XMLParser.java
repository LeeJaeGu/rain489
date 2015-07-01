package com.CFKorea.pbc.Utility;

import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class XMLParser extends DefaultHandler {
	
	/** Log Tag */
	private final String TAG = "XMLParser";
	private String elementValue = null;
	private String title = null;
	private Boolean elementOn = false;
	private static ArrayList<XMLValuesModel> dataList ;
	private static XMLValuesModel data = null;
	ArrayList<HashMap<String, String>> mMp3_attributes = new ArrayList<HashMap<String, String>>(); 
	private ArrayList<String> mMp3 = new ArrayList<String>();
	
	ArrayList<HashMap<String, String>> mImg_attributes = new ArrayList<HashMap<String, String>>();
	private ArrayList<String> mImg = new ArrayList<String>();
	
	ArrayList<HashMap<String, String>> mIntroImg_attributes = new ArrayList<HashMap<String, String>>();
	private ArrayList<String> mIntroImg = new ArrayList<String>();
	
	ArrayList<HashMap<String, String>> mSerialImg_attributes = new ArrayList<HashMap<String, String>>();
	private ArrayList<String> mSerialImg = new ArrayList<String>();
	
	ArrayList<HashMap<String, String>> mVideo_attributes = new ArrayList<HashMap<String, String>>();
	private ArrayList<String> mVideo = new ArrayList<String>();
	
	//2015.1.21 JYP youtube List add [[
	ArrayList<HashMap<String, String>> mYouTube_attributes = new ArrayList<HashMap<String, String>>();
	private ArrayList<String> mYouTube = new ArrayList<String>();
	//]]
	
	private static ArrayList<String> mMusicTitle =  new ArrayList<String>();
	private static ArrayList<String> mImageTitle =  new ArrayList<String>();
	private static ArrayList<String> mIntroImageTitle =  new ArrayList<String>();
	private static ArrayList<String> mVideoTitle =  new ArrayList<String>();
	//List<XmlValuesModel> list = null;
	private static String mAlbumInfo = "";
	private static String mError = "";
	private static String mTitle = "";
	private static String mAdmin = "";
	public static String getmAdmin() {
		return mAdmin;
	}
	public XMLParser() {
		
	}
	public static ArrayList<XMLValuesModel> getData() {
        return dataList;
    }
	public static ArrayList<String> getMusicTitle() {
        return mMusicTitle;
    }
	public static ArrayList<String> getImageTitle() {
        return mImageTitle;
    }
	public static ArrayList<String> getIntroImageTitle() {
        return mIntroImageTitle;
    }
	public static ArrayList<String> getVideoTitle() {
        return mVideoTitle;
    }
	public static String getAlbumInfo() {
        return mAlbumInfo;
    }
	public static String getError() {
		
        return mError;
    }
	
	public static String getTitle() {
        return mTitle;
    }
	
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        elementOn = true; 
        //Log.d("startElement" , "localName == " + localName + " //////elementValue = " + elementValue);
        
        if (localName.equalsIgnoreCase("root")) {
        	 data = new XMLValuesModel();
        	 dataList = new ArrayList<XMLValuesModel>();
        }
    
        else if (localName.equalsIgnoreCase("mp3"))
        {
        	HashMap<String, String> song = new HashMap<String, String>();
        	
        	song.put("id", attributes.getValue(0));	
        	song.put("size", attributes.getValue(1));
        	song.put("downflag", attributes.getValue(2));
        	
        	mMp3_attributes.add(song);
        	
        	Log.d(TAG , "mMp3.add(elementValue) ==================" + song);
        }
        else if (localName.equalsIgnoreCase("image"))
        {
        	HashMap<String, String> img = new HashMap<String, String>();
        	
        	img.put("id", attributes.getValue(0));	
        	img.put("size", attributes.getValue(1));
        	img.put("downflag", attributes.getValue(2));
        	
        	mImg_attributes.add(img);
        	
        	Log.d(TAG , "img.add(elementValue) ==================" + img);
        }
        else if (localName.equalsIgnoreCase("introimage"))
        {
        	HashMap<String, String> introimg = new HashMap<String, String>();
        	
        	introimg.put("id", attributes.getValue(0));	
        	introimg.put("size", attributes.getValue(1));
        	introimg.put("downflag", attributes.getValue(2));
        	
        	mIntroImg_attributes.add(introimg);
        	
        	Log.d(TAG , "infoimg.add(elementValue) ==================" + introimg);
        }
        else if (localName.equalsIgnoreCase("serialimage"))
        {
        	HashMap<String, String> serialimg = new HashMap<String, String>();
        	
        	serialimg.put("id", attributes.getValue(0));	
        	serialimg.put("size", attributes.getValue(1));
        	serialimg.put("downflag", attributes.getValue(2));
        	
        	mSerialImg_attributes.add(serialimg);
        	
        	Log.d(TAG , "infoimg.add(elementValue) ==================" + serialimg);
        }
        else if (localName.equalsIgnoreCase("video"))
        {
        	HashMap<String, String> movie = new HashMap<String, String>();
        	
        	movie.put("id", attributes.getValue(0));	
        	movie.put("size", attributes.getValue(1));
        	movie.put("downflag", attributes.getValue(2));
        	
        	mVideo_attributes.add(movie);
        	
        	Log.d(TAG , "movie.add(elementValue) ==================" + movie);
        }
        //2015.1.21 JYP youtube List add [[
        else if (localName.equalsIgnoreCase("youtube"))
        {
        	HashMap<String, String> youtube = new HashMap<String, String>();
        	
        	youtube.put("id", attributes.getValue(0));	
        	youtube.put("ThumbnailUrl", attributes.getValue(1));
        	
        	mYouTube_attributes.add(youtube);
        	
        	Log.d(TAG , "movie.add(elementValue) ==================" + mYouTube_attributes);
        }
        //]]
        
        
    }
    /**
     * This will be called when the tags of the XML end.
     **/
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
    	
        elementOn = false;

        if(localName.equalsIgnoreCase("modelid"))
        {
            data.setmModelid(elementValue);
        } 
        else if(localName.equalsIgnoreCase("licenseid"))
        {
            data.setmLicenseid(elementValue);
        } 
        else if(localName.equalsIgnoreCase("app_number"))
        {
            data.setmApp_number(elementValue);
        }
        else if(localName.equalsIgnoreCase("album_info_name"))
        {
            data.setmAlbum_info_name(elementValue);
            mAlbumInfo = elementValue;
        }
        else if(localName.equalsIgnoreCase("album_mp3_count"))
        {
            data.setmAlbum_mp3_count(elementValue);
        }
        else if(localName.equalsIgnoreCase("album_image_count"))
        {
            data.setmAlbum_image_count(elementValue);
        }
        else if(localName.equalsIgnoreCase("album_video_count"))
        {
            data.setmAlbum_video_count(elementValue);
        }
        //*추가*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        else if (localName.equalsIgnoreCase("result")) 
        {
            data.setmArtist_name(elementValue);
        }
        else if (localName.equalsIgnoreCase("artist_name")) 
        {
        	data.setmArtist_name(elementValue);
        }
        else if(localName.equalsIgnoreCase("mp3"))
        {
        	data.setmMp3_attributes(mMp3_attributes);
        	mMp3.add(elementValue);
        	data.setmMp3(mMp3);
        }
        else if(localName.equalsIgnoreCase("image"))
        {
        	data.setmImg_attributes(mImg_attributes);
        	mImg.add(elementValue);
        	data.setmImg(mImg);
        }
        else if(localName.equalsIgnoreCase("introimage"))
        {
        	data.setmIntroImg_attributes(mIntroImg_attributes);
        	mIntroImg.add(elementValue);
        	data.setmIntroImg(mIntroImg);
        }
        else if(localName.equalsIgnoreCase("serialimage"))
        {
        	data.setmIntroImg_attributes(mSerialImg_attributes);
        	mSerialImg.add(elementValue);
        	data.setmIntroImg(mSerialImg);
        }
        else if(localName.equalsIgnoreCase("video"))
        {
        	data.setmVideo_attributes(mVideo_attributes);
        	mVideo.add(elementValue);
        	data.setmVideo(mVideo);
        }
        //2015.1.21 JYP youtube List add [[
        else if(localName.equalsIgnoreCase("youtube"))
        {
        	data.setmYouTube_attributes(mYouTube_attributes);
        	mYouTube.add(elementValue);
        	data.setmYouTube(mYouTube);
        }
        //]]
        else if (localName.equalsIgnoreCase("TITLE"))
        {
        	data.setmTitle(title);
        	mTitle = title;
        	Log.d("DEBUG","XMLParser mTitle = " + mTitle);
        }
        else if (localName.equalsIgnoreCase("ERROR")) 
        {
        	data.setmError(elementValue);
        	mError = elementValue;
        	Log.d("DEBUG","XMLParser mError = " + mError);
        }
        else if (localName.equalsIgnoreCase("admin")) 
        {
        	data.setmAdmin(elementValue);
        	mAdmin = elementValue;
        	Log.d("DEBUG","XMLParser mAdmin = " + mAdmin);
        }
        else if(localName.equalsIgnoreCase("root")) 
        {
        	dataList.add(data);
        	mMusicTitle = data.getmMp3();
        	mImageTitle = data.getmImg();
        	mIntroImageTitle = data.getmIntroImg();
        	mVideoTitle = data.getmVideo();
        	
        	data = null;
        }
        /*
       Log.d("XMLParser", "data.getmModelid() = "+data.getmModelid());
       Log.d("XMLParser", "data.getmLicenseid() = "+data.getmLicenseid());
       Log.d("XMLParser", "data.getmApp_number() = "+data.getmApp_number());
       Log.d("XMLParser", "data.getmAlbum_info_name() = "+data.getmAlbum_info_name());
       Log.d("XMLParser", "data.getmAlbum_mp3_count() = "+data.getmAlbum_mp3_count());
       Log.d("XMLParser", "data.getmMp3() = "+data.getmMp3());
       */
       
       
       
    }
  
    /**
     * This is called to get the tags value
     **/
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if (elementOn) {
            elementValue = new String(ch, start, length);
            
            elementOn = false;
        }
  
    }
}
