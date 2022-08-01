package com.example.apstudyvoca;

import static java.security.AccessController.getContext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.common.base.CharMatcher;

import java.util.ArrayList;
import java.util.Random;

public class DBHelper extends SQLiteOpenHelper {

  Context context;

  public DBHelper(@Nullable Context context) {
    super(context, "vocadb", null, 1); //makes db.
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


  //Nombre de tables dans le DB  // db의 테이블 개수를 리턴
  public int numberOfTables(){
    String sql = "SELECT * FROM sqlite_master WHERE " +
        "type = 'table' AND name != 'android_metadata' AND name != 'sqlite_sequence';";
    Cursor cursor = getReadableDatabase().rawQuery(sql,null);
    int count=cursor.getCount();

    Log.d("db", "테이블 수는 "+count);
    return count;
  }


  //db의 테이블 리스트 (이름들)를 리턴해주는 메서드. // names of all the tables in the DB
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
  public void createTable(@NonNull String tableName){
    tableName = checkTableName(tableName);
    SQLiteDatabase db = getWritableDatabase();
    String sql = "CREATE TABLE "+tableName+" (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
        "word TEXT, meaning TEXT);";
    db.execSQL(sql);
    Log.d("db", "table ["+tableName +"] is created.");
  }


  //테이블의 이름을 변경해주는 메서드
  public void editTableName(String oldTableName, String newTableName){
    newTableName = checkTableName(newTableName);
    String sql = "ALTER TABLE "+oldTableName+" RENAME TO "+newTableName+";";
    SQLiteDatabase db = getWritableDatabase();
    db.execSQL(sql);
  }


//Verifier si le nom de table est convenant  //테이블명 적합성 체크
  public String checkTableName(String tableName){
    //Le nom d'une table ne doit pas commencé par un chiffre //테이블 이름은 숫자로 시작하면 안 됨.
    String sample = String.valueOf(tableName.charAt(0));
    if(sample.matches("[0-9]")){
      tableName = "_"+tableName;
    }

    //le nom d'une table ne peut pas avoir des caractères spéciaux.//테이블이름에 특수문자는 불가능
    String charsToRemove = "\n+ ×÷=/<>[]!@#₩%^&*()-'\":;,?`~\\|{}€£¥$°•○●□■♤♡◇♧☆▪︎¤《》¡¿.,";
    tableName = CharMatcher.anyOf(charsToRemove).removeFrom(tableName);

    //vérification de redondance  //테이블이름 중복체크.
    String[] tables = listOfTables();
    for(int i=0; i<tables.length; i++){
      if(tableName.equals(tables[i])){
        do{
          Random rand = new Random();
          tableName = tableName+rand.nextInt(10);
        }while(tableName.equals(tables[i]));
      }
    }

    return tableName;
  }


  //Supprimer une table spécifique //테이블을 삭제하는 메서드
  public void deleteTable(String tableName){
    String sql = "DROP TABLE "+tableName+";";
    SQLiteDatabase db = getWritableDatabase();
    db.execSQL(sql);
  }



  //특정 테이블에 자료를 입력하는 insert //insert a record(row) in a specific table.
  //if it returns true == success,  returns false == fail
  public boolean insert(String word, String meaning, String tableName){
    //cas redondance : Overlap case : 중복일 때 :
    String sql = "SELECT word FROM "+tableName+";";
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.rawQuery(sql, null);
    while(cursor.moveToNext()){
      if(word.equals(cursor.getString(0))){
        Log.d("db", "[insert Method error] There's same word : "+word);
        //Toast.makeText(context,"Word already exist", Toast.LENGTH_SHORT).show();
        return false; //[erreur] S'il y a le meme mot, retourne false et finit cette methode.
      }
    }
    //Non-redondance --> Inserer le data //Non-Overlap case --> insert the word.
    db = getWritableDatabase();
    sql = "INSERT INTO "+tableName+" (word, meaning) VALUES ('"+
        word+"', '"+meaning+"');";
    db.execSQL(sql);
    return true; //if the inserting is done without error, return true.
  }


  //특정 테이블에 몇 개의 row(ligne/행)이 있는지 조회
  //Nombre de ligne dans un table spécifique
  public int tableSize(String tableName){
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM "+tableName+";",null);
    int result = cursor.getCount();
    return result;
  }


  //특정 테이블의 자료를 ArrayList<Word> 타입으로 리턴.
  //Retourne un ArrayList<Word> d'une table spécifique
  public ArrayList<Word> getWordsFromTable(String tableName){
    ArrayList<Word> words = new ArrayList<>();
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM "+tableName+";", null);
    while(cursor.moveToNext()){
      words.add(new Word(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
    }
    return words;
  }

  //Modify the values of a row //modifier les valeurs d'une ligne //행 하나의 값 수정.
  public void modifyRow(String tableName, int _id, String word, String meaning){
    SQLiteDatabase db = getWritableDatabase();
    String sql = "UPDATE "+tableName+" SET word = '"+word+"', "
        +"meaning = '"+meaning+"' WHERE _id="+_id+";";
    db.execSQL(sql);
  }

  //Delete a row //supprimer une ligne //행 하나 지우기
  public void deleteRow(String tableName, int _id){
    SQLiteDatabase db = getWritableDatabase();
    String sql = "DELETE FROM "+tableName+" WHERE _id="+_id+";";
    db.execSQL(sql);
  }


  //db의 특정 테이블에서 라인(행/레코드) 하나를 찾아주는 메서드
  public String[] findRow(String tableName, int _id){

    return null;
  }



  //특정 검색어가 담긴 row들을 전체 테이블을 모두 검색해서 찾아주고 리스트로 리턴.
  public ArrayList<Word> search(String keyWord){
    return null;
  }





}//class DBHelper
