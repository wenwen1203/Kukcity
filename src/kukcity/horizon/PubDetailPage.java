package kukcity.horizon;


import org.json.JSONObject;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;



import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class PubDetailPage extends MapActivity{
	private String pubReference,name,address,phone;
	private TextView pubName,pubAddress,pubPhone;
	private NetworkConnData data; 
	private MapView mapView;
	private double lat,lng;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pubdetail);
    	this.lat=getIntent().getExtras().getDouble("lat");
        this.lng=getIntent().getExtras().getDouble("lng");
        this.name=getIntent().getExtras().getString("pubname");
        this.phone=getIntent().getExtras().getString("phone");
        this.address=getIntent().getExtras().getString("address");
        this.pubReference=getIntent().getExtras().getString("reference");
        Log.v("Test", "I am in the pub main page right noew");
        view_setUp();
	}
	
	
	
	public void view_setUp(){
		mapView = (MapView)findViewById(R.id.mapview);
		pubName=(TextView)this.findViewById(R.id.pubname);
		pubName.setText(name);
		pubAddress=(TextView)this.findViewById(R.id.address);
		pubAddress.setText(address);
		pubPhone=(TextView)this.findViewById(R.id.phonenum);
		pubPhone.setText(phone);
		MapDisplayActivity mapshow = new MapDisplayActivity(this,mapView,lat,lng);
		mapshow.setMapView();
		mapshow.setPubTile(name);
		mapshow.setMapMarker();
		
	}
	
		



	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
