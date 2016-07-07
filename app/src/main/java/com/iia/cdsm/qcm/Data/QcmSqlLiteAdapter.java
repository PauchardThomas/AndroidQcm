package com.iia.cdsm.qcm.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iia.cdsm.qcm.Entity.Category;
import com.iia.cdsm.qcm.Entity.Qcm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;


/**
 * Created by Thom' on 13/02/2016.
 */
public class QcmSqlLiteAdapter {

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
    /**
     * Date publi format
     */
    private static final String DATE_PUBLI_FORMAT = "yyyy-MM-dd";
    /**
     * Duration format
     */
    private static final String DURATION_FORMAT = "yyyy-MM-dd'T'HH:mm:ss+SSSS";
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
    public QcmSqlLiteAdapter(Context context) {
        this.helper = new iiaSqlLiteOpenHelper(context, iiaSqlLiteOpenHelper.DB_NAME, null, 1);
    }

    /**
     * Get Qcm database schema
     *
     * @return Qcm schema
     */
    public static String getSchema() {
        return "CREATE TABLE " + TABLE_QCM + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_LIBELLE + " TEXT NOT NULL," + COL_NB_POINTS + " TEXT NOT NULL," + COL_DATE_PUBLI
                + " DATE," + COL_DATE_FIN + " DATE," + COL_DURATION + " DATE," + COL_ID_SERVER
                + " INTEGER NOT NULL," + COL_CATEGORY_ID
                + " int  NOT NULL,FOREIGN KEY (`" + COL_CATEGORY_ID + "`) REFERENCES `category` (`_id`));";
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
     * Insert Qcm
     *
     * @param qcm Qcm
     * @return qcm id inserted
     */
    public long insert(Qcm qcm) {

        ContentValues values = this.qcmToContentValues(qcm);
        return db.insert(TABLE_QCM, null, values);
    }

    /**
     * Delete Qcm
     *
     * @param id Qcm id
     * @return Qcm id deleted
     */
    public long delete(int id) {

        String whereClauseDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(id)};
        return db.delete(TABLE_QCM, whereClauseDelete, whereArgsDelete);
    }

    /**
     * Update Qcm
     *
     * @param qcm Qcm
     * @return Qcm id updated
     */
    public long update(Qcm qcm) {

        String whereClauseUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate = {String.valueOf(qcm.getId())};
        ContentValues values = this.qcmToContentValues(qcm);
        return db.update(TABLE_QCM, values, whereClauseUpdate, whereArgsUpdate);

    }


    /**
     * Get only one Qcm
     *
     * @param id Qcm id server
     * @return Qcm selected
     */
    public Qcm getQcm(int id) {
        String[] cols = {COL_ID, COL_LIBELLE, COL_NB_POINTS, COL_DATE_PUBLI, COL_DATE_FIN,
                COL_CATEGORY_ID, COL_DURATION, COL_ID_SERVER};
        String whereClauses = COL_ID_SERVER + "= ?";
        String[] whereArgs = {String.valueOf(id)};
        Cursor c = db.query(TABLE_QCM, cols, whereClauses, whereArgs, null, null, null);
        Qcm qcm = null;

        if (c.getCount() > 0) {

            qcm = new Qcm();
            c.moveToFirst();
            qcm = cursorToItem(c);
        }
        return qcm;

    }

    /**
     * Get only one Qcm
     *
     * @param id Qcm id
     * @return Qcm selected
     */
    public Qcm getQcmById(int id) {
        String[] cols = {COL_ID, COL_LIBELLE, COL_NB_POINTS, COL_DATE_PUBLI, COL_DATE_FIN,
                COL_CATEGORY_ID, COL_DURATION, COL_ID_SERVER};
        String whereClauses = COL_ID + "= ?";
        String[] whereArgs = {String.valueOf(id)};
        Cursor c = db.query(TABLE_QCM, cols, whereClauses, whereArgs, null, null, null);
        Qcm qcm = null;

        if (c.getCount() > 0) {

            qcm = new Qcm();
            c.moveToFirst();
            qcm = cursorToItem(c);
        }
        return qcm;

    }

    /**
     * Get all Qcm
     *
     * @return List of Qcm
     */
    public ArrayList<Qcm> getQcms() {
        Cursor c = this.getAllCursor();
        ArrayList<Qcm> result = new ArrayList<>();

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
     * @return all cursors
     */
    public Cursor getAllCursor() {

        String[] cols = {COL_ID, COL_LIBELLE, COL_NB_POINTS, COL_DATE_PUBLI, COL_DATE_FIN,
                COL_CATEGORY_ID, COL_DURATION, COL_ID_SERVER};
        Cursor c = db.query(TABLE_QCM, cols, null, null, null, null, null);
        return c;
    }

    /**
     * Convert cursor to Qcm
     *
     * @param c cursor
     * @return Qcm
     */
    public static Qcm cursorToItem(Cursor c) {

        Qcm qcm = new Qcm();
        Category category = new Category();

        qcm.setId(c.getInt(c.getColumnIndex(COL_ID)));
        qcm.setLibelle(c.getString(c.getColumnIndex(COL_LIBELLE)));
        qcm.setNbPoints(c.getInt(c.getColumnIndex(COL_NB_POINTS)));
        qcm.setIdServer(c.getInt(c.getColumnIndex(COL_ID_SERVER)));
        category.setId(c.getInt(c.getColumnIndex(COL_CATEGORY_ID)));
        qcm.setCategory(category);

        SimpleDateFormat formatterDatePubli = new SimpleDateFormat(DATE_PUBLI_FORMAT);
        SimpleDateFormat formatterDuration = new SimpleDateFormat(DURATION_FORMAT);
        String datepubliInString = c.getString(c.getColumnIndex(COL_DATE_PUBLI));
        String datefinInString = c.getString(c.getColumnIndex(COL_DATE_FIN));
        String duration = c.getString(c.getColumnIndex(COL_DURATION));
        try {

            Date datePubli = null;
            Date dateFin = null;
            Date dateDuration = null;
            if (datepubliInString != null) {
                datePubli = formatterDatePubli.parse(datepubliInString);
            }
            if (datefinInString != null) {
                dateFin = formatterDatePubli.parse(datefinInString);
            }
            if (duration != null) {
                dateDuration = formatterDuration.parse(duration);
            }

            qcm.setDatePubli(datePubli);
            qcm.setDateFin(dateFin);
            qcm.setDuration(dateDuration);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return qcm;
    }

    /**
     * Convert Qcm to Content Values before inserting
     *
     * @param qcm Qcm
     * @return Content Values to insert
     */
    private ContentValues qcmToContentValues(Qcm qcm) {

        String datePubli = null;
        String dateFin = null;
        String duration = null;
        SimpleDateFormat datePubliFormat = new SimpleDateFormat(DATE_PUBLI_FORMAT);
        SimpleDateFormat durationFormat = new SimpleDateFormat(DURATION_FORMAT);
        if (qcm.getDatePubli() != null) {
            datePubli = datePubliFormat.format(qcm.getDatePubli());
        }
        if (qcm.getDateFin() != null) {
            dateFin = datePubliFormat.format(qcm.getDateFin());
        }
        if (qcm.getDuration() != null) {
            duration = durationFormat.format(qcm.getDuration());
        }
        ContentValues values = new ContentValues();
        values.put(COL_LIBELLE, qcm.getLibelle());
        values.put(COL_NB_POINTS, qcm.getNbPoints());
        values.put(COL_DATE_PUBLI, datePubli);
        values.put(COL_DATE_FIN, dateFin);
        values.put(COL_DURATION, duration);
        values.put(COL_ID_SERVER, qcm.getIdServer());
        values.put(COL_CATEGORY_ID, qcm.getCategory().getId());
        return values;

    }

}
