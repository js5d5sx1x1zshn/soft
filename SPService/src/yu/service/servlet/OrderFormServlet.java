package yu.service.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.redbaby.service.CommonUtil;

import net.sf.json.JSONArray;

import yu.service.mysql.OrderForm;

public class OrderFormServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String id  = req.getParameter("id");
		OrderForm orderForm = new OrderForm();
		List<yu.service.vo.OrderForm> forms = orderForm.getAdressList(id);
		JSONArray jsonArray = JSONArray.fromObject(forms);
		String jsonString = jsonArray.toString();
		CommonUtil.render(resp, "application/json", jsonString);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
