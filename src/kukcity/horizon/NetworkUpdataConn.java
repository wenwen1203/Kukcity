package kukcity.horizon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class NetworkUpdataConn {
	private String url;
	private Map<String,String> params; 
	private List<NameValuePair> formparams;
	private HttpResponse httpResponse;
	public NetworkUpdataConn(String url){
		this.url=url;
		Log.i("test for 1", "I am in the updata "+url);
		params = new HashMap<String,String>();
	}
	
	public void initConnect(){
		HttpPost httpRequest = new HttpPost(url);
		formparams = new ArrayList<NameValuePair>();
		for(Map.Entry<String, String> entry: params.entrySet()){
			Log.i("test for ", "key "+entry.getKey() +"value "+entry.getValue());
			formparams.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
		}
		Log.i("test for 2", "key "+formparams.size());
		try{
			
			HttpEntity httpentity=new UrlEncodedFormEntity(formparams,"UTF-8");
			httpRequest.setEntity(httpentity);
			Log.i("test for 3", "key ");
			HttpClient httpclient = new DefaultHttpClient();
			Log.i("test for 4", "key ");
			httpResponse = httpclient.execute(httpRequest);
			Log.i("test for 5", "key ");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public void setParamsList(String key, String param){
		params.put(key, param);
	}
	
	
	
}
