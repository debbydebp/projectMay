/*
 * @author ljw 
 * @since 2016.03.31
 * HttpURLConnection을 이용하여 웹에있는 내용을 토대로, 해당 정보 메소드 중 필요한 정보만을 분별 추출하여 저장하는 코드
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
      // 요청 URL
      URL url = new URL(prmURL);
      connection = (HttpURLConnection)url.openConnection();
      // 요청 방식 (GET or POST)
      connection.setRequestMethod("GET");
      // 요청 응답 타임아웃 설정
      connection.setConnectTimeout(3000);
      // 읽기 타임아웃 설정
      connection.setReadTimeout(3000);
      // 컨텐츠의 캐릭터셋이 euc-kr 이라면 (connection.getInputStream(), "euc-kr")
      
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
    wrm.Putin2Buffer("http://www.seongnam.go.kr/city/1000329/30253/bbsView.do?currentPage=1&idx=116955");//+mArrayList.get(i) 나중에 idx리스트를 for문 으로 돌린다
    //찾을 아이템에 있는 html code. string array에 저장한다.
    String [] findItem={"scope=\"row\">상호</th>","scope=\"row\">품목</th>","scope=\"row\">전화번호</th>","scope=\"row\">동</th>","scope=\"row\">구</th>","scope=\"row\">주소</th>"};
    
 
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
          System.out.println("none"); //*******************역시 야매 코드 값이 없을때 </td>가 출력됨. none 값을 sql로 전송하도록
        }else{
          System.out.println(tmpBuffer.toString()); //******************idx 페이지 조회결과 출력 받는곳. sql 넣는 함수 만들어서 넣기를
        }
      }
    }

    //
  }
}