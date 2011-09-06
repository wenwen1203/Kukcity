package kukcity.horizon;




import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class WriteReviewPage extends Activity implements OnClickListener, OnRatingBarChangeListener, OnKeyListener{
	private ImageButton cancle, checkin;
	private RatingBar environment,drink,service,food;
	private EditText comment;
	private String pubname;
	private SharedPreferences prefs;
	private String msg_tweet;
	private float environmentRate, serviceRate, drinkRate, foodRate;
	private final Handler twitterHandler = new Handler();
	final Runnable twitterNotification = new Runnable(){
		public void run(){
			Toast.makeText(getBaseContext(), "Tweet sent", Toast.LENGTH_LONG).show();
			comment.setText(null);
		}
	};
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.pubname = getIntent().getExtras().getString("pubname");
        setContentView(R.layout.reviewpage);
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        view_setUp();
	}
	
	public void view_setUp(){
		cancle=(ImageButton)this.findViewById(R.id.cancle);
		checkin=(ImageButton)this.findViewById(R.id.checkin);
		cancle.setOnClickListener(this);
		checkin.setOnClickListener(this);
		environment=(RatingBar)this.findViewById(R.id.ratingBar1);
		drink=(RatingBar)this.findViewById(R.id.ratingBar2);
		service=(RatingBar)this.findViewById(R.id.ratingBar3);
		food=(RatingBar)this.findViewById(R.id.ratingBar4);
		environment.setOnRatingBarChangeListener(this);
		drink.setOnRatingBarChangeListener(this);
		service.setOnRatingBarChangeListener(this);
		food.setOnRatingBarChangeListener(this);
		comment=(EditText)this.findViewById(R.id.comment);
		comment.setOnKeyListener(this);
		
	}
	

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.cancle:
			Intent intent = new Intent();
			intent.setClass(WriteReviewPage.this, KukcityActivity.class);
			startActivity(intent);
			WriteReviewPage.this.finish();	
			
			break;
			
		case R.id.checkin:
			if(TwitterUtils.isAuthenticated(prefs)){
				setTweetMsg(comment.getText().toString());
				sendTweet();
			}else{
				Intent tIntent = new Intent(getApplicationContext(), PrepareRequestTokenActivity.class);
				tIntent.putExtra("msg", getTweetMsg());
				startActivity(tIntent);
				break;
			}
		
		}
	}
	
	public void setTweetMsg(String text){
		msg_tweet = text;
		
	}
	
	private String getTweetMsg() {
		//return "Tweeting from Android App at " + new Date().toLocaleString();
		return  "#kukcity @"+pubname+":" +" "+ msg_tweet +" Rating: Environment "+getEnRatingBar()+ " Drink "+getDrinkRatingBar()+" Service "+getServiceRatingBar() + " Food "+getFoodRatingBar()+
					" Tweeting from Kukcity App";
	}	


	public void sendTweet(){
		Thread t = new Thread(){
			public void run(){
				try{
					
						Log.v("test for limit ", ""+getTweetMsg().length());
					
						TwitterUtils.sendTweet(prefs, getTweetMsg());
						twitterHandler.post(twitterNotification);
					
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		};
		t.start();
	}
	
	@Override
	public void onRatingChanged(RatingBar arg0, float rating, boolean fromUser) {
		switch(arg0.getId()){
		case R.id.ratingBar1:
			setEnRatingBar(rating);
			break;
		case R.id.ratingBar2:
			setDrinkRatingBar(rating);
			break;
		case R.id.ratingBar3:
			setServiceRatingBar(rating);
			break;
		case R.id.ratingBar4:
			setFoodRatingBar(rating);
			break;
		
		}
		
		
		
	}
	
	
	public void setEnRatingBar(float rating){
		environmentRate = rating;
	}
	
	public float getEnRatingBar(){
		return environmentRate;
	}
	public void setDrinkRatingBar(float rating){
		drinkRate = rating;
	}
	
	public float getDrinkRatingBar(){
		return drinkRate;
	}
	public void setServiceRatingBar(float rating){
		serviceRate = rating;
	}
	
	public float getServiceRatingBar(){
		return serviceRate;
	}
	public void setFoodRatingBar(float rating){
		foodRate = rating;
	}
	
	public float getFoodRatingBar(){
		return foodRate;
	}
	
	

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		Log.v("EditText", "the comment: "+comment.getText());
		return false;
	}
	
}
