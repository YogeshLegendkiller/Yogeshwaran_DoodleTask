package com.yogeshwaran_doodletask.yogeshwaran_doodletask.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;import android.app.LoaderManager;
import android.content.CursorLoader;

import android.content.Loader;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yogeshwaran_doodletask.yogeshwaran_doodletask.Adapters.IndexAdapter;
import com.yogeshwaran_doodletask.yogeshwaran_doodletask.Adapters.QuizAdapter;
import com.yogeshwaran_doodletask.yogeshwaran_doodletask.DAO.QuizDAO;
import com.yogeshwaran_doodletask.yogeshwaran_doodletask.Model.QuizModel;
import com.yogeshwaran_doodletask.yogeshwaran_doodletask.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<Cursor>{
    int count=0;
    List<QuizModel> quizobj;
    boolean exit = false;
    QuizModel quizModel;
    QuizDAO quizDAO;
    int bottompos;
    QuizAdapter quizAdapter;
    RecyclerView mrecyclerview,count_recyclerview;
    TextView question_textView;
    private Button btn_next;
    IndexAdapter indexAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quizobj=new ArrayList<>();
        quizDAO =new QuizDAO(this);
        mrecyclerview=(RecyclerView) findViewById(R.id.ans_recyclerview);
        count_recyclerview=(RecyclerView) findViewById(R.id.count);

        question_textView=(TextView) findViewById(R.id.question_text);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mrecyclerview.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        count_recyclerview.setLayoutManager(layoutManager1);
         btn_next= (Button) findViewById(R.id.btn);
        getLoaderManager().initLoader(0, null,this);

    }
    @Override
    public void onBackPressed() {
        if (exit) {
            quizDAO.delete();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        this.exit = true;
        Toast.makeText(this, "All The Data Will Be Deleted Once Exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                exit=false;
            }
        }, 2000);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(MainActivity.this, QuizDAO.Quiz_URI , null, null, null, null) {

            ForceLoadContentObserver mObserver = new ForceLoadContentObserver();

            @Override
            public Cursor loadInBackground() {
                Cursor c;
                c=quizDAO.CursorQuizList();
                if (c != null) {
                    c.registerContentObserver(mObserver);
                    c.setNotificationUri(getContext().getContentResolver(), getUri());
                }
                return c;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            quizobj=quizDAO.CursorTolistobj(data);
            quizModel=quizobj.get(count);
            List<QuizModel> list;
            question_textView.setText(String.valueOf(bottompos+1)+"."+ " "+quizModel.getQuestions());
            list=  JsonArrayToList(quizModel.getAnswers(),quizModel.getServerID(),quizModel.getPosition(),quizModel.getQuestions());
            SetAdapter(list,0);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_next.getText().toString().equals("Submit")) {
                     int answered=quizDAO.Count();
                     showAlertDialog(quizobj.size(),answered);
                } else {
                    count = count + 1;
                     if (count == quizobj.size() - 1) {
                        btn_next.setText("Submit");
                        Log.d("last", "last");

                    }
                    List<QuizModel> list = new ArrayList<QuizModel>();
                    list.clear();
                    QuizModel quizModel = quizobj.get(count);
                    question_textView.setText(String.valueOf(count+1)+"."+ " "+quizModel.getQuestions());
                    list = JsonArrayToList(quizModel.getAnswers(), quizModel.getServerID(), quizModel.getPosition(), quizModel.getQuestions());
                    SetAdapter(list,count);
                }
            }});
        List<QuizModel>integerslist=new ArrayList<>();
        for (int i=0;i<quizobj.size();i++) {
            QuizModel quizmodel=quizobj.get(i);
            quizmodel.setCount(i+1);
            integerslist.add(quizmodel);
        }
        indexAdapter=new IndexAdapter(integerslist,this);
        count_recyclerview.setAdapter(indexAdapter);
        indexAdapter.notifyDataSetChanged();
        if(indexAdapter!=null && bottompos >0){
            count_recyclerview.getLayoutManager().scrollToPosition(bottompos);
        }
        indexAdapter.setOnItemClickListener(new QuizAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                count=position;
                QuizModel   quizModel;
                if(position==quizobj.size()-1){
                    btn_next.setText("Submit");
                    quizModel=quizobj.get(quizobj.size()-1);
                }
                else {
                    btn_next.setText("Next");
                    Log.d("Next","Next");
                    quizModel=quizobj.get(position);
                }
                List<QuizModel> list=new ArrayList<>();
                list.clear();
                 question_textView.setText(String.valueOf(position+1)+"."+ " "+quizModel.getQuestions());
                list=  JsonArrayToList(quizModel.getAnswers(),quizModel.getServerID(),quizModel.getPosition(),quizModel.getQuestions());
                SetAdapter(list,position);
            }
        });

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    public void SetAdapter(final List<QuizModel>list,final  int  count){
        quizAdapter=new QuizAdapter(list);
        mrecyclerview.setAdapter(quizAdapter);
        quizAdapter.notifyDataSetChanged();
        quizAdapter.setOnItemClickListener(new QuizAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                QuizModel quizModel=list.get(position);
                quizModel.setServerID(quizModel.getServerID());
                quizModel.setPosition(position);
                quizDAO.UpdatePosition(quizModel);
                quizAdapter=new QuizAdapter(list);
                mrecyclerview.setAdapter(quizAdapter);
                quizAdapter.notifyDataSetChanged();
                bottompos=count;
                getLoaderManager().restartLoader(0, null,MainActivity.this);

            }
        });
    }
    public List<QuizModel> JsonArrayToList(String ans,Integer ServerID,Integer Position,String Questions){
        List<QuizModel>list=new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONArray(ans);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                QuizModel quizModels=new QuizModel();
                quizModels.setAnswers((char)i + " "+jsonObject.getString("value"));
                quizModels.setServerID(ServerID);
                quizModels.setPosition(Position);
                quizModels.setQuestions(Questions);
                list.add(quizModels);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    return list;
    }
    private void showAlertDialog(int total,int answerd) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Result")
                .setMessage("Total No of Question :" + total  + "\nNo of Questions Answered :"+ answerd )
                .setCancelable(false)
                .setNegativeButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(MainActivity.this,"Thank You",Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

