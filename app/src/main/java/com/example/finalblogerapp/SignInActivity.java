package com.example.finalblogerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {
    ImageView ImgUserPhoto;
    static int PReqCode = 1;
    static int PickPhotoRC = 1;
    Uri pickedImgUri;

    private EditText userEmail, userPassword, userPAssword2, userName;
    private ProgressBar loadingProgress;
    private Button regBtn,login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        userEmail = findViewById(R.id.regMail);
        userPassword = findViewById(R.id.regPassword);
        userPAssword2 = findViewById(R.id.regPassword2);
        userName = findViewById(R.id.regName);
        regBtn = findViewById(R.id.regBtn);
        //loadingProgress = findViewById(R.id.regProgressBar);
        //loadingProgress.setVisibility(View.INVISIBLE);
        login=findViewById(R.id.lin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent=new Intent(SignInActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                regBtn.setVisibility(View.INVISIBLE);
                //loadingProgress.setVisibility(View.VISIBLE);

                final String email = userEmail.getText().toString();
                final String password = userPassword.getText().toString();
                final String password2 = userPAssword2.getText().toString();
                final String name = userName.getText().toString();

                if (email.isEmpty() || name.isEmpty() || password.isEmpty() || password2.isEmpty()) {

                    showMessage("Please Verify all fields");
                    regBtn.setVisibility(View.VISIBLE);
                    //loadingProgress.setVisibility(View.INVISIBLE);

                } else {

                    if (!password.equals(password2)) {
                        showMessage("password should be matched");
                        regBtn.setVisibility(View.VISIBLE);
                        //loadingProgress.setVisibility(View.INVISIBLE);
                    } else


                        CreateUserAccount(email, name, password);
                }

            }
        });


//        ImgUserPhoto = findViewById(R.id.regUserPhoto);
//
//        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (Build.VERSION.SDK_INT >= 22) {
//
//                    checkAndRequestForPermission();
//
//                } else {
//                    openGallery();
//                }
//
//            }
//        });


    }//end of onCreate


    private void CreateUserAccount(final String email, final String name, final String password) {


        StringRequest request = new StringRequest(Request.Method.POST,
                AppServer.ADD_USER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showMessage(response);
                regBtn.setVisibility(View.VISIBLE);
                //loadingProgress.setVisibility(View.INVISIBLE);

                if(response.equals("inserted successfully")){
                   // regBtn.setText("go to login");
                    Intent HomeActivity = new Intent
                            (SignInActivity.this,MainActivity.class);
                    startActivity(HomeActivity);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<>();
                parameter.put("u_name", name);
                parameter.put("u_pass", password);
                parameter.put("u_email", email);
                return parameter;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(SignInActivity.this);
        queue.add(request);

    }


    // update user photo and name
    private void updateUserInfo(final String name, Uri pickedImgUri, final /*FirebaseUser*/ Object currentUser) {

        // first we need to upload user photo to firebase storage and get url
//
//        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
//        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
//        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                // image uploaded succesfully
//                // now we can get our image url
//
//                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//
//                        // uri contain user image url
//
//
//                        UserProfileChangeRequest profleUpdate = new UserProfileChangeRequest.Builder()
//                                .setDisplayName(name)
//                                .setPhotoUri(uri)
//                                .build();
//
//
//                        currentUser.updateProfile(profleUpdate)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//
//                                        if (task.isSuccessful()) {
//                                            // user info updated successfully
//                                            showMessage("Register Complete");
//                                            updateUI();
//                                        }
//
//                                    }
//                                });
//
//                    }
//                });
//
//
//
//
//
//            }
//        });

    }

    private void updateUI() {

        Intent homeActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(homeActivity);
        finish();

    }

    // simple method to show toast message
    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }

    private void openGallery() {
        //TODO: open gallery intent and wait for user to pick an image !
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, PickPhotoRC);
    }

    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(SignInActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SignInActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(SignInActivity.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();

            } else {
                ActivityCompat.requestPermissions(SignInActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }

        } else
            openGallery();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PickPhotoRC && data != null) {

            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            pickedImgUri = data.getData();
            ImgUserPhoto.setImageURI(pickedImgUri);

        }


    }
}
