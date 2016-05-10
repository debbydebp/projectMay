/*
 * @author ljw
 * @since 2016.05.06
 * 
 * NOTE : 성남시 홈페이지  링크에서 가져온다.
 * link:String
 * default 값은 성남시홈페이지
 * +extract(int,int):ArrayList<String>
 * 
 */

package com.WPR;

import java.util.ArrayList;

public class ListIDXExtrator{
  private WebReaderMacro wrm;
  public ListIDXExtrator(){
    wrm=new WebReaderMacro();
  }
  
  public ArrayList<String> extract(int FirstPageNUM, int LastPageNUM){
    ArrayList<String> tmpArray = new ArrayList<String>();
    String tmp=null;
    String link="http://www.seongnam.go.kr/city/1000329/30253/bbsList.do?currentPage=";

    
    
    //목록 스캐닝 (for idx number)
    for(int j=FirstPageNUM;j<LastPageNUM+1;j++){
      wrm.Putin2Buffer(link+j);
      
      for(int i=0;i<wrm.buffer.length()-12;i++){
        tmp=wrm.buffer.substring(i,i+12);
        if(tmp.equals("<a href="+'"'+"#11")){
          //tmp가 검색어를 충족시키면 tmp에 다음과 같이 저장함
          tmp=wrm.buffer.substring(i+10,i+16);
          //이제 tmp는 idx NUM 정보가 담겨져있다
          tmpArray.add(tmp); //tmpArray에 idx넘버를 String으로 저장
          //자료를 넘기고 현 phase를 넘김
        }
      }
    }
    System.out.println(tmpArray.size()+" of idx number has found at page "+FirstPageNUM+" to "+LastPageNUM+"...\n"+tmpArray);
    
    return tmpArray;
  }
}