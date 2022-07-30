package com.example.apstudyvoca;

public class Table {

  private String tableName;
  private int numberOfRaw;

  public Table(String tableName, int numberOfRaw) {
    this.tableName = tableName;
    this.numberOfRaw = numberOfRaw;
  }

  //table엔 id가 없다. 대신 table name이 고유의 값이며 id처럼 작동한다.
  // 따라서 같은 이름의 테이블이 존재하지 않는다.

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public int getNumberOfRaw() {
    return numberOfRaw;
  }

  public void setNumberOfRaw(int numberOfRaw) {
    this.numberOfRaw = numberOfRaw;
  }
}
