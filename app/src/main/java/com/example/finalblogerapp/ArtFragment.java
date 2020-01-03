package com.example.finalblogerapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtFragment extends Fragment {
    ArrayList<PostModel> postList;
    RecyclerView postRecyclerView;
    BlogerAdapter blogerAdapter;


    public ArtFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_art, container, false);

        postRecyclerView=v.findViewById(R.id.art_recycler);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

     fillArray();
        return v;
    }

    void fillArray(){

        postList =new ArrayList<>();


        StringRequest postRequest=new StringRequest(AppServer.GET_ART_POST_URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("ArtFragment",response);

                try {

                    JSONArray postJA=new JSONArray(response);
                    Log.i("ggggggggggggggggggg",String.valueOf(postJA.length()));
                    for(int i=0;i<postJA.length();i++){
                        JSONObject JOpost=postJA.getJSONObject(i);
                        Log.i("gggggnmae",JOpost.getString("u_name"));
                        Log.i("ggggt",JOpost.getString("p__title"));
                        Log.i("gggggc",JOpost.getString("p_content"));
                        Log.i("gggggggpt",JOpost.getString("p_time"));

                        Log.i("jghjgfhdgfhds","ifdsuifgdsiufgdufgfu");


                        postList.add(new PostModel(JOpost.getString("p__title"),
                                JOpost.getString("p_content"),
                                JOpost.getString("u_name"),
                                JOpost.getString("p_time"),
                                JOpost.getString("p__id"),
                                JOpost.getString("u_id")));
                    }
                    if (postList.size()>0){

                        blogerAdapter=new BlogerAdapter(postList,getActivity());
                        postRecyclerView.setAdapter(blogerAdapter);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "unable to read art data", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.i("errrrrrooorr",error.getMessage());

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue postQueue= Volley.newRequestQueue(getActivity());

        postQueue.add(postRequest);

    }



}
