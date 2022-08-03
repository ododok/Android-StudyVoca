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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

public class DBHelper extends SQLiteOpenHelper {

  private static String DB_PATH = ""; //db file path //db파일 경로
  private static String DB_NAME = "vocastudy.db";  //db name!

  Context context;

  public DBHelper(@Nullable Context context) {
    super(context, DB_NAME, null, 1); //makes db.
    DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";  //db file path //db파일 경로
    this.context = context;
    databaseCheck();
  }

  private void databaseCheck(){
    File dbFile = new File(DB_PATH + DB_NAME);
    if (!dbFile.exists()) {
      dbCopy();
      Log.d("db", "DB is copied.");
    }
  }

  private void dbCopy(){
    try{
      File folder = new File(DB_PATH);
      if(!folder.exists()){
        folder.mkdir();
      }
      InputStream inputStream = context.getAssets().open(DB_NAME);
      String out_filename = DB_PATH + DB_NAME;
      OutputStream outputStream = new FileOutputStream(out_filename);
      byte[] mBuffer = new byte[1024];
      int mLength;
      while ((mLength = inputStream.read(mBuffer)) > 0) {
        outputStream.write(mBuffer,0,mLength);
      }
      outputStream.flush();;
      outputStream.close();
      inputStream.close();
    }catch(IOException e){
      e.printStackTrace();
      Log.d("db", "!!! IOException in dbCopy !!!");
    }
  }



  @Override //초기화. 테이블 생성 기능. 처음 db생성시 기본으로 들어갈 테이블.
  public void onCreate(SQLiteDatabase db) {
    Log.d("db", "onCreate (DBHelper.java)");
//    db.execSQL("CREATE TABLE example (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
//        "word TEXT, meaning TEXT);" );
//    Log.d("db", "db & table 'example' is created.");

    //여기 db.close()하지말것.
  }

  @Override
  public void onOpen(SQLiteDatabase db) {
    super.onOpen(db);
    Log.d("db", "db open (onOpen in DBHelper.java)");
  }

  @Override //테이블 업그레이드. 삭제 혹은 다시 생성.
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//    if(newVersion>1) {
//      db.execSQL("DROP TABLE IF EXISTS example");
//    }
//    onCreate(db);

  }


  //Nombre de tables dans le DB  // db의 테이블 개수를 리턴
  public int numberOfTables(){
    String sql = "SELECT * FROM sqlite_master WHERE " +
        "type = 'table' AND name != 'android_metadata' AND name != 'sqlite_sequence';";
    Cursor cursor = getReadableDatabase().rawQuery(sql,null);
    int count=cursor.getCount();
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


  //특정 검색어가 담긴 row들을 전체 테이블을 모두 검색해서 찾아주고 리스트로 리턴.
  // keyword로 word와 meaning column을 전부 검색한다. 모든 테이블에서.
  //그 다음 각 테이블별로 word나 meaning에 해당 단어가 들어가는지 검사한다.
  public ArrayList<SearchItem> search(String keyword) {

    ArrayList<SearchItem> list = new ArrayList<>();
    String[] tableNames = listOfTables();
    SQLiteDatabase db = getWritableDatabase();

    for (int i = 0; i < tableNames.length; i++) { //각각의 테이블을 돌아가면서 tableList[i]에 대입.


      String sql = "SELECT * FROM " + tableNames[i] + " WHERE word LIKE '%" + keyword + "%';";
      Cursor cursor = db.rawQuery(sql, null);
      while (cursor.moveToNext()) {
        list.add(new SearchItem(tableNames[i], cursor.getInt(0),
            cursor.getString(1), cursor.getString(2)));
      }

      sql = "SELECT * FROM " + tableNames[i] + " WHERE meaning LIKE '%" + keyword + "%';";
      cursor = db.rawQuery(sql, null);
      while (cursor.moveToNext()) {
        list.add(new SearchItem(tableNames[i], cursor.getInt(0),
            cursor.getString(1), cursor.getString(2)));
      }

      //중복처리는 안 함. word와 meaning에 같은 단어를 넣을 확률이 거의 없기 때문에.

      cursor.close();

    }


    return list;
  }//search




  //create a table
  public void createTable(@NonNull String tableName){
    //problem d'apostrophe ' --> ''
    tableName = tableName.replace("'", "''");

    tableName = checkTableName(tableName);
    SQLiteDatabase db = getWritableDatabase();
    String sql = "CREATE TABLE "+tableName+" (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
        "word TEXT, meaning TEXT);";
    db.execSQL(sql);
    Log.d("db", "table ["+tableName +"] is created.");
  }


  //테이블의 이름을 변경해주는 메서드
  public void editTableName(String oldTableName, String newTableName){
    //problem d'apostrophe ' --> ''
    newTableName = newTableName.replace("'", "''");

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
    String charsToRemove = "\n+ ×÷=/<>[]!@#₩%^&*()-\":;,?`~\\|{}€£¥$°•○●□■♤♡◇♧☆▪︎¤《》¡¿.,";
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
    //cas redondance : Overlap of the word  : 중복일 때 :
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

    //problem d'apostrophe ' --> ''
    word = word.replace("'", "''");
    meaning = meaning.replace("'", "''");

    //Non-redondance --> Inserer le data //Non-Overlap case --> insert the word.
    db = getWritableDatabase();
    sql = "INSERT INTO "+tableName+" (word, meaning) VALUES ('"+
        word+"', '"+meaning+"');";
    db.execSQL(sql);
    return true; //if the inserting is done without error, return true.
  }


  //Modify the values of a row //modifier les valeurs d'une ligne //행 하나의 값 수정.
  public boolean modifyRow(String tableName, int _id, String word, String meaning){
    //problem d'apostrophe ' --> ''
    word = word.replace("'", "''");
    meaning = meaning.replace("'", "''");

    //cas redondance : Overlap case : 중복일 때 :
    String sql = "SELECT * FROM "+tableName+";";
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.rawQuery(sql, null);
    while(cursor.moveToNext()){
      if(word.equals(cursor.getString(1)) && cursor.getInt(0) != _id){
        Log.d("db", "[insert Method error] There's same word : "+word);
        //Toast.makeText(context,"Word already exist", Toast.LENGTH_SHORT).show();
        return false; //[erreur] S'il y a le meme mot, retourne false et finit cette methode.
      }
    }

    db = getWritableDatabase();
    sql = "UPDATE "+tableName+" SET word = '"+word+"', "
        +"meaning = '"+meaning+"' WHERE _id="+_id+";";
    db.execSQL(sql);
    return true;
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









}//class DBHelper
