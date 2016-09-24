package com.example.ossusum.driverassistant;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;


public class DashActivity extends Activity {

    LatLng homeLocation;
    LatLng workLocation;
    LatLng carLocation;
    LocationTamer gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        //TO-DO make buttons and reference them here
        //make it go and stay in landscape mode

        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);





        ImageButton navi = (ImageButton) findViewById(R.id.naviButton);
        navi.setImageResource(R.mipmap.ic_launcher);
        navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send to Navigation Activity
                Intent mapGPS = new Intent(getApplicationContext(),NavigationActivity.class);
                mapGPS.putExtra("Latitude", carLocation.latitude);
                mapGPS.putExtra("Longitude", carLocation.longitude);
                startActivity(mapGPS);
            }
        });

        ImageButton park = (ImageButton) findViewById(R.id.parkButton);
        park.setImageResource(R.mipmap.ic_launcher);
        park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get location
                gps = new LocationTamer(DashActivity.this);

                if(gps.canGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    carLocation = new LatLng(latitude, longitude);
                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Parked Car Marked", Toast.LENGTH_LONG).show();
                } else {
                    // Can't get location.
                    // GPS or network is not enabled.
                    // Ask user to enable GPS/network in settings.
                    gps.showSettingsAlert();
                }
                //save location in bundle
                //send a toast saying it has been made
                //hold variable
            }
        });

        ImageButton settings = (ImageButton) findViewById(R.id.settingButton);
        settings.setImageResource(R.mipmap.setting_icon);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open setting activity
                //put a setting for auto activation on hours of work with days
            }
        });

        ImageButton home = (ImageButton) findViewById(R.id.homeButton);
        home.setImageResource(R.mipmap.home_icon);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open navi actitiy and send to home address
                Uri gmmIntentUri =
                        Uri.parse("google.navigation:q="+homeLocation.latitude+","+homeLocation.longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        ImageButton work = (ImageButton) findViewById(R.id.workButton);
        work.setImageResource(R.mipmap.work_icon);
        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open navi to go to work
                Uri gmmIntentUri =
                        Uri.parse("google.navigation:q="+workLocation.latitude+","+workLocation.longitude);
                //false data TODO change to actual data
                Uri work = Uri.parse("google.navigation:q=44 Bleecker st+Brooklyn");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        ImageButton car = (ImageButton) findViewById(R.id.carButton);
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri =
                        Uri.parse("google.navigation:q="+carLocation.latitude+","+carLocation.longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        car.setImageResource(R.mipmap.ic_launcher);
    }
    public LatLng getCurrentLocation(){
        //return the current location not homeLocation
        return homeLocation;
    }

}