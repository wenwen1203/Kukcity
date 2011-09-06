package kukcity.horizon;



import java.util.ArrayList;
import java.util.List;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class TestMapActivity extends MapActivity implements OnClickListener {
	private MapView mapView;
	private double lat,lng;
	private String pubReference,name,address,phone;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testlayout);
		mapView = (MapView) findViewById(R.id.mapview);
		ImageButton back = (ImageButton)this.findViewById(R.id.backpubpage);
		back.setOnClickListener(this);
		this.lat=getIntent().getExtras().getDouble("lat");
        this.lng=getIntent().getExtras().getDouble("lng");
        this.name=getIntent().getExtras().getString("pubname");
        this.phone=getIntent().getExtras().getString("phone");
        this.address=getIntent().getExtras().getString("address");
		MapDisplayActivity mapshow = new MapDisplayActivity(this,mapView,lat,lng);
		mapshow.setPubTile(name);
		mapshow.setMapView();
		mapshow.setMapMarker();
		
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.backpubpage:
			Intent intent = new Intent();
			intent.setClass(TestMapActivity.this, PubMainPage.class);
			startActivity(intent);
			TestMapActivity.this.finish();
		
		}
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected boolean isLocationDisplayed() {
		return false;
	}

	
}
