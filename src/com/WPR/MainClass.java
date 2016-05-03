/*
 * @author ljw 
 * @since 2016.03.31
 * HttpURLConnection�� �̿��Ͽ� �����ִ� ������ ����, �ش� ���� �޼ҵ� �� �ʿ��� �������� �к� �����Ͽ� �����ϴ� �ڵ�
 * 
 * [0404Finished] list RM 
 * [0404Finished] idx RM
 * []SQL CONVERT
 * []list RM  class-nizing
 * []idx RM class-nizing
 *
 */
package com.WPR;


import java.util.StringTokenizer;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    //SQL Connect
    SqlConnection sql=new SqlConnection("root","1q2w3e4r","com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/");
    sql.createDB("placeDB");
    sql.createTable("placeTable(idx int, name varchar(16), tellN varchar(16), dong varchar(8), goo varchar(8), adress varchar(64), link varchar(64))");
    
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
    wrm.Putin2Buffer("http://");//+mArrayList.get(i) ���߿� idx����Ʈ�� for�� ���� ������
    //ã�� �����ۿ� �ִ� html code. string array�� �����Ѵ�.
    String[] findItem={"scope=\"row\">��ȣ</th>","scope=\"row\">ǰ��</th>","scope=\"row\">��ȭ��ȣ</th>","scope=\"row\">��</th>","scope=\"row\">��</th>","scope=\"row\">�ּ�</th>"};
    String[] itemArray=new String[5];
    int iArrayNum=0;
    //��ũ�������� �������� �����۵�   arraylist�� ���� 
    
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
          itemArray[iArrayNum]="null"; //*******************���� �߸� �ڵ� ���� ������ </td>�� ��µ�. 
        }else{
          // tmpBuffer.toString()�� ����Ǿ��ִ�.
          // mItemList.add(tmpBuffer.toString()); //�ϴ� �ּ�ó�� 0503
          itemArray[iArrayNum]=tmpBuffer.toString();
        }
        iArrayNum++;
        //iArrayNum ������Ų���� if�� ����
      }
    }
    //sql insert
    sql.insert2Table("placeTable (idx, name, tellN, dong, goo, address)",
                       "("+itemArray[0]+"), "+
                       "(\""+itemArray[1]+"\"), "+
                       "(\""+itemArray[2]+"\"), "+
                       "(\""+itemArray[3]+"\"), "+
                       "(\""+itemArray[4]+"\"), "+
                       "(\""+itemArray[5]+"\")");
    
    //
    
  sql.disconnect();
  }
}