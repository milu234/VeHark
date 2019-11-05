package com.example.vehark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    EditText txtemail, txtpassword , txtcnf_password;
    Button sign_up;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtemail = findViewById(R.id.email_id_signup);
        txtpassword = findViewById(R.id.password_signup);
        txtcnf_password= findViewById(R.id.cnf_password_signup);
        progressBar = findViewById(R.id.progressbar);
        sign_up = findViewById(R.id.signUp_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String email = txtemail.getText().toString().trim();
                String password = txtpassword.getText().toString().trim();
                String cnf_password = txtcnf_password.getText().toString().trim();


                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(SignUp.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }






                if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(SignUp.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(cnf_password))
                {
                    Toast.makeText(SignUp.this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (password.length() <  6){
                    Toast.makeText(SignUp.this, "Password Too Short", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(view.VISIBLE);

                if (password.equals(cnf_password))
                {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    progressBar.setVisibility(view.GONE);
                                    if (task.isSuccessful()) {

                                        startActivity(new Intent(getApplicationContext(),PayParkDetails.class));
                                        Toast.makeText(SignUp.this, "Regitration Succesful", Toast.LENGTH_SHORT).show();

                                    } else {

                                        Toast.makeText(SignUp.this, "Authentication Failed", Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });
                }




            }
        });
    }
}
