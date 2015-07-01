package com.CFKorea.pbc.Utility;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.CFKorea.pbc.R;

public class MyProgressDialog {

	private Context mContext;
	private Dialog mDialog;
	public MyProgressDialog(Context context) {
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	
	
	public void show() {
		 mDialog = new Dialog(mContext, android.R.style.Theme_Black);
		 View view = LayoutInflater.from(mContext).inflate(R.layout.new_dialog, null);
		 mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE );
		 mDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
		 mDialog.setContentView(view);
		 mDialog.show();
		
	}
	
	public void dismiss() {
		mDialog.dismiss();
	}
}
