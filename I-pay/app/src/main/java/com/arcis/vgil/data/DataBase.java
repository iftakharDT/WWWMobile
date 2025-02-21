package com.arcis.vgil.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase 
{   
    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "citydatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_City =
    		"create table citydata (_id integer primary key autoincrement, "
    				+ "cityid text not null, "
    				+ "cityname text not null, "
    				+ "areaid text not null, "
    				+ "areaname text not null );";
    private static final String DATABASE_CREATE_BankBrabch =
    		"create table bankbranchdata (_id integer primary key autoincrement, "
    				+ "bankid text not null, "
    				+ "bankname text not null, "
    				+ "branchid text not null, "
    				+ "branchname text not null );";
    public static final String DATABASE_CREATE_ProductName = "create table tbl_productsName (_id integer primary key autoincrement, "
    				+ " productid text not null ,"
    				+ " productName text not null ,"
    				+ " productcode text not null );";
    
    private final Context context;
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DataBase(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DATABASE_CREATE_City);
            db.execSQL(DATABASE_CREATE_BankBrabch);
            db.execSQL(DATABASE_CREATE_ProductName);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS citydata");
            db.execSQL("DROP TABLE IF EXISTS bankbranchdata");
            onCreate(db);
        }
    }    
    
    //---opens the database---
    public DataBase open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }
    
    //---insert a title into the database---
    public long insert(String DATABASE_TABLE, ContentValues initialValues)
    {
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular title---
    public boolean delete(String DATABASE_TABLE, String KEY_ROWID, long rowNumericId, String rowStringId, Boolean flag)
    {
    	if(flag){
        return db.delete(DATABASE_TABLE, KEY_ROWID + 
        		"=" + rowNumericId, null) > 0;
    	}else{
		return db.delete(DATABASE_TABLE, KEY_ROWID + 
        		"='" + rowStringId +"'", null) > 0;
    	}
    }
    //---deletes multiple title---
    public boolean deleteMultiple(String DATABASE_TABLE, String KEY_ROWID, String rowStringId, Boolean flag)
    {
    	if(flag){
        return db.delete(DATABASE_TABLE, KEY_ROWID + 
        		"IN(" + rowStringId+")", null) > 0;
    	}else{
		return db.delete(DATABASE_TABLE, KEY_ROWID + 
        		"IN('" + rowStringId +"')", null) > 0;
    	}
    }
    
  //---deletes a all the title---
    public boolean deleteAll(String DATABASE_TABLE)
    {
        return db.delete(DATABASE_TABLE,null, null) > 0;
    }

    //---retrieves all the titles---
    public Cursor getAll(String select)
    {
        return db.rawQuery(select, null);
    }

    //---retrieves a particular title---
    public Cursor getSingle(String select) throws SQLException
    {
        Cursor mCursor = db.rawQuery(select, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a title---
    public boolean update(String DATABASE_TABLE, ContentValues args, String KEY_ROWID, int rowNumericId, String rowStringId, Boolean flag)
    {
    	if(flag){
        return db.update(DATABASE_TABLE, args, 
                         KEY_ROWID + "=" + rowNumericId, null) > 0;
    	}else{
    		return db.update(DATABASE_TABLE, args, 
                    KEY_ROWID + "='" + rowStringId +"'", null) > 0;
    	}
    }
}
