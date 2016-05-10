package yu.service.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.redbaby.service.CommonUtil;

import net.sf.json.JSONArray;

import yu.service.mysql.ShopSort;
import yu.service.vo.VOShopSort;

public class GetShopSortListServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5877166631845740302L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ShopSort shopSort = new ShopSort();
		List<VOShopSort> arrayList = shopSort.getShopSorts();
		JSONArray jsonArray1 = JSONArray.fromObject(arrayList);
		String jsonString = jsonArray1.toString();
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
