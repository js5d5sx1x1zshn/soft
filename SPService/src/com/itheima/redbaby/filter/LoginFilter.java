package com.itheima.redbaby.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.redbaby.config.Constant;
import com.itheima.redbaby.service.CommonUtil;

public class LoginFilter implements Filter {

	public static Set<String> urls = new HashSet<String>();

	static {
		urls.add("/addresslist");
		urls.add("/userinfo");
		urls.add("/product/collect");
		urls.add("/checkout");
		urls.add("/ordersumbit");
		urls.add("/invoice");
		urls.add("/favorites");
		urls.add("/orderlist");
		urls.add("/orderdetail");
		urls.add("/ordercancel");
		urls.add("/logistics");
		urls.add("/addresslist");
		urls.add("/addresssave");
		urls.add("/addressdefault");
		urls.add("/addressdelete");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		req.getSession();
		req.getSession().getId();
		String substring = req.getRequestURI().substring(req.getContextPath().length());
 		if (urls.contains(substring)) {
			if (req.getSession().getAttribute("user") == null) {
				Map<String, Object> outMap = new HashMap<String, Object>();
				outMap.put(Constant.RESPONSE, "notlogin");
				CommonUtil.renderJson(resp, outMap);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
