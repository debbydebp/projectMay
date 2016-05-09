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

//for time
import java.lang.System;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SqlConnection{
  private static String id=null;
  private static String pw=null;
  private static String url=null;
  private static String driver=null;
  private static Connection conn;
  private static Statement stmt;
  private static ResultSet rs;
  
  public SqlConnection(String id, String pw, String url, String driver){
    long time = System.currentTimeMillis(); 
    SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    String str = dayTime.format(new Date(time));
    
    System.out.println("SQL Connection : "+str);
    this.id=id;
    this.pw=pw;
    this.url=url;
    this.driver=driver;
    try{
      Class.forName(driver);
      System.out.println("<Connecting..>");
      conn = DriverManager.getConnection(url,id,pw);
      
      stmt = conn.createStatement(); // create statement
      
      System.out.println("Connecting is successful\n\n");
      
    }catch(ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }
  
  public Boolean checkDBRedundancy(String input){   
    ResultSet rs=null;
    try{
      System.out.println("<\'"+input+"\' DB Redundancy Checking..>");
      Class.forName(driver); 
      
      String sql="SHOW DATABASES";
      System.out.println("SQL EXECUTE: "+sql);
      rs=stmt.executeQuery(sql);
      while(rs.next()){
        String str=rs.getNString(1);
        if(str.equals(input)){
          System.out.println("we already have got DataBase named \'"+input+"\'\n\n");
          return true;
        }
      }
      return false;
      
    } catch(ClassNotFoundException | SQLException e) { //
      e.printStackTrace();
    }
    return false;
  }
  
  //DB Creation func
  public void createDB(String input){
    try{
      System.out.println("<\'"+input+"\' DB Creating..>");
      Class.forName(driver); 
      
      String sql="CREATE DATABASE "+input;
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
  
  public Boolean checkTableRedundancy(String input,String dbIn){   
    ResultSet rs=null;
    try{
      System.out.println("<\'"+input+"\' DB Redundancy Checking..>");
      Class.forName(driver); 
      
      
      String sql="SHOW TABLES";
      System.out.println("SQL EXECUTE: "+sql);
      stmt.execute("use "+dbIn);
      rs=stmt.executeQuery(sql);
      while(rs.next()){
        String str=rs.getNString(1);
        if(str.equals(input)){
          System.out.println("we already have got table named \'"+input+"\' at \'"+dbIn+"\'\n\n");
          return true;
        }
      }
      return false;
      
    } catch(ClassNotFoundException | SQLException e) { //
      e.printStackTrace();
    }
    return false;
  }
  
  public void createTable(String input){
    try{
      Class.forName(driver); 
      
      System.out.println("<\'"+input+"\'Table Creating...>");
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
      
      System.out.println("<Insert into \'"+table+"\'Table..>");
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

/* [0503]
 * line 45 exception error
 * no suitable driver found for com.mysql.jdbc.Driver
 * java -cp classes;lib/mysql-connector-java-5.1.38-bin.jar com.WPR.MainClass
 * ȯ�溯�� ������ �ȵ�. �̷��ù� .  
 * [0505]
 * problem & solution : SqlConnection�� driver�� url�� �ٲ㼭 ���ڸ� �޾ƹ��Ⱦ���
 * exception problem has been resolved
 * problem : ȯ�溯�� �ذ��� ����
 * solution : �׳� mysql Ŀ��Ʈ�� ���̺귯���� �÷��� ��������
 * [0508]
 * redundancy check fuction added
 * +checkTableRedundancy(String,String):booleand
 * +checkDBRedundancy(String)
 * 
 */