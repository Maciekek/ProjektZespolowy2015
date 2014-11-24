package com.moneygiver.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.moneygiver.actions.Message;

/**
 * Created by Szymon on 2014-11-23.
 */
public class DatabaseAdapter {

    private DatabaseHelper databaseHelper;
    private Context context;
    public DatabaseAdapter(Context context) {
        databaseHelper = new DatabaseHelper(context);
        this.context = context;
    }

    public long insertData(int isLoggedIn) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(databaseHelper.IS_LOGGED_IN, isLoggedIn);
        return db.insert(databaseHelper.TABLE_NAME, null, contentValues);
    }

    public String getAllData() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {databaseHelper.UID, databaseHelper.IS_LOGGED_IN};
        Cursor cursor = db.query(databaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext()) {
            int index1 = cursor.getColumnIndex(databaseHelper.UID);
            int cUID = cursor.getInt(index1);
            int index2 = cursor.getColumnIndex(databaseHelper.IS_LOGGED_IN);
            int cIsLoggedIn = cursor.getInt(index2);
            buffer.append(cUID +" "+cIsLoggedIn + "\n");
        }
        return buffer.toString();
    }

    public void deleteAllData() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(databaseHelper.TABLE_NAME, null, null);
    }

    public int getLoggedIn() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {DatabaseHelper.IS_LOGGED_IN};
        String[] selectionArgs = {"1"};
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, columns,
                DatabaseHelper.IS_LOGGED_IN + " = ?", selectionArgs,
                null, null, null);

        int cIsLoggedIn;
        if(cursor.getCount() >= 1) {
            cursor.moveToLast();
            int index0 = cursor.getColumnIndex(DatabaseHelper.IS_LOGGED_IN);
            cIsLoggedIn = cursor.getInt(index0);
        } else {
            cIsLoggedIn = -1;
        }

        return cIsLoggedIn;
    }

    static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "MoneyGiver_sqLite";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_NAME = "LOGGEDIN";
        private static final String UID = "_id";
        private static final String IS_LOGGED_IN = "isLoggedIn";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + UID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + IS_LOGGED_IN + " INTEGER);";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS" + TABLE_NAME;
        private Context context;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i2) {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }
    }
}
