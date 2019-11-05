package com.example.vehark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText name;
    EditText license;
    EditText age;
    Button save_info;
    DatabaseReference Profileref;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String number;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.name);
        license = findViewById(R.id.license);
        age = findViewById(R.id.age);
        save_info  = findViewById(R.id.save_info);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Customer");

        final String  phone  = user.getPhoneNumber();


        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Customer");
//        Profileref = ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        save_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = phone;

                String name_c = name.getText().toString();
                String license_id = license.getText().toString();
                String age_id = age.getText().toString();


                Customer customer = new Customer(
                        name_c,
                        number,
                        license_id,
                        age_id
                );
                FirebaseDatabase.getInstance().getReference("Customer")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(ProfileActivity.this, Main2Activity.class);
                        startActivity(intent);

                    }
                });






//
//                Profileref.push().setValue(name_c);
//                Profileref.push().setValue(phone);
//                Profileref.push().setValue(license_id);
//                Profileref.push().setValue(age_id);





            }
        });



//        String license_id = license.getText().toString();
//        String age_id = license.getText().toString();



    }



}
