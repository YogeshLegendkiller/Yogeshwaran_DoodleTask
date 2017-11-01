package com.yogeshwaran_doodletask.yogeshwaran_doodletask.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;


import com.yogeshwaran_doodletask.yogeshwaran_doodletask.Model.QuizModel;
import com.yogeshwaran_doodletask.yogeshwaran_doodletask.Sqlite.DB_Connection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 4264 on 31/10/2017.
 */

public class QuizDAO extends DB_Connection {

    private static String DATABASE_NAME="doodle_db";
    public static final Uri Quiz_URI = Uri
            .parse("sqlite://" + "sf_visit" + "/" + DATABASE_NAME);
  private Context context;
    public QuizDAO(Context context) {
        super(context);
        this.context=context;
    }
    public void InsertData(QuizModel quizModel){
        ContentValues contentValues=new ContentValues();
        contentValues.put(QuizModel.QuizModel_ServerID,quizModel.getServerID());
        contentValues.put(QuizModel.QuizModel_Questions,quizModel.getQuestions());
        contentValues.put(QuizModel.QuizModel_Position,quizModel.getPosition());
        contentValues.put(QuizModel.QuizModel_answers,quizModel.getAnswers());

        db.insert(QuizModel.QuizModel_Table,null,contentValues);
    }
    private void UpdateData(QuizModel quizModel){
        ContentValues contentValues=new ContentValues();
        String[] id = {String.valueOf(quizModel.getServerID())};
        contentValues.put(QuizModel.QuizModel_ServerID,quizModel.getServerID());
        contentValues.put(QuizModel.QuizModel_answers,quizModel.getAnswers());
        contentValues.put(QuizModel.QuizModel_Position,quizModel.getPosition());
        contentValues.put(QuizModel.QuizModel_Questions,quizModel.getQuestions());
        db.update(QuizModel.QuizModel_Table,contentValues, QuizModel.QuizModel_ServerID + "= ?", id);
    }
    public void InsertOrUpdate(QuizModel quizModel){
        String query = " SELECT  * FROM " + QuizModel.QuizModel_Table   + " WHERE " + QuizModel.QuizModel_ServerID  + "  =" + quizModel.getServerID() + "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            UpdateData(quizModel);
        } else {
            InsertData(quizModel);
        }
        context.getContentResolver().notifyChange(Quiz_URI, null);
        c.close();
    }
    public void UpdatePosition(QuizModel quizModel){
        ContentValues contentValues=new ContentValues();
        String[] id = {String.valueOf(quizModel.getServerID())};
        contentValues.put(QuizModel.QuizModel_Position,quizModel.getPosition());
        context.getContentResolver().notifyChange(Quiz_URI, null);
        db.update(QuizModel.QuizModel_Table,contentValues, QuizModel.QuizModel_ServerID + "= ?", id);
    }
    public void delete() {
        db.execSQL("delete from " + QuizModel.QuizModel_Table);
    }
    public int Count() {
        String countQuery = "SELECT  " + QuizModel.QuizModel_Position + " from " + QuizModel.QuizModel_Table + " where " + QuizModel.QuizModel_Position  + " >= 0 " ;
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
     public List<QuizModel>listobj(){
         String query=" Select * from "+ QuizModel.QuizModel_Table ;
         Cursor cursor = db.rawQuery(query ,null);
         List<QuizModel> list = new ArrayList<>();
         if (cursor != null && cursor.getCount() > 0) {
             if (cursor.moveToFirst()) {
                 do {
                     QuizModel quizModel=new QuizModel();
                     quizModel.setPosition(cursor.getInt(cursor.getColumnIndex(QuizModel.QuizModel_Position)));
                     quizModel.setQuestions(cursor.getString(cursor.getColumnIndex(QuizModel.QuizModel_Questions)));
                     quizModel.setAnswers(cursor.getString(cursor.getColumnIndex(QuizModel.QuizModel_answers)));
                     quizModel.setServerID(cursor.getInt(cursor.getColumnIndex(QuizModel.QuizModel_ServerID)));
                     list.add(quizModel);
                 } while (cursor.moveToNext());
             }
         }
         if (cursor != null) {
             cursor.close();
         }
         return list;
     }
    public List<QuizModel>CursorTolistobj(Cursor cursor){
        List<QuizModel> list = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    QuizModel quizModel=new QuizModel();
                    quizModel.setPosition(cursor.getInt(cursor.getColumnIndex(QuizModel.QuizModel_Position)));
                    quizModel.setQuestions(cursor.getString(cursor.getColumnIndex(QuizModel.QuizModel_Questions)));
                    quizModel.setAnswers(cursor.getString(cursor.getColumnIndex(QuizModel.QuizModel_answers)));
                    quizModel.setServerID(cursor.getInt(cursor.getColumnIndex(QuizModel.QuizModel_ServerID)));
                    list.add(quizModel);
                } while (cursor.moveToNext());
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }
    public Cursor CursorQuizList(){
        String query=" Select * from "+ QuizModel.QuizModel_Table ;
        Cursor cursor = db.rawQuery(query ,null);
        cursor.setNotificationUri(context.getContentResolver(), Quiz_URI);
        return cursor;
    }
}
