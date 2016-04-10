/*
 * @author ljw 
 * @since 2016.03.31
 * HttpURLConnection�� �̿��Ͽ� �����ִ� ������ ����, �ش� ���� �޼ҵ� �� �ʿ��� �������� �к� �����Ͽ� �����ϴ� �ڵ�
 * 
 * [0404F] list RM 
 * [0404F] idx RM
 * []SQL CONVERT
 * []list RM  class-nizing
 * []idx RM class-nizing
 *  
 */
package com.WPR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.StringTokenizer;

import java.util.ArrayList;



class WebReaderMacro {
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
 //����� ���Ҵ�. �Ⱦ���. ���� ����� StringTokenize�� ������
 class StreamStringFinder{
 public StreamString buffer;
 public StreamStringFinder(){}
 public void Find(StreamString prmString, String find){
 String tmp;
 for(int i=0;i<prmString.buffer.length()-find.length();i++){
 tmp=prmString.buffer.substring(i,i+find.length());
 if(tmp.equals(find)){
 tmp=wrm.buffer.substring(i+10,i+16);
 }
 }
 }
 }
 
 */


public class MainClass{
  public static void main(String[] args){    
    WebReaderMacro wrm=new WebReaderMacro();
    ArrayList<String> mArrayList = new ArrayList<String>();
    String tmp=null;
    /*
     //<-----list RM-----> list���� idx �ѹ� �̾ƿ���
     //�˻��� ��� �������� �� ����
     int LastPageNUM=1;
     int FirstPageNUM=1;
     
     //��� ��ĳ�� (for idx number)
     for(int j=FirstPageNUM;j<LastPageNUM+1;j++){
     wrm.Putin2Buffer("http://www.seongnam.go.kr/city/1000329/30253/bbsList.do?currentPage="+j);
     
     for(int i=0;i<wrm.buffer.length()-12;i++){
     tmp=wrm.buffer.substring(i,i+12);
     if(tmp.equals("<a href="+'"'+"#11")){
     //tmp�� �˻�� ������Ű�� tmp�� ������ ���� ������
     tmp=wrm.buffer.substring(i+10,i+16);
     //���� tmp�� idx NUM ������ ������ִ�
     mArrayList.add(tmp); //mArrayList�� idx�ѹ��� String���� ����
     //�ڷḦ �ѱ�� �� phase�� �ѱ�
     }
     }
     }
     System.out.println(mArrayList.size()+" of idx numver has found at page 1 to "+LastPageNUM+"...\n"+mArrayList);
     */
    
    //<-----idx RM-----> IDX ������ ������ �б�
    wrm.Putin2Buffer("http://www.seongnam.go.kr/city/1000329/30253/bbsView.do?currentPage=1&idx=116955");//+mArrayList.get(i) ���߿� idx����Ʈ�� for�� ���� ������
    //ã�� �����ۿ� �ִ� html code. string array�� �����Ѵ�.
    String [] findItem={"scope=\"row\">��ȣ</th>","scope=\"row\">ǰ��</th>","scope=\"row\">��ȭ��ȣ</th>","scope=\"row\">��</th>","scope=\"row\">��</th>","scope=\"row\">�ּ�</th>"};
    
 
    StringTokenizer stn=new StringTokenizer(wrm.buffer.toString());
    String sTmp=null;
    String aTmp=null;
    
    while(stn.hasMoreTokens()){
      tmp=stn.nextToken();
      if(tmp.equals(findItem[0])||tmp.equals(findItem[1])||tmp.equals(findItem[2])||
         tmp.equals(findItem[3])||tmp.equals(findItem[4])||tmp.equals(findItem[5])){
        
        stn.nextToken(); //�߸�
        stn.nextToken(); //�߸�
        sTmp=stn.nextToken();
        aTmp=sTmp;
        StringBuffer tmpBuffer=new StringBuffer(aTmp);
        while(!sTmp.equals("</td>")){
          if(!sTmp.equals(tmpBuffer.toString())){ //��ġ�°� ��ŵ�ҷ��� ���� �߸� �ڵ�
            tmpBuffer.append(sTmp); 
          }
          tmpBuffer.append(" ");
          sTmp=stn.nextToken(); 
        }
        if(tmpBuffer.toString().equals("</td>")){
          System.out.println("none"); //*******************���� �߸� �ڵ� ���� ������ </td>�� ��µ�. none ���� sql�� �����ϵ���
        }else{
          System.out.println(tmpBuffer.toString()); //******************idx ������ ��ȸ��� ��� �޴°�. sql �ִ� �Լ� ���� �ֱ⸦
        }
      }
    }

    //
  }
}