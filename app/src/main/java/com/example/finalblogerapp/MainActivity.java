package com.example.finalblogerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    ArrayList<PostModel> postModels=new ArrayList<>();
    private static final String EXTRA_POST_ID ="com.example.finalblogerapp.post_is";
    Toolbar toolBar;
    TabLayout tabLayout;
    ViewPager viewPager;
    PostPagerActivity viewPagerAdapter;
    String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolBar=findViewById(R.id.main_toolbar);
        setSupportActionBar(toolBar);

        viewPager = findViewById(R.id.view_pager);
        viewPagerAdapter = new PostPagerActivity(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout =  findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        uID = getIntent().getStringExtra("currentUserId");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.setting)
        {
            Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.guide)
        {
            Toast.makeText(this, "guideline", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.add_post)
        {
           Intent i =new Intent(this,NewPostAc.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }



    }
