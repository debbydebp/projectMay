/*
 * @author ljw
 * @since 2016.05.06
 * 
 * NOTE : ������ Ȩ������  ��ũ���� �����´�.
 * link:String
 * default ���� ������Ȩ������
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

    
    
    //��� ��ĳ�� (for idx number)
    for(int j=FirstPageNUM;j<LastPageNUM+1;j++){
      wrm.Putin2Buffer(link+j);
      
      for(int i=0;i<wrm.buffer.length()-12;i++){
        tmp=wrm.buffer.substring(i,i+12);
        if(tmp.equals("<a href="+'"'+"#11")){
          //tmp�� �˻�� ������Ű�� tmp�� ������ ���� ������
          tmp=wrm.buffer.substring(i+10,i+16);
          //���� tmp�� idx NUM ������ ������ִ�
          tmpArray.add(tmp); //tmpArray�� idx�ѹ��� String���� ����
          //�ڷḦ �ѱ�� �� phase�� �ѱ�
        }
      }
    }
    System.out.println(tmpArray.size()+" of idx number has found at page "+FirstPageNUM+" to "+LastPageNUM+"...\n"+tmpArray);
    
    return tmpArray;
  }
}