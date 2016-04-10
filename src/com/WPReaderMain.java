import java.net.*;
import java.io.*;

class WPReader{
  public WPReader(String urlString, String searchPrm){
    try{
      URL prmURL = new URL(urlString);
      HttpURLConnection huc = (HttpURLConnection)prmURL.openConnection();
      
      huc.setRequestMethod("POST");
      huc.setDoInput(true);
      huc.setDoOutput(true);
      huc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
      OutputStream os =huc.getOutputStream();
      os.write(searchPrm.getBytes());
      os.flush();
      os.close();
      
      String buffer=null;
      BufferedReader in = new BufferedReader(new InputStreamReader(huc.getInputStream()));
      while((buffer=in.readLine())!=null){
        System.out.println(buffer);
      }
    }catch(Exception e){
      System.out.println("??");
    }finally {
      System.out.println("알수없는오류");
    }
  }
}

public class WPReaderMain{
  public static void main(String[] args){
  WPReader wpReader = new WPReader("http://www.seongnam.go.kr/city/1000329/30253/bbsList.do","serachSelect=add_item_5&searchWord=성남시");
  }
}