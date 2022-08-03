package com.example.apstudyvoca;

public class SearchItem {
  String tableName;
  int _id;
  String word;
  String meaning;

  public SearchItem(String tableName, int _id, String word, String meaning) {
    this.tableName = tableName;
    this._id = _id;
    this.word = word;
    this.meaning = meaning;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public int get_id() {
    return _id;
  }

  //I don't need set_id method because we cannot change _id on db.
  public void set_id(int _id) {
    this._id = _id;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public String getMeaning() {
    return meaning;
  }

  public void setMeaning(String meaning) {
    this.meaning = meaning;
  }
}
