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

    public static final String TABLE_PROPOSAL_USER = "proposal_User";
    public static final String COL_ID_USER = "id_user";
    public static final String COL_ID_QCM ="id_qcm";
    public static final String COL_ID_QUESTION = "id_question";
    public static final String COL_ID_PROPOSAL = "id_proposal";

    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;

    public ProposalUserSqLiteAdapter(Context context) {
        this.helper = new iiaSqlLiteOpenHelper(context, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
    }

    public static String getSchema() {
        return "CREATE TABLE " + TABLE_PROPOSAL_USER + "(" + COL_ID_USER + " INTEGER NOT NULL," +
                COL_ID_QUESTION + " INTEGER NOT NULL," + COL_ID_PROPOSAL + " INTEGER NOT NULL," +
                COL_ID_QCM + " INTEGER NOT NULL," +
                "PRIMARY KEY ("+COL_ID_USER +","+COL_ID_QUESTION+","+COL_ID_PROPOSAL+","+COL_ID_QCM+
                "), FOREIGN KEY (`" + COL_ID_USER + "`) REFERENCES `user` (`_id`)" +
                ", FOREIGN KEY (`"+COL_ID_QUESTION+"`) REFERENCES `question`(`_id`)" +
                ", FOREIGN KEY (`"+COL_ID_PROPOSAL+"`) REFERENCES `proposal`(`_id`)" +
                ", FOREIGN KEY (`"+COL_ID_QCM+"`) REFERENCES `qcm`(`_id`));";
    }

    public void open() {
        this.db = this.helper.getWritableDatabase();
    }

    public void close() {
        this.db.close();
    }

    public long insert(ProposalUser proposaluser) {

        ContentValues values = this.proposalUserToContentValues(proposaluser);
        return db.insert(TABLE_PROPOSAL_USER, null, values);
    }

    public long delete(long id_user,int id_proposal) {

        String whereClauseDelete = COL_ID_USER + "= ? AND " + COL_ID_PROPOSAL + "= ?";
        String[] whereArgsDelete = {String.valueOf(id_user),String.valueOf(id_proposal)};
        return db.delete(TABLE_PROPOSAL_USER, whereClauseDelete, whereArgsDelete);
    }

//    public long update(Proposal proposal) {
//
//        String whereClauseUpdate = COL_ID + "= ?";
//        String[] whereArgsUpdate = {String.valueOf(proposal.getId())};
//        ContentValues values = this.proposalToContentValues(proposal);
//        return db.update(TABLE_PROPOSAL, values, whereClauseUpdate, whereArgsUpdate);
//
//    }


//    public Proposal getProposal(int id) {
//        String[] cols = {COL_ID_USER,COL_ID_QUESTION,COL_ID_PROPOSAL};
//        String whereClauses = COL_ID + "= ?";
//        String[] whereArgs = {String.valueOf(id)};
//        Cursor c = db.query(TABLE_PROPOSAL, cols, whereClauses, whereArgs, null, null, null);
//        Proposal proposal = null;
//
//        if (c.getCount() > 0) {
//
//            c.moveToFirst();
//            proposal = cursorToItem(c);
//        }
//        return proposal;
//
//    }

    public ArrayList<ProposalUser> getProposalsUser(long id_user,int id_qcm) {
        Cursor c = this.getAllCursor(id_user,id_qcm);
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

    public ArrayList<ProposalUser> getProposalsUserQuestion(long id_user,int id_question) {
        Cursor c = this.getCursorsQuestion(id_user,id_question);
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

    public Cursor getAllCursor(long id_user,int id_qcm) {

        String[] cols = {COL_ID_USER,COL_ID_QCM,COL_ID_PROPOSAL,COL_ID_QUESTION};
        String whereClauses = COL_ID_USER + "= ? AND " + COL_ID_QCM + "= ?";
        String[] whereArgs = {String.valueOf(id_user),String.valueOf(id_qcm)};
        Cursor c = db.query(TABLE_PROPOSAL_USER, cols, whereClauses, whereArgs, null, null, null);
        return c;
    }

    public Cursor getCursorsQuestion(long id_user,int id_question) {

        String[] cols = {COL_ID_USER,COL_ID_QCM,COL_ID_PROPOSAL,COL_ID_QUESTION};
        String whereClauses = COL_ID_USER + " = ? AND " + COL_ID_QUESTION + " = ?";
        String[] whereArgs = {String.valueOf(id_user),String.valueOf(id_question)};
        Cursor c = db.query(TABLE_PROPOSAL_USER, cols, whereClauses, whereArgs, null, null, null);
        return c;
    }

    public static ProposalUser cursorToItem(Cursor c) {

        ProposalUser proposalUser = new ProposalUser();

        proposalUser.setId_user(c.getInt(c.getColumnIndex(COL_ID_USER)));
        proposalUser.setId_qcm(c.getInt(c.getColumnIndex(COL_ID_QCM)));
        proposalUser.setId_question(c.getInt(c.getColumnIndex(COL_ID_QUESTION)));
        proposalUser.setId_proposal(c.getInt(c.getColumnIndex(COL_ID_PROPOSAL)));

        return proposalUser;
    }

    private ContentValues proposalUserToContentValues(ProposalUser proposalUser) {


        ContentValues values = new ContentValues();
        values.put(COL_ID_USER, proposalUser.getId_user());
        values.put(COL_ID_QCM, proposalUser.getId_qcm());
        values.put(COL_ID_QUESTION, proposalUser.getId_question());
        values.put(COL_ID_PROPOSAL, proposalUser.getId_proposal());
        return values;

    }

}
