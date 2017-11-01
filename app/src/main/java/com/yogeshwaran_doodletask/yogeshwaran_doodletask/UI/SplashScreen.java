package com.yogeshwaran_doodletask.yogeshwaran_doodletask.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yogeshwaran_doodletask.yogeshwaran_doodletask.DataTransfer.dataset;
import com.yogeshwaran_doodletask.yogeshwaran_doodletask.R;
import com.yogeshwaran_doodletask.yogeshwaran_doodletask.network.ServiceConnection;

public class SplashScreen extends AppCompatActivity implements dataset {
   private ProgressBar  progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ServiceConnection serviceConnection=new ServiceConnection(this);
        serviceConnection.dataSource=SplashScreen.this;
        serviceConnection.Connection();
        progressBar=(ProgressBar)findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void Result(String value) {
        if(value.equals("Success")){
            progressBar.setVisibility(View.INVISIBLE);
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        else {
            Toast.makeText(this,"Please Check The Network",Toast.LENGTH_SHORT).show();
        }
    }
}
