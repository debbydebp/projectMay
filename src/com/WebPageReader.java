import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
 
public class WebPageReader {
 
    public static void main(String[] args) {
 
        HttpURLConnection connection = null;
 
        try {
            // 요청 URL
            URL url = new URL("http://javaking75.blog.me/rss?q=test&id=");
            // 문자열로 URL 표현
            System.out.println("URL :" + url.toExternalForm());
 
            connection = (HttpURLConnection) url.openConnection();
            // 요청 방식(GET or POST)
            connection.setRequestMethod("GET");
            // 요청응답 타임아웃 설정
            connection.setConnectTimeout(3000);
            // 읽기 타임아웃 설정
            connection.setReadTimeout(3000);
 
            System.out.println("getContentEncoding():" + connection.getContentEncoding());
            System.out.println("getContentType():" + connection.getContentType());
            System.out.println("getResponseCode():"    + connection.getResponseCode());
            System.out.println("getResponseMessage():" + connection.getResponseMessage());
            System.out.println("getRequestMethod():" + connection.getRequestMethod());
            
            System.out.println("getURL():" + connection.getURL()); // URL 얻어오기
            String queryString = connection.getURL().getQuery(); // URL의 쿼리스트링 부분 얻어오기
            System.out.println("getURL().getQuery():" + queryString);
            //쿼리 스트링의 값들을맵형태로 반환 (key,value)
            Map<String, String> queryMap = getQueryParser(queryString); 
            System.out.println(queryMap);
            System.out.println("q파라미터의 값:" + queryMap.get("q"));
 
            System.out
                    .println("=============================================================");
            // 요청한 URL에 대한 응답 내용 출력.
            BufferedReader reader = 
                    new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\r\n");
            }
            reader.close();
 
            System.out.println(buffer.toString());
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
 
        }
    }
 
    public static Map<String, String> getQueryParser(String query) {
 
        Map<String, String> returnData = new HashMap<String, String>();
        // query is from getQuery()
        StringTokenizer st = new StringTokenizer(query, "&", false); 
        
        while (st.hasMoreElements()) {  
            // First Pass to retrive the
            // "parametername=value" combo
            String paramValueToken = st.nextElement().toString();
            // StringTokenizer stParamVal = new StringTokenizer(paramValueToken,"=", false );
            
            //방식 변경
            String[] strParamVal = paramValueToken.split("=", 2);
            String paramName = strParamVal[0];
            String paramValue = strParamVal[1];
            returnData.put(paramName, paramValue);
            
             /* int i = 0;               
            while (stParamVal.hasMoreElements()) {
                //Second pass to separate the "paramname" and "value".
                // 1st token is param name
                // 2nd token is param value
 
                String separatedToken = stParamVal.nextElement().toString();
                
                if ( i== 0) {
                    //This indicates that it is the param name : ex val4,val5 etc
                    paramName = separatedToken;                    
                } else {
                    // This will hold value of the parameter
                    paramValue = separatedToken;
                }
                
                i++;    
            }*/ 
        }
        return returnData;
 
    }
 
}