package com.iia.cdsm.qcm.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iia.cdsm.qcm.Entity.Category;
import com.iia.cdsm.qcm.Entity.User;

import java.text.SimpleDateFormat;

/**
 * Created by Thom' on 23/03/2016.
 */
public class AccessUserCategorySqLiteAdapter {

    /**
     * Table access_user_category
     */
    public static final String TABLE_ACCESS_USER_CATEGORY = "access_user_category";
    /**
     * Column user_id
     */
    public static final String COL_USER_ID = "user_id";
    /**
     * Column category_id
     */
    public static final String COL_CATEGORY_ID = "category_id";

    /**
     * Database
     */
    private SQLiteDatabase db;
    /**
     * Helper
     */
    private SQLiteOpenHelper helper;

    /**
     * SqlLiteOpenHelper constructor
     *
     * @param context activity context
     */
    public AccessUserCategorySqLiteAdapter(Context context) {
        this.helper = new iiaSqlLiteOpenHelper(context, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
    }

    /**
     * Get AccessUserUserCategory database schema
     *
     * @return AccessUserUserCategory database schema
     */
    public static String getSchema() {
        return "CREATE TABLE " + TABLE_ACCESS_USER_CATEGORY + "(" + COL_USER_ID +
                " INTEGER," + COL_CATEGORY_ID + " INTEGER," +
                "PRIMARY KEY (" + COL_USER_ID + "," + COL_CATEGORY_ID +
                "), FOREIGN KEY " + "(`" + COL_CATEGORY_ID + "`) REFERENCES `category` (`_id`)," +
                "FOREIGN KEY (`" + COL_USER_ID + "`) REFERENCES `user` (`_id`));";
    }

    /**
     * Set database writable
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
     * Insert an authorization for a user to access a category
     *
     * @param user     user
     * @param category category
     * @return id inserted
     */
    public long insert(User user, Category category) {

        ContentValues values = this.userCategoryToContentValues(user, category);
        return db.insert(TABLE_ACCESS_USER_CATEGORY, null, values);
    }
    

    /**
     * Convert User and Category to ContentValues before inserting
     *
     * @param user     User
     * @param category Category
     * @return ContentValues to insert
     */
    private ContentValues userCategoryToContentValues(User user, Category category) {

        ContentValues values = new ContentValues();
        values.put(COL_USER_ID, user.getId());
        values.put(COL_CATEGORY_ID, category.getId());
        return values;

    }
}
