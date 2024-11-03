package com.EstateCrawler.app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

  public static void connect() {
    try (Connection connection = getConnection();
        Statement statement = connection.createStatement(); ) {

    } catch (SQLException SQLError) {
      System.out.println(SQLError.getMessage());
    }
  }

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection("jdbc:sqlite:estates.db");
  }

  public static void main(String args[]) {
    connect();
  }
}
