package com.hossam.devloper.meplace.Activity;
/*
 * Copyright (c) $today.year.kareem elsayed aly,no one has the authority to edit,delete,copy any part without my permission
 */

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.hossam.devloper.meplace.Dialogs.LoadingDialog;


import com.google.firebase.auth.AuthResult;
import com.hossam.devloper.meplace.R;
import com.hossam.devloper.meplace.Services;


public class Login extends AppCompatActivity {

    public static final String LOG_TAG = "Login";
    public static final int LOGIN_CODE = 1;
    private LoadingDialog dialog;
    AutoCompleteTextView mEmailView;
    EditText mPasswordView;

    private boolean registered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_login);
        initVariables();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            dialog.show();
            identifedUser();
        }
    }

    public void identifedUser() {
        registered = true;
        startActivity(new Intent(Login.this, MainActivity.class));
        startService(new Intent(getApplicationContext(), Services.class));
        dialog.dismiss();
    }

    public void initVariables() {
        dialog = new LoadingDialog(this);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.autocompletetextview_login_email);
        mPasswordView = (EditText) findViewById(R.id.edittext_login_password);
        findViewById(R.id.button_login_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        findViewById(R.id.button_login_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login.this.startActivity(new Intent(Login.this, SignUp.class));
                Login.this.finish();
            }
        });
    }

    private void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError("error_invalid_password");
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError("error_field_required");
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError("error_invalid_email");
            focusView = mEmailView;
            cancel = true;
        }
        if (registered) {
            cancel = true;
            Toast.makeText(Login.this, "Please wait... \r\n Image still loading", Toast.LENGTH_LONG).show();
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            dialog.show();
            LogInToFireBase(email, password);
        }
    }

    public void LogInToFireBase(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                identifedUser();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(Login.this, ((Exception) e).getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

}


