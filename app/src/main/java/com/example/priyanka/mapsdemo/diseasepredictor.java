package com.example.priyanka.mapsdemo;


import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;
import java.lang.*;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.firebase.auth.FirebaseAuth;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.util.ArrayList;
import java.util.Collections;

public class diseasepredictor extends AppCompatActivity implements View.OnClickListener{

    AnyChartView anyChartView;

    public static int indexcopy;

    String[] val={"polycythemia","macrocytic anemia","nutrient deficiency","Leukocytosis","Thrombocytosis"};
    float[] res={0,0,0,0,0};


    public static final int PERMISSION_REQUEST_STORAGE = 1000;
    public static final int READ_REQUEST_CODE = 12;
    String fm;


    EditText editText1,editText2,editText3,editText4,editText5,editText6,editText7;
    ImageButton button1,button2,button3,button4;

    private TensorFlowInferenceInterface tensorFlowInferenceInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diseasepredictor);

        anyChartView=findViewById(R.id.any_chart_view);
        anyChartView.setVisibility(View.GONE);

        //setupPieChart();

        tensorFlowInferenceInterface = new  TensorFlowInferenceInterface(getAssets(),"frozen_model.pb");

        editText1= (EditText)findViewById(R.id.rbc);
        editText2= (EditText)findViewById(R.id.sugar);
        editText3= (EditText)findViewById(R.id.acidity);
        editText4= (EditText)findViewById(R.id.colestrol);
     //   editText5= (EditText)findViewById(R.id.result);

        editText7 = (EditText)findViewById(R.id.d5);
        button1 = (ImageButton)findViewById(R.id.clear);
        button2 =(ImageButton)findViewById(R.id.predict);
        button3=(ImageButton)findViewById(R.id.upload);
      //  button4=(Button)findViewById(R.id.pie);
        // More code here
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performFileSearch();
            }
        });

        //button4.setOnClickListener(this);

    }

    public void setupPieChart()
    {
        Pie  pie = AnyChart.pie();
        List<DataEntry>  dataEntries = new ArrayList<>();

        for(int kk=0;kk<res.length;kk++)
        {

            dataEntries.add(new ValueDataEntry(val[kk],res[kk]));
        }

        pie.data(dataEntries);
        anyChartView.setChart(pie);

    }



    public void performFileSearch(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        startActivityForResult(intent,READ_REQUEST_CODE);






    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                String path = uri.getPath();
                path = path.substring(path.indexOf(":") + 1);


                int index = path.lastIndexOf('/');
                int lindex = path.lastIndexOf('.');


                fm = path.substring(index + 1, lindex);
                String filen = fm + ".pdf";

                Toast.makeText(this, path, Toast.LENGTH_SHORT).show();

                //writeFile();
                readFile();


            }
        }
    }


    public void readFile() {
        try {
            FileInputStream fileInputStream = openFileInput(fm+".txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            String lines;
            int i=0;
           while ((lines = bufferedReader.readLine()) != null) {

                if(i==0)
                    editText1.setText(lines);

                if(i==1)
                    editText2.setText(lines);

                if(i==2)
                    editText3.setText(lines);

                if(i==3)
                    editText4.setText(lines);

                if(i==4)
                    editText7.setText(lines);


                stringBuffer.append(lines + "\n");
                i++;

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeFile() {
        String textToSave = " 5.27\n83.00\n11\n12.50\n400";
// 16.0\n80\n14.4\n9.4\n451 -> //sam3

// hello 1.8\n109.6\n16.0\6.9\n180
// sam1 5.27\n83.00\n11\n12.50\n400
//
        try {
            FileOutputStream fileOutputStream = openFileOutput(fm+".txt", MODE_PRIVATE);
            fileOutputStream.write(textToSave.getBytes());
            fileOutputStream.close();

            Toast.makeText(getApplicationContext(), "Text Saved", Toast.LENGTH_SHORT).show();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.clear){
            editText1.setText("");
            editText2.setText("");
            editText3.setText("");
            editText4.setText("");
           // editText5.setText("");
            editText7.setText("");
            anyChartView.clear();
            anyChartView.setVisibility(View.GONE);

        }



        else if(v.getId()==R.id.predict){
            float n1,n2,n3,n4,n5;
            n1 = Float.parseFloat(editText1.getText().toString());
            n2 = Float.parseFloat(editText2.getText().toString());
            n3 = Float.parseFloat(editText3.getText().toString());
            n4 = Float.parseFloat(editText4.getText().toString());
            n5 = Float.parseFloat(editText7.getText().toString());

            n1 = n1/(float)(10.8);
            n2 = n2/(float)(100);
            n3 = n3/(float)(14.5);
            n4 = n4/(float)(10.0);
            n5 = n5/(float)(450);

            float[] inp={n1,n2,n3,n4,n5};


            tensorFlowInferenceInterface.feed("my_input/X",inp,1,5);
            tensorFlowInferenceInterface.run(new String[] {"my_output/Softmax"});
            tensorFlowInferenceInterface.fetch("my_output/Softmax",res);

            //editText5.setText("["+Float.toString(res[0]) +" "+Float.toString(res[1]) +" "+Float.toString(res[2]) +" "+Float.toString(res[3]) +" "+"]");
            String res_st = "";
            for(int pp=0;pp<5;pp++){
                res_st= res_st + Float.toHexString(res[pp]) + " ";
            }



            float max=res[0];
            int index=0;
            for(int i=0;i<5;i++){
                if(res[i]>max){
                    max=res[i];
                    index=i;
                }
            }

            indexcopy = index;
            // if(index==0)

            //editText5.setText(""+Integer.toString(index)+"");

           /* if(index==0) {
                editText5.setText("0");

            }

            if(index==1) {
                editText5.setText("1");

            }
            if(index==2) {
                editText5.setText("2");

            }
            if(index==3) {
                editText5.setText("3");

            }

            if(index==4) {
                editText5.setText("4");

            }*/

            setupPieChart();
            anyChartView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar_disease_monitor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.merchant_toolbar_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Main3Activity.class));
        }
        if (item.getItemId() == R.id.merchant_toolbar_bot) {
            //FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Chatbot.class));
        }

        if (item.getItemId() == R.id.merchant_toolbar_maps) {
            //FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MapsActivity.class));
        }

        if (item.getItemId() == R.id.merchant_toolbar_SOS) {
            Intent i = getPackageManager().getLaunchIntentForPackage("com.quickblox.sample.videochat.java");
            startActivity(i);
        }
        return false;
    }
}
