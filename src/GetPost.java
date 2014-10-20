import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;



public class GetPost {
	 /**
     * Send Post to the url 
     * 
     * @param url
     * @param param
     * @return URL represent the remote resoure result
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
           
            URLConnection connection = realUrl.openConnection();
           
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
           
            connection.connect();
           
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("Error occurs in GET！" + e);
            e.printStackTrace();
        }
        // use finally block to close io stream
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    public static Map toMap(String jsonString) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);
        
        Map result = new HashMap();
        Iterator iterator = jsonObject.keys();
        String key = null;
        String value = null;
        
        while (iterator.hasNext()) {

            key = (String) iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);

        }
        return result;

    }
   
	public static void main(String[] args) throws JSONException
{
	String s=GetPost.sendGet("http://api.uihoo.com/currency/currency.http.php", "from=usd&to=cny&format=json");
	Map map=toMap(s);
	Iterator iter=map.entrySet().iterator();
	while(iter.hasNext())
	{
		Map.Entry<String, String> entry=(Map.Entry<String, String>)iter.next();
		Object key=entry.getKey();
		Object Value=entry.getValue();
		System.out.println(key+" "+Value);
		
	}
	
}
}
