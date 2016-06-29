package com.iia.cdsm.qcm.datas;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.iia.cdsm.qcm.Data.iiaSqlLiteOpenHelper;

import org.junit.Test;

import java.io.IOException;

/**
 * Created by Thom' on 29/06/2016.
 */
public class AccessUserCategorySqLiteAdapterTest extends AndroidTestCase {

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

    public static long mId;
    public static int mUser;
    public static int mCategory;



    /**
     * Test if user access category is inserted
     *
     * @throws IOException
     */
    @Test
    public void insertTest() throws IOException {

        iiaSqlLiteOpenHelper helper = new iiaSqlLiteOpenHelper(mContext, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        mUser = 1;
        mCategory = 2;

        values.put(COL_USER_ID,mUser);
        values.put(COL_CATEGORY_ID,mCategory);


        mId = db.insert(TABLE_ACCESS_USER_CATEGORY, null, values);
        assertTrue(mId != -1);
    }

    /**
     * Test get access user category from database.
     */
    @Test
    public void getAccessCategoryUserTest() {
        iiaSqlLiteOpenHelper helper = new iiaSqlLiteOpenHelper(mContext, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query(TABLE_ACCESS_USER_CATEGORY, null, null, null, null, null, null);
        assertTrue(c.moveToFirst());

        int dbUser          = c.getInt(c.getColumnIndex(COL_USER_ID));
        int dbCategory = c.getInt(c.getColumnIndex(COL_CATEGORY_ID));


        assertEquals(mUser,dbUser);
        assertEquals(mCategory,dbCategory);

    }
}
