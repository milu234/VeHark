package com.example.vehark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        CardView cardViewFindParking = findViewById(R.id.card_find_parking);
        CardView cardViewFillLicense = findViewById(R.id.cardviewlicense);
        CardView cardViewProfile = findViewById(R.id.peronalInfoCard);
        CardView cardViewBookASlot = findViewById(R.id.bookaslot);
        CardView cardViewSlotList = findViewById(R.id.Help);

        cardViewFindParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Main2Activity.this,MapsActivity.class);
                startActivity(intent);
            }
        });

        Button btnSignOut = findViewById(R.id.signOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent);

            }
        });


        cardViewFindParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, MapsActivity.class);
                startActivity(intent);
            }
        });


        cardViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this,ProfilePage.class);
                startActivity(intent);
            }
        });

        cardViewFillLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this,ProfileActivity.class);
                startActivity(intent);

            }
        });

        cardViewBookASlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Main2Activity.this,SlotBookActivity.class);
                startActivity(intent);
            }
        });

        cardViewSlotList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(Main2Activity.this,ListOfParkings.class);
                startActivity(intent);
            }
        });












    }
}
