package kukcity.horizon;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kukcity.horizon.KukcityActivity.GetPubFinderHandler;
import kukcity.horizon.KukcityActivity.GetPubFinderThread;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class nearbylocation extends Activity implements OnClickListener, OnItemClickListener{

	private ImageButton homepage;
	private long rating;
	private String address,phone;
	private List<JSONObject> msList;
	private ListView nearbyResult;
	private String url="https://maps.googleapis.com/maps/api/place/search/json?location=52.9553,-1.18787&radius=1000&types=bar|night_club&sensor=false&key=AIzaSyBZUdfFhTKFy8KOb2fOiRkORc5UrxaCr8I";
	private String location="52.9551,-1.1492";
	private FindCurrentLocation currentLocation;
	private NetworkConnData data; 
	private ExecutorService executorService;
	private GetNearbylocationHandler handler;
	private ProgressDialog progressDialog;
	private static int THREADPOOL_SIZE=4;
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.nearbylocation);	
	        currentLocation = new FindCurrentLocation(this);
	        currentLocation.initLocation();
	        msList = new ArrayList<JSONObject>();
	        handler=new GetNearbylocationHandler();
	        executorService = Executors.newFixedThreadPool(THREADPOOL_SIZE);
	        executorService.submit(new GetNearbylocationThread());
	        view_setUp();
	      //  loadList();
	    }
	 
	 
	 private void loadList() {
		 try{
			 Log.v("test !!!!!!!!!", "there is no data" + currentLocation.getLocationPoint());
			 data = new NetworkConnData(currentLocation.getLocationPoint());
			 data.HttpClientDownLoadConnPlace();
			 JSONObject jsonObject=new JSONObject(data.getDownLoadData());
			 JSONArray array = jsonObject.getJSONArray("results");
			 if(array !=null && array.length()>0){
				 msList.clear();
				 for(int i=0;i<array.length();i++){
					 JSONObject object = array.optJSONObject(i);
					 msList.add(object);
				 }
			 }else{
				 Log.v("test", "there is no data");
			 }
			 
			 if(msList != null){
				 PubAdapter adapter = new PubAdapter(nearbylocation.this,msList);
				 nearbyResult.setAdapter(adapter);
			 }
			 
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		
		
	}


	public void view_setUp(){
		 progressDialog = new ProgressDialog(nearbylocation.this);
		 progressDialog.setTitle("Please wait..");
         progressDialog.setMessage("Searching the pubs now!");
         progressDialog.show();
		 nearbyResult = (ListView)this.findViewById(R.id.nearbyresult);
		 nearbyResult.setOnItemClickListener(this);
		 homepage=(ImageButton)this.findViewById(R.id.homepagenearby);
		 homepage.setOnClickListener(this);
		 
	 }


	@Override
	public void onClick(View v) {
		switch (v.getId()){
		
		case R.id.homepagenearby:
			Intent intent = new Intent();
			intent.setClass(nearbylocation.this, KukcityActivity.class);
			startActivity(intent);
			nearbylocation.this.finish();
			break;
		}
		
	}
	
	
	class GetNearbylocationHandler extends Handler{
		public void handleMessage(Message m){
			PubAdapter adapter = new PubAdapter(nearbylocation.this,msList);
			nearbyResult.setAdapter(adapter);
			progressDialog.dismiss();
		}
	}
	
	class GetNearbylocationThread implements Runnable{

		@Override
		public void run() {
			refreshList();
			Message msg = handler.obtainMessage();
			handler.sendMessage(msg);
			
		}
		
	}
	
	public void refreshList(){
		try{
			Log.v("test !!!!!!!!!", "there is no data" + currentLocation.getLocationPoint());
			 data = new NetworkConnData(currentLocation.getLocationPoint());
			 data.HttpClientDownLoadConnPlace();
			 JSONObject jsonObject=new JSONObject(data.getDownLoadData());
			 JSONArray array = jsonObject.getJSONArray("results");
			 if(array !=null && array.length()>0){
				 msList.clear();
				 for(int i=0;i<array.length();i++){
					 JSONObject object = array.optJSONObject(i);
					 msList.add(object);
				 }
			 }else{
				 Log.v("test", "there is no data");
			 }
			 
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Object obj = arg1.getTag();
		if(obj != null){
			Log.v("test", "AdapterView "+arg0+" View "+arg1+" int "+arg2 +" long "+arg3);
			String id=obj.toString();
			Intent pubPage = new Intent(nearbylocation.this, PubMainPage.class );
			Bundle b = new Bundle();
			b.putString("pubName", msList.get(arg2).optString("name"));
			b.putString("reference", msList.get(arg2).optString("reference"));
			String geometry = msList.get(arg2).optString("geometry");
			
			try{
				JSONObject tempObject = new JSONObject(geometry).getJSONObject("location") ;
				double lat = tempObject.optDouble("lat");
				double lng = tempObject.optDouble("lng");
				b.putDouble("lat", lat);
				b.putDouble("lng", lng);
				Log.i("test for lat lng", " lat "+lat +" lng "+lng);
		}catch(Exception e){
			e.printStackTrace();
		
		}
			pubPage.putExtras(b);
			startActivity(pubPage);
		}
		
	}
}
