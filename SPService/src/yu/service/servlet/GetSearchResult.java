package yu.service.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import yu.service.mysql.Shop;
import yu.service.vo.ListProduct;
import yu.service.vo.ShouyeGuanggao;

import com.itheima.redbaby.service.CommonUtil;

public class GetSearchResult extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9163323487366691924L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Shop shop = new Shop();
		String name = req.getParameter("name");
		System.out.println(name);
		//首页商品
		ArrayList<ListProduct> product = shop.getSearchResult(name);
		JSONArray jsonArray1 = JSONArray.fromObject(product);
		String jsonString = jsonArray1.toString();
		//转码
		CommonUtil.render(resp, "application/json", jsonString);
	}
	
}
