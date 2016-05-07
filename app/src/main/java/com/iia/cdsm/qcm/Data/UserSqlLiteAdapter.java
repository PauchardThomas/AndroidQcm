package com.iia.cdsm.qcm.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iia.cdsm.qcm.Entity.User;

/**
 * Created by Thom' on 13/02/2016.
 */
public class UserSqlLiteAdapter {

    /**
     * Table user
     */
    public static final String TABLE_USER = "`users`";
    /**
     * Column id
     */
    public static final String COL_ID = "_id";
    /**
     * Column username
     */
    public static final String COL_USERNAME = "username";
    /**
     * Column password
     */
    public static final String COL_PASSWORD = "password";
    /**
     * Column server ID
     */
    public static final String COL_ID_SERVER = "id_server";
    /**
     * Database
     */
    private SQLiteDatabase db;
    /**
     * Helper
     */
    private iiaSqlLiteOpenHelper helper;

    /**
     * Helper constructor
     *
     * @param context activity context
     */
    public UserSqlLiteAdapter(Context context) {
        this.helper = new iiaSqlLiteOpenHelper(context, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
    }

    /**
     * Get User database schema
     *
     * @return
     */
    public static String getSchema() {
        return "CREATE TABLE " + TABLE_USER + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USERNAME + " TEXT NOT NULL," + COL_PASSWORD + " TEXT NOT NULL," + COL_ID_SERVER + " int NOT NULL);";
    }

    /**
     * Get writable database
     */
    public void open() {
        this.db = this.helper.getWritableDatabase();
    }

    /**
     * Close database
     */
    public void close() {
        this.db.close();
    }

    /**
     * Insert user
     *
     * @param user User
     * @return User id inserted
     */
    public long insert(User user) {

        ContentValues values = this.userToContentValues(user);
        return db.insert(TABLE_USER, null, values);
    }

    /**
     * Delete User
     *
     * @param id id user
     * @return User id deleted
     */
    public long delete(int id) {

        String whereClauseDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(id)};
        return db.delete(TABLE_USER, whereClauseDelete, whereArgsDelete);
    }

    /**
     * Update User
     *
     * @param user User
     * @return User id updated
     */
    public long update(User user) {

        String whereClauseUpdate = COL_ID_SERVER + "= ?";
        String[] whereArgsUpdate = {String.valueOf(user.getIdServer())};
        ContentValues values = this.userToContentValues(user);
        long test = db.update(TABLE_USER, values, whereClauseUpdate, whereArgsUpdate);
        return test;

    }


    /**
     * Get only one user
     *
     * @param id user id
     * @return User
     */
    public User getUser(int id) {
        String[] cols = {COL_ID, COL_USERNAME, COL_PASSWORD, COL_ID_SERVER};
        String whereClauses = COL_ID_SERVER + "= ?";
        String[] whereArgs = {String.valueOf(id)};
        Cursor c = db.query(TABLE_USER, cols, whereClauses, whereArgs, null, null, null);
        User user = null;

        if (c.getCount() > 0) {

            c.moveToFirst();
            user = cursorToItem(c);
        }
        return user;

    }

    /**
     * Convert cursor To User
     *
     * @param c cursor
     * @return User
     */
    public static User cursorToItem(Cursor c) {

        User user = new User(null, null, 0);
        user.setId(c.getInt(c.getColumnIndex(COL_ID)));
        user.setUsername(c.getString(c.getColumnIndex(COL_USERNAME)));
        user.setPassword(c.getString(c.getColumnIndex(COL_PASSWORD)));
        user.setIdServer(c.getInt(c.getColumnIndex(COL_ID_SERVER)));
        return user;
    }

    /**
     * Convert User to ContentValue before inserting
     *
     * @param user User
     * @return Content Values to insert
     */
    private ContentValues userToContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, user.getUsername());
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_ID_SERVER, user.getIdServer());
        return values;

    }


}
