/*
 * @author ljw 
 * @since 2016.05.03
 * sql connection info pull
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
  /*
   //Register JDBC driver
   Class.forName("com.mysql.jdbc.Driver");
   
   //Open a connection
   System.out.println("Connecting to database...");
   conn = DriverManager.getConnection("jdbc:mysql://localhost/example", "root", "root");*/
  
  //sql connect at creation
  //SqlConnection sql=new SqlConnection("root","1q2w3e4r","com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/");
  public SqlConnection(String id, String pw, String url, String driver){
    System.out.println("SQL Connection");
    this.id=id;
    this.pw=pw;
    this.url=url;
    this.driver=driver;
    try{
      Class.forName(driver);
      System.out.println("Connecting..");
      conn = DriverManager.getConnection(url,id,pw);
      /*
       * [0503]
       * line 45 exception error
       * no suitable driver found for com.mysql.jdbc.Driver
       * java -cp classes;lib/mysql-connector-java-5.1.38-bin.jar com.WPR.MainClass
       * 환경변수 적용이 안됨. 이런시발 .  
       * 
       */
      System.out.println("Connecting is successful");

    }catch(ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }
  
  //DB Creation func
  public void createDB(String input){
    
      System.out.println(input+" DB Creating..");
    try{
      Class.forName(driver); 
      
      String sql="CREATE DATABASE "+input;
      stmt = conn.createStatement(); // Statement
      stmt.execute(sql);
      
      sql="use "+input;
      stmt.execute(sql);
      
      System.out.println(input+" DB Creation is finished.");
      
      } catch(ClassNotFoundException | SQLException e) { //
        e.printStackTrace();
      }
  }
  
  public void createTable(String input){
    try{
      Class.forName(driver); 
      
      String sql="create table "+input;
      stmt.execute(sql);
      
    } catch(ClassNotFoundException | SQLException e) {//
      e.printStackTrace();
    }   
  }
  
  public void insert2Table(String table, String input){
    try{
      Class.forName(driver); 
      
      String sql="insert into "+table+" values "+input;
      stmt.execute(sql);
      
    } catch(ClassNotFoundException | SQLException e) {//
      e.printStackTrace();
    }   
  }
  public void insert2Table(String input){
    try{
      Class.forName(driver); 
      
      String sql="insert into "+input;
      stmt.execute(sql);
      
    } catch(ClassNotFoundException | SQLException e) {//
      e.printStackTrace();
    }   
  }
  
  /*
  public void createDB(String input){
    try{
      String sql=;
      stmt.execute(sql);
    } catch(ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }   
  }
  */
  
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