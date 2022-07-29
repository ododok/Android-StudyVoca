package com.example.apstudyvoca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

  BottomFragmentVocas bottomFragmentVocas;
  BottomFragmentSearch bottomFragmentSearch;
  BottomFragmentInfo bottomFragmentInfo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);//
    setContentView(R.layout.activity_main);//
    setTitle("Android Project - Study Vocas");

    initBottomFragments();



  }//onCreate


  private void initBottomFragments() {
    bottomFragmentVocas = new BottomFragmentVocas();
    bottomFragmentSearch = new BottomFragmentSearch();
    bottomFragmentInfo = new BottomFragmentInfo();

    //기본 fragment set
    getSupportFragmentManager().beginTransaction().replace(R.id.container, bottomFragmentVocas).commit();

    BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
    bottomNavigation.setOnNavigationItemSelectedListener(item -> {
      switch(item.getItemId()){
        case R.id.bTabVocas:
          getSupportFragmentManager().beginTransaction().replace(R.id.container, bottomFragmentVocas).commit();
          return true;
        case R.id.bTabSearch:
          getSupportFragmentManager().beginTransaction().replace(R.id.container, bottomFragmentSearch).commit();
          return true;
        case R.id.bTabInfo:
          getSupportFragmentManager().beginTransaction().replace(R.id.container, bottomFragmentInfo).commit();
          return true;
      }
      return false;
    });
  }



}//class MainActivity