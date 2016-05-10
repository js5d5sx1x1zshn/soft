package yu.service.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import yu.service.mysql.Shop;
import yu.service.util.Maps;
import yu.service.vo.ListProduct;
import yu.service.vo.Product;
import yu.service.vo.ShouyeGuanggao;

import com.itheima.redbaby.service.CommonUtil;

public class ShopProductsList extends HttpServlet{

	/**
	 *获取商城首页商品列表 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Shop shop = new Shop();
		//首页商品
		ArrayList<ListProduct> product = shop.getShouyeProducts();
		JSONArray jsonArray1 = JSONArray.fromObject(product);
		//首页广告
		ArrayList<ShouyeGuanggao> guanggaos = shop.getShouyeGuangao();
		JSONArray jsonArray2 = JSONArray.fromObject(guanggaos);
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.element("product", jsonArray1);
		jsonObject2.element("guanggao", jsonArray2);
		String jsonString = jsonObject2.toString();
		//转码
		CommonUtil.render(resp, "application/json", jsonString);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
