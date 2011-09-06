package kukcity.horizon;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TwitterPage extends Activity implements OnClickListener {
	private SharedPreferences prefs;
	
	private TextView status;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitterlayout);
        this.prefs=PreferenceManager.getDefaultSharedPreferences(this);
        view_setUp();
	}

	private void view_setUp() {
		status = (TextView)this.findViewById(R.id.login_status);
		Button tweet = (Button)this.findViewById(R.id.tweet);
		Button clear = (Button)this.findViewById(R.id.clear);
		tweet.setOnClickListener(this);
		clear.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.tweet:
				Intent i = new Intent(getApplicationContext(), PrepareRequestTokenActivity.class);
				String msg = getIntent().getExtras().getString("msg");
				i.putExtra("tweet_msg", msg);
				startActivity(i);
				break;
			case R.id.clear:
				
				break;
		
		}
		
	}
	
	
}
