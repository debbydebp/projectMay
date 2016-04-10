java.net.URL;
java.net.URLConnection;
class WPReader{
  private static void connet(String urlString){
    URL TargetURL= new URL(urlString);
    HttpURLConnection huc = (HttpURLConnection) u.openConnection();
    huc.serRequestMethod("POST");
    huc.setDoinput(true);
    huc.setDoOutput(true);
    
  }
}

public class WPReaderMain{
  public static void main(String[] args){
  WPReader wpReader = new WPRreader;
  }
}