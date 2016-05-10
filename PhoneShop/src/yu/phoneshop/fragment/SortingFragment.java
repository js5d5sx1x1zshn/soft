package yu.phoneshop.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import yu.phoneshop.ProductsDetail;
import yu.phoneshop.R;
import yu.phoneshop.SearchActivity;
import yu.phoneshop.util.CommonUtil;
import yu.phoneshop.vo.ShouyeGuanggao;
import yu.phoneshop.vo.VOShopSort;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SortingFragment extends Fragment{
	
	private ListView listViewSort;
	private SortAdapter sortAdapter;
	private ListView listViewSortItem;
	private SortItemAdapter sortItemAdapter;
	/** volley¶ÔÏó*/
	private RequestQueue requestQueue;
	private StringRequest stringRequest; 
	 /**Í¼Æ¬»º´æ*/
    private ImageCache imageCache;
    private ImageLoader mImageLoader;
    private List<VOShopSort> shopSorts;
    private List<VOShopSort> shopSorts2;
    private List<VOShopSort> shopSorts3;
    private String currentSortId = "1";
    /**ËÑË÷¿ò*/
    private TextView editTextSearch;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	shopSorts = new ArrayList<VOShopSort>();
    	shopSorts2 = new ArrayList<VOShopSort>();
    	shopSorts3 = new ArrayList<VOShopSort>();
    	requestQueue = Volley.newRequestQueue(getActivity());
		final LruCache<String, Bitmap> mImageCache = new LruCache<String, Bitmap>(  
                20);  
		imageCache = new ImageCache() {  
            @Override  
            public void putBitmap(String key, Bitmap value) {  
                mImageCache.put(key, value);  
            }  
  
            @Override  
            public Bitmap getBitmap(String key) {  
                return mImageCache.get(key);  
            }  
        };  
        mImageLoader = new ImageLoader(requestQueue, imageCache);
    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(yu.phoneshop.R.layout.sorting_fragmen, null);
		editTextSearch = (TextView) view.findViewById(R.id.edit_search);
		editTextSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),SearchActivity.class);
				startActivity(intent);
				
			}
		});
		listViewSort = (ListView) view.findViewById(R.id.sort_listview);
		listViewSort.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				currentSortId = String.valueOf(position+1);
				if(shopSorts3.size()>0){
					shopSorts3.clear();
				}
				for(int i=0;i<shopSorts2.size();i++){
					if(shopSorts2.get(i).getPath().equals(currentSortId)){
						shopSorts3.add(shopSorts2.get(i));
					}
				}
				sortItemAdapter.notifyDataSetChanged();
			}
		});
		sortAdapter = new SortAdapter(getActivity());
		listViewSort.setAdapter(sortAdapter); 
		listViewSortItem = (ListView) view.findViewById(R.id.sort_listview_item);
		listViewSortItem.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),ProductsDetail.class);
				intent.putExtra("proid", shopSorts3.get(position).getId());
				startActivity(intent);
			}
		});
		listViewSort.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.e("onItemSelected","onItemSelected"+position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				Log.e("onItemSelected","onItemSelected");
			}
		});
		sortItemAdapter = new SortItemAdapter(getActivity());
		listViewSortItem.setAdapter(sortItemAdapter); 
		return view;
	}
	
	private class SortAdapter extends BaseAdapter{
		
		private Context context;
		private MyTag myTag;
		
		public SortAdapter(Context context) {
			this.context = context;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return shopSorts.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return shopSorts.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				myTag = new MyTag();
				convertView = LayoutInflater.from(context).inflate(R.layout.sort_listview_item, parent,false);
				myTag.textView = (TextView) convertView.findViewById(R.id.sort_listview_text);
				convertView.setTag(myTag);
			}else{
				myTag = (MyTag) convertView.getTag();
			}
			myTag.textView.setText(shopSorts.get(position).getId());
			return convertView;
		}
		private class MyTag{
			TextView textView;
		}
	}
	@Override
	public void onResume() {
		super.onResume();
		initData();
	}
	private void initData(){
		stringRequest = new StringRequest(CommonUtil.SERVER_URL1+"GetShopSortList", new Listener<String>() {
			@Override
			public void onResponse(String response) {
				JSONArray jsonArray;
				try {
					jsonArray = new JSONArray(response);
					if(shopSorts.size()>0){
						shopSorts.clear();
					}
					if(shopSorts2.size()>0){
						shopSorts2.clear();
					}
					for(int i=0;i<jsonArray.length();i++){
						VOShopSort voShopSort = new VOShopSort(jsonArray.getJSONObject(i).getString("sortid"), 
								jsonArray.getJSONObject(i).getString("id"), jsonArray.getJSONObject(i).getString("parentid"), 
								jsonArray.getJSONObject(i).getString("path"), jsonArray.getJSONObject(i).getString("shopname"),
								jsonArray.getJSONObject(i).getString("shopimg"));
						if(voShopSort.getParentid().equals("0")){
							shopSorts.add(voShopSort);
						}else if(voShopSort.getParentid().equals("1")){
							shopSorts2.add(voShopSort);
						}
					}
					myHandler.sendEmptyMessage(0);
				} catch (JSONException e) {
					Log.e("JSONException","JSONException"+e.toString());
					myHandler.sendEmptyMessage(1);
				}
			}
		},new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("StringRequest", "onErrorResponse"+error.getMessage());
				myHandler.sendEmptyMessage(1);
			}
		});
		requestQueue.add(stringRequest);
	}
	/**
	 * handler
	 * */
	private Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if(shopSorts3.size()>0){
					shopSorts3.clear();
				}
				for(int i=0;i<shopSorts2.size();i++){
					if(shopSorts2.get(i).getPath().equals(currentSortId)){
						shopSorts3.add(shopSorts2.get(i));
					}
				}
				sortAdapter.notifyDataSetChanged();
				sortItemAdapter.notifyDataSetChanged();
				Log.e("ontitem", "selected"+listViewSort.getSelectedItemId());
				break;
			case 1:
				Toast.makeText(getActivity(), "Çë¼ì²éÍøÂçÁ¬½Ó", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};
	private class SortItemAdapter extends BaseAdapter{
		
		private Context context;
		private MyTag myTag;
		
		public SortItemAdapter(Context context) {
			this.context = context;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return shopSorts3.size();
		}
		
		
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				convertView = LayoutInflater.from(context).inflate(R.layout.sort_listview_item2, parent,false);
				myTag = new MyTag();
				myTag.textView = (TextView) convertView.findViewById(R.id.textView1);
				myTag.imageView = (ImageView) convertView.findViewById(R.id.imageView1);
				convertView.setTag(myTag);
			}else{
				myTag = (MyTag) convertView.getTag();
			}
			myTag.textView.setText(shopSorts3.get(position).getShopname());
			ImageListener listener = ImageLoader  
	                .getImageListener(myTag.imageView, android.R.drawable.ic_menu_rotate,  
	                        android.R.drawable.ic_delete);  
	        mImageLoader.get(shopSorts3.get(position).getShopimg(), listener); 
			return convertView;
		}
		private class MyTag{
			TextView textView;
			ImageView imageView; 
		}
	}
}
