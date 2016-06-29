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
public class QuestionSqLiteAdapterTest extends AndroidTestCase {

    /**
     * Table Question
     */
    public static final String TABLE_QUESTION = "question";
    /**
     * Column id
     */
    public static final String COL_ID = "_id";
    /**
     * Column libelle
     */
    public static final String COL_LIBELLE = "libelle";
    /**
     * Column points
     */
    public static final String COL_POINTS = "points";
    /**
     * Column id Server
     */
    public static final String COL_ID_SERVER = "idServer";



    public static long mId;
    public static String mLibelle;
    public static int mPoints;
    public static int mIdServer;

    /**
     * Test if question is inserted
     * @throws IOException
     */
    @Test
    public void insertTest() throws IOException {

        iiaSqlLiteOpenHelper helper = new iiaSqlLiteOpenHelper(mContext, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        mLibelle = "libelleTest";
        mPoints = 1;
        mIdServer = 100;

        values.put(COL_LIBELLE,mLibelle);
        values.put(COL_POINTS,mPoints);
        values.put(COL_ID_SERVER,mIdServer);

        mId = db.insert(TABLE_QUESTION, null, values);
        assertTrue(mId != -1);
    }

    /**
     * Test select question from database
     */
    @Test
    public void getQuestionTest() {
        iiaSqlLiteOpenHelper helper = new iiaSqlLiteOpenHelper(mContext, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query(TABLE_QUESTION, null, null, null, null, null, null);
        assertTrue(c.moveToFirst());

        int dbId            = c.getInt(c.getColumnIndex(COL_ID));
        String dbLibelle    = c.getString(c.getColumnIndex(COL_LIBELLE));
        int dbPoints        = c.getInt(c.getColumnIndex(COL_POINTS));
        int dbIdServer      = c.getInt(c.getColumnIndex(COL_ID_SERVER));

        assertEquals(mId,dbId);
        assertEquals(mLibelle,dbLibelle);
        assertEquals(mPoints,dbPoints);
        assertEquals(mIdServer,dbIdServer);

    }
}
