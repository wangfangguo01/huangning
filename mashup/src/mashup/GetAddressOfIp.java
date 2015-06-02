package mashup;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import   java.text.SimpleDateFormat;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

public class GetAddressOfIp {
	String Ip;
	String cId;
	String cName;
	String sign = "simple_7fb5788ce07e4af384865d5e9b7a6a4a";
	String appId = "90";
    URLConnection connectionData; 
    StringBuilder sb; 
    BufferedReader br;// 读取data数据流 
    JSONObject jsonData; 
    JSONObject info; 
	public GetAddressOfIp(String ip)throws IOException ,NullPointerException{
		this.Ip = ip;
		SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd%20hh:mm:ss");
		String   date   =   sDateFormat.format(new java.util.Date());
		String postAddr = "http://route.showapi.com/20-1";
		postAddr += "?ip="+ip;
		postAddr += "&showapi_sign="+this.sign;
		postAddr += "&showapi_appid="+this.appId;
		postAddr +="&showapi_timestamp="+date;
		String result = GetResult(postAddr);
	    jsonData = JSONObject.fromObject(result); 
	    JSONObject show_body = jsonData.getJSONObject("showapi_res_body");
	    System.out.println(show_body.toString());
	    String city = show_body.getString("city");
	    this.cName = city;
	    city = city.replace("市", "");

		postAddr = "http://route.showapi.com/9-2";
		postAddr += "?area="+URLEncoder.encode(city, "UTF-8");
		postAddr += "&showapi_sign="+this.sign;
		postAddr += "&showapi_appid="+this.appId;
		postAddr +="&showapi_timestamp="+date;
		result = GetResult(postAddr);
	    jsonData = JSONObject.fromObject(result); 
	    show_body = jsonData.getJSONObject("showapi_res_body");
	    System.out.println(show_body.toString());
	    //System.out.println(postAddr);
	    this.cId = show_body.getJSONObject("cityInfo").getString("c1");
	    System.out.println(this.cId);
           //info = jsonData.getJSONObject("weatherinfo");
	}
	
	private String GetResult(String postAddr)throws IOException ,NullPointerException{
        URL url = new URL(postAddr);
        connectionData = url.openConnection(); 
        connectionData.setConnectTimeout(1000); 
        try { 
            br = new BufferedReader(new InputStreamReader( 
                    connectionData.getInputStream(), "UTF-8")); 
            sb = new StringBuilder(); 
            String line = null; 
            while ((line = br.readLine()) != null) 
                sb.append(line); 
        } catch (SocketTimeoutException e) { 
            System.out.println("连接超时"); 
        } catch (FileNotFoundException e) { 
            System.out.println("加载文件出错"); 
        } 
            String datas = sb.toString();
        return datas;
	}
	
	public String getcId(){
		return this.cId;
	}
	
	public String getcName(){
		return this.cName;
	}
	
    public static void main(String[] args) { 
        try { 
            new GetAddressOfIp("211.69.143.144"); // 101010100(北京)就是你的城市代码
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
	
}
