package com.example.apstudyvoca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

//fragment_random_word_view.xml과 연결된 파일.
/*
*  RandomStudy.java는 activity_random_study.xml 과 연결되어 있다.
* 뷰페이저를 위한 어댑터 파일은 RandomAdapter.java이다. 
* RandomWordView.java는 fragment_random_word_view와 연결되어 있다.
* */

public class RandomStudy extends AppCompatActivity {
  ArrayList<Word> items;
  String tableName;
  Toolbar toolbar;
  ViewPager2 viewPager;

  View viewPrevious, viewNext;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);//
    setContentView(R.layout.activity_random_study);//

    Intent intent = getIntent();
    tableName = intent.getStringExtra("tableName");
    DBHelper helper = new DBHelper(this);
    items = helper.getWordsFromTable(tableName);

    toolbar = findViewById(R.id.toolbarRandomStudy);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);// back button
    getSupportActionBar().setTitle("\uD83C\uDFB2 "+tableName); //title

    setInitViewPager();

    // 버튼대신 뷰를 누르면 작동하게 사용
    viewPrevious = findViewById(R.id.viewPrevious);
    viewPrevious.setOnClickListener(v->{ //이전 뷰 보기
      viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
    });
    viewNext = findViewById(R.id.viewNext);
    viewNext.setOnClickListener(v->{ //다음 뷰 보기
      viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
    });

  }//onCreate


  //view pager execute. 뷰페이져 실행.
  private void setInitViewPager() {

    viewPager = findViewById(R.id.viewPager);

    //데이터 로딩.
    RandomAdapter randomAdapter = new RandomAdapter(this, items);
    //프래그먼트에서는 getActivity로 참조하고, 액티비티에선 this로 참조해야 한다. 중요.

    viewPager.setAdapter(randomAdapter);
    //Adapter객체를 파라미터로 받고 ViewPager에 전달받는다.

    viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
    viewPager.setOffscreenPageLimit(items.size()); //최대 페이지 지정
    viewPager.setCurrentItem(0); //첫 시작 페이지

    //각 뷰간 간격. 뭔소린지 모르지만 복붙
    viewPager.setPageTransformer(new ViewPager2.PageTransformer(){
      @Override
      public void transformPage(@NonNull View page, float position) {
        float n = 0.8f; //0.8, 0.85로 바꿔넣어보며 마진 차이를 보자.
        float r = 1-Math.abs(position);
        page.setScaleX(n + r * 0.15f);
      }
    });

    //페이지가 바뀌면 콜백으로 자동으로 호출되는 메서드.
    viewPager.registerOnPageChangeCallback
        (new ViewPager2.OnPageChangeCallback(){
          @Override
          public void onPageSelected(int position) {
            super.onPageSelected(position);
          }
        });

  }//setInit()


  @Override //toolbar item event
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {

    switch(item.getItemId()){
      case android.R.id.home: //뒤로가기버튼 //back button
        finish();
        return true;

    }
    return super.onOptionsItemSelected(item);
  }
}//class RandomStudy