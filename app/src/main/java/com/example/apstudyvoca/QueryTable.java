package com.example.apstudyvoca;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
  Button buttonAddWord, buttonStudy, buttonStudyInverse;


  @Override
  protected void onCreate(Bundle savedInstanceState) {//
    super.onCreate(savedInstanceState);//
    setContentView(R.layout.activity_query_table);//


    Intent intent =getIntent();
    tableName = intent.getStringExtra("tableName");

    toolbar = findViewById(R.id.toolbarQueryTable);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);// back button
    getSupportActionBar().setTitle("\uD83D\uDCC2 "+tableName); //title

    helper = new DBHelper(this); //activity니까 this. fragment였으면 getContext().

    initRecyclerView();

    buttonAddWord = findViewById(R.id.buttonAddWord);
    buttonAddWord.setOnClickListener(v->{
      addItem();
    });

    buttonStudy = findViewById(R.id.buttonStudy);
    buttonStudy.setOnClickListener(v->{
      study();
    });

    buttonStudyInverse = findViewById(R.id.buttonStudyInverse);
    buttonStudyInverse.setOnClickListener(v->{
      studyInverse();
    });



  }//onCreate



  public void initRecyclerView(){
    //단어들을 RecyclerView로 보여주기.
    //개별 단어 클릭하면 수정 삭제 가능하도록 하기.
    recyclerViewWords = findViewById(R.id.recyclerViewWords0);
    LinearLayoutManager layoutManager =
        new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    recyclerViewWords.setLayoutManager(layoutManager);
    ArrayList<Word> words = helper.getWordsFromTable(tableName);
    wordAdapter = new WordAdapter(words, tableName);
    recyclerViewWords.setAdapter(wordAdapter);
  }

  @Override //툴바액션 //toolbar item click event
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {

    switch(item.getItemId()){
      case android.R.id.home: //뒤로가기버튼 //back button
        finish();
        return true;

      case R.id.itemAdd: //상단 툴바 단어 입력 버튼 (+) //add a word event
        addItem();
        return true;

      case R.id.itemStudy:  //List (단어장) 스터디 버튼 (같은 버튼을 더 눈에 띄는 곳에 넣을까?)
        study();
        return true;

      case R.id.itemStudyInverse:
        studyInverse();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }


  public void addItem(){
    Intent intent = new Intent(getApplicationContext(), AddWord.class);
    intent.putExtra("tableName", tableName);
    startActivity(intent);
  }

  public void study(){
    if(helper.tableSize(tableName)>=1) {
      Intent intent = new Intent(getApplicationContext(), RandomStudy.class);
      intent.putExtra("tableName", tableName);
      startActivity(intent);
    }else{
      Toast.makeText(getApplicationContext(),R.string.NoWordOnTheList, Toast.LENGTH_SHORT).show();
    }
  }


  public void studyInverse() {
    if(helper.tableSize(tableName) >= 1){
      Intent intent = new Intent(getApplicationContext(), RandomStudyInverse.class);
      intent.putExtra("tableName", tableName);
      startActivity(intent);
    }else{
      Toast.makeText(getApplicationContext(),R.string.NoWordOnTheList, Toast.LENGTH_SHORT).show();

    }
  }



  @Override //(top) tool bar inflation
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.query_table_top_menu, menu);
    return super.onCreateOptionsMenu(menu);
    //return true;
  }


  @Override  //refresh.새로고침. 다른액티비티가 finish()되서 여기로 다시 돌아오면 실행될 코드.
  protected void onPostResume() {
    super.onPostResume();
    initRecyclerView();
  }


}//class QueryTable