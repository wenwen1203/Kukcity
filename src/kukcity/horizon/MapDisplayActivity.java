package kukcity.horizon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;



public class MapDisplayActivity {
	private MapView mapview;
	
	private double lat;
	private double lng;
	private GeoPoint point;
	private Context context;
	private MapItemizedOverlay funPlaces;
	private String pubName,pubAdress;
	public MapDisplayActivity(Context context,MapView mapview,double lat, double lng){
		this.mapview=mapview;
		this.context=context;
		this.lat=lat;
		this.lng=lng;
		setGeoPoint(lat,lng);
	}
	
	public void setMapView(){
		mapview.setBuiltInZoomControls(true);
		mapview.setClickable(true);
		mapview.getController().setCenter(getGeoPoint());
		mapview.getController().setZoom(16);
		
	}

	public void setGeoPoint(double lat, double lng){
		point = new GeoPoint(
				(int) (lat * 1000000),
				(int) (lng * 1000000));
	}
	
	public GeoPoint getGeoPoint(){
		return point;
		
	}
	
	
	
	public void setMapMarker(){
		Drawable marker = context.getResources().getDrawable(R.drawable.full);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
	    funPlaces = new MapItemizedOverlay(pubName,marker,lat,lng,context);
	    mapview.getOverlays().add(funPlaces);
	}
	
	public void setPubTile(String n){
		pubName = n;
	}
	

}
