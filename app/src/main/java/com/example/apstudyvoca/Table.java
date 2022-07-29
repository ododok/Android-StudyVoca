package com.example.apstudyvoca;

public class Table {

  private String tableName;
  private int numberOfRaw = 0;

  public Table(String tableName, int numberOfRaw) {
    this.tableName = tableName;

  }

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
