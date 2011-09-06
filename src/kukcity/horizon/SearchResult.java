package kukcity.horizon;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class SearchResult extends Activity implements OnClickListener, OnItemClickListener{
	private ImageButton backSearch;
	private List<PubInfo> msList;
	private ListView searchList;
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.searchresult);
	        view_setUp();
	        loadList();
	    }
	 
	 public void view_setUp(){
		 backSearch = (ImageButton)this.findViewById(R.id.searchagain);
		 backSearch.setOnClickListener(this);
		 searchList = (ListView)this.findViewById(R.id.searchresultlist);
		 searchList.setOnItemClickListener(this);
	 }
	 public void loadList(){
			if(msList == null){
				msList = new ArrayList<PubInfo>();
			}
			PubInfo ms = new PubInfo();
			ms.setId("123456");
			ms.setPubName("Rose and Crown");
			ms.setPubReview("130 review");
			ms.setpubAdress("500 Derby Rd, Nottingham");
			msList.add(ms);
			msList.add(ms);
			msList.add(ms);
			msList.add(ms);
			
			if(msList != null){
				PubAdapter adapter = new PubAdapter(this,msList);
			
				searchList.setAdapter(adapter);
				
			}
			//loadingbar.setVisibility(View.GONE);
		}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.searchagain:
			Intent intent = new Intent();
			intent.setClass(SearchResult.this, searchablePub.class);
			startActivity(intent);
			SearchResult.this.finish();
			break;
		
		
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Object obj = arg1.getTag();
		if(obj != null){
			String id=obj.toString();
			Intent pubMainPage = new Intent(SearchResult.this, PubMainPage.class );
			Bundle b = new Bundle();
			b.putString("key", id);
			pubMainPage.putExtras(b);
			startActivity(pubMainPage);
		}
		
	}
}
