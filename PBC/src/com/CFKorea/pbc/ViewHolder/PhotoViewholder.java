package com.CFKorea.pbc.ViewHolder;

import android.graphics.Bitmap;

public class PhotoViewholder {
	
	private String id;	
	private String data;	
	private String display_Name; 
	private String size;
	private String title;
	private Bitmap thumnail;
	private boolean checkedState;
	public PhotoViewholder(String id, String data, String display_name, String size, String title, Bitmap thumnail) {
		this.id = id;
		this.data = data;
		this.display_Name = display_name;
		this.size = size;
		this.title = title;
		this.thumnail = thumnail;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public boolean getCheckedState() {
		return checkedState;
	}
	public void setCheckedState(boolean checkedState) {
		this.checkedState = checkedState;
	}
	public Bitmap getThumnail() {
		return thumnail;
	}
	public void setThumnail(Bitmap thumnail) {
		this.thumnail = thumnail;
	}
}
