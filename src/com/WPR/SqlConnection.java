/*
 * @author ljw 
 * @since 2016.05.03
 * sql connection info pull
 */
/*
 * [0503]
 * line 45 exception error
 * no suitable driver found for com.mysql.jdbc.Driver
 * java -cp classes;lib/mysql-connector-java-5.1.38-bin.jar com.WPR.MainClass
 * 환경변수 적용이 안됨. 이런시발 .  
 * [0505]
 * problem & solution : SqlConnection의 driver와 url을 바꿔서 인자를 받아버렸었음
 * exception problem has been resolved
 * problem : 환경변수 해결은 못함
 * solution : 그냥 mysql 커넥트를 라이브러리에 올려서 컴파일함
 */
package com.WPR;

//java.sql import 0503
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SqlConnection{
  private static String id=null;
  private static String pw=null;
  private static String url=null;
  private static String driver=null;
  private static Connection conn;
  private static Statement stmt;
  private static ResultSet rs;

  public SqlConnection(String id, String pw, String url, String driver){
    System.out.println("SQL Connection");
    this.id=id;
    this.pw=pw;
    this.url=url;
    this.driver=driver;
    try{
      Class.forName(driver);
      System.out.println("<Connecting..>");
      conn = DriverManager.getConnection(url,id,pw);

      System.out.println("Connecting is successful\n\n");

    }catch(ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }
  
  //DB Creation func
  public void createDB(String input){
    
      System.out.println("<"+input+" DB Creating..>");
    try{
      Class.forName(driver); 
      
      String sql="CREATE DATABASE "+input;
      stmt = conn.createStatement(); // Statement
      stmt.execute(sql);
      System.out.println("SQL EXECUTE: "+sql);
      
      sql="use "+input;
      stmt.execute(sql);
     System.out.println("SQL EXECUTE: "+sql);
      
      System.out.println("DB Creation is finished.\n\n");
      
      } catch(ClassNotFoundException | SQLException e) { //
        e.printStackTrace();
      }
  }
  
  public void createTable(String input){
    try{
      Class.forName(driver); 
      
      System.out.println("<Table Creating...>");
      String sql="create table "+input;
      stmt.execute(sql);
      System.out.println("SQL EXECUTE: "+sql);
      
      System.out.println("Table Creation is finished.\n\n");
      
    } catch(ClassNotFoundException | SQLException e) {//
      e.printStackTrace();
    }   
  }
  
  public void insert2Table(String table, String input){
    try{
      Class.forName(driver); 
      
      System.out.println("<Insert into Table..>");
      String sql="insert into "+table+" values "+input;
      stmt.execute(sql);
      System.out.println("SQL EXECUTE: "+sql);
      System.out.println("insert Successful\n\n");
      
    } catch(ClassNotFoundException | SQLException e) {//
      e.printStackTrace();
      System.out.println("\n**ERROR : INSERT PROCESS HAS GOT PROBLEM** \n\n");
    }   
  }
  public void insert2Table(String input){
    try{
      Class.forName(driver); 
      
      System.out.println("<Insert into Table...>");
      String sql="insert into "+input;
      stmt.execute(sql);
      System.out.println("SQL EXECUTE: "+sql);
      System.out.println("insert Successful\n\n");
      
    } catch(ClassNotFoundException | SQLException e) {//
      e.printStackTrace();
      System.out.println("\n**ERROR : INSERT PROCESS HAS GOT PROBLEM** \n\n");
    }   
  }
  
  public void disconnect(){
    try{
      Class.forName(driver); 
      
      stmt.close();
      conn.close();
    }catch(ClassNotFoundException | SQLException e) {//
      e.printStackTrace();
    }
  }
}