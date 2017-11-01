package com.yogeshwaran_doodletask.yogeshwaran_doodletask.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.yogeshwaran_doodletask.yogeshwaran_doodletask.DAO.QuizDAO;
import com.yogeshwaran_doodletask.yogeshwaran_doodletask.DataTransfer.dataset;
import com.yogeshwaran_doodletask.yogeshwaran_doodletask.Model.QuizModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.sql.DataSource;

/**
 * Created by 4264 on 31/10/2017.
 */

public class ServiceConnection {
    private Context context;
    public dataset dataSource;
    public ServiceConnection(Context mcontext) {
        this.context = mcontext;
    }

    public void Connection() {
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, "https://my-json-server.typicode.com/HungerNHeart/sampleJson/db",
                new JSONObject(),
                 new Response.Listener<JSONObject>() {

                     @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=response.getJSONArray("questionData");
                            QuizDAO quizDAO=new QuizDAO(context);
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                QuizModel quizModel=new QuizModel();
                                quizModel.setServerID(jsonObject.getInt("id"));
                                quizModel.setPosition(-1);
                                quizModel.setQuestions(jsonObject.getString("title"));
                                String ans=(jsonObject.getJSONArray("options")).toString();
                                quizModel.setAnswers(ans);
                                quizDAO.InsertOrUpdate(quizModel);
                                dataSource.Result("Success");
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        dataSource.Result("Failure");
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        VolleySingleton.getInstance().addToRequestQueue(obreq);
    }
}
