package com.example.vehark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PayAndParkInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_and_park_info);

        final TextView txtnames = (TextView)findViewById(R.id.name);

        TextView txtaddress = (TextView)findViewById(R.id.address);
        TextView txtopens = (TextView)findViewById(R.id.opens);
        TextView txtgst = (TextView)findViewById(R.id.gst);
        Button btnbookaslot = (Button)findViewById(R.id.bookaslot);
        Button back_btn = (Button)findViewById(R.id.back);


        final Intent intent = getIntent();
        txtnames.setText(intent.getStringExtra("Name"));
        txtaddress.setText(intent.getStringExtra("Address"));
        txtgst.setText(intent.getStringExtra("GST"));
        txtopens.setText(intent.getStringExtra("OpeningHours"));
        txtopens.append(" .00 AM - " + intent.getStringExtra("OpeningHours") + " .00 AM");



        btnbookaslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(PayAndParkInfo.this, SlotBookActivity.class);
                startActivity(intent1);
            }
        });

    }





}
