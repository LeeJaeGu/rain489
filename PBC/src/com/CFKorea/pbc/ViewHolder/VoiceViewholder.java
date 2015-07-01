package com.CFKorea.pbc.ViewHolder;

import android.graphics.Bitmap;

public class VoiceViewholder {
	
	private String id;	
	private String data;	
	private String display_Name; 
	private String size;
	private String title;
	private String duration;
	private String date_added;
	private String resolution;
	private Bitmap thumnail;
	private boolean checkedState;
	
	public VoiceViewholder(String id , String data, String display_Name, String size, String title, String duration, String date_added){
		this.id = id;	
		this.data = data;	
		this.display_Name = display_Name; 
		this.size = size;
		this.title = title;
		this.duration = duration;
		this.date_added = date_added;
		this.thumnail = thumnail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDisplay_Name() {
		return display_Name;
	}

	public void setDisplay_Name(String display_Name) {
		this.display_Name = display_Name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDate_added() {
		return date_added;
	}

	public void setDate_added(String date_added) {
		this.date_added = date_added;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public Bitmap getThumnail() {
		return thumnail;
	}

	public void setThumnail(Bitmap thumnail) {
		this.thumnail = thumnail;
	}
	public boolean getCheckedState() {
		return checkedState;
	}
	public void setCheckedState(boolean checkedState) {
		this.checkedState = checkedState;
	}
}
