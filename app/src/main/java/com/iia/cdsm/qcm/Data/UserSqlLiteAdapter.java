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

    public static final String TABLE_USER = "`users`";
    public static final String COL_ID = "_id";
    public static final String COL_USERNAME ="username";
    public static final String COL_PASSWORD = "password";
    private SQLiteDatabase db;
    private iiaSqlLiteOpenHelper helper;

    public UserSqlLiteAdapter(Context context) {
        this.helper = new iiaSqlLiteOpenHelper(context, iiaSqlLiteOpenHelper.DB_NAME,null,1);
    }

    public static String getSchema() {
        return "CREATE TABLE" + TABLE_USER + "(" + COL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USERNAME + "TEXT NOT NULL," + COL_PASSWORD + "TEXT NOT NULL);";
    }

    public void open() {this.db = this.helper.getWritableDatabase();}

    public void close() {this.db.close();}

    public long insert(User user) {

        ContentValues values = this.userToContentValues(user);
        return db.insert(TABLE_USER,null,values);
    }

    public long delete(int id) {

        String whereClauseDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(id)};
        return db.delete(TABLE_USER, whereClauseDelete, whereArgsDelete);
    }

    public long update (User user) {

        String whereClauseUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate = {String.valueOf(user.getId())};
        ContentValues values = this.userToContentValues(user);
        return db.update(TABLE_USER, values, whereClauseUpdate, whereArgsUpdate);

    }


    public User getUser(int id) {
        String[] cols = {COL_ID,COL_USERNAME,COL_PASSWORD};
        String whereClauses = COL_ID + "= ?";
        String[] whereArgs = {String.valueOf(id)};
        Cursor c = db.query(TABLE_USER,cols,whereClauses,whereArgs,null,null,null);
        User user = null;

        if (c.getCount() > 0) {

            c.moveToFirst();
            user = cursorToItem(c);
        }
        return user;

    }

    public static User cursorToItem(Cursor c) {

        User user = new User(0,null,null);
        user.setId(c.getInt(c.getColumnIndex(COL_ID)));
        user.setUsername(c.getString(c.getColumnIndex(COL_USERNAME)));
        user.setPassword(c.getString(c.getColumnIndex(COL_PASSWORD)));
        return user;
    }

    private ContentValues userToContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME,user.getUsername());
        values.put(COL_PASSWORD,user.getPassword());
        return values;

    }


}
