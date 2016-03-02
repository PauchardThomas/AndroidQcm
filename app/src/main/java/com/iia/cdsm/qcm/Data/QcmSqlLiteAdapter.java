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

    public static final String TABLE_QCM ="qcm";
    public static final String COL_ID ="_id";
    public static final String COL_LIBELLE ="libelle";
    public static final String COL_DATE_PUBLI = "date_publi";
    public static final String COL_DATE_FIN = "date_fin";
    public static final String COL_NB_POINTS ="nb_points";
    public static final String COL_CATEGORY_ID ="category_id";
    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;

    public QcmSqlLiteAdapter(Context context) {
        this.helper = new iiaSqlLiteOpenHelper(context, iiaSqlLiteOpenHelper.DB_NAME,null,1);
    }

    public static String getSchema() {
        return "CREATE TABLE" + TABLE_QCM + "(" + COL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_LIBELLE + "TEXT NOT NULL,"+COL_NB_POINTS+"TEXT NOT NULL,"+COL_DATE_PUBLI
                +"DATE NOT NULL,"+COL_DATE_FIN+"DATE NOT NULL,"+COL_CATEGORY_ID+"int DEFAULT NOT NULL,FOREIGN KEY (`"+COL_CATEGORY_ID+"`) REFERENCES `category` (`_id`));";
    }

    public void open() {this.db = this.helper.getWritableDatabase();}

    public void close() {this.db.close();}

    public long insert(Qcm qcm) {

        ContentValues values = this.qcmToContentValues(qcm);
        return db.insert(TABLE_QCM, null, values);
    }

    public long delete(int id) {

        String whereClauseDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(id)};
        return db.delete(TABLE_QCM, whereClauseDelete, whereArgsDelete);
    }

    public long update (Qcm qcm) {

        String whereClauseUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate = {String.valueOf(qcm.getId())};
        ContentValues values = this.qcmToContentValues(qcm);
        return db.update(TABLE_QCM, values, whereClauseUpdate, whereArgsUpdate);

    }


    public Qcm getQcm(int id) {
        String[] cols = {COL_ID,COL_LIBELLE,COL_NB_POINTS,COL_DATE_PUBLI,COL_DATE_FIN,COL_CATEGORY_ID};
        String whereClauses = COL_ID + "= ?";
        String[] whereArgs = {String.valueOf(id)};
        Cursor c = db.query(TABLE_QCM, cols, whereClauses, whereArgs, null, null, null);
        Qcm qcm = null;

        if (c.getCount() > 0) {

            c.moveToFirst();
            qcm = cursorToItem(c);
        }
        return qcm;

    }

    public ArrayList<Qcm> getQcms() {
        Cursor c = this.getAllCursor();
        ArrayList<Qcm> result = null;

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

        String[] cols = {COL_ID,COL_LIBELLE,COL_NB_POINTS,COL_DATE_PUBLI,COL_DATE_FIN,COL_CATEGORY_ID};
        Cursor c = db.query(TABLE_QCM,cols,null,null,null,null,null);
        return c;
    }

    public static Qcm cursorToItem(Cursor c) {

        Qcm qcm = new Qcm(0,null,null,null,0,null);
        qcm.setId(c.getInt(c.getColumnIndex(COL_ID)));
        qcm.setLibelle(c.getString(c.getColumnIndex(COL_LIBELLE)));
        qcm.setNbPoints(c.getInt(c.getColumnIndex(COL_NB_POINTS)));
        Category category = null;
        category.setId(c.getInt(c.getColumnIndex(COL_CATEGORY_ID)));
        qcm.setCategory(category);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String datepubliInString = c.getString(c.getColumnIndex(COL_DATE_PUBLI));
        String datefinInString = c.getString(c.getColumnIndex(COL_DATE_FIN));
        try {

            Date date = formatter.parse(datepubliInString);
            System.out.println(date);
            System.out.println(formatter.format(date));
            Date date2 = formatter.parse(datefinInString);
            qcm.setDatePubli(date);
            qcm.setDateFin(date2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return qcm;
    }

    private ContentValues qcmToContentValues(Qcm qcm) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String datePubli = sdf.format(qcm.getDatePubli());

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String dateFin = sdf.format(qcm.getDateFin());

        ContentValues values = new ContentValues();
        values.put(COL_LIBELLE,qcm.getLibelle());
        values.put(COL_NB_POINTS,qcm.getNbPoints());
        values.put(COL_DATE_PUBLI,datePubli);
        values.put(COL_DATE_FIN,dateFin);
        values.put(COL_CATEGORY_ID,qcm.getCategory().getId());
        return values;

    }

}
