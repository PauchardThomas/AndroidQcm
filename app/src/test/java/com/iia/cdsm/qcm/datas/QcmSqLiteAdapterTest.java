package com.iia.cdsm.qcm.datas;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.iia.cdsm.qcm.Data.iiaSqlLiteOpenHelper;

import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Thom' on 29/06/2016.
 */
public class QcmSqLiteAdapterTest extends AndroidTestCase {
    /**
     * Qcm Table
     */
    public static final String TABLE_QCM = "qcm";
    /**
     * Column id
     */
    public static final String COL_ID = "_id";
    /**
     * Column libelle
     */
    public static final String COL_LIBELLE = "libelle";
    /**
     * Column publication date
     */
    public static final String COL_DATE_PUBLI = "date_publi";
    /**
     * Column publication end date
     */
    public static final String COL_DATE_FIN = "date_fin";
    /**
     * Column number of points
     */
    public static final String COL_NB_POINTS = "nb_points";
    /**
     * Column server ID
     */
    public static final String COL_ID_SERVER = "idServer";
    /**
     * Column duration
     */
    public static final String COL_DURATION = "duration";
    /**
     * Column Category ID
     */
    public static final String COL_CATEGORY_ID = "category_id";

    public static long mId;
    public static String mLibelle;
    public static int mPoints;
    public static int mIdServer;

    /**
     * test insert qcm
     * @throws IOException
     */
    @Test
    public void insertTest() throws IOException {

        iiaSqlLiteOpenHelper helper = new iiaSqlLiteOpenHelper(mContext, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        mLibelle = "libelleTest";
        mPoints = 20;
        mIdServer = 100;

        values.put(COL_LIBELLE,mLibelle);
        values.put(COL_NB_POINTS,mPoints);
        values.put(COL_ID_SERVER,mIdServer);

        mId = db.insert(TABLE_QCM, null, values);
        assertTrue(mId != -1);
    }

    /**
     * Test select qcm from database
     */
    @Test
    public void getQcmTest() {
        iiaSqlLiteOpenHelper helper = new iiaSqlLiteOpenHelper(mContext, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query(TABLE_QCM, null, null, null, null, null, null);
        assertTrue(c.moveToFirst());

        int dbId            = c.getInt(c.getColumnIndex(COL_ID));
        String dbLibelle    = c.getString(c.getColumnIndex(COL_LIBELLE));
        int dbPoints        = c.getInt(c.getColumnIndex(COL_NB_POINTS));
        int dbIdServer      = c.getInt(c.getColumnIndex(COL_ID_SERVER));

        assertEquals(mId,dbId);
        assertEquals(mLibelle,dbLibelle);
        assertEquals(mPoints,dbPoints);
        assertEquals(mIdServer,dbIdServer);

    }
}
