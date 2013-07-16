package xk.interceptor;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import xk.pageModel.SessionInfo;
import xk.util.ResourceUtil;


import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * session拦截器
 * 
 * @author 孙宇
 * 
 */
public class SessionInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = 8632843215802761737L;
	private static final Logger logger = Logger.getLogger(SessionInterceptor.class);

	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
		SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest().getSession().getAttribute(ResourceUtil.getSessionInfoName());
		if (sessionInfo == null) {
			ServletActionContext.getRequest().setAttribute("msg", "您还没有登录或登录已超时，请重新登录，然后再刷新本功能！");
			return "noSession";
		}
		return actionInvocation.invoke();
	}

}
