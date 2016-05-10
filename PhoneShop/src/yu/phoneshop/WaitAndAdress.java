package yu.phoneshop;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yu.phoneshop.util.CommonUtil;
import yu.phoneshop.vo.Order;
import yu.phoneshop.vo.OrderForm;
import yu.phoneshop.vo.VOShopCart;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;

public class WaitAndAdress extends Activity{
	/** volley对象*/
	private RequestQueue requestQueue;
	private StringRequest stringRequest; 
	 /**图片缓存*/
    private ImageCache imageCache;
    private ImageLoader mImageLoader;
    /**默认地址*/
    private OrderForm orderForm;
    
    private ArrayList<Order> arrayListOrder; 
    private ArrayList<VOShopCart> arrayListShoplist; 
    private Order orders;
    private String shopCart;
    //订单号
    private TextView textViewOrderid;
    //收货人
    private TextView textviewReciever;
    //收货人电话
    private TextView textviewPhone;
    //收货地址
    private TextView textViewAdress;
    //商品图片
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    //取消订单
    private Button btnCancelOrder;
    //金额
    private TextView textViewshopmoney;
    private TextView textViewAllmoney;
    //提交已收货申请
    private Button btnPayOrder;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waite_adressa_ctivity);
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
		if(getIntent().getExtras()!=null){
			Bundle bundle = getIntent().getExtras();
			orders = (Order) bundle.getSerializable("order");
			shopCart = (String) bundle.getSerializable("shop");
			Log.e("getIntent","getIntent"+orders.getOrderId()+"  "+shopCart);
		}
		btnCancelOrder = (Button) findViewById(R.id.btn_cancel_order);
		btnCancelOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("cancel", "clickclick");
				cancelOrder();
			}
		});
		btnPayOrder = (Button) findViewById(R.id.btn_pay_order);
		btnPayOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				submitApply();
			}
		});
		textViewshopmoney = (TextView) findViewById(R.id.order_activity_shopmoney);
		textViewAllmoney = (TextView) findViewById(R.id.order_activity_allmoney);
        arrayListOrder = new ArrayList<Order>();
        arrayListShoplist = new ArrayList<VOShopCart>();
        textViewOrderid = (TextView) findViewById(R.id.textview_orderid);
        textviewReciever = (TextView) findViewById(R.id.order_reciever);
        textviewPhone = (TextView) findViewById(R.id.order_reciever_phone);
        textViewAdress = (TextView) findViewById(R.id.order_adress);
        imageView1 = (ImageView) findViewById(R.id.order_allorder_pic1);
        imageView2 = (ImageView) findViewById(R.id.order_allorder_pic2);
        imageView3 = (ImageView) findViewById(R.id.order_allorder_pic3);
        initView();
        Log.e("CommonUtil.MYUSER.getUserName()", "CommonUtil.MYUSER.getUserName()"+CommonUtil.MYUSER.getUserName());
        initAdress(CommonUtil.MYUSER.getUserName());
	}
    
    @Override
    protected void onResume() {
    	super.onResume();
//    	initData();
    	
    }
    private void initView(){
    	textViewOrderid.setText(orders.getOrderId());
    	textViewAllmoney.setText("￥"+(orders.getPrice()+0));//运费是0
    	textViewshopmoney.setText("￥"+orders.getPrice());
		try {
			JSONArray array = new JSONArray(shopCart);
			if(array.length()>0){
				imageView1.setVisibility(View.VISIBLE);
				ImageListener listener = ImageLoader  
		                .getImageListener(imageView1, android.R.drawable.ic_menu_rotate,  
		                        android.R.drawable.ic_delete);  
		        mImageLoader.get(array.getJSONObject(0).getString("pic"), listener);
		        if(array.length()>1){
		        	imageView2.setVisibility(View.VISIBLE);
					ImageListener listener2 = ImageLoader  
			                .getImageListener(imageView2, android.R.drawable.ic_menu_rotate,  
			                        android.R.drawable.ic_delete);  
			        mImageLoader.get(array.getJSONObject(1).getString("pic"), listener2); 
			        if(array.length()>2){
			        	imageView3.setVisibility(View.VISIBLE);
						ImageListener listener3 = ImageLoader  
				                .getImageListener(imageView3, android.R.drawable.ic_menu_rotate,  
				                        android.R.drawable.ic_delete);  
				        mImageLoader.get(array.getJSONObject(2).getString("pic"), listener3); 
			        }  
		          }
				}
		} catch (JSONException e) {
			Log.e("JSONException", "JSONException"+e.toString());
		}
    }
    /**
	 * 从服务器加载默认地址
	 */
	private void initAdress(String userid){
		Log.e("initAdress", "initAdress");
		stringRequest = new StringRequest(CommonUtil.SERVER_URL1+"DefaultAdress?userid="+userid+"", new Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					orderForm = new OrderForm(jsonObject.getString("userid"), jsonObject.getString("reciever"), 
							jsonObject.getString("phone"), jsonObject.getString("adress"), jsonObject.getString("isDefault"),
							jsonObject.getInt("id"));
					myHandler.sendEmptyMessage(0);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					myHandler.sendEmptyMessage(1);
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
				textviewReciever.setText(orderForm.getReciever());
				textviewPhone.setText(orderForm.getPhone());
				textViewAdress.setText(orderForm.getAdress());
//				textViewOrderid.setText(text);
				break;
			case 1:
				Toast.makeText(WaitAndAdress.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(WaitAndAdress.this, "取消申请已提交", Toast.LENGTH_SHORT).show();
				finish();
				break;
			case 3:
				Toast.makeText(WaitAndAdress.this, "取消失败", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(WaitAndAdress.this, "确认收货申请已提交到后台", Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(WaitAndAdress.this, "申请待审核", Toast.LENGTH_SHORT).show();
				break;
			case 6:
				Toast.makeText(WaitAndAdress.this, "提交失败", Toast.LENGTH_SHORT).show();
				break;
			default:
				
				break;
			}
		};
	};
	/***
	 * 申请退货申请
	 */
	private void cancelOrder(){
		requestQueue.add(new StringRequest(CommonUtil.SERVER_URL1+"SubmitApply?orderid="+orders.getOrderId()+"&status=3", new Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("response", "response"+response);
				if(response.equals("0")){
					myHandler.sendEmptyMessage(6);
				}else if(response.equals("1")){
					myHandler.sendEmptyMessage(4);
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
	/**
	 * 提交已收货申请
	 */
	private void submitApply(){
		requestQueue.add(new StringRequest(CommonUtil.SERVER_URL1+"SubmitApply?orderid="+orders.getOrderId()+"&status=4", new Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("response", "response"+response);
				if(response.equals("0")){
					myHandler.sendEmptyMessage(6);
				}else if(response.equals("1")){
					myHandler.sendEmptyMessage(4);
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
