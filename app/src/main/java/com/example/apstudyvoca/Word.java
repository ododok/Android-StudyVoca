package com.example.apstudyvoca;

public class Word {
  private int _id;
  private String word;
  private String meaning;

  public Word(int _id, String word, String meaning) {
    this._id = _id;
    this.word = word;
    this.meaning = meaning;
  }

  public int get_id() {
    return _id;
  }

  //id d'une ligne (de DB) ne change jamais
/*  public void set_id(int _id) {
    this._id = _id;
  }*/

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
