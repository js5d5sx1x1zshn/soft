package yu.phoneshop.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import yu.phoneshop.ProductsDetail;
import yu.phoneshop.R;
import yu.phoneshop.SearchActivity;
import yu.phoneshop.util.CommonUtil;
import yu.phoneshop.view.MyGridview;
import yu.phoneshop.vo.ListProduct;
import yu.phoneshop.vo.ShouyeGuanggao;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("InflateParams") public class ShopFragment extends Fragment{
	//实现下拉刷新的ScrollView
//	private PullToRefreshScrollView mPullRefreshScrollView;
	/**广告的view page*/
	private ViewPager viewPager;
	/** 广告上的父控件，为了确定大小而获取 */
	private RelativeLayout relative_fragment_content;
	/**广告的图片*/
	private List<ImageView> imageViews;
	/**广告的图片地址*/
	private List<ShouyeGuanggao> imageViewUrl;
	/** 单选按钮组，那些小点 */
	private RadioGroup radioGroup_fragment;
	/**广告下面的商品列表*/
	private MyGridview shopGridView;
	/**商品列表适配器*/
	private ShopFragmentAdapter fragmentAdapter;
	/** volley对象*/
	private RequestQueue requestQueue;
	private StringRequest stringRequest; 
	/** 标志位，标志已经初始化完成*/  
    private boolean isPrepared;  
    /**标志该碎片可见*/
    private boolean isVisible;
    /**商品集合*/
    private ArrayList<ListProduct> listProducts;
    /**图片缓存*/
    private ImageCache imageCache;
    private ImageLoader mImageLoader;
    /**搜索框*/
    private TextView editTextSearch;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		listProducts = new ArrayList<ListProduct>();
		imageViewUrl = new ArrayList<ShouyeGuanggao>();
		imageViews = new ArrayList<ImageView>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		/**整个商城fragment的view*/
		View view = inflater.inflate(yu.phoneshop.R.layout.shop_fragment, container, false);
		editTextSearch = (TextView) view.findViewById(R.id.edit_search);
		editTextSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),SearchActivity.class);
				startActivity(intent);
				
			}
		});
		//广告
		viewPager = (ViewPager) view.findViewById(R.id.viewPager_fragment);
		relative_fragment_content = (RelativeLayout) view
				.findViewById(R.id.relative_fragment_content);
		for(int i=0;i<4;i++){
			ImageView imageView = new ImageView(getActivity());
			imageView.setImageResource(R.drawable.huawei1);
			imageViews.add(imageView);
		}
		viewPager.setAdapter(new MyAdvertisementAdapter(imageViews));
		//根据屏幕宽度调整广告高度
		relative_fragment_content
		.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, getButtonViewPager()));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				Log.e("viewPager", "onPageSelected"+arg0);
				if(arg0 >= 0&&arg0 < 4){
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
		//广告下面的小点
		radioGroup_fragment = (RadioGroup) view.findViewById(R.id.radioGroup_fragment);
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
		//商品List View初始化
		shopGridView = (MyGridview) view.findViewById(R.id.shop_gridview);
		fragmentAdapter = new ShopFragmentAdapter(getActivity());
		shopGridView.setAdapter(fragmentAdapter);
		shopGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.e("onItemClick", "onItemClick"+id);
				Intent intent = new Intent(getActivity(),ProductsDetail.class);
				String s = listProducts.get(position).getId();
				intent.putExtra("proid", s);
				startActivity(intent);
			}
		});
//		mPullRefreshScrollView = (PullToRefreshScrollView) view;
		viewPager.setFocusable(true);
		viewPager.setFocusableInTouchMode(true);
		viewPager.requestFocus();
		isPrepared = true;
		return view;
	}
	@Override
	public void onResume() {
		super.onResume();
		initData();
	}
	/**当碎片可见时为true*/
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if(getUserVisibleHint()){
			isVisible = true;
		}else{
			isVisible = false;
		}
	}
	/**
	 * 从服务器加载数据
	 */
	private void initData(){
		Log.e("ShopFragment", "initData");
		//加载商品列表数据
		stringRequest = new StringRequest(CommonUtil.SERVER_URL1+"productList", new Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("StringRequest", "onResponse"+response);
				try {
					ArrayList<ListProduct> list = new ArrayList<ListProduct>();
					JSONObject jsonObject = new JSONObject(response);
					JSONArray jsonArray = (JSONArray) jsonObject.get("product");
					for(int i=0;i<jsonArray.length();i++){
						
						ListProduct product = new ListProduct(jsonArray.getJSONObject(i).getString("id"), 
								jsonArray.getJSONObject(i).getString("name"), jsonArray.getJSONObject(i).
								getString("price"), jsonArray.getJSONObject(i).getString("pic"),jsonArray.
								getJSONObject(i).getString("introduction"));
						list.add(product);
					}
					ArrayList<ShouyeGuanggao> list2 = new ArrayList<ShouyeGuanggao>();
					JSONArray jsonArray2 = (JSONArray) jsonObject.get("guanggao");
					for(int i=0;i<jsonArray2.length();i++){
						ShouyeGuanggao shouyeGuanggao = new ShouyeGuanggao(jsonArray2.getJSONObject(i).getString("id"),
							jsonArray2.getJSONObject(i).getString("shopid"), jsonArray2.getJSONObject(i).getString("imgurl"));
						list2.add(shouyeGuanggao);
					}
					if(!list.isEmpty()){
						listProducts.clear();
						listProducts.addAll(list);
						imageViewUrl.clear();
						imageViewUrl.addAll(list2);
						myHandler.sendEmptyMessage(0);
					}
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
	 * 
	 * */
	private Handler myHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				fragmentAdapter.notifyDataSetChanged();
				for(int i=0;i<4;i++){
					ImageListener listener = ImageLoader  
			                .getImageListener(imageViews.get(i), android.R.drawable.ic_menu_rotate,  
			                        android.R.drawable.ic_delete);  
			        mImageLoader.get(imageViewUrl.get(i).getImgurl(), listener); 
				}
				break;
			case 3:
				Toast.makeText(getActivity(), "请检查网路连接", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};
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
			
			return 4;
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
	 * 商品列表的适配器
	 * @author y
	 *
	 */
	class ShopFragmentAdapter extends BaseAdapter{
		private Context context;
		private MyTag myTag;
		public ShopFragmentAdapter(Context context) {
			// TODO Auto-generated constructor stub
			this.context = context;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listProducts.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listProducts.get(position);
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
				convertView = LayoutInflater.from(context).inflate(yu.phoneshop.R.layout.item_shop_listview, null);
				Log.e("getView", "getView"+listProducts.get(position).getIntroduction());
				myTag.imageView = (ImageView) convertView.findViewById(R.id.shop_imageview);
				myTag.textView_name = (TextView) convertView.findViewById(R.id.shop_name);
				myTag.textView_introduction = (TextView) convertView.findViewById(R.id.shop_introduction);
				myTag.textView_price = (TextView) convertView.findViewById(R.id.shop_price);
				convertView.setTag(myTag);
			}else{
				myTag = (MyTag) convertView.getTag();
			};
			  
	        // imageView是一个ImageView实例  
	        // ImageLoader.getImageListener的第二个参数是默认的图片resource id  
	        // 第三个参数是请求失败时候的资源id，可以指定为0  
	        ImageListener listener = ImageLoader  
	                .getImageListener(myTag.imageView, android.R.drawable.ic_menu_rotate,  
	                        android.R.drawable.ic_delete);  
	        mImageLoader.get(listProducts.get(position).getPic(), listener);  
	        myTag.textView_name.setText(listProducts.get(position).getName());
	        myTag.textView_introduction.setText(listProducts.get(position).getIntroduction());
	        myTag.textView_price.setText(listProducts.get(position).getPrice());
			return convertView;
		}
		private class MyTag{
			ImageView imageView;
			TextView textView_name;
			TextView textView_introduction;
			TextView textView_price;
		}
	}
	/**
	 * 根据屏幕宽度获得view page高度
	 */
	@SuppressLint("InflateParams") @SuppressWarnings("deprecation")
	private int getButtonViewPager() {
		Display display = getActivity().getWindowManager().getDefaultDisplay(); // Activity#getWindowManager()
		int lenButton = 0;
		//此处比例取决于图片的高宽比
		lenButton = display.getWidth() * 20 / 48;
		return lenButton;
	}
	
	 //构造函数
	public ShopFragment() {
			
	}
}
