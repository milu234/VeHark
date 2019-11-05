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

public class LoginForm extends AppCompatActivity {
    EditText txtemail , txtpassword;
    ProgressBar progressBar;
    Button login_btn;
    private FirebaseAuth firebaseAuth;

    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        signUp = findViewById(R.id.sign_up);


        txtemail = findViewById(R.id.email_id_login);
        txtpassword = findViewById(R.id.password_login);
        progressBar = findViewById(R.id.progressbar);
        login_btn = findViewById(R.id.login_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtemail.getText().toString().trim();
                String password = txtpassword.getText().toString().trim();



                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(LoginForm.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }






                if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(LoginForm.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }



                if (password.length() <  6){
                    Toast.makeText(LoginForm.this, "Password Too Short", Toast.LENGTH_SHORT).show();
                }



                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginForm.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(getApplicationContext(),PayParkDetails.class));
                                    Toast.makeText(LoginForm.this, "Login Succesful", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(LoginForm.this, "Login Failed , User does not exist", Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });





            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginForm.this, SignUp.class);
                startActivity(intent);
            }
        });


    }
}
