/*
 * @author ljw
 * @since 2016.03.31
 *
 * +buffer:StringBuffer
 * +putin2Buffer(prmURL):void
 * 
 */ 


package com.WPR;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebReaderMacro {
  public StringBuffer buffer;
  public WebReaderMacro(){}
  public void Putin2Buffer(String prmURL){
    HttpURLConnection connection = null;
    try {
      // ��û URL
      URL url = new URL(prmURL);
      connection = (HttpURLConnection)url.openConnection();
      // ��û ��� (GET or POST)
      connection.setRequestMethod("GET");
      // ��û ���� Ÿ�Ӿƿ� ����
      connection.setConnectTimeout(3000);
      // �б� Ÿ�Ӿƿ� ����
      connection.setReadTimeout(3000);
      // �������� ĳ���ͼ��� euc-kr �̶�� (connection.getInputStream(), "euc-kr")
      
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
      buffer = new StringBuffer(); 
      int read = 0; 
      char[] cbuff = new char[1024]; 
      while ((read = reader.read(cbuff)) > 0) {
        buffer.append(cbuff, 0, read); 
      }
      int i=0;
      reader.close();    
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }
}

/*
 * 
 * codeFinished 16.04.03
 * file-classifying at 16.05.03
 */