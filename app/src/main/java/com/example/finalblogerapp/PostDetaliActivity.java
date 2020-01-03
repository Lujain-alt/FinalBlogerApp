package com.example.finalblogerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.Map;

public class PostDetaliActivity extends AppCompatActivity {



    EditText editTextComment;
    Button btnAddComment;
    String pID,uID,uName;
    CommentAdapter commentAdapter;
    RecyclerView RvComment;
    ArrayList<CommentModel> commentList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detali);


        pID=getIntent().getStringExtra("pID");
        uID=getIntent().getStringExtra("currentUserID");
        uName=getIntent().getStringExtra("uName");
        Toast.makeText(PostDetaliActivity.this,"("+uID+")",Toast.LENGTH_LONG).show();
        Log.i("IDDDDD",pID+" & "+uID);
        editTextComment = findViewById(R.id.post_detail_comment);
        btnAddComment = findViewById(R.id.post_detail_add_comment_btn);

        RvComment=findViewById(R.id.rv_comment);
        RvComment.setLayoutManager(new LinearLayoutManager(PostDetaliActivity.this));
        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!editTextComment.getText().toString().isEmpty()) {
                    String comment_content = editTextComment.getText().toString();


                    CommentModel comment = new CommentModel(comment_content,uID,pID);
                    addComment(comment);

                } else {
                    Toast.makeText(PostDetaliActivity.this, "please write comment", Toast.LENGTH_SHORT).show();
                    btnAddComment.setVisibility(View.VISIBLE);
                }
            }
        });

        fillCommentsRV();

        }


    public void addComment(final CommentModel commentModel) {

        StringRequest request = new StringRequest(Request.Method.POST, AppServer.ADD_COMMENT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("PostDetailActivity",response);
                if(response.equals("true"))
                {

                    Toast.makeText(PostDetaliActivity.this, "Comments Added ", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PostDetaliActivity.this, "connection Erorr" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<>();
                parameter.put("com_text",editTextComment.getText().toString());
                parameter.put("u_id",uID);
                parameter.put("p__id",pID);
                return parameter;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(PostDetaliActivity.this);
        queue.add(request);

    }



//-----------------------------------Geting Post---------------------------------------------------------------



    public void fillCommentsRV()
    {
        commentList =new ArrayList<>();
        StringRequest commentRequest=new StringRequest(AppServer.GET_COMMENTS_URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("PostDetail",response);

                try {

                    JSONArray comJA=new JSONArray(response);
                    Log.i("LLLLLLLLLL",String.valueOf(comJA.length()));
                    for(int i=0;i<comJA.length();i++){
                        JSONObject JOcom=comJA.getJSONObject(i);
                        //commentList.add(CommentModel.createComment(JOcom));

                       commentList.add(new CommentModel(
                                JOcom.getString("com_text"),
                                JOcom.getString("u_id"),
                                JOcom.getString("p__id")
                               ));
                    }
                    if (commentList.size()>0){

                        commentAdapter=new CommentAdapter(commentList,PostDetaliActivity.this);
                        RvComment.setAdapter(commentAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(PostDetaliActivity.this, "unable to read comment data", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PostDetaliActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue postQueue= Volley.newRequestQueue(PostDetaliActivity.this);

        postQueue.add(commentRequest);

    }

    }

