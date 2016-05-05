/*
 * @author ljw 
 * @since 2016.03.31
 * HttpURLConnection�� �̿��Ͽ� �����ִ� ������ ����, �ش� ���� �޼ҵ� �� �ʿ��� �������� �к� �����Ͽ� �����ϴ� �ڵ�
 * 
 * [0404Finished] list RM 
 * [0404Finished] idx RM
 * [0505Finished]SQL CONVERT
 * []DB&table creation redundancy problem
 * [0505compiled but not tested]list RM  class-nizing
 * []idx RM class-nizing
 *
 */

package com.WPR;


import java.util.StringTokenizer;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainClass{
  public static void main(String[] args){    
    WebReaderMacro wrm=new WebReaderMacro();
    ListIDXExtrator listIDXExtrator=new ListIDXExtrator();
    ArrayList<String> mArrayList = new ArrayList<String>();
    String tmp=null;
    
    //SQL Connect
    SqlConnection sql=new SqlConnection("root","1q2w3e4r","jdbc:mysql://localhost:3306/","com.mysql.jdbc.Driver");
    //SQL DB CREATION
    sql.createDB("placeDB");
    sql.createTable("placeTable(idx int, name varchar(16), product varchar(16), tellN varchar(16), dong varchar(8), goo varchar(8), address varchar(64), link varchar(64))");
    
    //List���� IDX �ѹ��� �̾ƿ´�. 
    mArrayList = listIDXExtrator.extract(1,1,wrm); //first page to last page
    
    //<-----idx RM-----> IDX ������ ������ �б�
    int ItemidxNumber=116955;//idxNumber. �Ʒ� sql�� insert ȣ���Ҷ��� ���δ�.
    wrm.Putin2Buffer("http://www.seongnam.go.kr/city/1000329/30253/bbsView.do?idx=116955");//+mArrayList.get(i) ���߿� idx����Ʈ�� for�� ���� ������
    //ã�� �����ۿ� �ִ� html code. string array�� �����Ѵ�.
    String[] findItem={"scope=\"row\">��ȣ</th>","scope=\"row\">ǰ��</th>","scope=\"row\">��ȭ��ȣ</th>","scope=\"row\">��</th>","scope=\"row\">��</th>","scope=\"row\">�ּ�</th>"};
    //��ũ�������� �������� �����۵�   itemArray�� ����
    String[] itemArray=new String[6];
    //ArrayNumber for itemArray
    int iArrayNum=0;
    
    
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
          System.out.println(tmpBuffer.toString()+iArrayNum+" next\n");
          itemArray[iArrayNum]=tmpBuffer.toString();
        }
        iArrayNum++;
        //iArrayNum ������Ų���� if�� ����
      }
    }
    
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
    
    //
    
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