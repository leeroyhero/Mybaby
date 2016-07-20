package ru.bogdanov.mybaby.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Владимир on 14.07.2016.
 */
public class DataBase extends SQLiteOpenHelper {
    public static final String DB_NAME="baby_db";
    public static final String TAG="db_info";
    public static final int DB_VERSION=1;
    SQLiteDatabase database;



    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        database=getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
updateMyDataBase(sqLiteDatabase,0,DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void updateMyDataBase(SQLiteDatabase db,int oldVersion,int newVersion){
        if (oldVersion==0){
            db.execSQL("CREATE TABLE "+DBNamespace.TABLE_USER+
            " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    DBNamespace.TABLE_USER_NAME+" TEXT, "+
                    DBNamespace.TABLE_USER_PIC_ID+" NUMERIC);"
            );
            ContentValues contentValues=new ContentValues();
            contentValues.put(DBNamespace.TABLE_USER_NAME, "User");
            contentValues.put(DBNamespace.TABLE_USER_PIC_ID, 1);
            db.insert(DBNamespace.TABLE_USER, null, contentValues);

            db.execSQL("CREATE TABLE "+DBNamespace.TABLE_BABY+
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    DBNamespace.TABLE_BABY_NAME+" TEXT, "+
                    DBNamespace.TABLE_BABY_DATE+" NUMERIC, "+
                    DBNamespace.TABLE_BABY_WEIGHT+" REAL, "+
                    DBNamespace.TABLE_BABY_HEIGHT+" REAL);"
            );

            db.execSQL("CREATE TABLE "+DBNamespace.TABLE_BABY_INFO+
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    DBNamespace.TABLE_BABY_INFO_NAME+" TEXT, "+
                    DBNamespace.TABLE_BABY_INFO_BIRTH+" NUMERIC);"
            );
        }
    }

    public void addBabyState (String name, long date, float weight, float height ){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBNamespace.TABLE_BABY_NAME, name);
        contentValues.put(DBNamespace.TABLE_BABY_DATE, date);
        contentValues.put(DBNamespace.TABLE_BABY_WEIGHT, weight);
        contentValues.put(DBNamespace.TABLE_BABY_HEIGHT, height);
        database.insert(DBNamespace.TABLE_BABY, null, contentValues);
    }

    public void addBabyInfo(String name, long birth){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBNamespace.TABLE_BABY_INFO_NAME, name);
        contentValues.put(DBNamespace.TABLE_BABY_INFO_BIRTH, birth);
        database.insert(DBNamespace.TABLE_BABY_INFO, null, contentValues);
    }

    public void setUserName(String name){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBNamespace.TABLE_USER_NAME, name);
        database.update(DBNamespace.TABLE_USER, contentValues, null,null);
    }

    public void setUserPicId (int picId){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBNamespace.TABLE_USER_PIC_ID, picId);
        database.update(DBNamespace.TABLE_USER, contentValues, null,null);
    }
}
