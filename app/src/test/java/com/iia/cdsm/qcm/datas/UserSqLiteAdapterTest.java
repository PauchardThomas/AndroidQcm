package com.iia.cdsm.qcm.datas;

import android.content.ContentValues;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestRunner;


import com.iia.cdsm.qcm.Data.iiaSqlLiteOpenHelper;


import org.junit.Test;

import java.io.IOException;



/**
 * Created by Thom' on 23/04/2016.
 */
public class UserSqLiteAdapterTest extends AndroidTestCase {

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

    public static long mId;
    public static String mUsername;
    public static String mPassword;
    public static int mIdServer;


    /**
     * Test if user is inserted
     *
     * @throws IOException
     */
    @Test
    public void insertTest() throws IOException {

        iiaSqlLiteOpenHelper helper = new iiaSqlLiteOpenHelper(mContext, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        mUsername = "testUsername";
        mPassword = "testPassword";
        mIdServer = 100;

        values.put(COL_USERNAME,mUsername);
        values.put(COL_PASSWORD,mPassword);
        values.put(COL_ID_SERVER,mIdServer);

        mId = db.insert(TABLE_USER, null, values);
        assertTrue(mId != -1);
    }

    /**
     * Test select user from database
     */
    @Test
    public void getUserTest() {
        iiaSqlLiteOpenHelper helper = new iiaSqlLiteOpenHelper(mContext, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query(TABLE_USER, null, null, null, null, null, null);
        assertTrue(c.moveToFirst());

        int dbId          = c.getInt(c.getColumnIndex(COL_ID));
        String dbUsername = c.getString(c.getColumnIndex(COL_USERNAME));
        String dbPassword = c.getString(c.getColumnIndex(COL_PASSWORD));
        int dbIdServer    = c.getInt(c.getColumnIndex(COL_ID_SERVER));

        assertEquals(mId,dbId);
        assertEquals(mUsername,dbUsername);
        assertEquals(mPassword,dbPassword);
        assertEquals(mIdServer,dbIdServer);

    }

}

