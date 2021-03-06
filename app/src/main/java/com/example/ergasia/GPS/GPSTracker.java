package com.example.ergasia.GPS;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;
    private String completeAddress;
    private String city;
    private String country;

    //flag for GPS status
    boolean isGPSEnabled = false;
    //flag for network statuc
    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    Location location; //location
    double latitude; //latitude
    double longitude; //longitude
    private ArrayList<String> addr;


    //The minimum distance to change Update in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES =10; //10 meters

    //The minmum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; //1 minute

    //Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSTracker(Context mContext) {
        this.mContext = mContext;
        getLocation();
        System.out.println("constr:"+addr);

    }

    public ArrayList<String> getAddr() {
        System.out.println("getter:"+addr);
        return addr;
    }



    @TargetApi(Build.VERSION_CODES.M)
    public ArrayList<String> getLocation() {

        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);
            //getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            //getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                //no netwprk provider is enabled
                System.out.println("no network provider is enabled");
            } else {
                this.canGetLocation = true;
                //First get the location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                /*
                int checkPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
                System.out.println(checkPermission);
                int checkGranted = PackageManager.PERMISSION_GRANTED;
                System.out.println(checkGranted);
                */
                if (Build.VERSION.SDK_INT>=23){
                    if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (isGPSEnabled) {
                            if (location == null) {
                                locationManager.requestLocationUpdates(
                                        LocationManager.GPS_PROVIDER,
                                        MIN_TIME_BW_UPDATES,
                                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                                Log.d("GPS Enabled", "GPS Enabled");
                                if (locationManager != null) {
                                    location = locationManager
                                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                    if (location != null) {
                                        latitude = location.getLatitude();
                                        longitude = location.getLongitude();
                                    }
                                }

                            }

                        }
                    }
                } else {
                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            Log.d("GPS Enabled", "GPS Enabled");
                            if (locationManager != null) {
                                location = locationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }

                        }

                    }
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Geocoder geoCoder = new Geocoder(mContext,Locale.getDefault());
        System.out.println("geocoder:"+geoCoder);
        try {
            List<Address> addresses = geoCoder.getFromLocation(getLatitude(), getLongitude(), 1); //1 for only one address
            Address add = addresses.get(0);

            city = add.getLocality();;
            country = add.getCountryName();

            addr = new ArrayList<String>();
            addr.add(city);
            addr.add(", ");
            addr.add(country);



        } catch (IOException e) {
            e.printStackTrace();
        }
        return addr;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        getLatitude();
        getLongitude();
        getAddr();
    }

        @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        getAddr();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Function to get latitude
     * @return latitude
     */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        //return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * @return longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        //return longitude
        return longitude;
    }

    /**
     * Function to check if best network provider
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        //Setting dialog title
        alertDialog.setTitle("Paramétrage du GPS");

        //Setting Dialog Message
        alertDialog.setMessage("Le GPS est desactivé. Voulez-vous activer la géolocalisation ?");

        //On pressing Settings button
        alertDialog.setPositiveButton("Réglages", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        //on pressing cancel button
        alertDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(getApplicationContext(), "Veuillez entrer votre ville ainsi que votre pays", Toast.LENGTH_SHORT).show();
            }
        });

        //showing Alert Message
        alertDialog.show();
    }

        @TargetApi(Build.VERSION_CODES.M)
        public void stopUsingGPS() {
            if (Build.VERSION.SDK_INT>=23) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (locationManager != null) {
                        locationManager.removeUpdates(GPSTracker.this);
                    }
                }
            } else {
                if (locationManager != null) {
                    locationManager.removeUpdates(GPSTracker.this);
                }
            }
    }

}


