package kukcity.horizon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

class MapItemizedOverlay extends ItemizedOverlay {
	private List<OverlayItem> locations = new ArrayList<OverlayItem>();
	
	private Drawable marker;
	private Context context;
	private GeoPoint point;
	private double lat,lng;
	private MapDialogs dialogs;
	private String name;
	public MapItemizedOverlay(String name,Drawable defaultMarker,double lat, double lng,Context context) {
		super(defaultMarker);
		this.marker=defaultMarker;
		this.lat=lat;
		this.lng=lng;
		this.name=name;
		this.context=context;
		setGeoPoint(lat,lng);
		locations.add(new OverlayItem(getGeoPoint(), name,
				""));
		populate();
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		boundCenterBottom(marker);
	
	}

	public void setGeoPoint(double lat, double lng){
		point = new GeoPoint(
				(int) (lat * 1000000),
				(int) (lng * 1000000));
	}
	
	public GeoPoint getGeoPoint(){
		return point;
		
	}

	@Override
	protected OverlayItem createItem(int i) {
		return locations.get(i);
	}
	
	protected boolean onTap(int index){
		OverlayItem item = locations.get(index);
		MapDialogs dialogs = new MapDialogs(name,context,lat,lng);
		dialogs.setDisplay();
		return true;
	}
	

	
	
	
	@Override
	public int size() {
		return locations.size();
	}

}