package com.example.vehark;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

//import android.location.LocationListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location lastLocation;
    private Marker currentLocationMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    private double latitude, longitude;
    private int ProximityRadius = 10000;
    private double hlat;
    private double hlng;
    private MarkerOptions place2;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        checkUserLocationPermission();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapNearBy2);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);







    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        if (client == null){
                            buildGoogleApiClient();

                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }

                else {
                    Toast.makeText(this,"Permission Denied!!" , Toast.LENGTH_LONG).show();
                }
                return;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
//        Intent intent = getIntent();
//        double ulat = intent.getDoubleExtra("hlat",0);
//        double ulng = intent.getDoubleExtra("hlng",0);
//
//
//
//        LatLng hlatLng = new LatLng(ulat,ulng);
//
//        mMap.clear();
//        place2 = new MarkerOptions().position(hlatLng);
//        Log.d("mylog", "Added Markers");

//        mMap.moveCamera(CameraUpdateFactory.newLatLng(hlatLng));
//        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));





        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);

            }

        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }

    protected synchronized void buildGoogleApiClient(){
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastLocation = location;
        if (currentLocationMarker != null){
//            currentLocationMarker.remove();

        }

        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        InfoWindowAdapter infoWindowAdapter = new InfoWindowAdapter(getApplicationContext());
        markerOptions.position(latLng);
        markerOptions.title("Hi , You Are Here");
        mMap.setInfoWindowAdapter(infoWindowAdapter);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.user));

        currentLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));





        if (client != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }


    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(3000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }


    }




    public void onClick(View v){
        String parking = "restraunts", car_repair = "car_repair" ,  car_wash = "car_wash" ;
        Object transferData[] = new Object[2];

        GetNearByPlaces getNearbyPlaces = new GetNearByPlaces();
        final InfoWindowAdapter infoWindowAdapter = new InfoWindowAdapter(getApplicationContext());
        mMap.setInfoWindowAdapter(infoWindowAdapter);



        switch(v.getId())
        {
            case R.id.search_address:
                EditText addressField = (EditText) findViewById(R.id.location_search);
                String address = addressField.getText().toString();


                List<Address> addressList = null;
                MarkerOptions userMarkeroptions = new MarkerOptions();

                if (!TextUtils.isEmpty(address))
                {
                    Geocoder geocoder = new Geocoder(this);

                    try {
                        addressList = geocoder.getFromLocationName(address,6);

                        if (addressList != null)
                        {
                            for (int i=0; i<addressList.size(); i++)
                            {
                                Address userAddress = addressList.get(i);
                                LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());
                                userMarkeroptions.position(latLng);
                                userMarkeroptions.title(address.toUpperCase());
                                userMarkeroptions.snippet(String.valueOf(latLng));

                                userMarkeroptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                mMap.addMarker(userMarkeroptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                                mMap.setOnInfoWindowClickListener(this);
                                LatLng current = currentLocationMarker.getPosition();
                                double currentlat = current.latitude;
                                double currentlng = current.longitude;
                                SharedPreferences ref = getSharedPreferences("user_details",MODE_PRIVATE);
                                SharedPreferences.Editor editor = ref.edit();
                                editor.putString("my_lattitude",String.valueOf(currentlat));
                                editor.putString("my_longitude",String.valueOf(currentlng));
                                editor.commit();
                                LatLng userloc = userMarkeroptions.getPosition();
                                double userlat = userloc.latitude;
                                double userlng = userloc.longitude;






                            }

                        }
                        else
                        {
                            Toast.makeText(this,"Location Not Found",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                else
                {
                    Toast.makeText(this,"Please write a location",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.nearby_parkings:


                mMap.clear();



                String url = getUrl(latitude,longitude,parking);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);
                Toast.makeText(this,"Searching For Nearby Parking",Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"Showing Nearby Parking",Toast.LENGTH_SHORT).show();
                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Intent intent = new Intent(MapsActivity.this,InfoPage.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("nameOfPlace",marker.getTitle());
                        bundle.putString("vicinity",marker.getSnippet());
                        LatLng current = currentLocationMarker.getPosition();
                        double currentlat = current.latitude;
                        double currentlng = current.longitude;

                        LatLng results =  marker.getPosition();
                        double lat = results.latitude;
                        double lng = results.longitude;
                        bundle.putString("nameOfPlace",marker.getTitle());
                        bundle.putString("vicinity",marker.getSnippet());
                        bundle.putDouble("plat",lat);
                        bundle.putDouble("plng",lng);


//                        double dist;
//                        Location locationA = new Location("");
//
//                        locationA.setLatitude(currentlat);
//                        locationA.setLongitude(currentlng);
//
//                        Location locationB = new Location("");
//                        locationB.setLatitude(lat);
//                        locationB.setLongitude(lng);
//
//






                        intent.putExtras(bundle);
                        startActivity(intent);






                    }
                });













                break;



            case R.id.nearby_carrepair:
                mMap.clear();
                url = getUrl(latitude,longitude,car_repair);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);
                Toast.makeText(this,"Searching For Nearby Car Repair",Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"Showing Nearby Car Repair",Toast.LENGTH_SHORT).show();
                break;




            case R.id.nearby_carwash:
                mMap.clear();
                url = getUrl(latitude,longitude,car_wash);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);
                Toast.makeText(this,"Searching For Nearby Car Washes",Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"Showing Nearby Car Washing",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private String getUrl(double latitude,double longitude,String nearByPlace)
    {

//        Intent intent = getIntent();
//        latitude = intent.getDoubleExtra("hlat",0);
//        longitude = intent.getDoubleExtra("hlng",0);;
        latitude =lastLocation.getLatitude();
        longitude = lastLocation.getLongitude();
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + latitude + "," + longitude);
        googleURL.append("&radius=" + ProximityRadius);

        googleURL.append("&type=" + nearByPlace);


        googleURL.append("&sensor=true");
        googleURL.append("&key=" + "AIzaSyAb5A7jKDxhu0duhmPdY896K3BAJuiq-rg");

        Log.d("GoogleMapActivity","url = " + googleURL.toString());

        return  googleURL.toString();


    }







    public  boolean checkUserLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , REQUEST_LOCATION_CODE);

            }
            else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , REQUEST_LOCATION_CODE);
            }
            return false;
        }
        else
            return true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(MapsActivity.this, InfoPage.class);
        intent.putExtra("nameOfPlace",marker.getTitle());
        intent.putExtra("vicinity",marker.getSnippet());
        startActivity(intent);
    }

    public static double getDistanceMeters(double lat1, double lng1, double lat2, double lng2){
        double l1 = toRadians(lat1);
        double l2 = toRadians(lat2);
        double g1 = toRadians(lng1);
        double g2 = toRadians(lng2);

        double dist = acos(sin(l1) * sin(l2) + cos(l1) * cos(l2) * cos(g1 - g2));
        if(dist < 0) {
            dist = dist + Math.PI;
        }

        dist = (dist * 6378100)/1000;
        dist = Math.round(dist * 100);
        dist = dist/100;

        return dist;

    }





    public String getDistance(final double lat1, final double lon1, final double lat2, final double lon2)
    {

        final String[] parsedDistance = new String[1];
        final String[] response = new String[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://maps.googleapis.com/maps/api/directions/json?origin=" + lat1 + "," + lon1 + "&destination=" + lat2 + "," + lon2 + "&sensor=false&units=metric&mode=driving&key" + "");
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    response[0] = in.toString();

                    JSONObject jsonObject = new JSONObject(response[0]);
                    JSONArray array = jsonObject.getJSONArray("routes");
                    JSONObject routes = array.getJSONObject(0);
                    JSONArray legs = routes.getJSONArray("legs");
                    JSONObject steps = legs.getJSONObject(0);
                    JSONObject distance = steps.getJSONObject("distance");
                    parsedDistance[0] = distance.getString("text");


                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return parsedDistance[0];

    }




}