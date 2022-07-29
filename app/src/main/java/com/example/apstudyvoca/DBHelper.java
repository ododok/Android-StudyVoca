package com.example.apstudyvoca;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

  SQLiteDatabase db;

  public DBHelper(@Nullable Context context) {
    super(context, "vocadb", null, 1); //make "db".

  }


  @Override //초기화. 테이블 생성 기능. 처음 db생성시 기본으로 들어갈 테이블.
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("DROP TABLE IF EXISTS example");
    db.execSQL("CREATE TABLE example (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
        "word TEXT, meaning TEXT);" );
    //this.db=db;//////////////////////[???]
    Log.d("db", "db & table 'example' is created.");
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
    cursor.close();
    Log.d("db", "테이블 수는 "+count);
    return count;
  }

  //db의 테이블 리스트 (이름들)를 리턴해주는 메서드.
  public String[] listOfTables() {

    db = getWritableDatabase(); //포인트.테이블 이름을 얻을땐 db를 getWritableDatabase(); 로 읽기.
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

  //make a table
  public void createTable(String tableName){
    if(db==null) Log.d("db", "데이터베이스를 먼저 생성하시오");
    db = getWritableDatabase();
    String sql = "CREATE TABLE "+tableName+" (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
        "word TEXT, meaning TEXT);";
    db.execSQL(sql);
    Log.d("db", "table ["+tableName +"] is created.");
    db.close();
  }

  //특정 테이블에 자료를 입력하는 insert
  public void insert(String word, String meaning, String tableName){

  }

  //특정 테이블에 몇 개의 raw(ligne/행)이 있는지 조회
  public int tableSize(String tableName){
    return 0;
  }

  //db의 특정 테이블에서 라인(행/레코드) 하나를 찾아주는 메서드
  public String[] findRaw(String tableName, int rawIndex){
    return null;
  }




}//class DBHelper
