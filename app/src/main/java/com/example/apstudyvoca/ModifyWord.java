package com.example.apstudyvoca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class ModifyWord extends AppCompatActivity {
  private static final int CODE_MODIFYWORD_TO_QUERYTABLE = 301;

  Toolbar toolbar;
  Button btnCancelModifyWord, btnModifyWord;
  EditText editTextModifyWord, editTextModifyMeaning;
  String tableName, word, meaning;
  int _id;
  DBHelper helper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_modify_word);

    Intent intent = getIntent();
    tableName = intent.getStringExtra("tableName");
    _id = intent.getIntExtra("_id", -1);
    word = intent.getStringExtra("word");
    meaning = intent.getStringExtra("meaning");

    helper = new DBHelper(this);
    //dbhelper에 modify 기능을 만들어야 함. 파라미터로 테이블이름과 id를 넣고 자료를 찾도록.
    //테이블 이름을 전송받을 방법을 만들자.

    editTextModifyWord = findViewById(R.id.editTextModifyWord);
    editTextModifyMeaning = findViewById(R.id.editTextModifyMeaning);
    editTextModifyWord.setText(word);
    editTextModifyMeaning.setText(meaning);

    //cancel button
    btnCancelModifyWord = findViewById(R.id.btnCancelModifyWord);
    btnCancelModifyWord.setOnClickListener(v->{ finish(); });

    btnModifyWord = findViewById(R.id.btnModifyWord);
    btnModifyWord.setOnClickListener(v->{
      helper.modifyRow(tableName, _id,
          editTextModifyWord.getText().toString(),
          editTextModifyMeaning.getText().toString());
      finish();
    });


    //toolbar
    toolbar = findViewById(R.id.toolbarModifyWord);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);// back button
    getSupportActionBar().setTitle("\uD83D\uDCC2 "+tableName);




  }//onCreate

  @Override //툴바액션 //toolbar item click event
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {

    switch(item.getItemId()){
      case android.R.id.home: //뒤로가기버튼 //back button

        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return super.onCreateOptionsMenu(menu);
  }

}//class ModifyWord