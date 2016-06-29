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
public class ProposalUserSqLiteAdapterTest extends AndroidTestCase {
    /**
     * Table Proposal User
     */
    public static final String TABLE_PROPOSAL_USER = "proposal_User";
    /**
     * User id
     */
    public static final String COL_ID_USER = "id_user";
    /**
     * Qcm id
     */
    public static final String COL_ID_QCM = "id_qcm";
    /**
     * Question id
     */
    public static final String COL_ID_QUESTION = "id_question";
    /**
     * Proposal id
     */
    public static final String COL_ID_PROPOSAL = "id_proposal";

    public static long mId;
    public static int mUser;
    public static int mQcm;
    public static int mProposal;
    public static int mQuestion;


    /**
     * Test if user answer is inserted
     *
     * @throws IOException
     */
    @Test
    public void insertTest() throws IOException {

        iiaSqlLiteOpenHelper helper = new iiaSqlLiteOpenHelper(mContext, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        mUser = 1;
        mQcm = 2;
        mQuestion =3;
        mProposal = 4;

        values.put(COL_ID_USER,mUser);
        values.put(COL_ID_QCM,mQcm);
        values.put(COL_ID_QUESTION,mQuestion);
        values.put(COL_ID_PROPOSAL,mProposal);

        mId = db.insert(TABLE_PROPOSAL_USER, null, values);
        assertTrue(mId != -1);
    }

    /**
     * Test select user answer from database
     */
    @Test
    public void getProposalUserTest() {
        iiaSqlLiteOpenHelper helper = new iiaSqlLiteOpenHelper(mContext, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query(TABLE_PROPOSAL_USER, null, null, null, null, null, null);
        assertTrue(c.moveToFirst());

        int dbUser          = c.getInt(c.getColumnIndex(COL_ID_USER));
        String dbQcm = c.getString(c.getColumnIndex(COL_ID_QCM));
        String dbQuestion = c.getString(c.getColumnIndex(COL_ID_QUESTION));
        int dbProposal    = c.getInt(c.getColumnIndex(COL_ID_PROPOSAL));

        assertEquals(mUser,dbUser);
        assertEquals(mQcm,dbQcm);
        assertEquals(mQuestion,dbQuestion);
        assertEquals(mProposal,dbProposal);

    }
}
