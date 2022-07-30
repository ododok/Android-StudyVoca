package com.example.apstudyvoca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;

public class QueryTable extends AppCompatActivity {

  String tableName;
  Toolbar toolbar;
  RecyclerView recyclerViewWords;
  WordAdapter wordAdapter;
  DBHelper helper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {//
    super.onCreate(savedInstanceState);//
    setContentView(R.layout.activity_query_table);//


    Intent intent =getIntent();
    tableName = intent.getStringExtra("tableName");

    toolbar = findViewById(R.id.toolbarQueryTable);
    setSupportActionBar(toolbar);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);// back button
    getSupportActionBar().setTitle(tableName); //title

    helper = new DBHelper(this); //activity니까 this. fragment였으면 getContext().

    //단어들을 RecyclerView로 보여주기.
    //개별 단어 클릭하면 수정 삭제 가능하도록 하기.
    recyclerViewWords = findViewById(R.id.recyclerViewWords0);
    LinearLayoutManager layoutManager =
        new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    recyclerViewWords.setLayoutManager(layoutManager);



    //helper에 특정 테이블의 word와 meaning을 받아오는 메서드 작성하기.
    ArrayList<Word> words = helper.getWordsFromTable(tableName);
    wordAdapter = new WordAdapter(words);




    recyclerViewWords.setAdapter(wordAdapter);

  }//onCreate

  @Override //툴바액션 //toolbar item click event
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {

    switch(item.getItemId()){
      case android.R.id.home: //뒤로가기버튼
        finish();
        return true;
      case R.id.itemAdd: //단어 입력 버튼
        Toast.makeText(this, "test item 눌림", Toast.LENGTH_SHORT).show();
        return true;
      case R.id.itemStudy:  //List (단어장) 스터디 버튼 (같은 버튼을 더 눈에 띄는 곳에 넣을 예정)
        Toast.makeText(this, "study item 클릭", Toast.LENGTH_SHORT).show();
        return true;

    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.query_table_top_menu, menu);
    return super.onCreateOptionsMenu(menu);
    //return true;
  }

}//class QueryTable