package com.iia.cdsm.qcm.datas;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.AndroidTestCase;

import com.iia.cdsm.qcm.Data.iiaSqlLiteOpenHelper;

import org.junit.Test;


/**
 * Created by Thom' on 29/06/2016.
 */
public class DBTest extends AndroidTestCase{

    public static final String DB_NAME = "qcm.sqllite";

    /**
     * Test drop database
     */
    @Test
    public void dropDbTest() {
        assertTrue(mContext.deleteDatabase(DB_NAME));
    }

    /**
     * Test create database
     */
    @Test
    public void createDbTest() {
        iiaSqlLiteOpenHelper helper = new iiaSqlLiteOpenHelper(mContext, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        assertTrue(db.isOpen());
        db.close();
    }

}
