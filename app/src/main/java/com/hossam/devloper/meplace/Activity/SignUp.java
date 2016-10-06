package com.hossam.devloper.meplace.Activity;
/*
 * Copyright (c) $today.year.kareem elsayed aly,no one has the authority to edit,delete,copy any part without my permission
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hossam.devloper.meplace.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

import com.hossam.devloper.meplace.Dialogs.LoadingDialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hossam.devloper.meplace.DataEncapsulation.EncapUser;
import com.hossam.devloper.meplace.R;
import com.hossam.devloper.meplace.engine.modules.User;

import java.io.IOException;

public class SignUp extends AppCompatActivity {
    public static final int NewUserCreationID = 0;
    public static final int NewNameUpdaterID = 1;
    public static final int NewIMGUpdaterID = 2;
    public static final int UserResetID = 3;
    FirebaseUser firebaseUser;
    private Bitmap img;
    private boolean done = true;
    private Uri image;
    private LoadingDialog dialog;
    String name;

    AutoCompleteTextView mEmailView;
    EditText mPasswordView;
    EditText mconfirmPasswordView;
    EditText nameView;
    ImageView uriviewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_signup);
        init_variables();
    }

    public void init_variables() {
        dialog = new LoadingDialog(this);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.autocompletetextview_signup_email);
        mPasswordView = (EditText) findViewById(R.id.edittext_signup_password);
        mconfirmPasswordView = (EditText) findViewById(R.id.edittext_signup_confirmpassword);
        nameView = (EditText) findViewById(R.id.edittext_signup_name);
        uriviewer = (ImageView) findViewById(R.id.signUp_profilePicture);
        findViewById(R.id.button_signup_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignUp();
            }
        });

        findViewById(R.id.bring_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if select image button is pressed
                done = false;
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, 32);
            }
        });

        findViewById(R.id.button_signup_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if login text is pressed
                finish();
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });
    }

    private void attemptSignUp() {
        //if signup button is pressed
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mconfirmPasswordView.setError(null);
        nameView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmpassword = mconfirmPasswordView.getText().toString();
        name = nameView.getText().toString();
        boolean cancel = false;
        View focusView = mEmailView;
        //check for all fields have the appropriate inputs
        //starts here
        if (!TextUtils.isEmpty(name) && name.length() < 3) {
            nameView.setError("insert_longer_name");
            focusView = nameView;
            cancel = true;
        }
        if (TextUtils.isEmpty(name)) {
            nameView.setError("insert_your_name");
            focusView = nameView;
            cancel = true;
        }

        if (!password.contentEquals(confirmpassword)) {
            mconfirmPasswordView.setError("error_invalid_confirm_password");
            focusView = mconfirmPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(confirmpassword)) {
            mconfirmPasswordView.setError("reenter_yourpassword");
            focusView = mconfirmPasswordView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError("error_invalid_password");
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("enter_valid_password");
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

        //ends here

        //resizing selected image state if done or not
        if (!done) {
            cancel = true;
            Toast.makeText(this, "Wait Please...", Toast.LENGTH_SHORT).show();
        }
        //if any input is not allowed
        if (cancel) {
            focusView.requestFocus();
        } else {
            //all fields accepted as inputs
            dialog.show();
            //create user with name,password
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            firebaseUser = authResult.getUser();
                            createUserOnDatabase();
                            onSucceed(NewUserCreationID);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    onfailure(e, NewUserCreationID);
                }
            });
        }
    }

    public void uploadName() {
        //to upload name for the created user
        firebaseUser.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(name).build()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SignUp.this, "Change succeeded", Toast.LENGTH_LONG).show();
                changeNameOnDatabase(name);
                onSucceed(NewNameUpdaterID);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUp.this, "Change failed", Toast.LENGTH_LONG).show();
                onfailure(e, NewNameUpdaterID);
            }
        });
    }

    public void uploadImage() {
        //upload image if it exists
        uploadImage("", img, NewIMGUpdaterID);

    }

    public void uploadImage(final Object data, final Bitmap image, final int ID) {
        if (image != null) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference().child("images").child(firebaseUser.getUid());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            UploadTask uploadTask = storageReference.putBytes(bytes);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    onfailure(e, ID);
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    updatePic(data, taskSnapshot.getDownloadUrl().toString(), ID);


                }
            });
        } else
            updatePic(data, "", ID);
    }

    public void updatePic(final Object data, final String uri, final int ID) {
        firebaseUser.updateProfile(new UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(uri)).build()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SignUp.this, "Change succeeded", Toast.LENGTH_LONG).show();
                changePicOnDatabase(uri, ID);
                onSucceed(ID);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUp.this, "Change failed", Toast.LENGTH_LONG).show();
                onfailure(e, ID);
            }
        });
    }

    public void createUserOnDatabase() {
        User user = new User(firebaseUser.getDisplayName(), firebaseUser.getEmail(), firebaseUser.getUid(), "");
        FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid()).setValue(user);
    }

    public void changeNameOnDatabase(String name) {
        FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid()).child("name").setValue(name);
    }

    public void changePicOnDatabase(String imgUri, int ID) {
        FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid()).child("imgUri").setValue(imgUri);
    }

    public void deleteUserFromDatabase() {
        FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid()).setValue(null);
    }

    public void finalizeSignUp() {
        //finish signUp process
        dialog.dismiss();
        finish();
        //navigate to login activity
        startActivity(new Intent(SignUp.this, SplashActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //if result is complete then extract url image from the given data
            image = data.getData();
            try {
                //scale the image then pass it to the imageviewer
                img = scaleDown(MediaStore.Images.Media.getBitmap(this.getContentResolver(), image), 500, true);
                uriviewer.setImageBitmap(img);

                Toast.makeText(this, "Image Modification succeeded", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        done = true;
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        if (realImage.getWidth() > maxImageSize && realImage.getHeight() > maxImageSize) {
            float ratio = Math.min(
                    (float) maxImageSize / realImage.getWidth(),
                    (float) maxImageSize / realImage.getHeight());
            int width = Math.round((float) ratio * realImage.getWidth());
            int height = Math.round((float) ratio * realImage.getHeight());

            Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                    height, filter);
            return newBitmap;
        }
        return realImage;
    }

    private boolean isEmailValid(String email) {
        //check if the mail is valid
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //check if password is long enough to be accepted
        return password.length() > 7;
    }

    public void onfailure(Exception e, int ID) {
        //called whenever any called method fails
        switch (ID) {
            case NewUserCreationID:
                //creating new user with password ,email has failed
                //message will appear with the appropriate error
                dialog.dismiss();
                break;
            case NewNameUpdaterID:
                ////updating user name has failed
                dialog.dismiss();
                firebaseUser.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        deleteUserFromDatabase();
                        onSucceed(UserResetID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onfailure(e, UserResetID);
                    }
                });
                Toast.makeText(this, "Creation failed", Toast.LENGTH_SHORT).show();
                break;
            case NewIMGUpdaterID:
                //uploading image failed
                finalizeSignUp();
                break;
            case UserResetID:
                //reseting user data has failed
                Toast.makeText(SignUp.this, "Error Cannot be resolved \n please Contact Us", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(SignUp.this, SignUp.class));

        }
    }

    public void onSucceed(int ID) {
        //called whenever any called method succeed
        switch (ID) {
            case NewUserCreationID:
                //creating new user with password ,email succeeded
                uploadName();
                break;
            case NewNameUpdaterID:
                ////updating user name succeeded
                uploadImage();
                break;
            case NewIMGUpdaterID:
                //finish the signup steps
                finalizeSignUp();
                break;
            case UserResetID:
                //if reseting user is succeeded
                dialog.dismiss();
                Toast.makeText(SignUp.this, "Error Occuered While setting your account", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(SignUp.this, SignUp.class));
                break;
        }
    }
}



