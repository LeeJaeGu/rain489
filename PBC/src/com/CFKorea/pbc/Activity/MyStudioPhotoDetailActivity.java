package com.CFKorea.pbc.Activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.CFKorea.pbc.R;

public class MyStudioPhotoDetailActivity extends Activity implements OnClickListener
{
	
	int selectedIndex;
	ArrayList<String> gallerylist = new ArrayList<String>();;
	private ViewPager mPager;
	private int COUNT = 0;
	public static Bitmap bm;
	private ImageView one;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mystudiophotodetail);
		
		ImageButton btn = (ImageButton)findViewById(R.id.btn_back);
		ImageView one = (ImageView)findViewById(R.id.one);
		btn.setOnClickListener(this);
		
		Intent i = getIntent();
		Bundle extras = i.getExtras();
		gallerylist = (ArrayList<String>) i.getSerializableExtra("gallerylist");
		selectedIndex = extras.getInt("selectedIndex");
		Log.d("","selectedIndex = " + selectedIndex);
		COUNT = gallerylist.size();
		Log.d("","COUNT = " + COUNT);
		for(int t=0; t <COUNT; t++){
			String h = gallerylist.get(t);
			Log.d("","h =" + h);
			
		}
		if(COUNT==1||gallerylist.get(0).equals(gallerylist.get(1))){
			String imgPath = gallerylist.get(0);
			BitmapFactory.Options bfo = new BitmapFactory.Options();
			bfo.inSampleSize = 3;
			bm = BitmapFactory.decodeFile(imgPath, bfo);
			one.setImageBitmap(bm);
			
		}
		
		else{
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(new BkPagerAdapter(getApplicationContext()));
		mPager.setCurrentItem(COUNT);
		
		mPager.setOnPageChangeListener(new OnPageChangeListener() 
		{
            @Override
            public void onPageSelected(int position) 
            {
                if(position < COUNT)
                {
                	mPager.setCurrentItem(position+COUNT, false);
                }
                else if(position >= COUNT*2)
                {
                	mPager.setCurrentItem(position - COUNT, false);
                }
            }
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
		});
		}
	}
	
	private class BkPagerAdapter extends PagerAdapter {
		private Context mContext;
		private int mPosition = selectedIndex;
		
		
		public BkPagerAdapter(Context con) {
			super();
			mContext = con;
			Log.d("DEBUG","BkPagerAdapter(Context) con");
		}
		
		@Override
		public int getCount()
		{
			return COUNT * 3;
		} 
		

		@Override
		public Object instantiateItem(View pager, int position) 
		{
			
			position %= COUNT;
			
			int nPosition = mPosition + position;
			if(nPosition > COUNT-1)
			{
				nPosition -= COUNT;
			}
			Log.d("DEBUG","BkPagerAdapter position = " + position + ", mPosition = " + mPosition + ", nPosition = " + nPosition);
			String imgPath = gallerylist.get(nPosition);
			//Log.d("DEBUG","BkPagerAdapter imgPath " + imgPath + ", position = " + position + ", n = " + n);
			BitmapFactory.Options bfo = new BitmapFactory.Options();
			bfo.inSampleSize = 3;
			bm = BitmapFactory.decodeFile(imgPath, bfo);
			Bitmap resized = Bitmap.createScaledBitmap(bm, bm.getWidth(), bm.getHeight(), true);
			
			ImageView view = new ImageView(mContext);
			
			view.setImageBitmap(resized);
			((ViewPager) pager).addView(view);
			
			return view;
		}

		
		@Override
		public void destroyItem(View pager, int position, Object view) {
			((ViewPager) pager).removeView((View) view);
		}

		
		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}
	
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btn_back:
			finish();
			break;
		} 
	}
}
