package yu.phoneshop;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.google.gson.Gson;

import yu.phoneshop.util.CommonUtil;
import yu.phoneshop.vo.OrderForm;
import yu.phoneshop.vo.VOShopCart;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Settlement extends Activity{
	/**底部线性的上部分，动态调正大小*/
	private RelativeLayout relativeLayoutTop;
	/**底部的线性布局*/
	private LinearLayout linearLayout_botton;
	private RelativeLayout layoutSettlement;
	/**商品集合*/
	private List<VOShopCart> orderList;
	/** volley对象*/
	private RequestQueue requestQueue;
	private StringRequest stringRequest; 
	 /**图片缓存*/
    private ImageCache imageCache;
    private ImageLoader mImageLoader;
    private RelativeLayout relativeLayoutShopimg;
    /**默认地址*/
    private OrderForm orderForm;
    /**默认地址的布局*/
    private RelativeLayout relativeAdress;
    private RelativeLayout relativeLayout1;
    private TextView textview1;
    private TextView textview2;
    private TextView adress;
    /**提交订单*/
    private Button submitOrder;
    /**不包含运费的金额*/
    private float money;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settlement_activity);
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
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			List<VOShopCart> oederCarts = (List<VOShopCart>) bundle.getSerializable("shops");
			if(oederCarts!=null){
				relativeLayoutShopimg = (RelativeLayout) findViewById(R.id.shop_img);
				relativeLayoutShopimg.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Settlement.this,SettlementShopList.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("shops", (Serializable) orderList);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});
				orderList = oederCarts;
				//加载商品的图片
				if(orderList.size()>0){
					ImageView imageView = (ImageView)findViewById(R.id.settle_shop1);
					imageView.setVisibility(View.VISIBLE);
					ImageListener listener = ImageLoader  
			                .getImageListener(imageView, android.R.drawable.ic_menu_rotate,  
			                        android.R.drawable.ic_delete);  
			        mImageLoader.get(oederCarts.get(0).getPic(), listener); 	
			        if(orderList.size()>1){
			        	ImageView imageView2 = (ImageView)findViewById(R.id.imageView6);
			        	imageView2.setVisibility(View.VISIBLE);
						ImageListener listener2 = ImageLoader  
				                .getImageListener(imageView2, android.R.drawable.ic_menu_rotate,  
				                        android.R.drawable.ic_delete);  
				        mImageLoader.get(oederCarts.get(1).getPic(), listener2); 
				        if(orderList.size()>2){
				        	ImageView imageView3 = (ImageView)findViewById(R.id.imageView8);
				        	imageView3.setVisibility(View.VISIBLE);
							ImageListener listener3 = ImageLoader  
					                .getImageListener(imageView3, android.R.drawable.ic_menu_rotate,  
					                        android.R.drawable.ic_delete);  
					        mImageLoader.get(oederCarts.get(2).getPic(), listener3); 
				        }  
			        }
				}
				//设置商品数量
				TextView textView = (TextView) findViewById(R.id.shop_number);
				textView.setText("共"+orderList.size()+"件");
			}
			//金额
			money = bundle.getFloat("money");
			TextView t = (TextView) findViewById(R.id.textView5);
			t.setText("￥"+money);
			TextView payMoney = (TextView) findViewById(R.id.pay_money);
			payMoney.setText("￥"+(money+0));//运费根据距离算出 现在默认为包邮
		}else{
			linearLayout_botton.setVisibility(View.INVISIBLE);
		}
		relativeAdress = (RelativeLayout) findViewById(R.id.relative2);
		relativeLayout1 = (RelativeLayout) findViewById(R.id.relative1);
		textview1 = (TextView) findViewById(R.id.reciever);
		textview2 = (TextView) findViewById(R.id.reciever_phone);
		adress = (TextView) findViewById(R.id.adress);
		submitOrder = (Button) findViewById(R.id.submit_order);
		submitOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(orderForm==null){				
					myHandler.sendEmptyMessage(3);
					return;
				}
				createNewOrder();
			}
		});
		relativeLayoutTop = (RelativeLayout) findViewById(R.id.relative_top3);
		linearLayout_botton = (LinearLayout) findViewById(R.id.linearLayout3);
		//设置上部相对布局的高度
		relativeLayoutTop.setLayoutParams(new FrameLayout.LayoutParams
				(LayoutParams.MATCH_PARENT,getTopHeight()));
		layoutSettlement = (RelativeLayout) findViewById(R.id.settlement);
		layoutSettlement.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Settlement.this,AdressManagement.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initAdress(CommonUtil.MYUSER.getUserName());
	}
	/**
	 * 从服务器加载默认地址
	 */
	private void initAdress(String string){
		stringRequest = new StringRequest(CommonUtil.SERVER_URL1+"DefaultAdress?userid="+string+"", new Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					if(response.equals("null")){
						//无地址
						relativeLayout1.setVisibility(View.GONE);
						adress.setVisibility(View.GONE);
						
					}else{
						relativeLayout1.setVisibility(View.VISIBLE);
						adress.setVisibility(View.VISIBLE);
						JSONObject jsonObject = new JSONObject(response);
						orderForm = new OrderForm(jsonObject.getString("userid"), jsonObject.getString("reciever"), 
								jsonObject.getString("phone"), jsonObject.getString("adress"), jsonObject.getString("isDefault"),
								jsonObject.getInt("id"));
						myHandler.sendEmptyMessage(0);
					}
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
				if(orderForm!=null){
					relativeAdress.setVisibility(View.VISIBLE);
					textview1.setText(orderForm.getReciever());
					textview2.setText(orderForm.getPhone());
					adress.setText(orderForm.getAdress());
				}else{
					relativeAdress.setVisibility(View.GONE);
					Toast.makeText(Settlement.this, "请设置默认地址", Toast.LENGTH_SHORT).show();
				}
				break;
			case 1:
				Toast.makeText(Settlement.this, "请检查网路连接", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Intent intent = new Intent(Settlement.this,AllOrdersActivity.class);
				startActivityForResult(intent, 3);
				break;
			case 3:
				Toast.makeText(Settlement.this, "请创建地址", Toast.LENGTH_SHORT).show();
				break;		
			default:
				break;
			}
		};
	};
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
	         Log.e("Settlement", "Settlement"+data.getStringExtra("success"));
	         finish();
	    }
	};
	private int getTopHeight(){
		int s = getWindowManager().getDefaultDisplay().getHeight();
		findViewById(R.id.framelayout_products).measure(0, 0);
		linearLayout_botton.measure(0, 0);
		return s-linearLayout_botton.getMeasuredHeight()-getStatusheight();
	}
	/**
	 * 获取状态栏高度
	 * @return 高度
	 */
	 private int getStatusheight(){
		 int statusHeight = 0;
	        Rect localRect = new Rect();
	        getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
	        statusHeight = localRect.top;
	        if (0 == statusHeight){
	            Class<?> localClass;
	            try {
	                localClass = Class.forName("com.android.internal.R$dimen");
	                Object localObject = localClass.newInstance();
	                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
	                statusHeight = getResources().getDimensionPixelSize(i5);
	            } catch (ClassNotFoundException e) {
	                e.printStackTrace();
	            } catch (IllegalAccessException e) {
	                e.printStackTrace();
	            } catch (InstantiationException e) {
	                e.printStackTrace();
	            } catch (NumberFormatException e) {
	                e.printStackTrace();
	            } catch (IllegalArgumentException e) {
	                e.printStackTrace();
	            } catch (SecurityException e) {
	                e.printStackTrace();
	            } catch (NoSuchFieldException e) {
	                e.printStackTrace();
	            }
	        }
	        return statusHeight;
	 }
	 private void createNewOrder(){
		 Gson gson = new Gson();
		 final String string = gson.toJson(orderList);
		 requestQueue.add(new StringRequest(Method.POST, CommonUtil.SERVER_URL1+"CreateOrder", new Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(response.equals("0")){
					myHandler.sendEmptyMessage(2);
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				myHandler.sendEmptyMessage(1);
			}
		}){
			 @Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("adressid", String.valueOf(orderForm.getId()));
				params.put("shoplist", string);
				params.put("price", String.valueOf(money));
				params.put("userid", CommonUtil.MYUSER.getUserName());
				return params;
			}
		 });
	 }
}
