package yu.service.servlet;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.redbaby.service.CommonUtil;

import yu.service.util.Maps;
import yu.service.vo.ListProduct;
import yu.service.vo.Product;

/**
 * 根据商品id获得商品信息
 */
public class ProductServlet extends HttpServlet {

	 
	private static final long serialVersionUID = -5171023354363648380L;

	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Map<String, Object> outMap = new HashMap<String, Object>();
		String id  = req.getParameter("id");
		System.out.println(id);
		Map<String, ListProduct> prouducts = Maps.prouducts;
		System.out.println(prouducts.size());
		ListProduct product = prouducts.get(id);
		if (product == null) {
			product = prouducts.get("101");
			System.out.println(product.getId());
		}
		outMap.put("product", product);
		CommonUtil.renderJson(resp, outMap);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		doGet(req, resp);
	};
}
