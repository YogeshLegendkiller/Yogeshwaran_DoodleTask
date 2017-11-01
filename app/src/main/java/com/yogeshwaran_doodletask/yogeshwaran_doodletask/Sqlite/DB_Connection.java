package com.yogeshwaran_doodletask.yogeshwaran_doodletask.Sqlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.yogeshwaran_doodletask.yogeshwaran_doodletask.Sqlite.SqliteSingleton;

/**
 * Created by 4264 on 31/10/2017.
 */

public class DB_Connection {
    public SQLiteDatabase db;
    private SqliteSingleton dbHelper;
    private Context mContext;

    public DB_Connection(Context context) {
        this.mContext = context;
         open();
    }


    private void open() throws SQLException {
        if (dbHelper == null)
            dbHelper = SqliteSingleton.getHelper(mContext);
        db = dbHelper.getWritableDatabase();
    }

}
