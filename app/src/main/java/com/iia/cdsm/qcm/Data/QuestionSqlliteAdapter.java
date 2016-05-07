package com.iia.cdsm.qcm.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iia.cdsm.qcm.Entity.Proposal;
import com.iia.cdsm.qcm.Entity.Qcm;
import com.iia.cdsm.qcm.Entity.Question;
import com.iia.cdsm.qcm.View.QcmActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Thom' on 13/02/2016.
 */
public class QuestionSqlliteAdapter {

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
    /**
     * Column Qcm id
     */
    public static final String COL_QCM_ID = "qcm_id";
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
    public QuestionSqlliteAdapter(Context context) {
        this.helper = new iiaSqlLiteOpenHelper(context, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
    }

    /**
     * Get Question database schema
     *
     * @return Question schema
     */
    public static String getSchema() {
        return "CREATE TABLE " + TABLE_QUESTION + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_LIBELLE + " TEXT NOT NULL," + COL_POINTS + " int NOT NULL,"
                + COL_ID_SERVER + " INTEGER NOT NULL,"
                + COL_QCM_ID
                + " int  NOT NULL,FOREIGN KEY (`" + COL_QCM_ID + "`) REFERENCES `qcm` (`_id`));";
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
     * Insert Question
     *
     * @param question Question
     * @return question id inserted
     */
    public int insert(Question question) {

        ContentValues values = this.questionToContentValues(question);
        return (int) db.insert(TABLE_QUESTION, null, values);
    }

    /**
     * Delete Question
     *
     * @param id question id
     * @return Question id deleted
     */
    public long delete(int id) {

        String whereClauseDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(id)};
        return db.delete(TABLE_QUESTION, whereClauseDelete, whereArgsDelete);
    }

    /**
     * Update Question
     *
     * @param question Question
     * @return Question id updated
     */
    public long update(Question question) {

        String whereClauseUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate = {String.valueOf(question.getId())};
        ContentValues values = this.questionToContentValues(question);
        return db.update(TABLE_QUESTION, values, whereClauseUpdate, whereArgsUpdate);

    }

    /**
     * Get only one question
     *
     * @param id_server id_server
     * @return Question selected
     */
    public Question getQuestion(int id_server) {
        String[] cols = {COL_ID, COL_LIBELLE, COL_POINTS, COL_ID_SERVER, COL_QCM_ID};
        String whereClauses = COL_ID_SERVER + "= ?";
        String[] whereArgs = {String.valueOf(id_server)};
        Cursor c = db.query(TABLE_QUESTION, cols, whereClauses, whereArgs, null, null, null);
        Question question = null;

        if (c.getCount() > 0) {

            question = new Question();
            c.moveToFirst();
            question = cursorToItems(c);
        }
        return question;

    }

    /**
     * Get all questions
     *
     * @param id      id
     * @param context context
     * @return List of questions
     */
    public ArrayList<Question> getQuestions(int id, Context context) {
        Cursor c = this.getAllCursor(id);
        ArrayList<Question> result = new ArrayList<>();

        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                cursorToItem(c, context);
                result.add(cursorToItem(c, context));
            } while (c.moveToNext());
        }
        c.close();
        return result;
    }

    /**
     * Get all cursors
     *
     * @param id Qcm id
     * @return all cursors
     */
    public Cursor getAllCursor(int id) {

        String[] cols = {COL_ID, COL_LIBELLE, COL_POINTS, COL_ID_SERVER, COL_QCM_ID};
        String whereClauses = COL_QCM_ID + "= ?";
        String[] whereArgs = {String.valueOf(id)};
        Cursor c = db.query(TABLE_QUESTION, cols, whereClauses, whereArgs, null, null, null);
        return c;
    }

    /**
     * Convert cursor to Question
     *
     * @param c       cursor
     * @param context context
     * @return Question
     */
    public static Question cursorToItem(Cursor c, Context context) {

        Question question = new Question();
        ArrayList<Proposal> proposals = new ArrayList<Proposal>();
        ProposalSqlLiteAdapter proposalSqlLiteAdapter = new ProposalSqlLiteAdapter(context);
        question.setId(c.getInt(c.getColumnIndex(COL_ID)));
        question.setLibelle(c.getString(c.getColumnIndex(COL_LIBELLE)));
        question.setPoints(c.getInt(c.getColumnIndex(COL_POINTS)));
        question.setIdServer(c.getInt(c.getColumnIndex(COL_ID_SERVER)));
        Qcm qcm = new Qcm();
        qcm.setId(c.getInt(c.getColumnIndex(COL_QCM_ID)));
        question.setQcm(qcm);

        proposalSqlLiteAdapter.open();
        question.setProposals(proposalSqlLiteAdapter.getProposals(question.getId()));
        proposalSqlLiteAdapter.close();

        return question;
    }


    /**
     * Convert cursor to Item
     *
     * @param c cursor
     * @return Question
     */
    public static Question cursorToItems(Cursor c) {
        Question question = new Question();
        question.setId(c.getInt(c.getColumnIndex(COL_ID)));
        question.setLibelle(c.getString(c.getColumnIndex(COL_LIBELLE)));
        question.setPoints(c.getInt(c.getColumnIndex(COL_POINTS)));
        question.setIdServer(c.getInt(c.getColumnIndex(COL_ID_SERVER)));

        return question;
    }

    /**
     * Convert Question to ContentValues before inserting
     *
     * @param question Question
     * @return ConvertValues to insert
     */
    private ContentValues questionToContentValues(Question question) {


        ContentValues values = new ContentValues();
        values.put(COL_LIBELLE, question.getLibelle());
        values.put(COL_POINTS, question.getPoints());
        values.put(COL_ID_SERVER, question.getIdServer());
        values.put(COL_QCM_ID, question.getQcm().getId());
        return values;

    }


}
