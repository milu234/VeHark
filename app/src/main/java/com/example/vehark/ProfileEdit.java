package com.example.vehark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileEdit extends AppCompatActivity {
    private EditText name_edit, age_edit, license_edit , number_edit;
    private Button save_einfo;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        name_edit = findViewById(R.id.name_edit);
        age_edit = findViewById(R.id.age_edit);
        license_edit = findViewById(R.id.license_edit);
        number_edit = findViewById(R.id.number_edit);
        save_einfo = findViewById(R.id.save_edit_info);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer").child(firebaseAuth.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                Customer customer = dataSnapshot.getValue(Customer.class);
                name_edit.setText(customer.getName());
                age_edit.setText(customer.getAge());
                license_edit.setText(customer.getLicense());
                number_edit.setText(customer.getNumber());
                number_edit.setFocusable(false);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        save_einfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = name_edit.getText().toString();
                String age = age_edit.getText().toString();
                String license = license_edit.getText().toString();
                String number = number_edit.getText().toString();
                Customer customer = new Customer(name, age, license, number);
                databaseReference.setValue(customer);

                Intent intent = new Intent(ProfileEdit.this, ProfilePage.class);
                startActivity(intent);
            }
        });


    }
}
