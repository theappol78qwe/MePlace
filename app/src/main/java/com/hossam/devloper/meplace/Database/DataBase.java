package com.hossam.devloper.meplace.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "meplace_dp";
    private static final int DATABASE_VERSION = 2;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQLCommends.CREATE_ADD_FRIENDS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQLCommends.Drop_FRIEND_TABLE);

        onCreate(db);

    }

    public void insertData(SQLiteDatabase sql, String uniqueId, String friendID) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Friends_Table.UniqueId, uniqueId);
        contentValues.put(Contract.Friends_Table.FriendId, friendID);
        sql.insert(Contract.Friends_Table.Table_Name, null, contentValues);

    }




//    public boolean CheckOpject(String id) {
//        SQLiteDatabase db = getWritableDatabase();
//        String selectString = "SELECT * FROM " + Contract.Favorite_Table.Table_Name + " WHERE " + Contract.Favorite_Table.TitleFilm_col + " =?";
//
//        Cursor cursor = db.rawQuery(selectString, new String[]{id});
//
//        boolean checkOpject = false;
//        if (cursor.moveToFirst()) {
//            checkOpject = true;
//
//        }
//        cursor.close();
//        db.close();
//        return checkOpject;
//    }



}

