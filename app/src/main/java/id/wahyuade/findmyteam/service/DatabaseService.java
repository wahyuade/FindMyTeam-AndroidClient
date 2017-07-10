package id.wahyuade.findmyteam.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import id.wahyuade.findmyteam.model.Login.DataLogin;
import id.wahyuade.findmyteam.model.Login.LoginModel;

/**
 * Created by wahyuade on 07/07/17.
 */

public class DatabaseService extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "find-my-team";

    private static final String TABLE_USERS = "users";
    private static final String USERS_ID = "id";
    private static final String USERS_ID_USER = "_id_user";
    private static final String USERS_FIRSTNAME = "firstname";
    private static final String USERS_LASTNAME = "lastname";
    private static final String USERS_EMAIL = "email";
    private static final String USERS_ROLE = "role";
    private static final String USERS_X_API_KEY = "x_api_key";
    private static final String USERS_FOTO = "user_foto";

    public DatabaseService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "(" +
                USERS_ID + " INTEGER PRIMARY KEY," +
                USERS_ID_USER + " TEXT," +
                USERS_FIRSTNAME + " TEXT," +
                USERS_LASTNAME + " TEXT," +
                USERS_EMAIL + " TEXT," +
                USERS_FOTO + " TEXT," +
                USERS_ROLE + " INTEGER," +
                USERS_X_API_KEY + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean setUsersData(DataLogin dataLogin){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put(USERS_ID_USER, dataLogin.get_id());
        user.put(USERS_FIRSTNAME, dataLogin.getFirstname());
        user.put(USERS_LASTNAME, dataLogin.getLastname());
        user.put(USERS_EMAIL, dataLogin.getEmail());
        user.put(USERS_ROLE, dataLogin.getRole());
        user.put(USERS_FOTO, dataLogin.getUser_foto());
        user.put(USERS_X_API_KEY, dataLogin.getX_api_key());

        db.insert(TABLE_USERS, null, user);
        db.close();

        return true;
    }

    public void unSetUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db,0,0);
    }

    public boolean isAdmin(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{USERS_ROLE},null,null,null,null,null);
        cursor.moveToFirst();
        if(cursor.getInt(0) == 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkUsersTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(db, TABLE_USERS);
        db.close();
        if(cnt == 1){
            return true;
        }else{
            return false;
        }
    }

    public String getX_api_key(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{USERS_X_API_KEY},USERS_ID+"="+1,null,null,null,null,null);
        cursor.moveToFirst();
        db.close();
        return cursor.getString(0);
    }

    public String getNama(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{USERS_FIRSTNAME, USERS_LASTNAME},USERS_ID+"="+1,null,null,null,null,null);
        cursor.moveToFirst();
        db.close();
        return cursor.getString(0) +" "+cursor.getString(1);
    }

    public String getEmail(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{USERS_EMAIL},USERS_ID+"="+1,null,null,null,null,null);
        cursor.moveToFirst();
        db.close();
        return cursor.getString(0);
    }

    public String getUserFoto(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{USERS_FOTO},USERS_ID+"="+1,null,null,null,null,null);
        cursor.moveToFirst();
        db.close();
        return cursor.getString(0);
    }
}
