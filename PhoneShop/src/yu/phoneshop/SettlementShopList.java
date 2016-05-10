package yu.phoneshop;

import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;

import yu.phoneshop.vo.VOShopCart;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SettlementShopList extends Activity{
	private ListView myListView;
	private ShopAdapter shopAdapter;
	/**商品集合*/
	private List<VOShopCart> orderList;
	/** volley对象*/
	private RequestQueue requestQueue;
	private StringRequest stringRequest; 
	 /**图片缓存*/
    private ImageCache imageCache;
    private ImageLoader mImageLoader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settlement_shop_list);
		findViewById(R.id.icon_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
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
				orderList = oederCarts;
			}
		}
		shopAdapter = new ShopAdapter(this);
		myListView = (ListView) findViewById(R.id.settlement_shop_list);
		myListView.setAdapter(shopAdapter);
		
	}

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
			return orderList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return orderList.get(position);
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
				convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_cart2, parent, false);
				tag.textView1 = (TextView) convertView.findViewById(R.id.textView1);
				tag.price = (TextView) convertView.findViewById(R.id.price);
				tag.zhishi = (TextView) convertView.findViewById(R.id.zhishi);
				tag.imageView1 = (ImageView) convertView.findViewById(R.id.imageView1);
				tag.editText = (TextView) convertView.findViewById(R.id.number);
				convertView.setTag(tag);
			}else{
				tag = (Tag) convertView.getTag();
			}
			tag.textView1.setText(orderList.get(position).getImginfo());
			tag.price.setText("￥"+orderList.get(position).getPrice());
			tag.zhishi.setText("制式："+orderList.get(position).getZhishi()+" 颜色:"+orderList.get(position).getColor());
			tag.editText.setText(orderList.get(position).getNumber());
			ImageListener listener = ImageLoader  
	                .getImageListener(tag.imageView1, android.R.drawable.ic_menu_rotate,  
	                        android.R.drawable.ic_delete);  
	        mImageLoader.get(orderList.get(position).getPic(), listener); 
			return convertView;
		}
		private class Tag{
			private TextView textView1;
			private TextView price;
			private ImageView imageView1;
			private TextView zhishi;
			private TextView editText;
		}
	}
}
