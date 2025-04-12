package com.example.demoapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MessageAdapter {

    private Context context;

    static DataBaseHelper dbHelper;

    public MessageAdapter(Context _context){

        dbHelper = new DataBaseHelper(_context);
        context = _context;
    }

    public long insertMessage(Message message){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.MESSAGE, message.getMessage());
        contentValues.put(DataBaseHelper.NUMBER,message.getMobile());
        return db.insert(DataBaseHelper.MY_TABLE_NAME,null,contentValues);

    }

    public Message findMessage(String mobileNumber){
        Message msg = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] args = {mobileNumber};
        Cursor result = db.query(DataBaseHelper.MY_TABLE_NAME, null, "number = ? ", args, null, null, null);

        if(result != null){
            result.moveToNext();
            msg = new Message(result.getString(0), result.getString(1));
        }
        return msg;
    }

    static class DataBaseHelper extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "user_msg.db";
        private static final String MY_TABLE_NAME = "MESSAGES";
        private static final String NUMBER = "number";
        private static final String MESSAGE = "message";
        private static final String CREATE_USER_MESSAGE_TABLE =
                "CREATE TABLE " + MY_TABLE_NAME + " (" +
                        NUMBER + " TEXT PRIMARY KEY, " +
                        MESSAGE + " TEXT)";
        public DataBaseHelper(@Nullable Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }



        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_USER_MESSAGE_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    }



