package com.yogeshwaran_doodletask.yogeshwaran_doodletask.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yogeshwaran_doodletask.yogeshwaran_doodletask.Model.QuizModel;

/**
 * Created by 4264 on 31/10/2017.
 */

public class SqliteSingleton extends SQLiteOpenHelper {
    private static SqliteSingleton instance;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "doodle.db";

    private String quiztable=" CREATE TABLE " + QuizModel.QuizModel_Table + " ("
            + QuizModel.QuizModel_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + QuizModel.QuizModel_Questions + " TEXT ,"
            + QuizModel.QuizModel_answers + " TEXT ,"
            + QuizModel.QuizModel_Position + " INTEGER ,"
            + QuizModel.QuizModel_ServerID  + " INTEGER "
            + ");";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(quiztable);

    }
    public static synchronized SqliteSingleton getHelper(Context context) {
        if (instance == null)
            instance = new SqliteSingleton(context.getApplicationContext());
        return instance;
    }

    private SqliteSingleton(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + quiztable);

    }
}

