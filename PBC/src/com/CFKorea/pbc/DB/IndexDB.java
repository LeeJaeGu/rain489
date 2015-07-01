package com.CFKorea.pbc.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class IndexDB extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "index.db";
	private static final int DATABASE_VERSION = 1;
	
	
	public final static String TABLE_NAME = "pbc_voice_index";
	public final static String idx = "idx";
	public final static String savefile = "savefile";
	public static int num;
	
	public IndexDB(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.d("","index db start");
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		Log.d("","CREATE TABLE " + TABLE_NAME + " (" + idx + " INTEGER PRIMARY KEY AUTOINCREMENT," +savefile + " VARCHAR(20)" + ");");
		db.execSQL("CREATE TABLE " +TABLE_NAME+ "(" +idx + " INTEGER PRIMARY KEY AUTOINCREMENT, " +savefile+ " VARCHAR(20)" + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

}