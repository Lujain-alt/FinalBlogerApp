package com.example.finalblogerapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class NewPostActivity extends AppCompatActivity {
    public static final  int RESULT_GALLARY=0;
    ImageButton imageAddBTN;
    EditText titleTxt,descTxt;
    Button postBTN;
    private Bundle results;
    private String title;
    private String desc;
    PostModel postModel=new PostModel();
    String choosenCat;
    String uID;
    String cId;
    Spinner spinOrderType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        uID = getIntent().getStringExtra("currentUserId");

        spinOrderType =  findViewById(R.id.spinner);
        List<String> categories = new ArrayList<String>();
        categories.add("Art");
        categories.add("Technology");
        categories.add("Social");



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(NewPostActivity.this,
                R.layout.item_spinner, categories);
        dataAdapter.setDropDownViewResource(R.layout.item_spinner);
        spinOrderType.setAdapter(dataAdapter);
        spinOrderType.setSelection(3);

        spinOrderType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String item = parent.getItemAtPosition(position).toString();
                if (item.equals("Art"))
                    choosenCat = "1";
                else if (item.equals("Technology"))
                    choosenCat = "2";
                else if (item.equals("Social"))
                    choosenCat = "3";

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        titleTxt=findViewById(R.id.textTitle);
        titleTxt.setText(postModel.getTitle());
        descTxt=findViewById(R.id.textDesc);
        descTxt.setText(postModel.getContent());

        imageAddBTN=findViewById(R.id.imageBtn);
        imageAddBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent gallaryIntent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallaryIntent,RESULT_GALLARY);
            }
        });

        postBTN=findViewById(R.id.postBtn);
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


                if ( !choosenCat.isEmpty() && !descTxt.getText().toString().isEmpty()&!titleTxt.getText().toString().isEmpty()) {


                    // create post Object
                    PostModel post = new PostModel(choosenCat,descTxt.getText().toString(),titleTxt.getText().toString(),uID);

                    // Add post to database

                    switch (spinOrderType.getSelectedItem().toString()){
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

                    addPost(post);

                }
                else
                {
                    Toast.makeText(NewPostActivity.this,"please do not leave post without content",Toast.LENGTH_LONG).show();
                    postBTN.setVisibility(View.VISIBLE);

                }

            }
        });


    }


   /* public Bundle getMyData() {
        Bundle hm = new Bundle();
        hm.putString("title",title);
        hm.putString("desc",desc);
        return hm;
    }*/




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_GALLARY && resultCode == RESULT_OK){
            Uri imageUri;

            imageUri = data.getData();
            imageAddBTN.setImageURI(imageUri);
        }
    }


    public void addPost(final PostModel postModel) {

        StringRequest request = new StringRequest(Request.Method.POST, AppServer.ADD_POST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true"))
                {
                    Toast.makeText(NewPostActivity.this, "Posted ", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(NewPostActivity.this, "connection Erorr" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<>();
                parameter.put("p__title",postModel.getTitle());
                parameter.put("p_category",postModel.getCategory());
                parameter.put("p_content",postModel.getContent());
                parameter.put("u_id",postModel.getCurrentUser());
                parameter.put("p__id",postModel.getpID());
                parameter.put("c_id",cId);
                return parameter;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(NewPostActivity.this);
        queue.add(request);


    }
}

