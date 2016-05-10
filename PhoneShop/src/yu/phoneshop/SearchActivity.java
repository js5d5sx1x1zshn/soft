package yu.phoneshop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import yu.phoneshop.util.CommonUtil;
import yu.phoneshop.vo.ListProduct;
import yu.phoneshop.vo.OrderForm;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class SearchActivity extends Activity{
	
	/** volley对象*/
	private RequestQueue requestQueue;
	private StringRequest stringRequest;
	private EditText textViewSearch;
	private ArrayList<ListProduct> list;
	private ListView myListView;
	private MyAdapter myAdapter;
	private TextView textSearch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity);
		findViewById(R.id.icon_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		textSearch = (TextView) findViewById(R.id.btn_submit);
		textSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				search();
			}
		});
		requestQueue = Volley.newRequestQueue(this);
		textViewSearch = (EditText) findViewById(R.id.edit_search);
		textViewSearch.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				search();
				return false;
			}
		});
		list = new ArrayList<ListProduct>();
		myListView = (ListView) findViewById(R.id.search_istview);
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
			}
		});
		myAdapter = new MyAdapter(this);
		myListView.setAdapter(myAdapter);
	}
	
	private void search(){
		final String name = textViewSearch.getText().toString();
		if(name.equals("")){
			return;
		}
		stringRequest = new StringRequest(Method.POST,CommonUtil.SERVER_URL1+"GetSearchResult", new Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("search", "search"+response);
				if(list.size()>0){
					list.clear();
				}
				JSONArray jsonArray;
				try {
					jsonArray = new JSONArray(response);
					Log.e("search", "search"+jsonArray.toString());
					for(int i=0;i<jsonArray.length();i++){
						ListProduct product = new ListProduct(jsonArray.getJSONObject(i).getString("id"), 
								jsonArray.getJSONObject(i).getString("name"), jsonArray.getJSONObject(i).
								getString("price"), jsonArray.getJSONObject(i).getString("pic"),jsonArray.
								getJSONObject(i).getString("introduction"));
						list.add(product);
					}
					
					myHandler.sendEmptyMessage(0);
					
				} catch (JSONException e) {
					myHandler.sendEmptyMessage(1);
				}
			}
		},new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("StringRequest", "onErrorResponse"+error.getMessage());
				myHandler.sendEmptyMessage(1);
			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("name", name);
				return params;
			}
		};
		requestQueue.add(stringRequest);
	}
	/**
	 * handler
	 * */
	private Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				myAdapter.notifyDataSetChanged();
				break;
			case 1:
				Toast.makeText(SearchActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				break;
			default:
				break;
			}
		}
	};
	private class MyAdapter extends BaseAdapter{
		
		private Context context;
		private MyTag myTag;
		
		public MyAdapter(Context context) {
			this.context = context;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView = LayoutInflater.from(context).inflate(R.layout.search_activitu_item, parent,false);
				myTag = new MyTag();
				myTag.textView = (TextView) convertView.findViewById(R.id.search_name);
				convertView.setTag(myTag);
			}else{
				myTag = (MyTag) convertView.getTag();
			}
			myTag.textView.setText(list.get(position).getName()); 
			return convertView;
		}
		
		private class MyTag{
			TextView textView;
		}
		
	}
}
