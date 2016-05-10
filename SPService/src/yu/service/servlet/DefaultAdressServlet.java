package yu.service.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.redbaby.service.CommonUtil;

import net.sf.json.JSONObject;

import yu.service.mysql.User;
import yu.service.vo.OrderForm;


public class DefaultAdressServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8325621169211999685L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			String id = req.getParameter("userid");
			System.out.println(id);
			User user = new User();
			OrderForm orderForm = user.getDefaultAdress(id);
			JSONObject jsonObject = JSONObject.fromObject(orderForm);
			String jsonString = jsonObject.toString();
			CommonUtil.render(resp, "application/json", jsonString);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	}
}
