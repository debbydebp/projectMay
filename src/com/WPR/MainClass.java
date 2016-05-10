/*
 * @author ljw 
 * @since 2016.03.31
 * HttpURLConnection을 이용하여 웹에있는 내용을 토대로, 해당 정보 메소드 중 필요한 정보만을 분별 추출하여 저장하는 코드
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
    
    //<-----idx RM-----> IDX 컨텐츠 페이지 읽기
    i=0;
    while(i!=mArrayList.size()){
      int ItemidxNumber=Integer.parseInt(mArrayList.get(i));//idxNumber. 아래 sql로 insert 호출할때도 쓰인다.
      wrm.Putin2Buffer("http://www.seongnam.go.kr/city/1000329/30253/bbsView.do?idx="+ItemidxNumber);//+mArrayList.get(i) 나중에 idx리스트를 for문 으로 돌린다
      //찾을 아이템에 있는 html code. string array에 저장한다.
      String[] findItem={"scope=\"row\">상호</th>","scope=\"row\">품목</th>","scope=\"row\">전화번호</th>","scope=\"row\">동</th>","scope=\"row\">구</th>","scope=\"row\">주소</th>"};
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
 //만들다 말았다. 안쓸듯. 굳이 만들면 StringTokenize로 만들자
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