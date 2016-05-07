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
    /**
     * Table Category
     */
    public static final String TABLE_CATEGORY = "category";
    /**
     * Column id
     */
    public static final String COL_ID = "_id";
    /**
     * Column libelle
     */
    public static final String COL_LIBELLE = "libelle";
    /**
     * Column idServer
     */
    private static final String COL_ID_SERVER = "idServer";
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
     * @param context Activity context
     */
    public CategorySqlLiteAdapter(Context context) {
        this.helper = new iiaSqlLiteOpenHelper(context, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
    }


    /**
     * Get Category database schema
     *
     * @return Category schema database
     */
    public static String getSchema() {
        return "CREATE TABLE " + TABLE_CATEGORY + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_LIBELLE + " TEXT NOT NULL," + COL_ID_SERVER + " INTEGER NOT NULL);";
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
     * Insert Category
     *
     * @param category Category
     * @return Category id inserted
     */
    public long insert(Category category) {

        ContentValues values = this.categoryToContentValues(category);
        return db.insert(TABLE_CATEGORY, null, values);
    }

    /**
     * Delete Category from database
     *
     * @param id category id
     * @return id deleted
     */
    public long delete(int id) {

        String whereClauseDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(id)};
        return db.delete(TABLE_CATEGORY, whereClauseDelete, whereArgsDelete);
    }

    /**
     * Update Category from database
     *
     * @param category Category
     * @return id updated
     */
    public long update(Category category) {

        String whereClauseUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate = {String.valueOf(category.getId())};
        ContentValues values = this.categoryToContentValues(category);
        return db.update(TABLE_CATEGORY, values, whereClauseUpdate, whereArgsUpdate);

    }


    /**
     * Get only one Category from database
     *
     * @param id category id
     * @return Category
     */
    public Category getCategory(int id) {
        String[] cols = {COL_ID, COL_LIBELLE, COL_ID_SERVER};
        String whereClauses = COL_ID_SERVER + "= ?";
        String[] whereArgs = {String.valueOf(id)};
        Cursor c = db.query(TABLE_CATEGORY, cols, whereClauses, whereArgs, null, null, null);
        Category category = null;

        if (c.getCount() > 0) {

            category = new Category();
            c.moveToFirst();
            category = cursorToItem(c);
        }
        return category;

    }

    /**
     * Get all categories from database
     *
     * @return List of categories
     */
    public ArrayList<Category> getCategories() {
        Cursor c = this.getAllCursor();
        ArrayList<Category> result = new ArrayList<>();

        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                cursorToItem(c);
                Category cat = cursorToItem(c);
                result.add(cat);
            } while (c.moveToNext());
        }
        c.close();
        return result;
    }

    /**
     * Get all Cursor from Category Column
     *
     * @return Category cursors
     */
    public Cursor getAllCursor() {

        String[] cols = {COL_ID, COL_LIBELLE, COL_ID_SERVER};
        Cursor c = db.query(TABLE_CATEGORY, cols, null, null, null, null, null);
        return c;
    }

    /**
     * Convert cursor to Category
     *
     * @param c Cursor
     * @return Category
     */
    public static Category cursorToItem(Cursor c) {

        Category category = new Category(0, null, 0);
        category.setId(c.getInt(c.getColumnIndex(COL_ID)));
        category.setLibelle(c.getString(c.getColumnIndex(COL_LIBELLE)));
        category.setIdServer(c.getInt(c.getColumnIndex(COL_ID_SERVER)));

        return category;
    }

    /**
     * Convert category to ContentValues before inserting
     *
     * @param category Category
     * @return ContentValues to insert
     */
    private ContentValues categoryToContentValues(Category category) {
        ContentValues values = new ContentValues();
        values.put(COL_LIBELLE, category.getLibelle());
        values.put(COL_ID_SERVER, category.getIdServer());
        return values;

    }

}
