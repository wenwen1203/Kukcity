package kukcity.horizon;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class searchablePub extends Activity implements OnClickListener, OnSeekBarChangeListener{
	private ImageButton mainpage;
	private ImageButton search;
	private SeekBar noisy, music, drink, distance;
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.searchablepage);
	        view_setUp();
	               
	    }
	
	 
	 public void view_setUp(){
		 mainpage = (ImageButton)this.findViewById(R.id.mainpageback);
		 search=(ImageButton)this.findViewById(R.id.search);
		 mainpage.setOnClickListener(this);
		 search.setOnClickListener(this);
		 noisy=(SeekBar)this.findViewById(R.id.seekBar1);
		 noisy.setMax(100);
		 music=(SeekBar)this.findViewById(R.id.seekBar2);
		 music.setMax(100);
		 drink=(SeekBar)this.findViewById(R.id.seekBar3);
		 drink.setMax(100);
		 distance=(SeekBar)this.findViewById(R.id.seekBar4);
		 distance.setMax(100);
		 noisy.setOnSeekBarChangeListener(this);
		 music.setOnSeekBarChangeListener(this);
		 drink.setOnSeekBarChangeListener(this); 
		 distance.setOnSeekBarChangeListener(this);
	 }


	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.mainpageback:
			Intent intent = new Intent();
			intent.setClass(searchablePub.this, KukcityActivity.class);
			startActivity(intent);
			searchablePub.this.finish();
			break;
		case R.id.search:
			Intent intentSearchResult = new Intent(this, SearchResult.class);
			//Bundle bundle = new Bundle();
			
			startActivity(intentSearchResult);
		
		}
		
	}


	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		Log.v("seek bar", "Onprogress "+ arg1 +" seekBar name "+arg0.getId());
		
	}


	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		Log.v("seek bar", "start "+arg0.getProgress()+" seekBar name "+arg0.getId());
		
	}


	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		Log.v("seek bar", "stop "+arg0.getProgress()+" seekBar name "+arg0.getId());
		
	}
	
}

