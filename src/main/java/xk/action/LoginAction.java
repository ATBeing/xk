package xk.action;

import org.apache.log4j.Logger;


import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;


import xk.pageModel.Json;
import xk.pageModel.SessionInfo;
import xk.pageModel.User;
import xk.service.UserServiceI;
import xk.util.IpUtil;
import xk.util.ResourceUtil;

import com.opensymphony.xwork2.ModelDriven;

@ParentPackage(value = "basePackage")
@Namespace(value = "/")
@Action(value = "loginAction", results = { @Result(name = "doNotNeedAuth_userInfo", location = "/layout/userInfo.jsp") })
public class LoginAction extends BaseAction implements ModelDriven<User> {
	private static final Logger logger = Logger.getLogger(LoginAction.class);

	private User user = new User();

	@Override
	public User getModel() {
		return user;
	}

	private UserServiceI userService;

	public UserServiceI getUserService() {
		return userService;
	}

	public String doNotNeedAuth_userInfo() {
		return "doNotNeedAuth_userInfo";
	}

	@Autowired
	public void setUserService(UserServiceI userService) {
		this.userService = userService;
	}

	public void doNotNeedSession_login() {
		logger.debug(" login() start");
		Json j = new Json();
		try {
			User u = userService.login(user);
			if (u != null) {
				SessionInfo sessionInfo = new SessionInfo();
				sessionInfo.setUserId(u.getId());
				
				sessionInfo.setLoginName(user.getUname());
				sessionInfo.setIp(IpUtil.getIpAddr(ServletActionContext.getRequest()));
				
				sessionInfo.setLoginPassword(user.getPwd());
				ServletActionContext.getRequest().getSession()
						.setAttribute(ResourceUtil.getSessionInfoName(), sessionInfo);
				sessionInfo.setRole(u.getRole());
				sessionInfo.setRoleIds(u.getRoleIds());
				sessionInfo.setRoleNames(u.getRoleNames());
				sessionInfo.setIdd(u.getIdd());
				sessionInfo.setPhone(u.getPhone());
				sessionInfo.setEmail(u.getEmail());
				sessionInfo.setCreatetime(u.getCreatetime());
				sessionInfo.setModifytime(u.getModifytime());

				j.setSuccess(true);
				j.setMsg("登陆成功");

				logger.info(user.getUname() + " login successfully");
			} else {
				j.setMsg("登陆失败,用户名或密码错误");
				logger.info(user.getUname() + " fail to login,the failed password is"
						+ user.getPwd());
			}
		} catch (Exception e) {
			logger.error(user.getUname() + " fail to login which is the system's error："
					+ "\n  " + e.getMessage());
			j.setMsg("登陆失败,用户名或密码错误");
		}
		super.writeJson(j);
	}

	// 退出系统
	public void doNotNeedSession_logout() {
		// 销毁Session
		SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest()
				.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		logger.info(sessionInfo.getLoginName() + "退出系统");
		ServletActionContext.getRequest().getSession().invalidate();
		// 返回到登陆页面
	}

	public void doNotNeedAuth_editUserInfo() {
		logger.debug("doNotNeedAuth_editUserInfo() start");
		Json j = new Json();
		try {
			userService.editUserInfo(user);
			j.setSuccess(true);
			j.setMsg("编辑成功");
		} catch (Exception e) {
			logger.error(user.getUname() + "\n" + e.getMessage());
			j.setMsg("编辑失败，请重新编辑，如果多次不成功，请暂停编辑并联系管理员");
		}
		super.writeJson(j);
	}

}
