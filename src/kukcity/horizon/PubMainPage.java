package kukcity.horizon;


import java.util.List;

import org.json.JSONObject;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PubMainPage extends MapActivity implements OnClickListener{
	private ImageButton mainpage,reviewPage,showMap;
	
	private List<JSONObject> msList;
	private Button pubNameButton;
	private String mainPubName,pubReference;
	private TextView pubName,pubAddress,pubPhone;
	private String address,phone;
	private NetworkConnData data; 
	private MapView mapView;
	private double lat,lng;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pubmainpage);
        this.mainPubName = getIntent().getExtras().getString("pubName");
        this.pubReference=getIntent().getExtras().getString("reference");
        this.lat=getIntent().getExtras().getDouble("lat");
        this.lng=getIntent().getExtras().getDouble("lng");
        load_info();
        view_setUp();
        view_setUptwo();
        
       
        
      //  loadList();
	}
	
	public void view_setUp(){
		pubNameButton=(Button)this.findViewById(R.id.mainpubname);
		pubNameButton.setText(mainPubName);
		pubNameButton.setOnClickListener(this);
		mapView = (MapView)findViewById(R.id.mapview);
		pubName=(TextView)this.findViewById(R.id.pubname);
		pubAddress=(TextView)this.findViewById(R.id.address);
		pubPhone=(TextView)this.findViewById(R.id.phonenum);
		mainpage=(ImageButton)this.findViewById(R.id.mainpage);
		reviewPage=(ImageButton)this.findViewById(R.id.review);
		showMap=(ImageButton)this.findViewById(R.id.showmap);
		//pubReview=(ListView)this.findViewById(R.id.userreview);
		mainpage.setOnClickListener(this);
		reviewPage.setOnClickListener(this);
		showMap.setOnClickListener(this);
		
		
	}
	public void view_setUptwo(){
		pubName.setText(mainPubName);
		pubAddress.setText(address);
		pubPhone.setText(phone);
		MapDisplayActivity mapshow = new MapDisplayActivity(this,mapView,lat,lng);
		mapshow.setMapView();
		mapshow.setPubTile(mainPubName);
		mapshow.setMapMarker();
		
	}

	public void load_info() {
		try{
			data = new NetworkConnData(pubReference);
			data.HttpClientDownLoadDetailPlace();
          	JSONObject jsonObject=new JSONObject(data.getDownLoadData()).getJSONObject("result");
          	address=jsonObject.optString("formatted_address");
          	phone=jsonObject.optString("formatted_phone_number");	
	}catch(Exception e){
		e.printStackTrace();
	
	}
	}

	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.mainpage:
			Intent intent = new Intent();
			intent.setClass(PubMainPage.this, KukcityActivity.class);
			startActivity(intent);
			PubMainPage.this.finish();
			break;
		case R.id.review:
			Intent reIntent = new Intent(PubMainPage.this, WriteReviewPage.class);
			Bundle bl = new Bundle();
			bl.putString("pubname", mainPubName);
			reIntent.putExtras(bl);
			startActivity(reIntent);
			break;
		
		case R.id.showmap:
			FindCurrentLocation currentLocation = new FindCurrentLocation(this);
			currentLocation.initLocation();
			String curpoint=currentLocation.getLocationPoint();
			String url="http://maps.google.com/maps?saddr="+curpoint+"&daddr="+lat+","+lng;
			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			//Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ditu.google.cn/maps?hl=zh&mrt=loc&q="+lat+","+lng));
			startActivity(i);
			break;
		case R.id.mainpubname:
			Intent pubIntent = new Intent(PubMainPage.this,PubDetailPage.class);
			Bundle b = new Bundle();
			b.putString("pubname", mainPubName);
			b.putString("address",address);
			b.putString("phone", phone);
			b.putDouble("lat", lat);
			b.putDouble("lng", lng);
			pubIntent.putExtras(b);
			startActivity(pubIntent);
		}
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
