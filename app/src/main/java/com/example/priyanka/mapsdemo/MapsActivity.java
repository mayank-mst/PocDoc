package com.example.priyanka.mapsdemo;

import android.Manifest;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener,
LocationListener{

    public  int copy;
    private NotificationManagerCompat notificationManager;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
    }

    public ArrayList<String> types_list = new ArrayList<String>();

    public ArrayList<String> sample_types = new ArrayList<String>();
    public ArrayList<String> sample_names = new ArrayList<String>();
    public ArrayList<Integer> index_val = new ArrayList<Integer>();
    String t= "";


    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker currentLocationmMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 10000;
    double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        notificationManager = NotificationManagerCompat.from(this);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            bulidGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this,"Permission Denied" , Toast.LENGTH_LONG).show();
                }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            bulidGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }


    protected synchronized void bulidGoogleApiClient() {
        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();

    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastlocation = location;
        if(currentLocationmMarker != null)
        {
            currentLocationmMarker.remove();

        }
        Log.d("lat = ",""+latitude);
        LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentLocationmMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }
    }





    public void onClick(View v)
    {
        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();

        GetNearbyPlacesData maps = new GetNearbyPlacesData();
        sample_types = maps.types_final;
        sample_names = maps.names_final;






        switch(v.getId())
        {
           case R.id.B_hopistals:
                mMap.clear();
                String hospital = "hospital";
                String url = getUrl(latitude, longitude, hospital);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                //Toast.makeText(MapsActivity.this, "Showing Nearby Hospitals", Toast.LENGTH_SHORT).show();


                break;


            case R.id.B_schools:
                mMap.clear();
                String school = "gym";
                url = getUrl(latitude, longitude, school);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this, "Showing Nearby Gyms", Toast.LENGTH_SHORT).show();
                break;

            case R.id.B_restaurants:
                mMap.clear();
                String resturant = "restaurant";
                url = getUrl(latitude, longitude, resturant);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this, "Showing Nearby Restaurants", Toast.LENGTH_SHORT).show();
                break;

            case R.id.B_notify:

                String temp = new String("");
                String [] temp_types = {""};
                String name = "";
                for(int i=0;i<sample_types.size();i++)
                {
                    temp = sample_types.get(i);
                    temp_types = temp.split(",");
                    name = sample_names.get(i);


                    //String [] food = {"cafe","ice cream"};
                    // include disease pred, and declare the disease variable as "static" and acces it here

                    //String disease = "cancer";
                    // create a hashmap for list of foods for diseases to be avoided/ taken

                    diseasepredictor d = new diseasepredictor();
                    index_val.add(d.indexcopy);


                    int ab =(Integer) index_val.toArray()[index_val.size()-1];

                    if ( ab ==0) {
                        for (int j = 0; j < temp_types.length; j++) {
                            if (temp_types[j].equals("cafe"))
                            {
                                String s= "Since you have Polycythemia,Please Avoid "+name+"\n Try to reduce the consumption of White Flour,sugar foods";

                                Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                                        .setSmallIcon(R.drawable.notif_icon)
                                        .setContentTitle("Food Notification")
                                        .setContentText(s)
                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                        .build();

                                notificationManager.notify(1, notification);
                            }


                        }
                    }

                    if (ab == 1) {
                       // for (int j = 0; j < temp_types.length; j++) {
                         //   if (temp_types[j].equals("cafe"))
                           // {
                                String s= "Since you have Macrocytic Anaemia,Please supplements like spinach and green veggies around this location.";

                                Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                                        .setSmallIcon(R.drawable.notif_icon)
                                        .setContentTitle("Food Notification")
                                        .setContentText(s)
                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                        .build();

                                notificationManager.notify(1, notification);
                           // }


                       // }
                    }

                    if (ab== 2) {
                       // for (int j = 0; j < temp_types.length; j++) {
                           // if (temp_types[j].equals("bar"))
                           // {
                             String s= "Since you have Nutrient Deficiency,Please supplements like leafy veggies around this location.";

                                Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                                        .setSmallIcon(R.drawable.notif_icon)
                                        .setContentTitle("Food Notification")
                                        .setContentText(s)
                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                        .build();

                                notificationManager.notify(1, notification);
                          //  }


                      //  }
                    }

                    if (ab == 4) {
                        for (int j = 0; j < temp_types.length; j++) {
                            if (temp_types[j].equals("cafe"))
                            {
                                String s= "Please Avoid "+name+"since you have Thrombocytosis";

                                Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                                        .setSmallIcon(R.drawable.notif_icon)
                                        .setContentTitle("Food Notification")
                                        .setContentText(s)
                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                        .build();

                                notificationManager.notify(1, notification);
                            }


                        }
                    }

                    if (ab == 3) {
                        for (int j = 0; j < temp_types.length; j++) {
                            if (temp_types[j].equals("bar"))
                            {
                                String s= "Please Avoid "+name+"Since you have Leukocytosis";

                                Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                                        .setSmallIcon(R.drawable.notif_icon)
                                        .setContentTitle("Food Notification")
                                        .setContentText(s)
                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                        .build();

                                notificationManager.notify(1, notification);
                            }


                        }
                    }
                }











                break;
        }
    }



    private String getUrl(double latitude , double longitude , String nearbyPlace)
    {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyBLEPBRfw7sMb73Mr88L91Jqh3tuE4mKsE");

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }


    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            return false;

        }
        else
            return true;
    }
    String type1 = "";


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }


}
