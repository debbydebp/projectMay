package com.jdbcTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class jdbcTest {
  public static void main(String[] args) {
    Connection conn;
    try {
// The newInstance() call is a work around for some
// broken Java implementations
      
      //root","1q2w3e4r","com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/
      Class.forName("com.mysql.jdbc.Driver");
      System.out.println("Connecting..");
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","1q2w3e4r");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  } 
}