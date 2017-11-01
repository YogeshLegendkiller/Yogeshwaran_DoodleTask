package com.yogeshwaran_doodletask.yogeshwaran_doodletask.Model;

/**
 * Created by 4264 on 31/10/2017.
 */

public class QuizModel  {
 public static final String QuizModel_Table="quiz_table";
 public static final String QuizModel_ID="primary_id";
 public static final String QuizModel_ServerID="server_id";
 public static final String QuizModel_Questions="questions";
 public static final String QuizModel_answers="answers";
 public static final String QuizModel_Position="position";

 private Integer ServerID;
    private Integer Count;

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    public Integer getPosition() {
        return Position;
    }

    public void setPosition(Integer position) {
        Position = position;
    }

    private String Questions;
    private Integer Position;

    public Integer getServerID() {
        return ServerID;
    }

    public void setServerID(Integer serverID) {
        ServerID = serverID;
    }

    public String getQuestions() {
        return Questions;
    }

    public void setQuestions(String questions) {
        Questions = questions;
    }

    public String getAnswers() {
        return Answers;
    }

    public void setAnswers(String answers) {
        Answers = answers;
    }

    private String Answers;

}
