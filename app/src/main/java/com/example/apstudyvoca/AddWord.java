package com.example.apstudyvoca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddWord extends AppCompatActivity {
  Toolbar toolbar;
  Button btnCancelWord, btnAddWord;
  EditText editTextAddWord, editTextAddMeaning;
  String tableName;
  DBHelper helper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);//
    setContentView(R.layout.activity_add_word);//

    Intent intent = getIntent();
    tableName = intent.getStringExtra("tableName");

    helper = new DBHelper(this);

    //toolbar
    toolbar = findViewById(R.id.toolbarAddWord);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);// back button
    getSupportActionBar().setTitle("\uD83D\uDCC2 "+tableName); //title

    editTextAddWord = findViewById(R.id.editTextAddWord);
    editTextAddMeaning = findViewById(R.id.editTextAddMeaning);

    //cancel button event
    btnCancelWord = findViewById(R.id.btnCancelWord);
    btnCancelWord.setOnClickListener(v->{
      finish();
    });

    //Add Button Event
    btnAddWord = findViewById(R.id.btnAddWord);
    btnAddWord.setOnClickListener(v->{
      if(editTextAddWord.length()>0 && editTextAddMeaning.length()>0){
        //db에 입력가능하게 만들어야 함.
        String word = editTextAddWord.getText().toString();
        String meaning = editTextAddMeaning.getText().toString();
        boolean result = helper.insert(word, meaning, tableName); //proglem[todo]
        if(!result){
          Toast.makeText(getApplicationContext(),"Word already exist", Toast.LENGTH_SHORT).show();
        }else{

          Toast.makeText(getApplicationContext(),"entered correctly", Toast.LENGTH_SHORT).show();
          editTextAddWord.setText(null);
          editTextAddMeaning.setText(null);
        }

      }else{
        Toast.makeText(getApplicationContext(),
            "Please do not leave word and meaning empty", Toast.LENGTH_SHORT).show();
      }
    });

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
    //getMenuInflater().inflate(R.menu.query_table_top_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }


}//class AddWord