import java.net.*;
import java.io.*;

class WPReader{
  public WPReader(String urlString, String searchPrm){
    try{
      
      URL prmURL = new URL(urlString);
      HttpURLConnection huc = (HttpURLConnection)prmURL.openConnection();
      System.out.println("HttpURL Connection is opened");
      
      huc.setRequestMethod("GET");
      System.out.println("request method...post");
      huc.setDoInput(true);
      System.out.println("doInput...TRUE");
      huc.setDoOutput(true);
      System.out.println("doOutput...TRUE");
      
      huc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
      System.out.println("RequestPropoerty has been set");
      
      OutputStream os =huc.getOutputStream();
      os.write(searchPrm.getBytes());
      os.flush();
      os.close();
      String buffer=null;
      
      System.out.println("5?");
      BufferedReader in = new BufferedReader(new InputStreamReader(huc.getInputStream()));
      System.out.println("6?");
     
      while((buffer=in.readLine())!=null){
        System.out.println(buffer);
        System.out.println("next");
      }
    }catch(MalformedURLException e){
      e.printStackTrace();
    }catch(IOException e){
      e.printStackTrace();
    }finally {
      System.out.println(" ");
    }
  }
}

public class WPRMain{
  public static void main(String[] args){
  WPReader wpReader = new WPReader("http://www.seongnam.go.kr/city/1000329/30253/bbsList.do?searchOrganDeptCd=","serachSelect=add_item_5&searchWord=¼º³²½Ã");
  }
}