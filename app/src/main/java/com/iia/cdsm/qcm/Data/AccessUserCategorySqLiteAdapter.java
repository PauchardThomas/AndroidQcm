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
    public static final String TABLE_ACCESS_USER_CATEGORY ="access_user_category";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_CATEGORY_ID ="category_id";

    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;

    public AccessUserCategorySqLiteAdapter(Context context) {
        this.helper = new iiaSqlLiteOpenHelper(context, iiaSqlLiteOpenHelper.DB_NAME,null,1);
    }

    public static String getSchema() {
        return "CREATE TABLE " + TABLE_ACCESS_USER_CATEGORY + "(" + COL_USER_ID +
                " INTEGER," + COL_CATEGORY_ID + " INTEGER," +
                 "PRIMARY KEY ("+COL_USER_ID+","+COL_CATEGORY_ID+
                "), FOREIGN KEY " +"(`"+COL_CATEGORY_ID+"`) REFERENCES `category` (`_id`)," +
                "FOREIGN KEY (`"+COL_USER_ID+"`) REFERENCES `user` (`_id`));";
    }

    public void open() {this.db = this.helper.getWritableDatabase();}

    public void close() {this.db.close();}

    public long insert(User user,Category category) {

        ContentValues values = this.userCategoryToContentValues(user, category);
        return db.insert(TABLE_ACCESS_USER_CATEGORY, null, values);
    }

   /* public long delete(int id) {

        String whereClauseDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(id)};
        return db.delete(TABLE_QCM, whereClauseDelete, whereArgsDelete);
    }*/

   /* public long update (Qcm qcm) {

        String whereClauseUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate = {String.valueOf(qcm.getId())};
        ContentValues values = this.qcmToContentValues(qcm);
        return db.update(TABLE_QCM, values, whereClauseUpdate, whereArgsUpdate);

    }*/

    private ContentValues userCategoryToContentValues(User user,Category category) {

        ContentValues values = new ContentValues();
        values.put(COL_USER_ID,user.getId());
        values.put(COL_CATEGORY_ID,category.getId());
        return values;

    }
}
