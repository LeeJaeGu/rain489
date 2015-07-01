package com.CFKorea.pbc.Utility;

import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

public class Define {

	//Main List positon
	public static final int AR = 0;
	public static final int MUSIC_PLAYER = 1;
	public static final int PHOTO_GALLERY = 2;
	public static final int VIDEO_RECODING = 3;
	public static final int MYSTUDIO = 4;
	public static final int PROFILE = 5;
	
	///mnt/sdcard
	public static final String SDCARD = Environment.getExternalStorageDirectory().toString();
	public static final String COMPANY = "/CFKorea";
	public static final String MUSE = "/Muse";
	
	//public static final String SINGER = "/HALO";
	public static final String SINGER = "/HALO_2nd";
	public static final String PHOTO = "/MyStudio/Image";
	public static final String VIDEO = "/MyStudio/Video";
	public static final String VOICE = "/MyStudio/Voice";
	public static final String ALBUMIMAGE = "/Albumimage";
	
	
	//Album Info
	public static final String ALBUM_INFO = "HALO_2nd";
	
	public static final String MUSIC = "/Music";
	//SecurityActivity
	public static final String MUSICCARD_PATH =  SDCARD + COMPANY + SINGER;
	
	//mnt/sdcard/CFKorea/Halo_2nd/Music
	public static final String MUSICCARD_PATH_MUSIC =  SDCARD + COMPANY + SINGER + "/Music";
	public static final String MUSE_PATH_MUSIC =  SDCARD + COMPANY + MUSE;
	public static final String MUSICCARD_PATH_IMAGE =  SDCARD + COMPANY + SINGER + "/Image";
	public static final String MUSICCARD_PATH_VIDEO =  SDCARD + COMPANY + SINGER + "/Video";
	public static final String MAIN_ALBUM_IMAGE =  SDCARD + COMPANY + MUSE + ALBUMIMAGE;
	//2015.1.21 JYP YOUTUBE path add[[
	public static final String MUSICCARD_PATH_YOUTUBE =  SDCARD + COMPANY + SINGER + "/Youtube";
	//]]
	//���μ���
	//public static final String POSTURL = "http://192.168.0.200:8080/MusicCard/MCServer/mclicense.jsp";
	//public static final String DOWNLOADURL = "http://192.168.0.200:8080/MusicCard/MCServer/mcmusicdown.jsp?"; //fileid=
	
	//public static final String POSTURL = "http://192.168.0.13:8888/MusicCard/MCServer/mclicense.jsp?";

	//public static final String DOWNLOADCOMPLETE = "http://192.168.0.200:8080/MusicCard/MCServer/mclicense.jsp?"; ////cmd=2&modelid=&licenseid=&fileid="
	//2014.12.9 jae gu login path add [[
	
	//���μ��� TEST
//	public static final String JOINURL = "http://115.68.182.113/TEST/userInfo/joinAction.jsp?";
//	public static final String LOGINURL = "http://115.68.182.113/TEST/userInfo/loginAction.jsp?";
//	public static final String POSTURL = "http://115.68.182.113/TEST/MCServer/mclicense.jsp";
//	public static final String DOWNLOADURL = "http://115.68.182.113/TEST/MCServer/mcmusicdown.jsp?"; //fileid=
//	public static final String DOWNLOADURL_IMAGE = "http://115.68.182.113/TEST/MCServer/mcimagedown.jsp?"; //fileid=
//	public static final String DOWNLOADURL_MOVIE = "http://115.68.182.113/TEST/MCServer/mcvideodown.jsp?"; //fileid=
	//2015.1.26. JYP youtube PostURL
	public static final String YOUTUBE_POSTURL = "http://115.68.182.113/MusicCard/MCServer/mcarcontentsdown.jsp";
		
	//���μ���
	public static final String POSTURL = "http://115.68.182.113/MusicCard/MCServer/mclicense.jsp";
	public static final String DOWNLOADURL = "http://115.68.182.113/MusicCard/MCServer/mcmusicdown.jsp?"; //fileid=
	public static final String DOWNLOADURL_IMAGE = "http://115.68.182.113/MusicCard/MCServer/mcimagedown.jsp?"; //fileid=
	public static final String DOWNLOADURL_MOVIE = "http://115.68.182.113/MusicCard/MCServer/mcvideodown.jsp?"; //fileid=
	public static final String JOINURL = "http://115.68.182.113/Pyeonghwa/userInfo/joinAction.jsp?";
	public static final String LOGINURL = "http://115.68.182.113/Pyeonghwa/userInfo/loginAction.jsp?";
		
	 	
	//RecoderActivity
	public static final String MY_STUDIO_PHOTO = SDCARD + COMPANY + PHOTO;
	public static final String MY_STUDIO_VIDEO = SDCARD + COMPANY + "/PyungHwa/Video";
	public static final String MY_STUDIO_VOICE = SDCARD + COMPANY + "/PyungHwa/Voice";
	
	public static final String CREATE_USER_INFO = "1";
	public static final String SUCCESS_USER_INFO = "2";
	
	//SongManager
	public static final Uri MEDIA_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	public static final String SELECTION = MediaStore.Images.Media.DATA + " like ? ";
	
	public static final String[] SELECTION_ARGS = new String[] {"%" + Define.MUSICCARD_PATH_MUSIC +"%"};
	
	public static final String ORDER_BY = MediaStore.Images.Media.TITLE + " DESC";
	
	//MusicVideoManager
	public static final Uri MEDIA_URI_MUSIC_VIDEO = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	public static final Uri MEDIA_URI_MUSIC_VOICE = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	public static final String[] SELECTION_ARGS_MUSIC_VIDEO = new String[] {"%" + Define.MUSICCARD_PATH_VIDEO +"%"};
	
	//PhotoThumNail
	public static final Uri MEDIA_URI_PHOTO = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	
	
	//Youtube selector
	public static final String APP_NUMBER = "2";
	
	
}