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
import android.widget.Toast;

public class ModifyWord extends AppCompatActivity {

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

    editTextModifyWord = findViewById(R.id.editTextModifyWord);
    editTextModifyMeaning = findViewById(R.id.editTextModifyMeaning);
    editTextModifyWord.setText(word);
    editTextModifyMeaning.setText(meaning);

    //cancel button
    btnCancelModifyWord = findViewById(R.id.btnCancelModifyWord);
    btnCancelModifyWord.setOnClickListener(v->{ finish(); });

    btnModifyWord = findViewById(R.id.btnModifyWord);
    btnModifyWord.setOnClickListener(v->{
      boolean overlap;
        overlap = helper.modifyRow(tableName, _id,
            editTextModifyWord.getText().toString(),
            editTextModifyMeaning.getText().toString());

        if(!overlap) {
          Toast.makeText(getApplicationContext(), R.string.SameWord, Toast.LENGTH_SHORT).show();
        } else {
          finish();
        }
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