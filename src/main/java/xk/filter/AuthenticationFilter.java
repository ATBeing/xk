package xk.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import xk.pageModel.SessionInfo;
import xk.util.ResourceUtil;


public class AuthenticationFilter implements Filter {

	String url = "/";
	String errorUrl = "/";

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 获得sessionInfo
		HttpSession session = req.getSession();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ResourceUtil
				.getSessionInfoName());
		try {
			// 先判断是否登录
			if (sessionInfo.getLoginName() == null) {
				res.sendRedirect(req.getContextPath() + url);
				return;
			} else {
				// 把登陆后的角色分开
				String[] roleNames = sessionInfo.getRoleNames().split(",");
				for (String roleName : roleNames) {
					// 判断角色里面是否含有admin
					if (!roleName.equals("admin")) {
						res.sendRedirect(req.getContextPath() + errorUrl);
						return;
					}
				}
			}
		} catch (Exception e) {
			res.sendRedirect(req.getContextPath() + url);
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		url = config.getInitParameter("url");
		errorUrl = config.getInitParameter("errorUrl");
	}
}
