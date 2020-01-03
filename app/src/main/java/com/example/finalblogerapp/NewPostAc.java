package com.example.finalblogerapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.finalblogerapp.NewPostActivity.RESULT_GALLARY;

public class NewPostAc extends AppCompatActivity {
    ImageButton imageAddBTN;
    EditText titleTxt,descTxt;
    Button postBTN;
    List<String> categories;
    Spinner spinner;
    PostModel postModel=new PostModel();
    String choosenCat;
    String uID;
    String cId;

    ArtFragment artFragment=new ArtFragment();
    TechonlogyFragment techonlogyFragment=new TechonlogyFragment();
    SocialFragment socialFragment=new SocialFragment();
    BlogerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post2);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        titleTxt=findViewById(R.id.textTitle);
        descTxt=findViewById(R.id.textDesc);
        imageAddBTN=findViewById(R.id.imageBtn);
        imageAddBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallaryIntent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallaryIntent,RESULT_GALLARY);
            }
        });
        postBTN=findViewById(R.id.postBtn);

        List<String> categories = new ArrayList<String>();
        categories.add("Art");
        categories.add("Technology");
        categories.add("Social");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (spinner.getSelectedItem().toString()){
                    case "Art" :
                        cId = "1";
                        break;
                    case "Technology" :
                        cId = "2";
                        break;
                    case "Social" :
                        cId = "3";
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        titleTxt.setText(postModel.getTitle());
        descTxt.setText(postModel.getContent());




        postBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final  String PostTitle = titleTxt.getText().toString().trim();
                final String PostDesc = descTxt.getText().toString().trim();
                //final String Cat_name

                if (TextUtils.isEmpty(PostTitle)) {
                    titleTxt.setError("Please write Title of your post");
                }else if (TextUtils.isEmpty(PostDesc)) {
                    descTxt.setError("Please write description of your post");
                }


                if ( !cId.isEmpty() && !descTxt.getText().toString().isEmpty()&!titleTxt.getText().toString().isEmpty()) {


                    // create post Object
                    PostModel post = new PostModel(choosenCat,descTxt.getText().toString(),titleTxt.getText().toString(),uID);

                    // Add post to database

                    getUserId();
                    addPost(post);
//                    Toast.makeText(NewPostAc.this, "added", Toast.LENGTH_SHORT).show();
                    Toast.makeText(NewPostAc.this, LoginActivity.userName, Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(NewPostAc.this,"please do not leave post without content",Toast.LENGTH_LONG).show();
                    postBTN.setVisibility(View.VISIBLE);

                }


            }
        });




        }


    public void addPost(final PostModel postModel) {

        StringRequest request = new StringRequest(Request.Method.POST, AppServer.ADD_POST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true"))
                {
                    Toast.makeText(NewPostAc.this, "Posted ", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(NewPostAc.this, "connection Erorr" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<>();
                parameter.put("p__title",titleTxt.getText().toString());
                parameter.put("p_content",descTxt.getText().toString());
                parameter.put("u_id",uID);
                parameter.put("c_id",cId);
                return parameter;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(NewPostAc.this);
        queue.add(request);

    }


    void getUserId(){

        StringRequest postRequest=new StringRequest(Request.Method.POST,AppServer.GET_USERID_URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray postJA = new JSONArray(response);
                    for(int i=0;i<postJA.length();i++){
                        JSONObject JOpost=postJA.getJSONObject(i);
                        int a = JOpost.getInt("id");
                        uID = String.valueOf(a);
//                        Toast.makeText(NewPostAc.this, uID, Toast.LENGTH_SHORT).show();
                        Log.d("qqq",response);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(NewPostAc.this, "unable to read tech data", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewPostAc.this, "connection Erorr" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<>();
                parameter.put("name",LoginActivity.userName);

                return parameter;
            }
        };

        RequestQueue postQueue= Volley.newRequestQueue(NewPostAc.this);

        postQueue.add(postRequest);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_GALLARY && resultCode == RESULT_OK){
            Uri imageUri;

            imageUri = data.getData();
            imageAddBTN.setImageURI(imageUri);
        }
    }
}
