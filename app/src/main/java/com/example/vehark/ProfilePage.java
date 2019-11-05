package com.example.vehark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePage extends AppCompatActivity {
    Button btnedit;
    TextView  dname, dage,dnumber ,dlicense;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String ename, elicense, eage , enumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        dname = findViewById(R.id.displayName);
        dage = findViewById(R.id.displayAge);
        dnumber = findViewById(R.id.displayNumber);
        dlicense = findViewById(R.id.displayLicense);
        btnedit = findViewById(R.id.edit);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer").child(firebaseAuth.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                Customer customer = dataSnapshot.getValue(Customer.class);
                dname.setText(customer.getName());
                ename = customer.getName();

                dage.setText(customer.getAge());
                 eage = customer.getAge();


                dnumber.setText(customer.getNumber());
                enumber = customer.getNumber();

                dlicense.setText(customer.getLicense());
                elicense = customer.getLicense();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                // Getting Post failed, log a message

                // [START_EXCLUDE]
                Toast.makeText(ProfilePage.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]

            }
        });


        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePage.this,ProfileEdit.class);

                startActivity(intent);
            }
        });

    }
}
