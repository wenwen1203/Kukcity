package kukcity.horizon;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;

import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class FindCurrentLocation {
	private double latitude,longitude;
	private Context context;
	private Location location;
	private String networkProvider = LocationManager.NETWORK_PROVIDER;
	private String GpsProvider = LocationManager.GPS_PROVIDER;
	private LocationManager locationManager;
	private LocationListener locationListener;
	public FindCurrentLocation(Context context){
		this.context=context;
		Log.v("Test test test test ", " hi i am here ");
		
		
	}
	
	public void initLocation(){
		locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		if(startLocation(networkProvider,context)){
			updateLocation(location,context);
		}else{
			if(startLocation(GpsProvider,context)){
				updateLocation(location,context);
			}
			
		}
		Log.i("init location ", "lat "+location.getLatitude()+"long "+location.getLongitude());
		
	}
	
	private boolean startLocation(String provider, final Context context){
		Location location = locationManager.getLastKnownLocation(provider);
		locationListener = new LocationListener(){

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				updateLocation(location,context);
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
		
		if(location != null){
			this.location=location;
			Log.i("Test for the location ", "lat "+location.getLatitude()+"long "+location.getLongitude());
			setLocation(location);
			return true;
		}
		
		return false;
		
	}
	
	
	private void updateLocation(Location location, Context context){
		if(location != null){
			
			Log.i("Test for the location update", "lat "+location.getLatitude()+"long "+location.getLongitude());
		}
		
	}
	
	public void setLocation(Location location){
		latitude = location.getLatitude();
		longitude = location.getLongitude();
	}
	
	public double getLatitude(){
		return latitude;
	}
	
	public double getLongitude(){
		return longitude;
	}
	
	public String getLocationPoint(){
		String temp = ""+latitude+","+""+longitude;
		return temp;
	}
	
}
