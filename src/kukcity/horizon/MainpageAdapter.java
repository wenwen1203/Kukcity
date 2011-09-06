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
import android.widget.TextView;




public class MainpageAdapter extends BaseAdapter{
	private List<JSONObject> msList;
	private Context context;
	private JSONObject data;
	private AsyncImageLoader asyncImageLoader;
	public MainpageAdapter(Context context,List<JSONObject> msList){
		this.context=context;
		this.msList=msList;
	}
	
	
	
	@Override
	public int getCount() {
		
		return msList.size();
	}

	@Override
	public Object getItem(int location) {
		
		return msList.get(location);
	}

	@Override
	public long getItemId(int location) {
	
		return location;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		asyncImageLoader = new AsyncImageLoader();
		convertView = LayoutInflater.from(context).inflate(R.layout.home_list_item, null);
		MsgHolder ms = new MsgHolder();
		data = (JSONObject)msList.get(position);
		JSONObject source=null;
		ms.msicon=(ImageView)convertView.findViewById(R.id.headicon);
		ms.msuser=(TextView)convertView.findViewById(R.id.musername);
		ms.mstime=(TextView)convertView.findViewById(R.id.mtime);
		ms.mstext=(TextView)convertView.findViewById(R.id.mtext);
		ms.msimage = (ImageView)convertView.findViewById(R.id.hasimage);
		ms.home_source=(TextView)convertView.findViewById(R.id.home_source);
		try{
		if(data !=null){
			
			convertView.setTag(data.get("id_str"));
				
				ms.msuser.setText(data.optString("from_user"));
				ms.mstext.setText(data.optString("text"));
				ms.mstime.setText(data.optString("created_at"));
				
				Drawable cachedImage = asyncImageLoader.loadDrawable(data.getString("profile_image_url"),ms.msicon, new ImageCallback(){
                    @Override
                    public void imageLoaded(Drawable imageDrawable,ImageView imageView, String imageUrl) {
                        imageView.setImageDrawable(imageDrawable);
                    }
                });
				if (cachedImage == null) {
					ms.msicon.setImageResource(R.drawable.icon);
				} else {
					ms.msicon.setImageDrawable(cachedImage);
				}
				
			//	if(!"null".equals(data.getString("image"))){
					//ms.msimage.setImageResource(R.drawable.hasimage);
				//}
				
				
			
		}
		}catch(JSONException e){
			e.printStackTrace();
			
		}
		return convertView;
	}
	
	
	
	
	
}

