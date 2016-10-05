package com.hossam.devloper.meplace.Activity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hossam.devloper.meplace.DataEncapsulation.EncapUser;
import com.hossam.devloper.meplace.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText inputName;
    EditText inputPass;
    EditText inputEmail;
    EditText inputPhone;
    EditText inputAge;
    EditText inputHome;
    Button btnRegister;
    FirebaseAuth mAuth;
    DatabaseReference firebase;
    DatabaseReference reference;
    DatabaseReference ref_sreach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        intializeUI();

    }

    @Override
    public void onClick(View v) {

        firebase = FirebaseDatabase.getInstance().getReference();

        mAuth.createUserWithEmailAndPassword(inputEmail.getText().toString().trim(), inputPass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    reference = firebase.child("Database");
                    ref_sreach = firebase.child("Database");
                    EncapUser user = new EncapUser(inputName.getText().toString(), inputPass.getText().toString(), inputPhone.getText().toString(),
                            inputEmail.getText().toString(), inputAge.getText().toString(), inputHome.getText().toString());

                    reference.child("Users").child(mAuth.getCurrentUser().getUid()).child("information").setValue(user);

                    ref_sreach.child("Search").child("Phone").child(inputPhone.getText().toString()).setValue(mAuth.getCurrentUser().getUid());

                    Toast.makeText(RegisterActivity.this, "Welcome in MePlace ", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(RegisterActivity.this, "Wrong with Regsiter", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

    private void intializeUI() {

        inputName = (EditText) findViewById(R.id.name_text);
        inputPass = (EditText) findViewById(R.id.pass_text);
        inputEmail = (EditText) findViewById(R.id.email_text);
        inputPhone = (EditText) findViewById(R.id.phone_text);
        inputAge = (EditText) findViewById(R.id.age_text);
        inputHome = (EditText) findViewById(R.id.home_text);
        btnRegister = (Button) findViewById(R.id.reg_btn);
        btnRegister.setOnClickListener(this);
    }
}
