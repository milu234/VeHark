package com.example.vehark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "1" ;
    RelativeLayout relay1,relay2;
    Button payAndPark;




    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            relay1.setVisibility(View.VISIBLE);
            relay2.setVisibility(View.VISIBLE);
        }
    };


    EditText editTextPhone , editTextCode;
    FirebaseAuth mAuth;
    String codeSent;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mAuth = FirebaseAuth.getInstance();
        editTextPhone = findViewById(R.id.number);
        editTextCode = findViewById(R.id.code);
        payAndPark = findViewById(R.id.payandPark);


        relay1 = (RelativeLayout) findViewById(R.id.relay1);
        relay2 = (RelativeLayout) findViewById(R.id.rellay2);

        handler.postDelayed(runnable , 2000);


        payAndPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginForm.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.getCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode();
//                Toast.makeText(MainActivity.this, "Please Wait Code will be send !!", Toast.LENGTH_SHORT).show();

            }
        });



        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkVerificationCode();

            }
        });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(MainActivity.this, Main2Activity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }

//********************************************Pay and Park********************************************************************




    }



    private void checkVerificationCode() {
        String code = editTextCode.getText().toString();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                           startActivity(intent);

                            Toast.makeText(getApplicationContext(),"Login SuccesFul", Toast.LENGTH_LONG).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(getApplicationContext(),"Incorrect Code", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                });
    }
    private void sendVerificationCode() {

        String phone = editTextPhone.getText().toString().trim();

        if (phone.isEmpty()){
            editTextPhone.setError("Phone Number is Required");
            editTextPhone.requestFocus();
            return;
        }


        if (phone.length() < 10){
            editTextPhone.setError("Please Enter a Valid Phone Number ");
            editTextPhone.requestFocus();
            return;
        }


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeSent = s;
        }
    };









}
