package com.example.finalblogerapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SocialFragment extends Fragment {
    ArrayList<PostModel> postList;
    RecyclerView postRecyclerView;
    BlogerAdapter blogerAdapter;
    static String userPost;


    public SocialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_social, container, false);
        postRecyclerView=v.findViewById(R.id.social_recycler);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        /*NewPostActivity activity = (NewPostActivity) getActivity();

        Bundle results = activity.getMyData();
        results.getString("title");
         results.getString("desc");*/

        fillArray();
        return v;
    }

    void fillArray(){

        postList =new ArrayList<>();


        StringRequest postRequest=new StringRequest(AppServer.GET_SOCIAL_POST_URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("ggggggggggggggggggg","jhjghjghjghjghj");

                    JSONArray postJA=new JSONArray(response);
                    Log.i("ggggggggggggggggggg",String.valueOf(postJA.length()));
                    for(int i=0;i<postJA.length();i++){
                        JSONObject JOpost=postJA.getJSONObject(i);
                        Log.i("gggggnmae",JOpost.getString("u_name"));
                        Log.i("ggggt",JOpost.getString("p__title"));
                        Log.i("gggggc",JOpost.getString("p_content"));
                        Log.i("gggggggpt",JOpost.getString("p_time"));

                        Log.i("jghjgfhdgfhds","ifdsuifgdsiufgdufgfu");


                        postList.add(new PostModel(
                                JOpost.getString("p__title"),
                                JOpost.getString("p_content"),
                                JOpost.getString("u_name"),
                                JOpost.getString("p_time"),
                                JOpost.getString("p__id"),
                                JOpost.getString("u_id")));
                    }

                    blogerAdapter=new BlogerAdapter(postList,getContext());
                    postRecyclerView.setAdapter(blogerAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "unable to read social data", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue postQueue= Volley.newRequestQueue(getContext());

        postQueue.add(postRequest);

    }

}
