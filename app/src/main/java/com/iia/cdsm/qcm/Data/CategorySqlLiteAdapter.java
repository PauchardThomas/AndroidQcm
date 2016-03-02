package com.iia.cdsm.qcm.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iia.cdsm.qcm.Entity.Category;

import java.util.ArrayList;

/**
 * Created by Thom' on 13/02/2016.
 */
public class CategorySqlLiteAdapter {
    public static final String TABLE_CATEGORY ="category";
    public static final String COL_ID ="_id";
    public static final String COL_LIBELLE ="libelle";
    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;

    public CategorySqlLiteAdapter(Context context) {
        this.helper = new iiaSqlLiteOpenHelper(context, iiaSqlLiteOpenHelper.DB_NAME,null,1);
    }

    public static String getSchema() {
        return "CREATE TABLE" + TABLE_CATEGORY + "(" + COL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_LIBELLE + "TEXT NOT NULL);";
    }

    public void open() {this.db = this.helper.getWritableDatabase();}

    public void close() {this.db.close();}

    public long insert(Category category) {

        ContentValues values = this.userToContentValues(category);
        return db.insert(TABLE_CATEGORY, null, values);
    }

    public long delete(int id) {

        String whereClauseDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(id)};
        return db.delete(TABLE_CATEGORY, whereClauseDelete, whereArgsDelete);
    }

    public long update (Category category) {

        String whereClauseUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate = {String.valueOf(category.getId())};
        ContentValues values = this.userToContentValues(category);
        return db.update(TABLE_CATEGORY, values, whereClauseUpdate, whereArgsUpdate);

    }


    public Category getCategory(int id) {
        String[] cols = {COL_ID,COL_LIBELLE};
        String whereClauses = COL_ID + "= ?";
        String[] whereArgs = {String.valueOf(id)};
        Cursor c = db.query(TABLE_CATEGORY, cols, whereClauses, whereArgs, null, null, null);
        Category category = null;

        if (c.getCount() > 0) {

            c.moveToFirst();
            category = cursorToItem(c);
        }
        return category;

    }

    public ArrayList<Category> getCategories() {
        Cursor c = this.getAllCursor();
        ArrayList<Category> result = null;

        if(c.getCount() > 0) {
            c.moveToFirst();
            do {
                cursorToItem(c);
                result.add(cursorToItem(c));
            }while(c.moveToNext());
        }
        c.close();
        return result;
    }

    public Cursor getAllCursor(){

        String[] cols = {COL_ID,COL_LIBELLE};
        Cursor c = db.query(TABLE_CATEGORY,cols,null,null,null,null,null);
        return c;
    }

    public static Category cursorToItem(Cursor c) {

        Category category = new Category(0,null);
        category.setId(c.getInt(c.getColumnIndex(COL_ID)));
        category.setLibelle(c.getString(c.getColumnIndex(COL_LIBELLE)));

        return category;
    }

    private ContentValues userToContentValues(Category category) {
        ContentValues values = new ContentValues();
        values.put(COL_LIBELLE,category.getLibelle());
        return values;

    }

}
