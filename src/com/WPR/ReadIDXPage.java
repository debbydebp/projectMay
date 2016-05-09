/*
 * @author ljw
 * @since 2016.05.06
 * Read idx page. fit for SeonNam-si homepage.
 * 
 */

package com.WPR;

import java.util.StringTokenizer;
import java.util.ArrayList;

public class ReadIDXPage{
  public ReadIDXPage(){}
  
  public String[] read(String[] findItem, WebReaderMacro wrm){
    StringTokenizer stn=new StringTokenizer(wrm.buffer.toString());
    String tmp=null;
    String sTmp=null;
    String aTmp=null;
    String[] itemArray=new String[6];
    //ArrayNumber for itemArray
    int iArrayNum=0;
    
    //stringtoken check & storage at array
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
          // ""tmpBuffer.toString()�� ����Ǿ��ִ�.""
          // mItemList.add(tmpBuffer.toString()); //�ϴ� �ּ�ó�� 0503
          System.out.println(tmpBuffer.toString()+" has been storaged at \'"+iArrayNum+"\'. ");
          itemArray[iArrayNum]=tmpBuffer.toString();
        }
        iArrayNum++;
        //iArrayNum ������Ų���� if�� ����
      }
    }
    return itemArray;
  }
}