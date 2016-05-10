package yu.service.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yu.service.mysql.Shop;
import yu.service.mysql.ShopDetail;
import yu.service.util.Maps;
import yu.service.vo.ListProduct;
import yu.service.vo.Product;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.itheima.redbaby.config.Constant;
import com.itheima.redbaby.service.CommonUtil;

public class ProductsDetail extends HttpServlet{

	/**
	 * 商品详情
	 */
	private static final long serialVersionUID = -791245011778779603L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String id  = req.getParameter("id");
		if(id==null){
			id = "101";
		}
		ShopDetail shopDetail = new ShopDetail();
		//产品图片数组
		JSONArray jsonArray = JSONArray.fromObject(shopDetail.getDetailGuangao(id));
		Product product = shopDetail.getDetailContent(id);
		JSONArray zhishi = JSONArray.fromObject(product.getZhishi());
		product.setZhishi(null);
		JSONArray color = JSONArray.fromObject(product.getColor());
		product.setColor(null);
		JSONObject jsonObject1 = JSONObject.fromObject(product);
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.element("imgs", jsonArray);
		jsonObject2.element("part1", jsonObject1);
		jsonObject2.element("zhishi", zhishi);
		jsonObject2.element("color", color);
		String jsonString = jsonObject2.toString();
		System.out.println(jsonString);
		CommonUtil.render(resp, "application/json", jsonString);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
}
