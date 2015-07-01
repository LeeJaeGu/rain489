package com.CFKorea.pbc.Utility;

import java.util.ArrayList;
import java.util.HashMap;

public class XMLValuesModel {
	
	private static int mPeriod;
	private String mModelid = "";
	private String mLicenseid = "";
	private String mApp_number = "";
	private String mAlbum_info_name = "";
	private String mAlbum_mp3_count = "";
	private String mAlbum_image_count = "";
	private String mAlbum_video_count = "";
	private String mArtist_name = "";
	
	


	private String mError = "";
	private String mAdmin = "";
	
	public String getmAdmin() {
		return mAdmin;
	}

	public void setmAdmin(String mAdmin) {
		this.mAdmin = mAdmin;
	}

	public String getmTitle() {
		return mTitle;
	}

	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}


	private String mTitle = "";

	
	private ArrayList<HashMap<String, String>> mMp3_attributes = new ArrayList<HashMap<String, String>>();
	private ArrayList<String> mMp3 = new ArrayList<String>();
	     
	//Halo 2nd add [[
	private ArrayList<HashMap<String, String>> mImg_attributes = new ArrayList<HashMap<String, String>>();
	private ArrayList<String> mImg = new ArrayList<String>();
	
	private ArrayList<HashMap<String, String>> mIntroImg_attributes = new ArrayList<HashMap<String, String>>();
	private ArrayList<String> mIntroImg = new ArrayList<String>();
	
	public ArrayList<HashMap<String, String>> getmIntroImg_attributes() {
		return mIntroImg_attributes;
	}

	public void setmIntroImg_attributes(
			ArrayList<HashMap<String, String>> mIntroImg_attributes) {
		this.mIntroImg_attributes = mIntroImg_attributes;
	}

	public ArrayList<String> getmIntroImg() {
		return mIntroImg;
	}

	public void setmIntroImg(ArrayList<String> mIntroImg) {
		this.mIntroImg = mIntroImg;
	}


	private ArrayList<HashMap<String, String>> mVideo_attributes = new ArrayList<HashMap<String, String>>();
	private ArrayList<String> mVideo = new ArrayList<String>();
	//]]
	//2015.1.21 JYP youtube List add [[
	private ArrayList<HashMap<String, String>> mYouTube_attributes = new ArrayList<HashMap<String, String>>();
	private ArrayList<String> mYouTube = new ArrayList<String>();
	//]]
	public void setPeriod(int period)
	{
	    if(period<1980){
	        this.mPeriod = 7;
	    }else if(period<1990){
	        this.mPeriod = 8;
	    }else if(period<2000){
	        this.mPeriod = 9;
	    }
	}

	//2015.1.21 JYP youtube List add [[
	public ArrayList<HashMap<String, String>> getmYouTube_attributes() {
		return mYouTube_attributes;
	}


	public void setmYouTube_attributes(
			ArrayList<HashMap<String, String>> mYouTube_attributes) {
		this.mYouTube_attributes = mYouTube_attributes;
	}


	public ArrayList<String> getmYouTube() {
		return mYouTube;
	}


	public void setmYouTube(ArrayList<String> mYouTube) {
		this.mYouTube = mYouTube;
	}
	//]]

	public String getmArtist_name() {
		return mArtist_name;
	}


	public void setmArtist_name(String mArtist_name) {
		this.mArtist_name = mArtist_name;
	}


	public ArrayList<String> getmMp3() {
		return mMp3;
	}


	public void setmMp3(ArrayList<String> mMp3) {
		this.mMp3 = mMp3;
	}


	public static int getmPeriod() {
		return mPeriod;
	}


	public static void setmPeriod(int mPeriod) {
		XMLValuesModel.mPeriod = mPeriod;
	}


	public String getmModelid() {
		return mModelid;
	}


	public void setmModelid(String mModelid) {
		this.mModelid = mModelid;
	}


	public String getmLicenseid() {
		return mLicenseid;
	}


	public void setmLicenseid(String mLicenseid) {
		this.mLicenseid = mLicenseid;
	}


	public String getmApp_number() {
		return mApp_number;
	}


	public void setmApp_number(String mApp_number) {
		this.mApp_number = mApp_number;
	}


	public String getmAlbum_info_name() {
		return mAlbum_info_name;
	}


	public void setmAlbum_info_name(String mAlbum_info_name) {
		this.mAlbum_info_name = mAlbum_info_name;
	}


	public String getmAlbum_mp3_count() {
		return mAlbum_mp3_count;
	}


	public void setmAlbum_mp3_count(String mAlbum_mp3_count) {
		this.mAlbum_mp3_count = mAlbum_mp3_count;
	}


	public ArrayList<HashMap<String, String>> getmMp3_attributes() {
		return mMp3_attributes;
	}


	public void setmMp3_attributes(ArrayList<HashMap<String, String>> mMp3_attributes) {
		this.mMp3_attributes = mMp3_attributes;
	}


	public String getmError() {
		return mError;
	}


	public void setmError(String mError) {
		this.mError = mError;
	}
	
	//Halo 2nd add [[
	
	
	public ArrayList<HashMap<String, String>> getmImg_attributes() {
		return mImg_attributes;
	}


	public String getmAlbum_image_count() {
		return mAlbum_image_count;
	}


	public void setmAlbum_image_count(String mAlbum_image_count) {
		this.mAlbum_image_count = mAlbum_image_count;
	}


	public String getmAlbum_video_count() {
		return mAlbum_video_count;
	}


	public void setmAlbum_video_count(String mAlbum_video_count) {
		this.mAlbum_video_count = mAlbum_video_count;
	}


	public void setmImg_attributes(
			ArrayList<HashMap<String, String>> mImg_attributes) {
		this.mImg_attributes = mImg_attributes;
	}


	public ArrayList<String> getmImg() {
		return mImg;
	}


	public void setmImg(ArrayList<String> mImg) {
		this.mImg = mImg;
	}


	public ArrayList<HashMap<String, String>> getmVideo_attributes() {
		return mVideo_attributes;
	}


	public void setmVideo_attributes(
			ArrayList<HashMap<String, String>> mVideo_attributes) {
		this.mVideo_attributes = mVideo_attributes;
	}


	public ArrayList<String> getmVideo() {
		return mVideo;
	}


	public void setmVideo(ArrayList<String> mVideo) {
		this.mVideo = mVideo;
	}
}
