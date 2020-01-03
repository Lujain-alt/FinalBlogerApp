package com.example.finalblogerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText userMail, userPassword;
    private Button btnLogin,btnSign;
    private ProgressBar loginProgress;
    private Intent HomeActivity;
    private Intent PostDetailsActivity;
    private ImageView loginPhoto;
    static String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userMail = findViewById(R.id.login_mail);
        userPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.loginBtn);
        //loginProgress = findViewById(R.id.login_progress);
        HomeActivity = new Intent(this,com.example.finalblogerapp.LoginActivity.class);
        btnSign=findViewById(R.id.signBtn);




        //loginProgress.setVisibility(View.INVISIBLE);
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //loginProgress.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.INVISIBLE);

                Intent registerActivity = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(registerActivity);
                finish();


            }
        });


        //loginProgress.setVisibility(View.INVISIBLE);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //loginProgress.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.VISIBLE);

                final String name = userMail.getText().toString();
                final String password = userPassword.getText().toString();

                userName = userMail.getText().toString();


                if (name.isEmpty() || password.isEmpty()) {
                    showMessage("Please Verify All Field");
                    btnLogin.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.INVISIBLE);
                } else {
                    signIn(name, password);
                }

            }
        });


    }



    private void signIn(final String name, final String password) {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppServer.GET_INFO_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){

                try {

                    JSONObject data = new JSONObject(response);
                    String u_id=data.getString("u_id");
                    String u_email=data.getString("u_email");
                    Log.i("fffffffffffffffff",u_id);

                    if (response.equals("error name or password")){
                        btnLogin.setVisibility(View.VISIBLE);
                        //loginProgress.setVisibility(View.INVISIBLE);
                    }else{
                        btnLogin.setVisibility(View.INVISIBLE);
                        //loginProgress.setVisibility(View.VISIBLE);
                        Intent i=new Intent(LoginActivity.this,MainActivity.class);
                        HomeActivity.putExtra("userName", name);
                        HomeActivity.putExtra("currentUserId", u_id);
                        HomeActivity.putExtra("currentUserEmail", u_email);
                        startActivity(i);

                    }


                } catch (Exception e) {

                    e.printStackTrace();
                    showMessage(response);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(LoginActivity.this, "Connection Erorr", Toast.LENGTH_SHORT).show();

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<>();
                parameter.put("u_name", name);
                parameter.put("u_pass", password);
                return parameter;
            }
        };


        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(request);

    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onStart() {
        super.onStart();


    }
}
