/*
 * @author ljw 
 * @since 2016.03.31
 * HttpURLConnection을 이용하여 웹에있는 내용을 토대로, 해당 정보 메소드 중 필요한 정보만을 분별 추출하여 저장하는 코드
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
     //<-----list RM-----> list에서 idx 넘버 뽑아오기
     //검색할 목록 페이지의 수 적기
     int LastPageNUM=1;
     int FirstPageNUM=1;
     
     //목록 스캐닝 (for idx number)
     for(int j=FirstPageNUM;j<LastPageNUM+1;j++){
     wrm.Putin2Buffer("http://www.seongnam.go.kr/city/1000329/30253/bbsList.do?currentPage="+j);
     
     for(int i=0;i<wrm.buffer.length()-12;i++){
     tmp=wrm.buffer.substring(i,i+12);
     if(tmp.equals("<a href="+'"'+"#11")){
     //tmp가 검색어를 충족시키면 tmp에 다음과 같이 저장함
     tmp=wrm.buffer.substring(i+10,i+16);
     //이제 tmp는 idx NUM 정보가 담겨져있다
     mArrayList.add(tmp); //mArrayList에 idx넘버를 String으로 저장
     //자료를 넘기고 현 phase를 넘김
     }
     }
     }
     System.out.println(mArrayList.size()+" of idx numver has found at page 1 to "+LastPageNUM+"...\n"+mArrayList);
     */
    
    //<-----idx RM-----> IDX 컨텐츠 페이지 읽기
    wrm.Putin2Buffer("http://");//+mArrayList.get(i) 나중에 idx리스트를 for문 으로 돌린다
    //찾을 아이템에 있는 html code. string array에 저장한다.
    String[] findItem={"scope=\"row\">상호</th>","scope=\"row\">품목</th>","scope=\"row\">전화번호</th>","scope=\"row\">동</th>","scope=\"row\">구</th>","scope=\"row\">주소</th>"};
    String[] itemArray=new String[5];
    int iArrayNum=0;
    //토크나이저로 가져오는 아이템들   arraylist에 저장 
    
    StringTokenizer stn=new StringTokenizer(wrm.buffer.toString());
    String sTmp=null;
    String aTmp=null;
    
    while(stn.hasMoreTokens()){
      tmp=stn.nextToken();
      if(tmp.equals(findItem[0])||tmp.equals(findItem[1])||tmp.equals(findItem[2])||
         tmp.equals(findItem[3])||tmp.equals(findItem[4])||tmp.equals(findItem[5])){
        
        stn.nextToken(); //야매
        stn.nextToken(); //야매
        sTmp=stn.nextToken();
        aTmp=sTmp;
        StringBuffer tmpBuffer=new StringBuffer(aTmp);
        
        while(!sTmp.equals("</td>")){
          if(!sTmp.equals(tmpBuffer.toString())){ //겹치는거 스킵할려고 만든 야매 코드
            tmpBuffer.append(sTmp); 
          }
          tmpBuffer.append(" ");
          sTmp=stn.nextToken(); 
        }
        
        if(tmpBuffer.toString().equals("</td>")){
          itemArray[iArrayNum]="null"; //*******************역시 야매 코드 값이 없을때 </td>가 출력됨. 
        }else{
          // tmpBuffer.toString()에 저장되어있다.
          // mItemList.add(tmpBuffer.toString()); //일단 주석처리 0503
          itemArray[iArrayNum]=tmpBuffer.toString();
        }
        iArrayNum++;
        //iArrayNum 증가시킨다음 if문 종결
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