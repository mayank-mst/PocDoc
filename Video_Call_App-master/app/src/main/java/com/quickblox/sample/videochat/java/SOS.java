package com.quickblox.sample.videochat.java;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.quickblox.sample.videochat.java.activities.BaseActivity;
import com.quickblox.sample.videochat.java.activities.LoginActivity;


public class SOS extends BaseActivity implements View.OnClickListener{

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    Button button1,button2;
    private static  final int REQUEST_LOCATION=1;
    String phoneNo="7299930321";
    String message="Ambulance service immediately required";
    int flag=0;
    LocationManager locationManager;
    String latitude,longitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sos);

        button1=(Button)findViewById(R.id.sosbutton);
        button2=(Button)findViewById(R.id.ambulancebutton);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
    }

    @Override
    public void onClick(View view){
        if(view.getId()==R.id.ambulancebutton){
            flag=1;

            //locationfinder();
            sendSMSMessage();
        }

        else if(view.getId()==R.id.sosbutton){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    protected void locationfinder(){


        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Check gps is enable or not

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            //Write Function To enable gps

            OnGPS();
        }
        else
        {
            //GPS is already On then

            getLocation();
        }
    }

    private void getLocation() {


    }

    private void OnGPS() {



    }



    protected void sendSMSMessage() {
       // phoneNo = txtphoneNo.getText().toString();
      //  message = txtMessage.getText().toString();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }

        if(flag==1){
            Toast.makeText(getApplicationContext(),"Ambulance is on its way",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
}



