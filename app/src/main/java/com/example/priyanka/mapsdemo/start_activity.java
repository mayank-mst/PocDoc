package com.example.priyanka.mapsdemo;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class start_activity extends AppCompatActivity implements View.OnClickListener{


    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_activity);


        Button b =(Button)findViewById(R.id.clickme);
        b.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.clickme){
            Intent i;
            startActivity(new Intent(this,MapsActivity.class));


        }
    }



}
