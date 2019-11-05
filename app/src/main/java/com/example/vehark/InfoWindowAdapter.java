package com.example.vehark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public InfoWindowAdapter(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v =  inflater.inflate(R.layout.activity_info_page, null);

        ImageView imageView = (ImageView) v.findViewById(R.id.image_map);
        TextView nameOfPlace = (TextView) v.findViewById(R.id.nameOfPlace);
        TextView vicinity = (TextView) v.findViewById(R.id.vicinity);





        // TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
        String nameofplace = marker.getTitle();
        if (nameofplace.contains("Residency") || (nameofplace.contains("Restraunts")) || nameofplace.contains("Dhabba") || nameofplace.contains("Hotels")){
            imageView.setImageResource(R.drawable.car);
        }
        if(nameofplace.contains("Medical") || nameofplace.contains("Bank")  ) {
            imageView.setImageResource(R.drawable.car);
        }

        if (nameofplace.contains("Petrol") || (nameofplace.contains("Pump")) || (nameofplace.contains("Clinic")) ) {
            imageView.setImageResource(R.drawable.car);
        }
        nameOfPlace.setText(marker.getTitle());
        vicinity.setText(marker.getSnippet());

        //tvLng.setText("Longitude:"+ latLng.longitude);
        return v;
    }


}