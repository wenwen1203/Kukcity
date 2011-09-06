package kukcity.horizon;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;

class MapDialogs extends Dialog implements android.view.View.OnClickListener{
	private Context context;
	private double lat,lng;
	private String name;
	public MapDialogs(String name,Context context,double lat,double lng) {
		super(context);
		this.name=name;
		this.context=context;
		this.lat=lat;
		this.lng=lng;
		
	}

	public void setDisplay() {
		setContentView(R.layout.dialoglayout);
		setTitle(name);
		Button showWay = (Button)findViewById(R.id.showway);
		Button back = (Button)findViewById(R.id.back);
		showWay.setOnClickListener(this);
		back.setOnClickListener(this);
		show();
	}

	public void setPubTitle(String n){
		name =n;
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.back:
			dismiss();
			break;
			
		case R.id.showway:
			String latstring = ""+lat;
			String lngstring = ""+lng;
			FindCurrentLocation currentLocation = new FindCurrentLocation(context);
			currentLocation.initLocation();
			String curpoint=currentLocation.getLocationPoint();
			Log.i("direction", "location "+curpoint);
			//Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ditu.google.cn/maps?hl=zh&mrt=loc&q="+latstring+","+lngstring));
			String url="http://maps.google.com/maps?saddr="+curpoint+"&daddr="+latstring+","+lngstring;
			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			context.startActivity(i);
			break;
		}
		
	}
	
	
	
	
}