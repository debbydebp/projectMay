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
            // ��û URL
            URL url = new URL("http://javaking75.blog.me/rss?q=test&id=");
            // ���ڿ��� URL ǥ��
            System.out.println("URL :" + url.toExternalForm());
 
            connection = (HttpURLConnection) url.openConnection();
            // ��û ���(GET or POST)
            connection.setRequestMethod("GET");
            // ��û���� Ÿ�Ӿƿ� ����
            connection.setConnectTimeout(3000);
            // �б� Ÿ�Ӿƿ� ����
            connection.setReadTimeout(3000);
 
            System.out.println("getContentEncoding():" + connection.getContentEncoding());
            System.out.println("getContentType():" + connection.getContentType());
            System.out.println("getResponseCode():"    + connection.getResponseCode());
            System.out.println("getResponseMessage():" + connection.getResponseMessage());
            System.out.println("getRequestMethod():" + connection.getRequestMethod());
            
            System.out.println("getURL():" + connection.getURL()); // URL ������
            String queryString = connection.getURL().getQuery(); // URL�� ������Ʈ�� �κ� ������
            System.out.println("getURL().getQuery():" + queryString);
            //���� ��Ʈ���� �����������·� ��ȯ (key,value)
            Map<String, String> queryMap = getQueryParser(queryString); 
            System.out.println(queryMap);
            System.out.println("q�Ķ������ ��:" + queryMap.get("q"));
 
            System.out
                    .println("=============================================================");
            // ��û�� URL�� ���� ���� ���� ���.
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
            
            //��� ����
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