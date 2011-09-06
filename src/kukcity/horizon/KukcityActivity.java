package kukcity.horizon;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
public class KukcityActivity extends Activity implements OnClickListener, OnItemClickListener{

    /** Called when the activity is first created. */
	private List<JSONObject> msList;
    private ImageButton checkIn,refresh;
    private ProgressBar loadingbar;
    private LinearLayout list_foot, loading;
    private ListView listview;
    private TextView msg;
    private ExecutorService executorService;
    private static int PAGE_SIZE=5;
    private int TOTAL_PAGE=0;
    private static int THREADPOOL_SIZE=4;
    private GetPubFinderHandler handler;
    private String url= "https://wenwenintern.appspot.com/kukcity/";
    private NetworkConnData data;
    private ProgressDialog progressDialog;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        view_setUp();
        handler=new GetPubFinderHandler();
        executorService=Executors.newFixedThreadPool(THREADPOOL_SIZE);
        executorService.submit(new GetPubFinderThread());
        msList = new ArrayList<JSONObject>();
        //loadList();

    }

	private void view_setUp() {
		progressDialog = new ProgressDialog(KukcityActivity.this);
		progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("Reading the data now!");
        progressDialog.show();
		//explore=(ImageButton) this.findViewById(R.id.explorepub);
		//explore.setOnClickListener(this);
        refresh=(ImageButton)this.findViewById(R.id.refresh);
        refresh.setOnClickListener(this);
		checkIn=(ImageButton)this. findViewById(R.id.checkinpub);
		checkIn.setOnClickListener(this);
		listview = (ListView)this.findViewById(R.id.listView1);
		list_foot = (LinearLayout)LayoutInflater.from(this).inflate(R.layout.list_footer,null);
		loading = (LinearLayout)list_foot.findViewById(R.id.loading);
		msg = (TextView)list_foot.findViewById(R.id.msg);
		listview.addFooterView(list_foot);
		listview.setOnItemClickListener(this);
		msg.setOnClickListener(this);

		
		//loadingbar=(ProgressBar)findViewById(R.id.progressBar1);
	}
	
	
	

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		//case R.id.explorepub:
			//Intent exPage = new Intent(this, searchablePub.class );
			//startActivity(exPage);
			
			//break;
		case R.id.refresh:
			refreshList();
			break;
		case R.id.checkinpub:
			Intent review = new Intent(this, nearbylocation.class);
			startActivity(review);
			break;
		
		case R.id.msg:
			executorService.submit(new GetPubFinderThread());
			msg.setVisibility(View.GONE);
			loading.setVisibility(View.VISIBLE);
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Object obj=arg1.getTag();
		if(obj!=null){
		
		}
		

	}
	
	class GetPubFinderHandler extends Handler{
		public void handleMessage(Message m){
			MainpageAdapter adapter = new MainpageAdapter(KukcityActivity.this,msList);
			if(TOTAL_PAGE>1){
				adapter.notifyDataSetChanged();
			}else if(TOTAL_PAGE==1){
				listview.setAdapter(adapter);
				
			}
			listview.setSelection((TOTAL_PAGE-1)*PAGE_SIZE+1);
			progressDialog.dismiss();
			loading.setVisibility(View.VISIBLE);
			msg.setVisibility(View.VISIBLE);
			
		}
	}
	
	class GetPubFinderThread implements Runnable{

		@Override
		public void run() {
			refreshList();
			Message msg = handler.obtainMessage();
			handler.sendMessage(msg);
			
		}
		
	}
	
	
	private void refreshList(){
		try{
			 data = new NetworkConnData(url);
			data.HttpClientDownLoadConn();
			JSONArray array = new JSONArray(data.getDownLoadData());
			if(array !=null && array.length()>0){
				TOTAL_PAGE++;
				Log.v("Test",""+TOTAL_PAGE);
				msList.clear();
				for(int i = 0; i<array.length();i++){
					JSONObject object = array.optJSONObject(i);
					if(object.optString("text").equals("null")){
						
					}else {
						msList.add(array.optJSONObject(i));
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
			
	}	
}	
		
		
		
	
	
	
	
	
	
	
	
	
	
	
	