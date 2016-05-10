package yu.phoneshop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import yu.phoneshop.util.CommonUtil;
import yu.phoneshop.vo.ShouyeGuanggao;
import yu.phoneshop.vo.VOShopCart;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Paint.Join;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShopCartActivity extends Activity{
	/**商品适配器*/
	private ShopAdapter shopAdapter;
	private ListView myListView;
	/**底部线性的上部分，动态调正大小*/
	private RelativeLayout relativeLayoutTop;
	/**底部的线性布局*/
	private RelativeLayout linearLayout_botton;
	/**标题栏*/
	private RelativeLayout layout_main_container;
	/** volley对象*/
	private RequestQueue requestQueue;
	private StringRequest stringRequest; 
	 /**图片缓存*/
    private ImageCache imageCache;
    private ImageLoader mImageLoader;
	/**购物车数据*/
    private List<VOShopCart> carts;
    /**全选按钮之类*/
    private TextView quanxuan;
//    private TextView fanxuan;
    private TextView delete;
    private RelativeLayout relative_choise;
    /**全选按钮的标志*/
    private Map<Integer, Boolean> check_flags;
    /**要删除的物品*/
    private List<VOShopCart> deleteCarts;
    /**要共享的物品*/
    private List<VOShopCart> orderList;
    /**结算按钮*/
    private Button jiesuanBtn;
    /**总价格textview*/
    private TextView textAllMoney;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_cart); 
		findViewById(R.id.icon_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		relativeLayoutTop = (RelativeLayout) findViewById(R.id.relative_top4);
		linearLayout_botton = (RelativeLayout) findViewById(R.id.relative_botton);
		layout_main_container = (RelativeLayout) findViewById(R.id.title_top);
		//设置上部相对布局的高度
		relativeLayoutTop.setLayoutParams(new FrameLayout.LayoutParams
			(LayoutParams.MATCH_PARENT,getTopHeight()));
		relative_choise = (RelativeLayout) findViewById(R.id.choise);
		quanxuan = (TextView) findViewById(R.id.quanxuan); 
//		fanxuan = (TextView) findViewById(R.id.fanxuan);
		delete = (TextView) findViewById(R.id.delete);  
		textAllMoney = (TextView) findViewById(R.id.all_money);
		
		check_flags = new HashMap<Integer, Boolean>();
		quanxuan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(quanxuan.getText().equals("全选")){
					for(int i=0;i<carts.size();i++){
						check_flags.put(i, true);
						textAllMoney.setText("￥"+changeAllMoney());
					}
					shopAdapter.notifyDataSetChanged();
					quanxuan.setText("取消");
				}else if(quanxuan.getText().equals("取消")){
					for(int i=0;i<carts.size();i++){
						check_flags.put(i, false);
						textAllMoney.setText("￥"+changeAllMoney());
					}
					shopAdapter.notifyDataSetChanged();
					quanxuan.setText("全选");
				}
			}
		});
//		fanxuan.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				for(int i=0;i<carts.size();i++){
//					check_flags.put(i, false);
//					shopAdapter.notifyDataSetChanged();
//				}
//			}
//		});
		orderList = new ArrayList<VOShopCart>();
		deleteCarts = new ArrayList<VOShopCart>();
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				deleteCarts.clear();
				for(int i=0;i<carts.size();i++){
					if(check_flags.get(i)){
						VOShopCart shopCart = new VOShopCart();
						shopCart.setColor(carts.get(i).getColor());
						shopCart.setZhishi(carts.get(i).getZhishi());
						shopCart.setShopid(carts.get(i).getShopid());
						shopCart.setUserid(CommonUtil.MYUSER.getUserName());
						deleteCarts.add(shopCart);
					}
				}
				deleteCarts(deleteCarts);
			}
		});
		jiesuanBtn = (Button) findViewById(R.id.btn_shopcart);
		jiesuanBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				float m = 0;                                                                                                              
				orderList.clear();
				for(int i=0;i<carts.size();i++){
					if(check_flags.get(i)){
						m = m +Float.parseFloat(carts.get(i).getPrice());
						orderList.add(carts.get(i));
					}
				}
				if(orderList.size()==0){
					Toast.makeText(ShopCartActivity.this, "请选择商品", Toast.LENGTH_SHORT).show();
				}else{
					Intent intent = new Intent(ShopCartActivity.this,Settlement.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("shops", (Serializable) orderList);
					bundle.putFloat("money", m);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});
		shopAdapter = new ShopAdapter(this);
		myListView = (ListView) findViewById(R.id.cart_istview);
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(check_flags.get(position)){
					check_flags.put(position, false);
					shopAdapter.notifyDataSetChanged();
				}else{
					check_flags.put(position, true);
					shopAdapter.notifyDataSetChanged();
				}
				textAllMoney.setText("￥"+changeAllMoney());
			}
		});
		requestQueue = Volley.newRequestQueue(this);
		carts = new ArrayList<VOShopCart>();
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
//		RelativeLayout.LayoutParams layoutParams =  (android.widget.RelativeLayout.LayoutParams) myListView.getLayoutParams();
//		layoutParams.addRule(RelativeLayout.BELOW,R.id.choise);
//		layoutParams.height = getTopHeight();
//		myListView.setLayoutParams(layoutParams);
		myListView.setAdapter(shopAdapter);
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		init();
	}
	/**
	 * 加载服务器
	 */
    private void init(){
    	stringRequest = new StringRequest(CommonUtil.SERVER_URL1+"ShowCart?userid="+CommonUtil.MYUSER.getUserName()+"", new Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					JSONArray jsonArray = new JSONArray(response);
					List<VOShopCart> c = new ArrayList<VOShopCart>();
					for(int i=0;i<jsonArray.length();i++){
						JSONObject object= jsonArray.getJSONObject(i);
						VOShopCart shopCart = new VOShopCart(object.getString("shopid"), 
								object.getString("number"), object.getString("price"),
								object.getString("imginfo"),object.getString("zhishi"),
								object.getString("color"),object.getString("pic"));
						c.add(shopCart);
					}
					carts.clear();
					carts.addAll(c);
					if(check_flags.isEmpty()){
						for(int i=0;i<carts.size();i++){
							check_flags.put(i, false);
						}
					}
					myHandler.sendEmptyMessage(0);
				} catch (JSONException e) {
					Log.e("JSONException","JSONException"+e.toString());
				}
			}
		},new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("StringRequest", "onErrorResponse"+error.getMessage());
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
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				Log.e("init", "notifyDataSetChanged");
				refeshChecks();
				shopAdapter.notifyDataSetChanged();
				textAllMoney.setText("￥"+changeAllMoney());
				break;
			case 1:
				
				break;
			}
		}
	};
	/**
	 * 购物车list的适配器
	 * */
	private class ShopAdapter extends BaseAdapter{
		private Context context;
		private Tag tag;
		public ShopAdapter(Context context) {
			this.context = context;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return carts.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return carts.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				tag = new Tag();
				convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_cart, parent, false);
				tag.textView1 = (TextView) convertView.findViewById(R.id.textView1);
				tag.price = (TextView) convertView.findViewById(R.id.price);
				tag.zhishi = (TextView) convertView.findViewById(R.id.zhishi);
				tag.imageView1 = (ImageView) convertView.findViewById(R.id.imageView1);
				tag.editText = (EditText) convertView.findViewById(R.id.number);
				tag.checkBox = (CheckBox) convertView.findViewById(R.id.cart_chenkbox);
				convertView.setTag(tag);
			}else{
				tag = (Tag) convertView.getTag();
			}
			tag.textView1.setText(carts.get(position).getImginfo());
			tag.price.setText("￥"+carts.get(position).getPrice());
			tag.zhishi.setText("制式："+carts.get(position).getZhishi()+" 颜色:"+carts.get(position).getColor());
			tag.editText.setText(carts.get(position).getNumber());
			ImageListener listener = ImageLoader  
	                .getImageListener(tag.imageView1, android.R.drawable.ic_menu_rotate,  
	                        android.R.drawable.ic_delete);  
	        mImageLoader.get(carts.get(position).getPic(), listener); 
	        
	        tag.checkBox.setChecked(check_flags.get(position));
	        Log.e("setChecked", "setChecked"+check_flags.get(position));
			return convertView;
		}
		private class Tag{
			private TextView textView1;
			private TextView price;
			private ImageView imageView1;
			private TextView zhishi;
			private EditText editText;
			private CheckBox checkBox;
		}
	}
	
	private int getTopHeight(){
		int s = getWindowManager().getDefaultDisplay().getHeight();
		findViewById(R.id.framelayout_products).measure(0, 0);
		linearLayout_botton.measure(0, 0);
		Log.e("height","height"+getStatusheight());
			 	Log.e("height", "height"+(s-linearLayout_botton.getMeasuredHeight()-getStatusheight()));
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
	 /**删除购物车的数据*/
	 private void deleteCarts(List<VOShopCart> carts){
		 if(carts.size()==0){
			 return;
		 }
		 Gson gson = new Gson();
		 final String string = gson.toJson(carts);
		 requestQueue.add(new StringRequest(Method.POST,CommonUtil.SERVER_URL1+"DeleteCart",new Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("response", "response"+response);
				if(Boolean.valueOf(response)){
					init();
					
				}else{
					Toast.makeText(ShopCartActivity.this, "删除失败！", Toast.LENGTH_SHORT);
				}
			}
		},new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				
			}
		}){
			 @Override
			protected Map<String, String> getParams() throws AuthFailureError {
				 
				Map<String, String> params = new HashMap<String, String>();
				params.put("deletes", string);
				return params;
			}
		 });
	 }
	 //计算选中商品总价格
	 private float changeAllMoney(){
		 float m = 0;
		 for(int i=0;i<check_flags.size();i++){
				if(check_flags.get(i)){
					m = m +Float.parseFloat(carts.get(i).getPrice());
				}
		 }
		 return m;
	 }
	 //删除商品后 刷新商品选中状态
	 private void refeshChecks(){
		 check_flags.clear();
		 for(int i=0;i<carts.size();i++){
			 check_flags.put(i, false);
		}
	 }
}
