package com.iia.cdsm.qcm.Data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iia.cdsm.qcm.Entity.Proposal;
import com.iia.cdsm.qcm.Entity.Question;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Thom' on 13/02/2016.
 */
public class ProposalSqlLiteAdapter {

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
    /**
     * Column Question ID
     */
    public static final String COL_QUESTION_ID = "question_id";
    /**
     * Database
     */
    private SQLiteDatabase db;
    /**
     * Helper
     */
    private SQLiteOpenHelper helper;

    /**
     * ProposalSqlLiteAdapter constructor
     *
     * @param context activity context
     */
    public ProposalSqlLiteAdapter(Context context) {
        this.helper = new iiaSqlLiteOpenHelper(context, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
    }

    /**
     * Get Proposal database schema
     *
     * @return database schema
     */
    public static String getSchema() {
        return "CREATE TABLE " + TABLE_PROPOSAL + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_LIBELLE + " TEXT NOT NULL," + COL_IS_ANSWER + " BOOLEAN NOT NULL," + COL_ID_SERVER + " INTEGER NOT NULL," +
                COL_QUESTION_ID + " int  NOT NULL,FOREIGN KEY (`" + COL_QUESTION_ID + "`) REFERENCES `question` (`_id`));";
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
     * Insert Proposal
     *
     * @param proposal Proposal
     * @return id inserted
     */
    public long insert(Proposal proposal) {

        ContentValues values = this.proposalToContentValues(proposal);
        return db.insert(TABLE_PROPOSAL, null, values);
    }

    /**
     * Delete Proposal
     *
     * @param id id to deleted
     * @return Proposal id deleted
     */
    public long delete(int id) {

        String whereClauseDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(id)};
        return db.delete(TABLE_PROPOSAL, whereClauseDelete, whereArgsDelete);
    }

    /**
     * Update Proposal
     *
     * @param proposal proposal to update
     * @return Proposal id updated
     */
    public long update(Proposal proposal) {

        String whereClauseUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate = {String.valueOf(proposal.getId())};
        ContentValues values = this.proposalToContentValues(proposal);
        return db.update(TABLE_PROPOSAL, values, whereClauseUpdate, whereArgsUpdate);

    }

    /**
     * Get only one Proposal
     *
     * @param id proposal id to get
     * @return Proposal to get
     */
    public Proposal getProposal(int id) {
        String[] cols = {COL_ID, COL_LIBELLE, COL_IS_ANSWER, COL_ID_SERVER, COL_QUESTION_ID};
        String whereClauses = COL_ID + "= ?";
        String[] whereArgs = {String.valueOf(id)};
        Cursor c = db.query(TABLE_PROPOSAL, cols, whereClauses, whereArgs, null, null, null);
        Proposal proposal = null;

        if (c.getCount() > 0) {

            c.moveToFirst();
            proposal = cursorToItem(c);
        }
        return proposal;

    }

    /**
     * Get all Proposals from a Question
     *
     * @param id_question Question id
     * @return List of proposals from a question
     */
    public ArrayList<Proposal> getProposals(int id_question) {
        Cursor c = this.getAllCursor(id_question);
        ArrayList<Proposal> result = new ArrayList<>();

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
     * Get all cursor from Proposal column
     *
     * @param id_question id_question
     * @return Proposal cursors
     */
    public Cursor getAllCursor(int id_question) {

        String[] cols = {COL_ID, COL_LIBELLE, COL_IS_ANSWER, COL_ID_SERVER, COL_QUESTION_ID};
        String whereClauses = COL_QUESTION_ID + "= ?";
        String[] whereArgs = {String.valueOf(id_question)};
        Cursor c = db.query(TABLE_PROPOSAL, cols, whereClauses, whereArgs, null, null, null);
        return c;
    }

    /**
     * Convert cursor to Proposal
     *
     * @param c cursor
     * @return Proposal
     */
    public static Proposal cursorToItem(Cursor c) {

        Proposal proposal = new Proposal();
        proposal.setId(c.getInt(c.getColumnIndex(COL_ID)));
        proposal.setLibelle(c.getString(c.getColumnIndex(COL_LIBELLE)));

        proposal.setIdServer(c.getInt(c.getColumnIndex(COL_ID_SERVER)));
        int isAnswer = c.getInt(c.getColumnIndex(COL_IS_ANSWER));
        boolean answer = false;
        if (isAnswer != 0) {
            answer = true;
        }
        proposal.setIsAnswer(answer);
        Question question = new Question();
        question.setId(c.getInt(c.getColumnIndex(COL_QUESTION_ID)));
        proposal.setQuestion(question);


        return proposal;
    }

    /**
     * Convert Proposal to ContentValues before inserting
     *
     * @param proposal Proposal
     * @return Content Values to inserting
     */
    private ContentValues proposalToContentValues(Proposal proposal) {


        ContentValues values = new ContentValues();
        values.put(COL_LIBELLE, proposal.getLibelle());
        values.put(COL_IS_ANSWER, proposal.isAnswer());
        values.put(COL_ID_SERVER, proposal.getIdServer());
        values.put(COL_QUESTION_ID, proposal.getQuestion().getId());
        return values;

    }


}
