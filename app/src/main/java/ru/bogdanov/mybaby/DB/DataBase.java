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
                    DBNamespace.TABLE_USER_NAME+" TEXT);"
            );
            ContentValues contentValues=new ContentValues();
            contentValues.put(DBNamespace.TABLE_USER_NAME, "User");
            db.insert(DBNamespace.TABLE_USER, null, contentValues);
            db.execSQL("CREATE TABLE "+DBNamespace.TABLE_BABY+
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    DBNamespace.TABLE_BABY_NAME+" TEXT, "+
                    DBNamespace.TABLE_BABY_BIRTH+" NUMERIC, "+
                    DBNamespace.TABLE_BABY_DATE+" NUMERIC, "+
                    DBNamespace.TABLE_BABY_WEIGHT+" REAL, "+
                    DBNamespace.TABLE_BABY_HEIGHT+" REAL);"
            );
        }
    }



    public void setUserName(String name){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBNamespace.TABLE_USER_NAME, name);
        database.update(DBNamespace.TABLE_USER, contentValues, null,null);
    }
}
