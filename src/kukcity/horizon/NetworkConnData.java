package kukcity.horizon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.net.Uri;
import android.util.Log;

public class NetworkConnData {
	private String url,location;
	private String downloadData;
	private DefaultHttpClient client;
	public NetworkConnData(String url){
		this.url=url;
		

	}
	

	public void HttpClientDownLoadConnPlace(){
		try{
			
			client = new DefaultHttpClient();
			
			String uri_param = "location="+URLEncoder.encode(url);
			uri_param += "&radius="+URLEncoder.encode("1000");
			uri_param += "&types="+URLEncoder.encode("bar|night_club");
			uri_param += "&sensor="+URLEncoder.encode("false");
			uri_param += "&key="+URLEncoder.encode("AIzaSyBZUdfFhTKFy8KOb2fOiRkORc5UrxaCr8I");
			String uri_root = "https://maps.googleapis.com/maps/api/place/search/json?";
			String url = uri_root+uri_param;
		
			URI uri = new URI(url);
		
			HttpGet get = new HttpGet(uri);
			HttpResponse response = client.execute(get);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                HttpEntity entity = response.getEntity();  
               
            } 
			InputStreamReader in = new InputStreamReader(response.getEntity().getContent());
			BufferedReader reader = new BufferedReader(in);
			StringBuilder sb = new StringBuilder();
			String line="";
			String nl = System.getProperty("line.separator");
          	while((line=reader.readLine()) !=null){
          		sb.append(line+nl);
          	}
          	reader.close();
          	String temp = sb.toString();
         
		setDownLoadData(temp);
		}catch(Exception e){
			e.fillInStackTrace();
		
		}
	}
	
	public void HttpClientDownLoadDetailPlace(){
		try{
			client = new DefaultHttpClient();
			String uri_param = "reference="+URLEncoder.encode(url);
			uri_param += "&sensor="+URLEncoder.encode("true");
			uri_param += "&key="+URLEncoder.encode("AIzaSyBZUdfFhTKFy8KOb2fOiRkORc5UrxaCr8I");
			String uri_root = "https://maps.googleapis.com/maps/api/place/details/json?";
			String url = uri_root+uri_param;
	
			URI uri = new URI(url);
		
			HttpGet get = new HttpGet(uri);
	
			HttpResponse response = client.execute(get);
			InputStreamReader in = new InputStreamReader(response.getEntity().getContent());
			BufferedReader reader = new BufferedReader(in);
			StringBuilder sb = new StringBuilder();
			String line="";
			String nl = System.getProperty("line.separator");
			while((line=reader.readLine()) !=null){
				sb.append(line+nl);
			}
			reader.close();
			String temp = sb.toString();
			setDownLoadData(temp);
		
		}catch(Exception e){
			e.fillInStackTrace();
		
		}
		
		
		
	}
	
	
	
	
	
	
	public void HttpClientDownLoadConn(){
		try{
		
			client = new DefaultHttpClient();
			URI url_url = new URI(url);
	
			HttpGet get = new HttpGet(url_url);

		
			HttpResponse response = client.execute(get);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                HttpEntity entity = response.getEntity();  
            
                //stream = entity.getContent();  
            } 
			InputStreamReader in = new InputStreamReader(response.getEntity().getContent());
			BufferedReader reader = new BufferedReader(in);
			StringBuilder sb = new StringBuilder();
			String line="";
			String nl = System.getProperty("line.separator");
          	while((line=reader.readLine()) !=null){
          		sb.append(line+nl);
          	}
          	reader.close();
          	String temp = sb.toString();
         
		setDownLoadData(temp);
		}catch(Exception e){
			e.fillInStackTrace();
		
		}
	}
	
	
	public void setDownLoadData(String data){
		this.downloadData=data;
	}
	
	public String getDownLoadData(){
		
		return downloadData;
	}
	
}
