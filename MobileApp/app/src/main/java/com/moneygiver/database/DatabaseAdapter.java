package com.moneygiver.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.moneygiver.DBObjects.UserData;

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
        contentValues.put(databaseHelper.IS_LOGGED_IN_COLUMN, isLoggedIn);
        return db.insert(databaseHelper.TABLE_NAME_LOGGEDIN, null, contentValues);
    }

    public long insertUserData(UserData user) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(databaseHelper.USERNAME_COLUMN, user.getUsername());
        contentValues.put(databaseHelper.INCOME_COLUMN, user.getIncome());
        contentValues.put(databaseHelper.PASSWORD_COLUMN, user.getPassword());
        return db.insert(databaseHelper.TABLE_NAME_USER, null, contentValues);
    }


    public String getAllData() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {databaseHelper.UID, databaseHelper.IS_LOGGED_IN_COLUMN};
        Cursor cursor = db.query(databaseHelper.TABLE_NAME_LOGGEDIN, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext()) {
            int index1 = cursor.getColumnIndex(databaseHelper.UID);
            int cUID = cursor.getInt(index1);
            int index2 = cursor.getColumnIndex(databaseHelper.IS_LOGGED_IN_COLUMN);
            int cIsLoggedIn = cursor.getInt(index2);
            buffer.append(cUID +" "+cIsLoggedIn + "\n");
        }
        return buffer.toString();
    }

    public void clearLoginData() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(databaseHelper.TABLE_NAME_LOGGEDIN, null, null);
    }

    public void clearUserData () {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(databaseHelper.TABLE_NAME_USER, null, null);
    }

    public int getLoggedIn() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {DatabaseHelper.IS_LOGGED_IN_COLUMN};
        String[] selectionArgs = {"1"};
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME_LOGGEDIN, columns,
                DatabaseHelper.IS_LOGGED_IN_COLUMN + " = ?", selectionArgs,
                null, null, null);

        int cIsLoggedIn;
        if(cursor.getCount() >= 1) {
            cursor.moveToLast();
            int index0 = cursor.getColumnIndex(DatabaseHelper.IS_LOGGED_IN_COLUMN);
            cIsLoggedIn = cursor.getInt(index0);
        } else {
            cIsLoggedIn = -1;
        }

        return cIsLoggedIn;
    }

    public UserData getUserData() {
        UserData ud = new UserData();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {DatabaseHelper.USERNAME_COLUMN, DatabaseHelper.INCOME_COLUMN, DatabaseHelper.PASSWORD_COLUMN};
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME_USER, columns,
               null, null,
                null, null, null);

        if(cursor.getCount() >= 1) {
            cursor.moveToLast();
            int indexUser = cursor.getColumnIndex(DatabaseHelper.USERNAME_COLUMN);
            int indexIncome = cursor.getColumnIndex(DatabaseHelper.INCOME_COLUMN);
            int indexPassword = cursor.getColumnIndex(DatabaseHelper.PASSWORD_COLUMN);

            ud.setPassword(cursor.getString(indexPassword));
            ud.setUsername(cursor.getString(indexUser));
            ud.setIncome(cursor.getInt(indexIncome));
        } else {
            ud.setUsername("null");
            ud.setIncome(-1);
            ud.setPassword("null1");
        }
        return ud;

    }

    static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "MoneyGiver_sqLite";
        private static final int DATABASE_VERSION = 4;
        private static final String TABLE_NAME_LOGGEDIN = "LOGGEDIN";
        private static final String UID = "_id";
        private static final String IS_LOGGED_IN_COLUMN = "isLoggedIn";
        private static final String CREATE_TABLE_LOGGEDIN = "CREATE TABLE " + TABLE_NAME_LOGGEDIN + " (" + UID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + IS_LOGGED_IN_COLUMN + " INTEGER);";
        private static final String DROP_TABLE_LOGGEDIN = "DROP TABLE IF EXISTS " + TABLE_NAME_LOGGEDIN;
        private Context context;

        private static final String TABLE_NAME_USER = "USERDATA";
        private static final String USERNAME_COLUMN = "USERNAME";
        private static final String INCOME_COLUMN = "INCOME";
        private static final String PASSWORD_COLUMN = "PASSWORD";

        private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_NAME_USER + " (" + UID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USERNAME_COLUMN + " TEXT, " + INCOME_COLUMN +
                " INTEGER, " + PASSWORD_COLUMN +" TEXT);";
        private static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS " + TABLE_NAME_USER;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_LOGGEDIN);
            db.execSQL(CREATE_TABLE_USER);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i2) {
            db.execSQL(DROP_TABLE_LOGGEDIN);
            db.execSQL(DROP_TABLE_USER);
            onCreate(db);
        }
    }
}
