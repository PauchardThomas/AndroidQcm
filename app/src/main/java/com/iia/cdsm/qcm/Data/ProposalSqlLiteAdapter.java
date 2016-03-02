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

    public static final String TABLE_PROPOSAL = "proposal";
    public static final String COL_ID = "_id";
    public static final String COL_LIBELLE ="libelle";
    public static final String COL_IS_ANSWER ="isAnswer";
    public static final String COL_QUESTION_ID = "question_id";
    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;

    public ProposalSqlLiteAdapter(Context context) {
        this.helper = new iiaSqlLiteOpenHelper(context, iiaSqlLiteOpenHelper.DB_NAME,null,1);
    }

    public static String getSchema() {
        return "CREATE TABLE" + TABLE_PROPOSAL + "(" + COL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_LIBELLE + "TEXT NOT NULL,"+COL_IS_ANSWER+"BOOLEAN NOT NULL,"+
                COL_QUESTION_ID+"int DEFAULT NOT NULL,FOREIGN KEY (`"+COL_QUESTION_ID+"`) REFERENCES `question` (`_id`));";
    }

    public void open() {this.db = this.helper.getWritableDatabase();}

    public void close() {this.db.close();}

    public long insert(Proposal proposal) {

        ContentValues values = this.proposalToContentValues(proposal);
        return db.insert(TABLE_PROPOSAL, null, values);
    }

    public long delete(int id) {

        String whereClauseDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(id)};
        return db.delete(TABLE_PROPOSAL, whereClauseDelete, whereArgsDelete);
    }

    public long update (Proposal proposal) {

        String whereClauseUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate = {String.valueOf(proposal.getId())};
        ContentValues values = this.proposalToContentValues(proposal);
        return db.update(TABLE_PROPOSAL, values, whereClauseUpdate, whereArgsUpdate);

    }


    public Proposal getProposal(int id) {
        String[] cols = {COL_ID,COL_LIBELLE,COL_IS_ANSWER,COL_QUESTION_ID};
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

    public ArrayList<Proposal> getProposals() {
        Cursor c = this.getAllCursor();
        ArrayList<Proposal> result = null;

        if(c.getCount() > 0) {
            c.moveToFirst();
            do {
                cursorToItem(c);
                result.add(cursorToItem(c));
            }while(c.moveToNext());
        }
        c.close();
        return result;
    }

    public Cursor getAllCursor(){

        String[] cols = {COL_ID,COL_LIBELLE,COL_IS_ANSWER,COL_QUESTION_ID};
        Cursor c = db.query(TABLE_PROPOSAL,cols,null,null,null,null,null);
        return c;
    }

    public static Proposal cursorToItem(Cursor c) {

        Proposal proposal = new Proposal(0,null,false,null);
        proposal.setId(c.getInt(c.getColumnIndex(COL_ID)));
        proposal.setLibelle(c.getString(c.getColumnIndex(COL_LIBELLE)));

       int isAnswer =c.getInt(c.getColumnIndex(COL_IS_ANSWER));
        boolean answer  = false;
        if(isAnswer != 0) {
            answer = true;
        }
        proposal.setIsAnswer(answer);
        Question question = null;
        question.setId(c.getInt(c.getColumnIndex(COL_QUESTION_ID)));
        proposal.setQuestion(question);



        return proposal;
    }

    private ContentValues proposalToContentValues(Proposal proposal) {


        ContentValues values = new ContentValues();
        values.put(COL_LIBELLE,proposal.getLibelle());
        values.put(COL_IS_ANSWER,proposal.isAnswer());

        values.put(COL_QUESTION_ID, proposal.getQuestion().getId());
        return values;

    }


}
