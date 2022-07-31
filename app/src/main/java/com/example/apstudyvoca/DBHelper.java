package com.example.apstudyvoca;

import static java.security.AccessController.getContext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

  Context context;

  public DBHelper(@Nullable Context context) {
    super(context, "vocadb", null, 1); //make "db".
    this.context = context;
  }


  @Override //초기화. 테이블 생성 기능. 처음 db생성시 기본으로 들어갈 테이블.
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE example (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
        "word TEXT, meaning TEXT);" );

    Log.d("db", "db & table 'example' is created.");
    //여기 db.close()하지말것.
  }

  @Override //테이블 업그레이드. 삭제 혹은 다시 생성.
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS example");
    onCreate(db);
  }

  //db의 테이블 개수를 리턴해주는 메서드
  public int numberOfTables(){
    String sql = "SELECT * FROM sqlite_master WHERE " +
        "type = 'table' AND name != 'android_metadata' AND name != 'sqlite_sequence';";
    Cursor cursor = getReadableDatabase().rawQuery(sql,null);
    int count=cursor.getCount();

    Log.d("db", "테이블 수는 "+count);
    return count;
  }

  //db의 테이블 리스트 (이름들)를 리턴해주는 메서드. //the names of all tables
  public String[] listOfTables() {
    SQLiteDatabase db = getWritableDatabase(); //포인트.테이블 이름을 얻을땐 db를 getWritableDatabase(); 로 읽기.
    Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
    int i=0;
    String[] arr = new String[numberOfTables()];
    while (cursor.moveToNext()) {
      String tableName = cursor.getString(0);
      if (tableName.equals("android_metadata") || tableName.equals("sqlite_sequence")) {
        continue;
      } else {
        arr[i++] = tableName;
      }
    }
    return arr;
  }

  //create a table
  public void createTable(String tableName){
    SQLiteDatabase db = getWritableDatabase();
    String sql = "CREATE TABLE "+tableName+" (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
        "word TEXT, meaning TEXT);";
    db.execSQL(sql);
    Log.d("db", "table ["+tableName +"] is created.");
  }

  //특정 테이블에 자료를 입력하는 insert //insert a record(row) in a specific table.
  public boolean insert(String word, String meaning, String tableName){
    //cas redondance : Overlap case : 중복일 때 :
    String sql = "SELECT word FROM "+tableName+";";
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.rawQuery(sql, null);
    while(cursor.moveToNext()){
      if(word.equals(cursor.getString(0))){
        Log.d("db", "[insert Method error] There's same word : "+word);
        //Toast.makeText(context,"Word already exist", Toast.LENGTH_SHORT).show();
        return false; //<--S'il y a le meme mot, retourne false et finit cette methode.
      }
    }
    //Non-Overlap case --> insert the word.
    db = getWritableDatabase();
    sql = "INSERT INTO "+tableName+" (word, meaning) VALUES ('"+
        word+"', '"+meaning+"');";
    db.execSQL(sql);
    return true; //if the inserting is completed, return true.
  }

  //특정 테이블에 몇 개의 row(ligne/행)이 있는지 조회
  public int tableSize(String tableName){
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM "+tableName+";",null);
    int result = cursor.getCount();

    return result;
  }


  public ArrayList<Word> getWordsFromTable(String tableName){
    ArrayList<Word> words = new ArrayList<>();
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM "+tableName+";", null);
    while(cursor.moveToNext()){
      words.add(new Word(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
    }
    return words;
  }

  //db의 특정 테이블에서 라인(행/레코드) 하나를 찾아주는 메서드
  public String[] findRow(String tableName, int _id){

    return null;
  }

  //테이블의 이름을 변경해주는 메서드
  public void editTableName(String tableName){

  }

  //테이블을 삭제하는 메서드
  public void deleteTable(String tableName){

  }



}//class DBHelper
