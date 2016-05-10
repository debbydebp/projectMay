/*
 * @author ljw 
 * @since 2016.03.31
 * HttpURLConnection�� �̿��Ͽ� �����ִ� ������ ����, �ش� ���� �޼ҵ� �� �ʿ��� �������� �к� �����Ͽ� �����ϴ� �ڵ�
 */ 


package com.WPR;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.lang.Number;

public class MainClass{
  public static void main(String[] args){
    WebReaderMacro wrm=new WebReaderMacro();
    ListIDXExtrator listIDXExtrator=new ListIDXExtrator();
    ReadIDXPage readIDXPage = new ReadIDXPage();
    int i;
    int j;
    
    
    ArrayList<String> mArrayList = new ArrayList<String>();
    ArrayList<String> tmpAL = new ArrayList<String>();
    String tmp=null;
    
    //SQL Connect
    SqlConnection sql=new SqlConnection("root","1q2w3e4r","jdbc:mysql://localhost:3306/","com.mysql.jdbc.Driver");
    //SQL DB Redundancy Check & Create
    if(!sql.checkDBRedundancy("placedb")){
      sql.createDB("placedb");
    }
    //SQL TABLE Redundancy Check & Create
    if(!sql.checkTableRedundancy("placetable","placedb")){
      sql.createTable("placetable(idx int, name varchar(50), product varchar(16), tellN varchar(16), dong varchar(8), goo varchar(8), address varchar(64), link varchar(64))");
    }
    //extract IDX number from List(wrm) (firstPage, lastPage)
    mArrayList = listIDXExtrator.extract(1,1); //first page to last page
    
    //insert redundancy check
    //idx check in db & storage at tmpAL
    sql.sqlExecute("select idx from placetable","placedb",tmpAL);
    System.out.println("table in : "+tmpAL);
    for(i=0; i<mArrayList.size();i++){
      for(j=0;j<tmpAL.size();j++){
        if((mArrayList.get(i)).equals(tmpAL.get(j))){
          //System.out.println(mArrayList.get(i)+" "+tmpAL.get(j)+" remove");
          mArrayList.remove(i);
        }
      }
    }
    System.out.println("Redundancy Check is fine \n idx number to insert "+mArrayList);
    
    //<-----idx RM-----> IDX ������ ������ �б�
    i=0;
    while(i!=mArrayList.size()){
      int ItemidxNumber=Integer.parseInt(mArrayList.get(i));//idxNumber. �Ʒ� sql�� insert ȣ���Ҷ��� ���δ�.
      wrm.Putin2Buffer("http://www.seongnam.go.kr/city/1000329/30253/bbsView.do?idx="+ItemidxNumber);//+mArrayList.get(i) ���߿� idx����Ʈ�� for�� ���� ������
      //ã�� �����ۿ� �ִ� html code. string array�� �����Ѵ�.
      String[] findItem={"scope=\"row\">��ȣ</th>","scope=\"row\">ǰ��</th>","scope=\"row\">��ȭ��ȣ</th>","scope=\"row\">��</th>","scope=\"row\">��</th>","scope=\"row\">�ּ�</th>"};
      //storage for item read from readIDXPage
      String[] itemArray=new String[6];
      
      //idx page read
      itemArray=readIDXPage.read(findItem,wrm);
      //SQL insert
      sql.insert2Table("placeTable (idx, name, product, tellN, dong, goo, address, link) values "+
                       "("+ItemidxNumber+", "+
                       "\'"+itemArray[0]+"\', "+
                       "\'"+itemArray[1]+"\', "+
                       "\'"+itemArray[2]+"\', "+
                       "\'"+itemArray[3]+"\', "+
                       "\'"+itemArray[4]+"\', "+
                       "\'"+itemArray[5]+"\', "+
                       "\'http://visionmind.net\')");
      i++; // next idx item
    }
    sql.disconnect();
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

/* [0404Finished] list RM 
 * [0404Finished] idx RM
 * [0505Finished]SQL CONVERT
 * [0508Finished]DB&table creation redundancy problem
 * [0505Finished]list RM  class-nizing
 * [0506Finisehd]idx RM class-nizing
 * [0510Finished] insert redundancy problem
 * 
 * [] main Class argument(agrs) class-nizing
 * [] db&table creation fit for design-pattern
 */