package com.hossam.devloper.meplace.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hossam.devloper.meplace.Fragments.HomeFragment;
import com.hossam.devloper.meplace.R;
import com.hossam.devloper.meplace.Services;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userEmail;
    EditText passName;
    Button btnLogin;
    Button btnReg;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        intialiazUI();
    }

    private void intialiazUI() {
        userEmail = (EditText) findViewById(R.id.email_text);
        passName = (EditText) findViewById(R.id.pass_text);
        btnLogin = (Button) findViewById(R.id.login_btn);
        btnReg = (Button) findViewById(R.id.register_btn);
        btnLogin.setOnClickListener(this);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });

    }

    @Override
    public void onClick(View v) {
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(userEmail.getText().toString(), passName.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mAuth.getCurrentUser();
                        if (task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            startService(new Intent(getApplicationContext(), Services.class));
                            System.out.println( mAuth.getCurrentUser().getUid());



                        }

                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Error in sign in", Toast.LENGTH_SHORT).show();
                            System.out.println( mAuth.getCurrentUser());
                        }


                    }
                });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    startActivity(new Intent(LoginActivity.this, MapsActivity.class));
                    Toast.makeText(LoginActivity.this, "ID user is : "+currentUser.getUid(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Error in sign in", Toast.LENGTH_SHORT).show();
                }

            }
        };

    }
}
