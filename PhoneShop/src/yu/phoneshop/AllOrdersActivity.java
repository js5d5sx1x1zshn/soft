package yu.phoneshop;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yu.phoneshop.util.CommonUtil;
import yu.phoneshop.vo.Order;
import yu.phoneshop.vo.VOShopCart;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AllOrdersActivity extends Activity{
	private ListView myListView;
	private MyAdapter myAdapter;
	
	/** volley对象*/
	private RequestQueue requestQueue;
	private StringRequest stringRequest; 
	 /**图片缓存*/
    private ImageCache imageCache;
    private ImageLoader mImageLoader;

    private ArrayList<Order> arrayListOrder; 
     
    private ArrayList<String> arrayListShoplist;
    /** 访问地址*/
    private static final String URL_ALL_ORDER = CommonUtil.SERVER_URL1+"GetNotPay?userid="+CommonUtil.MYUSER.getUserName()+"";
    private static final String URL_WAIT_PAY = CommonUtil.SERVER_URL1+"GetNotPay2?userid="+CommonUtil.MYUSER.getUserName()+"&status=0";
    private static final String URL_WAIT_GET = CommonUtil.SERVER_URL1+"GetNotPay2?userid="+CommonUtil.MYUSER.getUserName()+"&status=1";
    private static final String URL_WAIT_COMMENT = CommonUtil.SERVER_URL1+"GetNotPay2?userid="+CommonUtil.MYUSER.getUserName()+"&status=2";
    private static final String URL_WAIT_EXCHANGE = CommonUtil.SERVER_URL1+"GetNotPay2?userid="+CommonUtil.MYUSER.getUserName()+"&status=3";
    private static final String URL_WAIT_COMPLETE = CommonUtil.SERVER_URL1+"GetNotPay2?userid="+CommonUtil.MYUSER.getUserName()+"&status=4";
    
    private String orderUrl;
    private String title;
    private TextView textViewEmptyInfo;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_orders_activity);
		findViewById(R.id.icon_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		int orderid = getIntent().getIntExtra("orderurl", 0);
		switch (orderid) {
		case 0:
			orderUrl = URL_ALL_ORDER;
			title = "所有订单";
			break;
		case 1:
			orderUrl =URL_WAIT_PAY;
			title = "待付款订单";
			break;
		case 2:
			orderUrl = URL_WAIT_GET;
			title = "待收货订单";
			break;
		case 3:
			orderUrl = URL_WAIT_COMMENT;
			title = "待评价订单";
			break;
		case 4:
			orderUrl = URL_WAIT_EXCHANGE;
			title = "退换订单";
			break;
		case 5:
			orderUrl = URL_WAIT_COMPLETE;
			title = "已完成订单";
			break;
		default:
			break;
		}
		
		requestQueue = Volley.newRequestQueue(this);
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
        
        TextView textViewTitile = (TextView) findViewById(R.id.all_order_titile);
        textViewTitile.setText(title);
        
        arrayListOrder = new ArrayList<Order>();
        arrayListShoplist = new ArrayList<String>();
		myListView = (ListView) findViewById(R.id.allorderis_listview);
		textViewEmptyInfo = (TextView) findViewById(R.id.textview_empty_info);
		myAdapter = new MyAdapter(this);
		myListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int status = Integer.parseInt(arrayListOrder.get(position).getStatus());
				switch (status) {
				case 0:
					Intent intent = new Intent(AllOrdersActivity.this,OrderActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("order", arrayListOrder.get(position));
					bundle.putSerializable("shop", arrayListShoplist.get(position));
					intent.putExtras(bundle);
					startActivity(intent);
					break;
				case 1:
					Intent intent2 = new Intent(AllOrdersActivity.this,WaitAndAdress.class);
					Bundle bundle2 = new Bundle();
					bundle2.putSerializable("order", arrayListOrder.get(position));
					bundle2.putSerializable("shop", arrayListShoplist.get(position));
					intent2.putExtras(bundle2);
					startActivity(intent2);
					break;
				case 3:
					Intent intent3 = new Intent(AllOrdersActivity.this,QuitChangeShop.class);
					Bundle bundle3 = new Bundle();
					bundle3.putSerializable("order", arrayListOrder.get(position));
					bundle3.putSerializable("shop", arrayListShoplist.get(position));
					intent3.putExtras(bundle3);
					startActivity(intent3);
					break;
				case 4:
					Intent intent4 = new Intent(AllOrdersActivity.this,CompletedOrder.class);
					Bundle bundle4 = new Bundle();
					bundle4.putSerializable("order", arrayListOrder.get(position));
					bundle4.putSerializable("shop", arrayListShoplist.get(position));
					intent4.putExtras(bundle4);
					startActivity(intent4);
					break;
				default:
					break;
				}
			}
		});
		myListView.setAdapter(myAdapter);
	}
	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}
	private void initData(){
		Log.e("allorder", "initData");
		stringRequest = new StringRequest(orderUrl, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					Log.e("OrderActivity","initData"+jsonObject.toString());
					JSONArray arrayShoplist = jsonObject.getJSONArray("shoplist");
					JSONArray arrayOrders = jsonObject.getJSONArray("orders");
					if(arrayListOrder.size()>0){
						arrayListOrder.clear();
					}
					if(arrayListShoplist.size()>0){
						arrayListShoplist.clear();
					}
					
					for(int i=0;i<arrayOrders.length();i++){
						Order order = new Order(arrayOrders.getJSONObject(i).getString("orderId"), arrayOrders.getJSONObject(i).getString("status"), 
								arrayOrders.getJSONObject(i).getDouble("price"), arrayOrders.getJSONObject(i).getString("time"), 
								arrayOrders.getJSONObject(i).getString("adressId"), null, 
								arrayOrders.getJSONObject(i).getString("userid"));
						arrayListOrder.add(order);
					}
					
					for(int j=0;j<arrayShoplist.length();j++){
						JSONArray jsonArrayListShop = arrayShoplist.getJSONArray(j);
						arrayListShoplist.add(jsonArrayListShop.toString());
					}
					if(arrayListOrder.isEmpty()||arrayListShoplist.isEmpty()){
						Log.e("AllOrdersActivity", "getdata 数据为空");
						myHandler.sendEmptyMessage(2);
					}else{
						myHandler.sendEmptyMessage(0);
					}
				} catch (JSONException e) {
					Log.e("JSONException", "JSONException"+e.toString());
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				myHandler.sendEmptyMessage(1);
			}
		});
		requestQueue.add(stringRequest);
	}
	
	private Handler myHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				textViewEmptyInfo.setVisibility(View.GONE);
				myAdapter.notifyDataSetInvalidated();
				break;
			case 1:
				Toast.makeText(AllOrdersActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
				break;
			case 2: 
				textViewEmptyInfo.setVisibility(View.VISIBLE);
				textViewEmptyInfo.setText(title+"为空");
				break;
			case 4:
				Toast.makeText(AllOrdersActivity.this, "支付申请已提交到后台", Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(AllOrdersActivity.this, "申请待审核", Toast.LENGTH_SHORT).show();
				break;
			case 6:
				Toast.makeText(AllOrdersActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
				break;	
			case 7:
				Toast.makeText(AllOrdersActivity.this, "确认收货申请已提交到后台", Toast.LENGTH_SHORT).show();
				break;
			case 8:
				Toast.makeText(AllOrdersActivity.this, "取消申诉申请已提交到后台", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};
	public void onBackPressed() {
		Log.e("AllOrdersActivity", "onBackPressed");
		Intent intent = new Intent();
		intent.putExtra("success", "success");
		setResult(RESULT_OK,intent);
		finish();
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
			return arrayListShoplist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return arrayListShoplist.get(position);
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
				convertView = LayoutInflater.from(context).inflate(R.layout.all_orders_item,parent,false);
				myTag.picImageView1 = (ImageView) convertView.findViewById(R.id.allorder_pic1);
				myTag.picImageView2 = (ImageView) convertView.findViewById(R.id.allorder_pic2);
				myTag.picImageView3 = (ImageView) convertView.findViewById(R.id.allorder_pic3);
				myTag.textViewAllorderPic = (TextView) convertView.findViewById(R.id.allorder_pic_textview);
				myTag.pay = (TextView) convertView.findViewById(R.id.pay_money);
				myTag.allmoney = (TextView) convertView.findViewById(R.id.all_order_allmoney);
				convertView.setTag(myTag);
			}else{
				myTag = (MyTag) convertView.getTag();
			}
			myTag.allmoney.setText(""+arrayListOrder.get(position).getPrice());
			myTag.pay.setTag(position);
			int status = Integer.parseInt(arrayListOrder.get(position).getStatus());
			switch (status) {
			case 0:
				myTag.pay.setText("去结算");
				break;
			case 1:
				myTag.pay.setText("确认收货");
				break;
			case 3:
				myTag.pay.setText("取消申诉");
				break;
			case 4:
				myTag.pay.setText("已完成订单");
				myTag.pay.setClickable(false);
				break;
			default:
				break;
			}
			myTag.pay.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Log.e("myTag.pay","onClick"+v.getTag());	
					int status = Integer.parseInt(arrayListOrder.get((Integer) v.getTag()).getStatus());
					switch (status) {
					case 0:
						submitApply(arrayListOrder.get((Integer) v.getTag()).getOrderId(),1);
						break;
					case 1:
						submitApply(arrayListOrder.get((Integer) v.getTag()).getOrderId(),3);
						break;
					case 3:
						submitApply(arrayListOrder.get((Integer) v.getTag()).getOrderId(),4);
						break;
					default:
						break;
					}
				}
			});
			try {
				JSONArray array = new JSONArray(arrayListShoplist.get(position));
				if(array.length()>0){
				myTag.picImageView1.setVisibility(View.VISIBLE);
				ImageListener listener = ImageLoader  
		                .getImageListener(myTag.picImageView1, android.R.drawable.ic_menu_rotate,  
		                        android.R.drawable.ic_delete);  
		        mImageLoader.get(array.getJSONObject(0).getString("pic"), listener);
	        	myTag.textViewAllorderPic.setText(array.getJSONObject(0).getString("imginfo"));
	        	myTag.picImageView2.setVisibility(View.GONE);
		        myTag.picImageView3.setVisibility(View.GONE);
	        	myTag.textViewAllorderPic.setVisibility(View.VISIBLE);
		        if(array.length()>1){
		        	myTag.picImageView2.setVisibility(View.VISIBLE);
					ImageListener listener2 = ImageLoader  
			                .getImageListener(myTag.picImageView2, android.R.drawable.ic_menu_rotate,  
			                        android.R.drawable.ic_delete);  
			        mImageLoader.get(array.getJSONObject(1).getString("pic"), listener2); 
		        	myTag.textViewAllorderPic.setVisibility(View.GONE);
		        	myTag.picImageView3.setVisibility(View.GONE);
			        if(array.length()>2){
						ImageListener listener3 = ImageLoader  
				                .getImageListener(myTag.picImageView3, android.R.drawable.ic_menu_rotate,  
				                        android.R.drawable.ic_delete);  
				        mImageLoader.get(array.getJSONObject(2).getString("pic"), listener3); 
				        myTag.picImageView3.setVisibility(View.VISIBLE);
			        }  
		          }
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return convertView;
		}
		private class MyTag{
			ImageView picImageView1;
			ImageView picImageView2;
			ImageView picImageView3;
			TextView textViewAllorderPic;
			TextView pay;
			TextView allmoney;
		}
	}
	/**
	 * 提交支付申请 
	 */
	private void submitApply(String orderid,final int status){
		requestQueue.add(new StringRequest(CommonUtil.SERVER_URL1+"SubmitApply?orderid="+orderid+"&status="+status+"", new Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("response", "response"+response);
				if(response.equals("0")){
					myHandler.sendEmptyMessage(6);
				}else if(response.equals("1")){
					switch (status) {
					case 1:
						myHandler.sendEmptyMessage(4);
						break;
					case 3:
						myHandler.sendEmptyMessage(7);
						break;
					case 4:
						myHandler.sendEmptyMessage(8);
						break;
					default:
						break;
					}
				}else if(response.equals("2")){
					myHandler.sendEmptyMessage(5);
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				myHandler.sendEmptyMessage(1);
			}
		}));
	}
}
