package com.example.home.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * SQLite 사용할 수 있도록 SQLiteOpenHelper 클래스를 상속받아 DBHelper 생성
 */
public class DBHelper extends SQLiteOpenHelper {
    Context context;
    SQLiteDatabase db;
    Cursor cursor;

    private static final String DATABASE_NAME = "projectDBa.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "projectTablea";
    private static final String TABLE_NAME2 = "oneday";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " name TEXT, spot TEXT, price TEXT, pricesum INT, date TEXT);");
        db.execSQL("CREATE TABLE " + TABLE_NAME2 + "(_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " oneday INT);");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        //Toast.makeText(context,"onOpen() 메소드 호출", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Toast.makeText(context,"onUpgrade() 메소드 호출", Toast.LENGTH_LONG).show();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }

    public void insert(String name, String spot, String price, Integer pricesum, String date) {
        db = getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(null, '" + name
                + "', '" + spot + "', '" + price + "', '" + pricesum + "', '" + date + "');");
        db.close();
    }

    public void insert2(Integer pricesum) {
        db = getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE_NAME2 + " VALUES(null, '" + pricesum + "');");
        db.close();
    }



    public void drop() {
        db = getReadableDatabase();
        db.execSQL("DROP TABLE " + TABLE_NAME);
        db.execSQL("DROP TABLE " + TABLE_NAME2);
        onCreate(db);
        db.close();

    }
    public void drop2() {
        db = getReadableDatabase();
        db.execSQL("DROP TABLE " + TABLE_NAME2);
        db.execSQL("CREATE TABLE " + TABLE_NAME2 + "(_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " oneday INT);");
        db.close();

    }


    // 테이블에 있는 전체 레코드 쿼리
    public Cursor CursorQuery() {
        db = getReadableDatabase();
        //Toast.makeText(context,"CursorQuery() 메소드 호출", Toast.LENGTH_LONG).show();
        cursor = db.rawQuery("SELECT * FROM " +  TABLE_NAME, null);
        return cursor;
    }

    public Cursor totalgogo(){
        db = getReadableDatabase();
        cursor = db.rawQuery("SELECT SUM(pricesum) AS total FROM " + TABLE_NAME, null);
        return cursor;
    }
    public Cursor onegogo(){
        db = getReadableDatabase();
        cursor = db.rawQuery("SELECT SUM(oneday) AS total FROM " + TABLE_NAME2, null);
        if(cursor == null){
            cursor = db.rawQuery("SELECT 0", null);
        }
        return cursor;
    }

    public Cursor graphgogo(){
        db = getReadableDatabase();
        cursor = db.rawQuery("SELECT spot FROM " + TABLE_NAME + " GROUP BY spot ORDER BY pricesum desc LIMIT 5", null);
        return cursor;
    }

    public Cursor graphprice(){
        db = getReadableDatabase();
        cursor = db.rawQuery("SELECT SUM(pricesum) AS sum FROM " + TABLE_NAME + " GROUP BY spot ORDER BY sum desc LIMIT 5", null);
        return cursor;
    }
}
