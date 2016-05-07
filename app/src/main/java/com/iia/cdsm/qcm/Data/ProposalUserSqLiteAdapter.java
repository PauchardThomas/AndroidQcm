package com.iia.cdsm.qcm.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iia.cdsm.qcm.Entity.Proposal;
import com.iia.cdsm.qcm.Entity.ProposalUser;
import com.iia.cdsm.qcm.Entity.Question;

import java.util.ArrayList;

/**
 * Created by Thom' on 14/04/2016.
 */
public class ProposalUserSqLiteAdapter {

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

    /**
     * Database
     */
    private SQLiteDatabase db;
    /**
     * Helper
     */
    private SQLiteOpenHelper helper;

    /**
     * Helper constructor
     *
     * @param context activity context
     */
    public ProposalUserSqLiteAdapter(Context context) {
        this.helper = new iiaSqlLiteOpenHelper(context, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
    }

    /**
     * Get ProposalUser database schema
     *
     * @return ProposalUser schema
     */
    public static String getSchema() {
        return "CREATE TABLE " + TABLE_PROPOSAL_USER + "(" + COL_ID_USER + " INTEGER NOT NULL," +
                COL_ID_QUESTION + " INTEGER NOT NULL," + COL_ID_PROPOSAL + " INTEGER NOT NULL," +
                COL_ID_QCM + " INTEGER NOT NULL," +
                "PRIMARY KEY (" + COL_ID_USER + "," + COL_ID_QUESTION + "," + COL_ID_PROPOSAL + "," + COL_ID_QCM +
                "), FOREIGN KEY (`" + COL_ID_USER + "`) REFERENCES `user` (`_id`)" +
                ", FOREIGN KEY (`" + COL_ID_QUESTION + "`) REFERENCES `question`(`_id`)" +
                ", FOREIGN KEY (`" + COL_ID_PROPOSAL + "`) REFERENCES `proposal`(`_id`)" +
                ", FOREIGN KEY (`" + COL_ID_QCM + "`) REFERENCES `qcm`(`_id`));";
    }

    /**
     * Get database writable
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
     * Insert User's proposals
     *
     * @param proposaluser User's proposal
     * @return ProposalUser id inserted
     */
    public long insert(ProposalUser proposaluser) {

        ContentValues values = this.proposalUserToContentValues(proposaluser);
        return db.insert(TABLE_PROPOSAL_USER, null, values);
    }

    /**
     * Delete User's proposals
     *
     * @param id_user     user id
     * @param id_proposal proposal id
     * @return ProposalUser id deleted
     */
    public long delete(long id_user, int id_proposal) {

        String whereClauseDelete = COL_ID_USER + "= ? AND " + COL_ID_PROPOSAL + "= ?";
        String[] whereArgsDelete = {String.valueOf(id_user), String.valueOf(id_proposal)};
        return db.delete(TABLE_PROPOSAL_USER, whereClauseDelete, whereArgsDelete);
    }


    /**
     * Get User's proposals by user  and qcm
     *
     * @param id_user user id
     * @param id_qcm  qcm id
     * @return List of User's proposals
     */
    public ArrayList<ProposalUser> getProposalsUser(long id_user, int id_qcm) {
        Cursor c = this.getAllCursor(id_user, id_qcm);
        ArrayList<ProposalUser> result = new ArrayList<>();

        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                cursorToItem(c);
                result.add(cursorToItem(c));
            } while (c.moveToNext());
        }
        c.close();
        return result;
    }

    /**
     * Get User's proposals by user and question
     *
     * @param id_user     user id
     * @param id_question question id
     * @return List of User's proposals
     */
    public ArrayList<ProposalUser> getProposalsUserQuestion(long id_user, int id_question) {
        Cursor c = this.getCursorsQuestion(id_user, id_question);
        ArrayList<ProposalUser> result = new ArrayList<>();

        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                cursorToItem(c);
                result.add(cursorToItem(c));
            } while (c.moveToNext());
        }
        c.close();
        return result;
    }

    /**
     * Get all cursors
     *
     * @param id_user user id
     * @param id_qcm  qcm id
     * @return All cursors
     */
    public Cursor getAllCursor(long id_user, int id_qcm) {

        String[] cols = {COL_ID_USER, COL_ID_QCM, COL_ID_PROPOSAL, COL_ID_QUESTION};
        String whereClauses = COL_ID_USER + "= ? AND " + COL_ID_QCM + "= ?";
        String[] whereArgs = {String.valueOf(id_user), String.valueOf(id_qcm)};
        Cursor c = db.query(TABLE_PROPOSAL_USER, cols, whereClauses, whereArgs, null, null, null);
        return c;
    }

    /**
     * Get all Cursors ProposalUsers
     *
     * @param id_user     user id
     * @param id_question question id
     * @return ProposalUser curosr
     */
    public Cursor getCursorsQuestion(long id_user, int id_question) {

        String[] cols = {COL_ID_USER, COL_ID_QCM, COL_ID_PROPOSAL, COL_ID_QUESTION};
        String whereClauses = COL_ID_USER + " = ? AND " + COL_ID_QUESTION + " = ?";
        String[] whereArgs = {String.valueOf(id_user), String.valueOf(id_question)};
        Cursor c = db.query(TABLE_PROPOSAL_USER, cols, whereClauses, whereArgs, null, null, null);
        return c;
    }

    /**
     * Convert Cursor to ProposalUser
     *
     * @param c cursor
     * @return User's proposals
     */
    public static ProposalUser cursorToItem(Cursor c) {

        ProposalUser proposalUser = new ProposalUser();

        proposalUser.setId_user(c.getInt(c.getColumnIndex(COL_ID_USER)));
        proposalUser.setId_qcm(c.getInt(c.getColumnIndex(COL_ID_QCM)));
        proposalUser.setId_question(c.getInt(c.getColumnIndex(COL_ID_QUESTION)));
        proposalUser.setId_proposal(c.getInt(c.getColumnIndex(COL_ID_PROPOSAL)));

        return proposalUser;
    }

    /**
     * Convert ProposalUser to Content Values before inserting
     *
     * @param proposalUser ProposalUser
     * @return Content Values to insert
     */
    private ContentValues proposalUserToContentValues(ProposalUser proposalUser) {


        ContentValues values = new ContentValues();
        values.put(COL_ID_USER, proposalUser.getId_user());
        values.put(COL_ID_QCM, proposalUser.getId_qcm());
        values.put(COL_ID_QUESTION, proposalUser.getId_question());
        values.put(COL_ID_PROPOSAL, proposalUser.getId_proposal());
        return values;

    }

}
