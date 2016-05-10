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
import yu.phoneshop.view.MyAddButton;
import yu.phoneshop.view.MySubButton;
import yu.phoneshop.vo.ListProduct;
import yu.phoneshop.vo.Product;
import yu.phoneshop.vo.ShouyeGuanggao;
import yu.phoneshop.vo.VOShopCart;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ProductsDetail extends Activity{
	/**广告的view page*/
	private ViewPager viewPager;
	/** 广告上的父控件，为了确定大小而获取 */
	private RelativeLayout relative_fragment_content;
	/**广告的图片*/
	private List<ImageView> imageViews;
	/**广告的图片地址*/
	private List<ShouyeGuanggao> imageViewUrl;
	/**商品信息*/
	private Product products;
	/** 单选按钮组，那些小点 */
	private RadioGroup radioGroup_fragment;
	/**底部线性的上部分，动态调正大小*/
	private RelativeLayout relativeLayoutTop;
	/**底部的线性布局*/
	private LinearLayout linearLayout_botton;
	/** volley对象*/
	private RequestQueue requestQueue;
	private StringRequest stringRequest; 
	 /**图片缓存*/
    private ImageCache imageCache;
    private ImageLoader mImageLoader;
    /**第一部分的控件*/
    private TextView imgText;
    private TextView detailPrice;
    private TextView detailIntroduction;
    /**动态控件*/
    private RadioGroup groupzhishi;
    private RadioGroup groupcolor;
    /**数量*/
    private MyAddButton textViewAdd;
    private MySubButton textViewSub;
    private EditText editTextNum;
    /**底盘*/
    private ImageView goShopCart;
    /**按钮控件*/
    private Button addCartBtn;
    private Button goToOrder;
    /**产品id*/
    private String proid;
    private String url;
    /**缓存共享用户*/
    private SharedPreferences myPreferences;
    private Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.products_detail); 
		findViewById(R.id.icon_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		myPreferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
		editor = myPreferences.edit();
		CommonUtil.MYUSER.setUserName(myPreferences.getString("username", ""));
		Intent intent = getIntent();
		if(intent.getStringExtra("proid")!=null){
			proid = intent.getStringExtra("proid");
			url = CommonUtil.SERVER_URL1+"ShopDetail?id="+proid;
		}else{
			myHandler.sendEmptyMessage(3);
			this.finish();
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
        
		viewPager = (ViewPager) findViewById(R.id.viewPager_fragment);
		relative_fragment_content = (RelativeLayout) findViewById(R.id.relative_fragment_content);
		relativeLayoutTop = (RelativeLayout) findViewById(R.id.relative_top);
		linearLayout_botton = (LinearLayout) findViewById(R.id.linearLayout1);
		//设置上部相对布局的高度
		relativeLayoutTop.setLayoutParams(new FrameLayout.LayoutParams
				(LayoutParams.MATCH_PARENT,getTopHeight()));
		
		imageViews = new ArrayList<ImageView>();
		imageViewUrl = new ArrayList<ShouyeGuanggao>();
		for(int i=0;i<6;i++){
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(R.drawable.detail_shop1);
			imageViews.add(imageView);
		}
		products = new Product();
		viewPager.setAdapter(new MyAdvertisementAdapter(imageViews));
		//按钮及监听
		addCartBtn = (Button) findViewById(R.id.btn_add_cart);
		addCartBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(CommonUtil.MYUSER.getUserName()==null||CommonUtil.MYUSER.getUserName().equals("")){
					Toast.makeText(ProductsDetail.this, "请先登录", Toast.LENGTH_SHORT).show();
					return;
				}
				addToCart();
			}
		});
		goToOrder = (Button) findViewById(R.id.go_to_order);
		goToOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(CommonUtil.MYUSER.getUserName()==null||CommonUtil.MYUSER.getUserName().equals("")){
					Toast.makeText(ProductsDetail.this, "请先登录", Toast.LENGTH_SHORT).show();
					return;
				}
				RadioButton radioButton = (RadioButton) findViewById(groupzhishi.getCheckedRadioButtonId());
				String zhishi1 = "";
				if(radioButton != null){
					zhishi1 = radioButton.getText().toString();
				}
				final String zhishi = zhishi1;
				RadioButton radioButton2 = (RadioButton) findViewById(groupcolor.getCheckedRadioButtonId());
				String color1 = "";
				if(radioButton2 != null){
					color1 = radioButton2.getText().toString();
				}
				final String color = color1;
				final String number = editTextNum.getText().toString();
				if(number.equals("")){
					//数量为空
					myHandler.sendEmptyMessage(2);
					return;
				}else if(Integer.parseInt(number)>=Integer.parseInt(products.getUplimit())){
					//数量过多
					myHandler.sendEmptyMessage(4);
					return;
				}else if(Integer.parseInt(number)>Integer.parseInt(products.getNumber())){
					//超出库存
					myHandler.sendEmptyMessage(5);
					return;
				}
				List<VOShopCart> orderList = new ArrayList<VOShopCart>(); 
				VOShopCart cart = new VOShopCart(products.getId(), number, products.getPrice(), 
						products.getImginfo(), zhishi, color, products.getPic());
				orderList.add(cart);
				Intent intent = new Intent(ProductsDetail.this,Settlement.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("shops", (Serializable) orderList);
				bundle.putFloat("money", Float.parseFloat(products.getPrice()));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		//根据屏幕宽度调整广告高度
		relative_fragment_content
		.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, getButtonViewPager()));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				if(arg0 >= 0&&arg0 < 6){
					radioGroup_fragment.getChildAt(arg0).performClick();
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		radioGroup_fragment = (RadioGroup) findViewById(R.id.radioGroup_fragment);
		//设置默认第一页选中
		radioGroup_fragment.getChildAt(0).performClick();
		for (int i = 0; i < radioGroup_fragment.getChildCount(); i++) {
			RadioButton rButton = (RadioButton) radioGroup_fragment.getChildAt(i);
			// 设置tag的下标与滑动界面的下标保持一至
			rButton.setTag(i);
			// 背景状态设置
			rButton.setBackgroundResource(R.drawable.slide_image_dot_c2);
			// 去掉单选按钮前面的点颜色为空
			rButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
		}
		/* 单选按钮组被选中的监听 */
		radioGroup_fragment.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						 
						RadioButton rButton = (RadioButton) group
								.findViewById(checkedId);
						int index = (Integer) (rButton.getTag());
						viewPager.setCurrentItem(index);
					}
		});
		
		imgText = (TextView) findViewById(R.id.imgtext);
		detailPrice = (TextView) findViewById(R.id.detail_price);
		detailIntroduction = (TextView) findViewById(R.id.detail_introduction);
		groupzhishi = (RadioGroup) findViewById(R.id.radiogroup_zhishi);
		groupcolor = (RadioGroup) findViewById(R.id.radiogroup_color);
		textViewAdd = (MyAddButton) findViewById(R.id.detail_add);
		textViewAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(editTextNum.getText().toString().equals("")){
					editTextNum.setText("1");
					return;
				}
				int num = Integer.parseInt(editTextNum.getText().toString());
				if(num>=1&&num<999){
					num++;
					editTextNum.setText(""+num);
				}
			}
		});
		textViewSub = (MySubButton) findViewById(R.id.detail_sub); 
		textViewSub.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(editTextNum.getText().toString().equals("")){
					return;
				}
				int num = Integer.parseInt(editTextNum.getText().toString());
				if(num>1&&num<=999){
					num--;
					editTextNum.setText(""+num);
				}
			}
		});
		editTextNum = (EditText) findViewById(R.id.detai_num);
		goShopCart = (ImageView) findViewById(R.id.btn_shopcart);
		goShopCart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(CommonUtil.MYUSER.getUserName()==null||CommonUtil.MYUSER.getUserName().equals("")){
					Toast.makeText(ProductsDetail.this, "请先登录", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent(ProductsDetail.this,ShopCartActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		init();
	}
	/**
	 * 实现左右滑动广告Adapter
	 */
	class MyAdvertisementAdapter extends PagerAdapter {
		private List<ImageView> list = null;
		public MyAdvertisementAdapter() {
		}
		public MyAdvertisementAdapter(List<ImageView> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			if (list != null) {
				return list.size();
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(list.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			
		
			container.addView(list.get(position));// 每一个item实例化对象
			
			return list.get(position);
		}
	}
	/**
	 * 加载数据
	 */
	private void init(){
		stringRequest = new StringRequest(url, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(response);
					JSONObject object = jsonObject.getJSONObject("part1");
					products.setId(object.getString("id"));
					products.setImginfo(object.getString("imginfo"));
					products.setIntroduction(object.getString("introduction"));
					products.setName(object.getString("name"));
					products.setNumber(object.getString("number"));
					products.setPic(object.getString("pic"));
					products.setPrice(object.getString("price"));
					products.setProdNum(object.getString("prodNum"));
					products.setUplimit(object.getString("uplimit"));
					products.setZhishi(jsonObject.getJSONArray("zhishi").toString());
					products.setColor(jsonObject.getJSONArray("color").toString());
					ArrayList<ShouyeGuanggao> list2 = new ArrayList<ShouyeGuanggao>();
					JSONArray jsonArray2 = (JSONArray) jsonObject.get("imgs");
					Log.e("jsonArray2", "jsonArray2"+jsonArray2.toString());
					for(int i=0;i<jsonArray2.length();i++){
						ShouyeGuanggao shouyeGuanggao = new ShouyeGuanggao(jsonArray2.getJSONObject(i).getString("id"),
							jsonArray2.getJSONObject(i).getString("shopid"), jsonArray2.getJSONObject(i).getString("imgurl"));
						list2.add(shouyeGuanggao);
					}
					
					imageViewUrl.clear();
					imageViewUrl.addAll(list2);
					myHandler.sendEmptyMessage(0);
					
				} catch (JSONException e) {
					Log.e("JSONException","JSONException"+e.toString());
				}
			}
		},new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("StringRequest", "onErrorResponse"+error.getMessage());
				myHandler.sendEmptyMessage(3);
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
				imgText.setText(products.getImginfo());
				detailPrice.setText(products.getPrice());
				detailIntroduction.setText(products.getIntroduction());
				//动态控件
				try {
					JSONArray zhishi = new JSONArray(products.getZhishi());
					groupzhishi.removeAllViews();
					TextView textView1 = new TextView(ProductsDetail.this);
					textView1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
					textView1.setText("制式：");
					textView1.setTextColor(Color.parseColor("#000000"));
					groupzhishi.addView(textView1);
					for(int i=0;i<zhishi.length();i++){
						RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(LayoutParams.WRAP_CONTENT, 
								CommonUtil.dip2px(ProductsDetail.this, 30));
						layoutParams.setMargins(0, CommonUtil.dip2px(ProductsDetail.this, 5), 0, 0);
						RadioButton radioButton = new RadioButton(ProductsDetail.this);
						radioButton.setLayoutParams(layoutParams);
						radioButton.setBackgroundResource(R.drawable.selector_shape_button2);
						radioButton.setText(zhishi.getJSONObject(i).getString("name"));
						Bitmap a = null;
						radioButton.setButtonDrawable(new BitmapDrawable(a));
						radioButton.setTextSize(14);
						groupzhishi.addView(radioButton);
						if(i==0){
							groupzhishi.check(radioButton.getId());
						}
					}
					if(zhishi.length()==0){
						groupzhishi.setVisibility(View.GONE);
					}
					
					JSONArray color = new JSONArray(products.getColor());
					groupcolor.removeAllViews();
					TextView textView2 = new TextView(ProductsDetail.this);
					textView2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
					textView2.setText("颜色：");
					textView2.setTextColor(Color.parseColor("#000000"));
					groupcolor.addView(textView2);
					for(int i=0;i<color.length();i++){
						RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(LayoutParams.WRAP_CONTENT, 
								CommonUtil.dip2px(ProductsDetail.this, 30));
						layoutParams.setMargins(0, CommonUtil.dip2px(ProductsDetail.this, 5), 0, 0);
						RadioButton radioButton = new RadioButton(ProductsDetail.this);
						radioButton.setLayoutParams(layoutParams);
						radioButton.setBackgroundResource(R.drawable.selector_shape_button2);
						radioButton.setText(color.getJSONObject(i).getString("name"));
						Bitmap a = null;
						radioButton.setButtonDrawable(new BitmapDrawable(a));
						radioButton.setTextSize(14);
						groupcolor.addView(radioButton);
						if(i==0){
							groupcolor.check(radioButton.getId());
						}
					}
					if(color.length()==0){
						groupcolor.setVisibility(View.GONE);
					}
				} catch (JSONException e) {
					Log.e("JSONException","动态控件"+e.toString());
				}
				
				for(int i=0;i<imageViewUrl.size();i++){
					ImageListener listener = ImageLoader  
			                .getImageListener(imageViews.get(i), android.R.drawable.ic_menu_rotate,  
			                        android.R.drawable.ic_delete);  
			        mImageLoader.get(imageViewUrl.get(i).getImgurl(), listener); 
				}
				break;
			case 2:
				Toast.makeText(ProductsDetail.this, "数量不能为空", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(ProductsDetail.this, "此产品还不存在", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(ProductsDetail.this, "购买数量过多", Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(ProductsDetail.this, "库存不足", Toast.LENGTH_SHORT).show();
				break;
			case 6:
				Toast.makeText(ProductsDetail.this, "添加成功", Toast.LENGTH_SHORT).show();
				break;
			case 7:
				Toast.makeText(ProductsDetail.this, "添加失败", Toast.LENGTH_SHORT).show();
				break;
			case 8:
				Toast.makeText(ProductsDetail.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};
	/**
	 * 根据屏幕宽度获得view page高度
	 */
	@SuppressLint("InflateParams") @SuppressWarnings("deprecation")
	private int getButtonViewPager() {
		Display display = getWindowManager().getDefaultDisplay(); // Activity#getWindowManager()
		int lenButton = 0;
		//此处比例取决于图片的高宽比
		lenButton = display.getWidth();
		return lenButton;
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
	 /**
		 * 上传服务器加入商品购物车
		 */
		private void addToCart(){
			Log.e("addToCart","addToCart");
			RadioButton radioButton = (RadioButton) findViewById(groupzhishi.getCheckedRadioButtonId());
			String zhishi1 = "";
			if(radioButton != null){
				zhishi1 = radioButton.getText().toString();
			}
			final String zhishi = zhishi1;
			RadioButton radioButton2 = (RadioButton) findViewById(groupcolor.getCheckedRadioButtonId());
			String color1 = "";
			if(radioButton2 != null){
				color1 = radioButton2.getText().toString();
			}
			final String color = color1;
			final String number = editTextNum.getText().toString();
			if(number.equals("")){
				//数量为空
				myHandler.sendEmptyMessage(2);
				return;
			}else if(Integer.parseInt(number)>=Integer.parseInt(products.getUplimit())){
				//数量过多
				myHandler.sendEmptyMessage(4);
				return;
			}else if(Integer.parseInt(number)>Integer.parseInt(products.getNumber())){
				//超出库存
				myHandler.sendEmptyMessage(5);
				return;
			}
			requestQueue.add(new StringRequest(Method.POST,CommonUtil.SERVER_URL1+"AddToCart", new Listener<String>() {

				@Override
				public void onResponse(String response) {
					if(response.equals("0")){
						myHandler.sendEmptyMessage(6);
					}else if(response.equals("1")){
						myHandler.sendEmptyMessage(7);
					}
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					
				}
			}){
				@Override
				protected Map<String, String> getParams() throws AuthFailureError {
				Log.e("addToCart","addToCart");
					Map<String, String> params = new HashMap<String, String>();
					params.put("zhishi", zhishi);
					params.put("color", color);
					params.put("number", number);
					params.put("proid", proid);
					Log.e("userid", CommonUtil.MYUSER.getUserName());
					params.put("userid", CommonUtil.MYUSER.getUserName());
					return params;
				}
			});
		}
}
