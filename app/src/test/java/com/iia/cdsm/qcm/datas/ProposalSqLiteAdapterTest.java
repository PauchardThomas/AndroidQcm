package com.iia.cdsm.qcm.datas;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.AndroidTestCase;

import com.iia.cdsm.qcm.Data.iiaSqlLiteOpenHelper;

import org.junit.Test;

import java.io.IOException;

/**
 * Created by Thom' on 29/06/2016.
 */
public class ProposalSqLiteAdapterTest extends AndroidTestCase {
    /**
     * Proposal Table
     */
    public static final String TABLE_PROPOSAL = "proposal";
    /**
     * Column id
     */
    public static final String COL_ID = "_id";
    /**
     * Column libelle
     */
    public static final String COL_LIBELLE = "libelle";
    /**
     * Column isAnswer
     */
    public static final String COL_IS_ANSWER = "isAnswer";
    /**
     * Column Server ID
     */
    public static final String COL_ID_SERVER = "idServer";


    public static long mId;
    public static String mLibelle;
    public static int mIsAnswer;
    public static int mIdServer;

    /**
     * Test if proposal is inserted
     * @throws IOException
     */
    @Test
    public void insertTest() throws IOException {

        iiaSqlLiteOpenHelper helper = new iiaSqlLiteOpenHelper(mContext, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        mLibelle = "libelleTest";
        mIsAnswer = 1;
        mIdServer = 100;

        values.put(COL_LIBELLE,mLibelle);
        values.put(COL_IS_ANSWER,mIsAnswer);
        values.put(COL_ID_SERVER,mIdServer);

        mId = db.insert(TABLE_PROPOSAL, null, values);
        assertTrue(mId != -1);
    }

    /**
     * Test select proposal from database
     */
    @Test
    public void getProposalTest() {
        iiaSqlLiteOpenHelper helper = new iiaSqlLiteOpenHelper(mContext, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query(TABLE_PROPOSAL, null, null, null, null, null, null);
        assertTrue(c.moveToFirst());

        int dbId            = c.getInt(c.getColumnIndex(COL_ID));
        String dbLibelle    = c.getString(c.getColumnIndex(COL_LIBELLE));
        int dbIsAnswer      = c.getInt(c.getColumnIndex(COL_IS_ANSWER));
        int dbIdServer      = c.getInt(c.getColumnIndex(COL_ID_SERVER));

        assertEquals(mId,dbId);
        assertEquals(mLibelle,dbLibelle);
        assertEquals(mIsAnswer,dbIsAnswer);
        assertEquals(mIdServer,dbIdServer);

    }
}
