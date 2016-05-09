
package com.WPR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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


public class MainClass{
   
  public static void main(String[] args){

    WebReaderMacro wrm=new WebReaderMacro();
    ArrayList<String> mArrayList = new ArrayList<String>();
    String tmp=null;
    String tmp2=null;
    
    
    
  //-------------------------------------------------------------------------------------------
     Connection conn = null;
     Statement stmt = null;
     ResultSet rs = null;
     //내가 추가한 부분↓
     
     /*StringTokenizer st=null;
     int num2=0;
     int num3=0;*/
     
     //내가 추가한 부분↑
     
     try{

        //Register JDBC driver
        Class.forName("com.mysql.jdbc.Driver");

        //Open a connection
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection("jdbc:mysql://localhost/example", "root", "root");
  //-----------------------------------------------------------------------------------------------
    
/*/////////////////////////////////////////////////////최신곡-주목받는 곡 모두 가져오는 코드////////////////////////////////////////////////////////////////
        
        
        //여기에서 최신 곡 테이블을 생성해준다.
        stmt=conn.createStatement();
        stmt.executeUpdate("create table music(song varchar(70), artist varchar(70), album varchar(70));");
        
    for(int num=1; num<24; num++){//1페이지부터 23페이지까지 읽기
       //<-----idx RM-----> IDX 컨텐츠 페이지 읽기
       wrm.Putin2Buffer("http://music.naver.com/listen/newTrack.nhn?domain=DOMESTIC&page="+num);//+mArrayList.get(i) 나중에 idx리스트를 for문 으로 돌린다
       //찾을 아이템에 있는 html code. string array에 저장한다.
       String [] findItem={"class=\"ellipsis\"","alt=\"\""};
       String [] findItem2={"class=\"_title"};
       String [] findItem3={"class=\"_album"};
       
       StringTokenizer stn=new StringTokenizer(wrm.buffer.toString());
       
       String sTmp=null;
       String aTmp=null;
       String sTmp2=null;
       String aTmp2=null;
       String bTmp2=null;
       int count=1;
       int count1=0;
       
       //String s1=null;
       String s2=null;
       
       while(stn.hasMoreTokens()){
         tmp=stn.nextToken();
         //여기에서 노래 제목을 가져옴
         if(tmp.equals(findItem2[0])){
             
             stn.nextToken(); //야매
             stn.nextToken(); //야매
             
             sTmp=stn.nextToken();
             aTmp=sTmp;
             StringBuffer tmpBuffer=new StringBuffer(aTmp);
             while(!sTmp.equals("><span")){
               if(!sTmp.equals(tmpBuffer.toString())){ //겹치는거 스킵할려고 만든 야매 코드
                 tmpBuffer.append(sTmp); 
               }
               tmpBuffer.append(" ");
               sTmp=stn.nextToken(); 
             }
             if(tmpBuffer.toString().equals("><span")){
               System.out.println("none"); //*******************역시 야매 코드 값이 없을때 </td>가 출력됨. none 값을 sql로 전송하도록
             }else{
              sTmp2=tmpBuffer.toString();
              sTmp2=sTmp2.substring(7,sTmp2.length()-2);
              String smile = sTmp2;
              StringTokenizer stn2 = new StringTokenizer(smile,"\'");
              while(stn2.hasMoreTokens()){
                 
                 String s1 = stn2.nextToken().toString();
                 //System.out.println(s1);
                 if(count1<1){
                    s2 = s1;
                    System.out.println(s1);
                    sTmp2=s1;
                 }                               
                    StringBuilder sb = new StringBuilder(s2);
                 if(count1>=1){
                    
                    sb.append("`"+s1);
                    System.out.println(sb);
                    s2=sb.toString();
                    sTmp2=sb.toString();
                 }                 
                 count1++;
                 
                 //System.out.println(sTmp2); //******************idx 페이지 조회결과 출력 받는곳. sql 넣는 함수 만들어서 넣기를
              }
              count1=0;
              //System.out.println(1);
             }
           }
         //여기에서 가수 이름을 가져옴
         if(tmp.equals(findItem[0])){
           
           //stn.nextToken(); //야매
           stn.nextToken(); //야매
           
           sTmp=stn.nextToken();
           aTmp=sTmp;
           StringBuffer tmpBuffer=new StringBuffer(aTmp);
           while(!sTmp.equals("</span>")){
             if(!sTmp.equals(tmpBuffer.toString())){ //겹치는거 스킵할려고 만든 야매 코드
               tmpBuffer.append(sTmp); 
             }
             tmpBuffer.append(" ");
             sTmp=stn.nextToken(); 
           }
           if(tmpBuffer.toString().equals("</span>")){
             System.out.println("none"); //*******************역시 야매 코드 값이 없을때 </td>가 출력됨. none 값을 sql로 전송하도록
           }else{
             aTmp2=tmpBuffer.toString();
             System.out.println(aTmp2); //******************idx 페이지 조회결과 출력 받는곳. sql 넣는 함수 만들어서 넣기를
             
           }
         }
         //여기에서는 가수들 중에서 가수 이름이 한 명만 등록되어 있지 않은 가수들 이름을 가져옴(위에서는 못찾기 때문에)
         if(tmp.equals(findItem[1])){
            
            //stn.nextToken(); //야매
             //stn.nextToken(); //야매
             
             sTmp=stn.nextToken();
             aTmp=sTmp;
             StringBuffer tmpBuffer=new StringBuffer(aTmp);
             while(!sTmp.equals(">")){
               if(!sTmp.equals(tmpBuffer.toString())){ //겹치는거 스킵할려고 만든 야매 코드
                 tmpBuffer.append(sTmp); 
               }
               tmpBuffer.append(" ");
               sTmp=stn.nextToken(); 
             }
             if(tmpBuffer.toString().equals(">")){
               System.out.println("none"); //*******************역시 야매 코드 값이 없을때 </td>가 출력됨. none 값을 sql로 전송하도록
             }else{
               aTmp2=tmpBuffer.toString();
               if(count<10){
                  aTmp2=aTmp2.substring(27,aTmp2.length()-4);
               }
               else if(count<100){
                  aTmp2=aTmp2.substring(28,aTmp2.length()-4);
               }
               else if(count<1000){
                  aTmp2=aTmp2.substring(29,aTmp2.length()-4);
               }
               //System.out.println(aTmp2); //******************idx 페이지 조회결과 출력 받는곳. sql 넣는 함수 만들어서 넣기를
               
               
               String smile = aTmp2;
              StringTokenizer stn2 = new StringTokenizer(smile,"\'");
              while(stn2.hasMoreTokens()){
                 
                 String s1 = stn2.nextToken().toString();
                 //System.out.println(s1);
                 if(count1<1){
                    s2 = s1;
                    System.out.println(s1);
                    aTmp2=s1;
                 }                               
                    StringBuilder sb = new StringBuilder(s2);
                 if(count1>=1){
                    
                    sb.append("`"+s1);
                    System.out.println(sb);
                    s2=sb.toString();
                    aTmp2=sb.toString();
                 }                 
                 count1++;
                 
                 //System.out.println(sTmp2); //******************idx 페이지 조회결과 출력 받는곳. sql 넣는 함수 만들어서 넣기를
              }
              count1=0;
               
               
             }
         }
         //여기에서 앨범명을 가져옴
         if(tmp.equals(findItem3[0])){
             
             //stn.nextToken(); //야매
             stn.nextToken(); //야매
             
             sTmp=stn.nextToken();
             aTmp=sTmp;
             StringBuffer tmpBuffer=new StringBuffer(aTmp);
             while(!sTmp.equals("<td")){
               if(!sTmp.equals(tmpBuffer.toString())){ //겹치는거 스킵할려고 만든 야매 코드
                 tmpBuffer.append(sTmp); 
               }
               tmpBuffer.append(" ");
               sTmp=stn.nextToken(); 
             }
             if(tmpBuffer.toString().equals("<td")){
               System.out.println("none"); //*******************역시 야매 코드 값이 없을때 </td>가 출력됨. none 값을 sql로 전송하도록
             }else{
               bTmp2=tmpBuffer.toString();
               bTmp2=bTmp2.substring(17,bTmp2.length()-17);
               //System.out.println(bTmp2); //******************idx 페이지 조회결과 출력 받는곳. sql 넣는 함수 만들어서 넣기를
               
               String smile = bTmp2;
              StringTokenizer stn2 = new StringTokenizer(smile,"\'");
              while(stn2.hasMoreTokens()){
                 
                 String s1 = stn2.nextToken().toString();
                 //System.out.println(s1);
                 if(count1<1){
                    s2 = s1;
                    System.out.println(s1);
                    bTmp2=s1;
                 }                               
                    StringBuilder sb = new StringBuilder(s2);
                 if(count1>=1){
                    
                    sb.append("`"+s1);
                    System.out.println(sb);
                    s2=sb.toString();
                    bTmp2=sb.toString();
                 }                 
                 count1++;
                 
                 //System.out.println(sTmp2); //******************idx 페이지 조회결과 출력 받는곳. sql 넣는 함수 만들어서 넣기를
              }
              count1=0;
                              
                                                          
               
               
               stmt = conn.createStatement();
               stmt.executeUpdate("insert into music values('"+sTmp2+"','"+aTmp2+"','"+bTmp2+"')");
               count++;
             }
             System.out.println("----------------------------------------------------------------------------------");
           }
           
       }
    }
    
//====================================================최신곡-주목받는 곡 모두 가져오는 코드================================================================*/
    
//====================================================TOP100차트에서 순위,곡명,가수명 가져오는 코드=========================================================
    
        //여기에서 실시간 TOP100 테이블을 생성해준다.
        stmt=conn.createStatement();
        stmt.executeUpdate("create table realTimeChart(rank varchar(5), r_song varchar(70), r_artist varchar(70));");        
        
    for(int num=1; num<3; num++){
       //<-----idx RM-----> IDX 컨텐츠 페이지 읽기
       wrm.Putin2Buffer("http://music.naver.com/listen/top100.nhn?domain=DOMESTIC&page="+num);//+mArrayList.get(i) 나중에 idx리스트를 for문 으로 돌린다
       //찾을 아이템에 있는 html code. string array에 저장한다.
       String [] findItem4={"class=\"dsc\">2016년"};
       String [] findItem2={"class=\"_title"};
       String [] findItem={"class=\"ellipsis\"","alt=\"\""};
       String [] findItem5={"class=\"like\">"};
    
       StringTokenizer stn=new StringTokenizer(wrm.buffer.toString());
       
       String sTmp=null;
       String aTmp=null;
       String sTmp2=null;
       String aTmp2=null;
       //String bTmp2=null;
       String tTmp2=null;
       int count=0;
       int count1=0;
       int count2=0;
       
       String s2=null;
       
       while(stn.hasMoreTokens()){
            tmp=stn.nextToken();
            //여기에서 현재 차트의 기준을 가져옴
            if(tmp.equals(findItem4[0])){
                
                //stn.nextToken(); //야매
                //stn.nextToken(); //야매
                
                sTmp=stn.nextToken();
                aTmp=sTmp;
                StringBuffer tmpBuffer=new StringBuffer(aTmp);
                while(!sTmp.equals("</div>")){
                  if(!sTmp.equals(tmpBuffer.toString())){ //겹치는거 스킵할려고 만든 야매 코드
                     tmpBuffer.append(sTmp); 
                     }
                     tmpBuffer.append(" ");
                     sTmp=stn.nextToken(); 
                   }
                   if(tmpBuffer.toString().equals("</div>")){
                      System.out.println("none"); //*******************역시 야매 코드 값이 없을때 </td>가 출력됨. none 값을 sql로 전송하도록
                   }
                   else{
                      tTmp2=tmpBuffer.toString();
                      tTmp2=tTmp2.substring(0,tTmp2.length()-8);
                      //System.out.println("2016년 "+tTmp2);
                   }
            }
            
            //여기에서 가수 이름을 가져옴
            if(tmp.equals(findItem[0])){
              
               //stn.nextToken(); //야매
               stn.nextToken(); //야매
              
               sTmp=stn.nextToken();
               aTmp=sTmp;
               StringBuffer tmpBuffer=new StringBuffer(aTmp);
               while(!sTmp.equals("</span>")){
                  if(!sTmp.equals(tmpBuffer.toString())){ //겹치는거 스킵할려고 만든 야매 코드
                     tmpBuffer.append(sTmp); 
                  }
                  tmpBuffer.append(" ");
                  sTmp=stn.nextToken(); 
               }
               if(tmpBuffer.toString().equals("</span>")){
                  System.out.println("none"); //*******************역시 야매 코드 값이 없을때 </td>가 출력됨. none 값을 sql로 전송하도록
               }
               else{
                  aTmp2=tmpBuffer.toString();
                  System.out.println(aTmp2); //******************idx 페이지 조회결과 출력 받는곳. sql 넣는 함수 만들어서 넣기를
                
               }
            }
            //여기에서는 가수들 중에서 가수 이름이 한 명만 등록되어 있지 않은 가수들 이름을 가져옴(위에서는 못찾기 때문에)
            else if(tmp.equals(findItem[1])){
               
               //stn.nextToken(); //야매
                //stn.nextToken(); //야매
                
                sTmp=stn.nextToken();
                aTmp=sTmp;
                StringBuffer tmpBuffer=new StringBuffer(aTmp);
                while(!sTmp.equals(">")){
                   if(!sTmp.equals(tmpBuffer.toString())){ //겹치는거 스킵할려고 만든 야매 코드
                      tmpBuffer.append(sTmp); 
                   }
                   tmpBuffer.append(" ");
                   sTmp=stn.nextToken(); 
                }
                if(tmpBuffer.toString().equals(">")){
                   System.out.println("none"); //*******************역시 야매 코드 값이 없을때 </td>가 출력됨. none 값을 sql로 전송하도록
                }
                else{
                   aTmp2=tmpBuffer.toString();
                   if(count<10){
                      aTmp2=aTmp2.substring(27,aTmp2.length()-4);
                   }
                   else if(count<100){
                      aTmp2=aTmp2.substring(28,aTmp2.length()-4);
                   }
                   else if(count<1000){
                      aTmp2=aTmp2.substring(29,aTmp2.length()-4);
                   }
                   //System.out.println(aTmp2); //******************idx 페이지 조회결과 출력 받는곳. sql 넣는 함수 만들어서 넣기를
                  
                  
                   String smile = aTmp2;
                   StringTokenizer stn2 = new StringTokenizer(smile,"\'");
                   while(stn2.hasMoreTokens()){
                    
                      String s1 = stn2.nextToken().toString();
                      //System.out.println(s1);
                      if(count1<1){
                         s2 = s1;
                         System.out.println(s1);
                         aTmp2=s1;
                      }                               
                      StringBuilder sb = new StringBuilder(s2);
                      if(count1>=1){
                       
                         sb.append("`"+s1);
                         System.out.println(sb);
                         s2=sb.toString();
                         aTmp2=sb.toString();
                      }                 
                      count1++;
                      
                      //System.out.println(sTmp2); //******************idx 페이지 조회결과 출력 받는곳. sql 넣는 함수 만들어서 넣기를
                   }
                   count1=0;                   
                }                                
            }
            
            //여기에서 노래 제목을 가져옴
            if(tmp.equals(findItem2[0])){
                
                stn.nextToken(); //야매
                stn.nextToken(); //야매
                
                sTmp=stn.nextToken();
                aTmp=sTmp;
                StringBuffer tmpBuffer=new StringBuffer(aTmp);
                while(!sTmp.equals("><span")){
                   if(!sTmp.equals(tmpBuffer.toString())){ //겹치는거 스킵할려고 만든 야매 코드
                    tmpBuffer.append(sTmp); 
                   }
                   tmpBuffer.append(" ");
                   sTmp=stn.nextToken(); 
                }
                if(tmpBuffer.toString().equals("><span")){
                   System.out.println("none"); //*******************역시 야매 코드 값이 없을때 </td>가 출력됨. none 값을 sql로 전송하도록
                }
                else{
                   sTmp2=tmpBuffer.toString();
                   sTmp2=sTmp2.substring(7,sTmp2.length()-2);
                   String smile = sTmp2;
                   StringTokenizer stn2 = new StringTokenizer(smile,"\'");
                   while(stn2.hasMoreTokens()){
                    
                      String s1 = stn2.nextToken().toString();
                    
                      if(count1<1){
                         s2 = s1;
                         //System.out.println(s1);
                         sTmp2=s1;
                      }                               
                      StringBuilder sb = new StringBuilder(s2);
                      if(count1>=1){
                       
                         sb.append("`"+s1);
                         //System.out.println(sb);
                         s2=sb.toString();
                         sTmp2=sb.toString();
                      }                 
                      count1++;
              
                      //System.out.println(sTmp2); //******************idx 페이지 조회결과 출력 받는곳. sql 넣는 함수 만들어서 넣기를
                 }
                 count1=0;
                 count++;
                 System.out.println(count+(num-1)*50);
                 count2=count+(num-1)*50;
                 System.out.println(sTmp2);
                 
                 //stmt = conn.createStatement();
                  //stmt.executeUpdate("insert into realTimeChart values('"+count2+"','"+sTmp2+"','"+aTmp2+"')");
                }
            }
            
            //DB에 데이터 저장을 위해 임시로 가수이름 다음 값을 하나 찾아줌(사용하는 데이터는 아님)
            if(tmp.equals(findItem5[0])){
                
                stn.nextToken(); //야매
                stn.nextToken(); //야매
                stn.nextToken(); //야매
                
                sTmp=stn.nextToken();
                aTmp=sTmp;
                StringBuffer tmpBuffer=new StringBuffer(aTmp);
                while(!sTmp.equals("title=\"좋아요\"")){
                  if(!sTmp.equals(tmpBuffer.toString())){ //겹치는거 스킵할려고 만든 야매 코드
                     tmpBuffer.append(sTmp); 
                     }
                     tmpBuffer.append(" ");
                     sTmp=stn.nextToken(); 
                   }
                   if(tmpBuffer.toString().equals("title=\"좋아요\"")){
                      //System.out.println("none"); //*******************역시 야매 코드 값이 없을때 </td>가 출력됨. none 값을 sql로 전송하도록
                   }
                   else if(count!=0){                                         
                      stmt = conn.createStatement();
                     stmt.executeUpdate("insert into realTimeChart values('"+count2+"','"+sTmp2+"','"+aTmp2+"')");
                   }
            }                                                            
       }       
       System.out.println("2016년 "+tTmp2);
    }
    
//=====================================================TOP100차트에서 순위,곡명,가수명 가져오는 코드========================================================    
    
    
   }catch (SQLException ex) {
      //Handle errors for JDBC
      ex.printStackTrace();
   } catch (Exception e){
       //Handle errors for Class.forName
      e.printStackTrace();
   }
    //
  }
}