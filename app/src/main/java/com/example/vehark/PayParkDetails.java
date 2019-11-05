package com.example.vehark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PayParkDetails extends AppCompatActivity {
    DatabaseReference databaseReference;
    EditText txtname, txtgst,txtaddress,txtoh;
    Button save_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_park_details);
        txtname = findViewById(R.id.name);
        txtgst = findViewById(R.id.gst);
        txtaddress = findViewById(R.id.address);
        txtoh = findViewById(R.id.openhour);
        save_info = findViewById(R.id.save_info);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("PayAndPark");



        save_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtname.getText().toString();
                String gst = txtgst.getText().toString();
                String address = txtaddress.getText().toString();
                String oh = txtoh.getText().toString();


                PayAndPark payAndPark = new PayAndPark(
                        name,
                        gst,
                        address,
                        oh

                );

                FirebaseDatabase.getInstance().getReference("PayAndPark")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(payAndPark).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(PayParkDetails.this, LoginForm.class);
                        startActivity(intent);

                    }
                });



            }
        });
    }

}
