package com.jdbcTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class jdbcTest {
  public static void main(String[] args) {
    try {
// The newInstance() call is a work around for some
// broken Java implementations
      
      
      Class.forName("com.mysql.jdbc.Driver");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  } 
}