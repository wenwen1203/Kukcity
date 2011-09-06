package kukcity.horizon;




import java.util.List;

import kukcity.horizon.AsyncImageLoader.ImageCallback;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;




public class PubAdapter extends BaseAdapter{
	private List<JSONObject> pubList;
	private JSONObject data;
	private Context context;
	private float rating;
	private AsyncImageLoader asyncImageLoader;
	private NetworkConnData network; 
	public PubAdapter(Context context,List pubList){
		this.context=context;
		this.pubList=pubList;
	}
	
	
	
	@Override
	public int getCount() {
		
		return pubList.size();
	}

	@Override
	public Object getItem(int location) {
		
		return pubList.get(location);
	}

	@Override
	public long getItemId(int location) {
	
		return location;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		asyncImageLoader = new AsyncImageLoader();
		convertView = LayoutInflater.from(context).inflate(R.layout.pubinfolist, null);
		PubHolder pub = new PubHolder();
		data=(JSONObject)pubList.get(position);
		pub.pubIcon=(ImageView)convertView.findViewById(R.id.pubicon);
		pub.pubName=(TextView)convertView.findViewById(R.id.pubname);
		pub.pubRating=(RatingBar)convertView.findViewById(R.id.smallrating);
		pub.pubAdress=(TextView)convertView.findViewById(R.id.address);
		pub.pubReview=(TextView)convertView.findViewById(R.id.review);
		try{
			network = new NetworkConnData(data.optString("reference"));
			network.HttpClientDownLoadDetailPlace();
          	JSONObject jsonObject=new JSONObject(network.getDownLoadData()).getJSONObject("result");
          	rating = jsonObject.optLong("rating");
          	
			if(data!=null){
				convertView.setTag(data.get("id"));
				pub.pubName.setText(data.optString("name"));
				pub.pubAdress.setText(data.optString("vicinity"));
				pub.pubRating.setRating(rating);
				Drawable cachedImage = asyncImageLoader.loadDrawable(data.getString("icon"),pub.pubIcon, new ImageCallback(){
                    @Override
                    public void imageLoaded(Drawable imageDrawable,ImageView imageView, String imageUrl) {
                        imageView.setImageDrawable(imageDrawable);
                    }
                });
				if (cachedImage == null) {
					pub.pubIcon.setImageResource(R.drawable.icon);
				} else {
					pub.pubIcon.setImageDrawable(cachedImage);
				}
				
			}
			
			
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return convertView;
	}
	
	
	
	
	
}



