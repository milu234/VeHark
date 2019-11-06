package com.example.vehark;



import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNearByPlaces extends AsyncTask<Object,String , String> implements GoogleMap.OnInfoWindowClickListener {
    private  String googleplaceData , url;
    private GoogleMap mMap;
    float results = 0f;



    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String) objects[1];

        DownloadUrl downloadUrl = new DownloadUrl();
        try
        {
            googleplaceData = downloadUrl.ReadTheUrl(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return googleplaceData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String,String>> nearByPlacesList = null;
        DataParser2 dataParser  = new DataParser2();
        nearByPlacesList = dataParser.parse(s);

        DisplayNearbyPlaces(nearByPlacesList);
    }




    public void DisplayNearbyPlaces(List<HashMap<String,String>> nearByPlacesList)
    {


        for (int i = 0 ; i<nearByPlacesList.size(); i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();

            HashMap<String,String> googleNearbyPlace = nearByPlacesList.get(i);
            String nameOfPlace = googleNearbyPlace.get("place_name");
            String vicinity = googleNearbyPlace.get("vicinity");
            double lat = Double.parseDouble(googleNearbyPlace.get("lat"));
            double lng = Double.parseDouble(googleNearbyPlace.get("lng"));


            LatLng latLng = new LatLng(lat,lng);
            markerOptions.position(latLng);
            markerOptions.title(nameOfPlace);
            markerOptions.snippet(vicinity);





            if (nameOfPlace.contains("Hotel") || (nameOfPlace.contains("Service")) || nameOfPlace.contains("IMAX") || nameOfPlace.contains("Bank") || nameOfPlace.contains("Metro") || nameOfPlace.contains("Residency") || nameOfPlace.contains("Airtel") || nameOfPlace.contains("Cottage") ){
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.parking_barrier));
            }
            if(nameOfPlace.contains("Gas") || nameOfPlace.contains("Enterprise") || nameOfPlace.contains("Automobiles") || nameOfPlace.contains("Industries")){
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.car_repair));
            }

            if (nameOfPlace.contains("Petroleum") || (nameOfPlace.contains("Honda")) || (nameOfPlace.contains("Care")) || (nameOfPlace.contains("Motoshines")) || (nameOfPlace.contains("Salon")) ) {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.car_wash));
            }


            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));









        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }



}
