package com.moneygiver.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.moneygiver.DBObjects.UserData;

import java.util.HashMap;
import java.util.Iterator;

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

    public void insertMonthly(UserData user) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Iterator it = user.getMonthly().entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pairs = (HashMap.Entry)it.next();
            ContentValues cv = new ContentValues();
            cv.put(databaseHelper.NAME_COLUMN, pairs.getKey().toString());
            cv.put(databaseHelper.VALUE_COLUMN, (Double) pairs.getValue());
            db.insert(databaseHelper.TABLE_NAME_MONTHLY, null, cv);

            System.out.println(pairs.getKey() + " = " + pairs.getValue());

            it.remove(); // avoids a ConcurrentModificationException
        }
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

    public void cleanMonthlyData() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(databaseHelper.TABLE_NAME_MONTHLY, null, null);
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

    public HashMap<String, Double> getMonthly() {
        HashMap<String, Double> monthly = new HashMap<String, Double>();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {DatabaseHelper.NAME_COLUMN, DatabaseHelper.VALUE_COLUMN};
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME_MONTHLY, columns,
                null, null,
                null, null, null);

        cursor.moveToFirst();
        int nameIndex = cursor.getColumnIndex(DatabaseHelper.NAME_COLUMN);
        int valueIndex = cursor.getColumnIndex(DatabaseHelper.VALUE_COLUMN);
        while (!cursor.isAfterLast()) {
            monthly.put(cursor.getString(nameIndex), cursor.getDouble(valueIndex));
            cursor.moveToNext();
        }
        return monthly;

    }

    static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "MoneyGiver_sqLite";
        private static final int DATABASE_VERSION = 6;
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

        private static final String TABLE_NAME_MONTHLY = "MONTHLY";
        private static final String NAME_COLUMN = "NAME";
        private static final String VALUE_COLUMN = "VALUE";


        private static final String CREATE_TABLE_MONTHLY = "CREATE TABLE " + TABLE_NAME_MONTHLY + " (" + UID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME_COLUMN + " TEXT, " + VALUE_COLUMN +
                " REAL);";
        private static final String DROP_TABLE_MONTHLY = "DROP TABLE IF EXISTS " + TABLE_NAME_MONTHLY;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_LOGGEDIN);
            db.execSQL(CREATE_TABLE_USER);
            db.execSQL(CREATE_TABLE_MONTHLY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i2) {
            db.execSQL(DROP_TABLE_LOGGEDIN);
            db.execSQL(DROP_TABLE_USER);
            db.execSQL(DROP_TABLE_MONTHLY);

            onCreate(db);
        }
    }
}
