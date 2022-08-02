package com.example.apstudyvoca;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Collections;

public class RandomStudyInverse extends AppCompatActivity {
  ArrayList<Word> items;
  String tableName;
  Toolbar toolbar;
  ViewPager2 viewPager;
  View viewPrevious, viewNext;
  ProgressBar progressBar;
  TextView tvProgress;


  @SuppressLint("ClickableViewAccessibility")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);//
    setContentView(R.layout.activity_random_study);//

    Intent intent = getIntent();
    tableName = intent.getStringExtra("tableName");

    DBHelper helper = new DBHelper(this);
    items = helper.getWordsFromTable(tableName);

    Collections.shuffle(items); //random 기능.
    //랜덤기능은 RandomAdapter 에서 하면 안 되고 여기서 랜덤으로 해줘야 한다.
    //왜냐면 랜덤어댑터에서는 유저가 뷰에서 선택한 포지션을 인덱스로 하여 아이템을 선택하는데
    //(정확한 수학적 이유는 모르겠지만) 랜덤으로 돌리면 중복으로 나타나는 아이템과 누락되는
    //아이템이 생겨난다. 고로 화면으로 뿌리는 Adapter 에 넘기기 전인 여기서 ArrayList를
    //랜덤으로 한 번 섞어줘야 한다.

    toolbar = findViewById(R.id.toolbarRandomStudy);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);// back button
    getSupportActionBar().setTitle("\uD83C\uDFB2 "+tableName); //title

    setInitViewPager();

    // 버튼대신 뷰를 누르면 작동하게 사용. ViewPrevious(kind of a button)
    viewPrevious = findViewById(R.id.viewPrevious);
    viewPrevious.setOnClickListener(v->{ //이전 뷰 보기
      viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
    });
    viewPrevious.setOnTouchListener((v, event) -> {
      switch(event.getAction()){
        case MotionEvent.ACTION_UP:
          viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
          return true;
      }
      return false;
    });

    //ViewNext (kind of a button)
    viewNext = findViewById(R.id.viewNext);
    viewNext.setOnClickListener(v->{ //다음 뷰 보기
      viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
    });
    viewNext.setOnTouchListener((v, event)->{
      switch(event.getAction()){
        case MotionEvent.ACTION_UP:
          viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
          return true;
      }
      return false;
    });

    //progress bar
    progressBar = findViewById(R.id.progressBar);
    tvProgress = findViewById(R.id.tvProgress);
    //페이지가 바뀌면 콜백으로 자동호출되는 익명메서드.
    viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
      @Override
      public void onPageSelected(int position) {
        super.onPageSelected(position);
        tvProgress.setText( (position+1)+" / "+items.size());
        progressBar.setMax(items.size()-1);
        progressBar.setProgress(position);
      }
    });

  }//onCreate

  //view pager execute. 뷰페이져 실행.
  private void setInitViewPager() {

    viewPager = findViewById(R.id.viewPager);

    //데이터 로딩.
    RandomAdapterInverse randomAdapterInverse = new RandomAdapterInverse(this, items);
    //프래그먼트에서는 getActivity로 참조하고, 액티비티에선 this로 참조해야 한다. 중요.

    viewPager.setAdapter(randomAdapterInverse);
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

}//class RandomStudyInverse
