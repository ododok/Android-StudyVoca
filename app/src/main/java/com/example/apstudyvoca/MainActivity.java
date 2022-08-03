package com.example.apstudyvoca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

  BottomFragmentVocas bottomFragmentVocas;
  BottomFragmentSearch bottomFragmentSearch;
  BottomFragmentInfo bottomFragmentInfo;
  Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);//
    setContentView(R.layout.activity_main);//
    //setTitle("Android Project - Study Vocas");

    initBottomFragments();

    toolbar = findViewById(R.id.toolBarMain);
    setSupportActionBar(toolbar);
    getSupportActionBar().setIcon(R.drawable.icon_minicat);
    getSupportActionBar().setTitle(" Voca Study"); //title

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

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    initBottomFragments(); //무언가 뷰를 refresh할 수 있는, 뷰를 띄워주는 메서드를 넣는 자리.
  }

}//class MainActivity