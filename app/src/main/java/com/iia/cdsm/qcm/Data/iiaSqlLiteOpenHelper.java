package com.iia.cdsm.qcm.Data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iia.cdsm.qcm.Entity.Proposal;

/**
 * Created by Thom' on 13/02/2016.
 */
public class iiaSqlLiteOpenHelper extends SQLiteOpenHelper {

    /**
     * Database name
     */
    public static final String DB_NAME = "qcm.sqllite";

    /**
     * SqlliteOpenHelper constructor
     *
     * @param context activity context
     * @param name    database name
     * @param factory factory
     * @param version application version
     */
    public iiaSqlLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                int version) {
        super(context, name, factory, version);
    }


    /**
     * Create Database
     *
     * @param db database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CategorySqlLiteAdapter.getSchema());
        db.execSQL(QcmSqlLiteAdapter.getSchema());
        db.execSQL(QuestionSqlliteAdapter.getSchema());
        db.execSQL(ProposalSqlLiteAdapter.getSchema());
        db.execSQL(UserSqlLiteAdapter.getSchema());
        db.execSQL(ProposalUserSqLiteAdapter.getSchema());
        db.execSQL(AccessUserCategorySqLiteAdapter.getSchema());
    }

    /**
     * Update Database
     *
     * @param db         Database
     * @param oldVersion Database old version
     * @param newVersion Database new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
