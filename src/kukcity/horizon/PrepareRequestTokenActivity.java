package kukcity.horizon;


import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class PrepareRequestTokenActivity extends Activity{
	private OAuthConsumer consumer;
	private OAuthProvider provider;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		try{
			this.consumer=new CommonsHttpOAuthConsumer(Information.CONSUMER_KEY, Information.CONSUMER_SECRET);
			this.provider=new CommonsHttpOAuthProvider(Information.REQUEST_URL,Information.ACCESS_URL,Information.AUTHORIZE_URL);
		}catch(Exception e){
			Log.e("test", "Error creating consumer/ provider",e);
		}
		new OAuthRequestTokenTask(this,consumer,provider).execute();
	}
	
	public void onNewIntent(Intent intent){
		super.onNewIntent(intent);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		final Uri uri = intent.getData();
		if (uri != null && uri.getScheme().equals(Information.OAUTH_CALLBACK_SCHEME)) {

			new RetrieveAccessTokenTask(this,consumer,provider,prefs).execute(uri);
			finish();	
		}
	}
	
	
	public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void>{
		private Context	context;
		private OAuthProvider provider;
		private OAuthConsumer consumer;
		private SharedPreferences prefs;
		
		public RetrieveAccessTokenTask(Context context, OAuthConsumer consumer,OAuthProvider provider, SharedPreferences prefs) {
			this.context = context;
			this.consumer = consumer;
			this.provider = provider;
			this.prefs=prefs;
		}
		@Override
		protected Void doInBackground(Uri... params) {
			final Uri uri = params[0];
			final String oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

			try {
				provider.retrieveAccessToken(consumer, oauth_verifier);

				final Editor edit = prefs.edit();
				edit.putString(OAuth.OAUTH_TOKEN, consumer.getToken());
				edit.putString(OAuth.OAUTH_TOKEN_SECRET, consumer.getTokenSecret());
				edit.commit();
				
				String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
				String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
				
				consumer.setTokenWithSecret(token, secret);
				context.startActivity(new Intent(context,WriteReviewPage.class));

				executeAfterAccessTokenRetrieval();
				
				
				
			} catch (Exception e) {
				Log.e("test", "OAuth - Access Token Retrieval Error", e);
			}

			return null;
		}
		
		private void executeAfterAccessTokenRetrieval() {
			String msg = getIntent().getExtras().getString("tweet_msg");
			try {
				TwitterUtils.sendTweet(prefs, msg);
			} catch (Exception e) {
				Log.e("TEST", "OAuth - Error sending to Twitter", e);
			}
		}
		
		
	}
	
}
