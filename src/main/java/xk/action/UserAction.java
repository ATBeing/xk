package xk.action;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import xk.pageModel.DataGrid;
import xk.pageModel.Json;
import xk.pageModel.SessionInfo;
import xk.pageModel.User;
import xk.service.UserServiceI;
import xk.util.ResourceUtil;


import com.opensymphony.xwork2.ModelDriven;

@ParentPackage(value = "basePackage")
@Namespace(value = "/user")
@Action(value = "userAction", results = {
		@Result(name = "user", location = "/admin/user.jsp"),
		@Result(name = "userAdd", location = "/admin/userAdd.jsp"),
		@Result(name = "userEdit", location = "/admin/userEdit.jsp") })
public class UserAction extends BaseAction implements ModelDriven<User> {

	private User user = new User();

	@Override
	public User getModel() {
		return user;
	}

	private static final Logger logger = Logger.getLogger(UserAction.class);

	private UserServiceI userService;

	public UserServiceI getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserServiceI userService) {
		this.userService = userService;
	}

	public String user() {
		return "user";
	}

	public String userAdd() {
		return "userAdd";
	}

	public String userEdit() {
		return "userEdit";
	}

	public void reg() {
		Json j = new Json();
		try {
			if (userService.reg(user) != null) {
				j.setSuccess(true);
				j.setMsg("注册成功");
			} else {
				j.setMsg("注册失败,请重新注册");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			j.setMsg("注册失败,请重新注册");
		}
		super.writeJson(j);
	}

	public void add() {
		Json j = new Json();
		try {
			User u = userService.reg(user);
			if (u != null) {
				j.setSuccess(true);
				j.setMsg("添加成功");
				logger.info("admin add "+user.getUname()+"successfully");
			} else {
				j.setMsg("添加失败");
				logger.info("admin fail to add "+user.getUname());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			j.setMsg("添加失败,请重试");
		}
		super.writeJson(j);
	}


	public void datagrid() {
		try {
			DataGrid d = userService.datagrid(user);
			super.writeJson(d);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}

	public void remove() {
		Json j = new Json();
		try {
			userService.remove(user.getIds());
			j.setSuccess(true);
			j.setMsg("删除成功");
			logger.info(user.getUname() + "删除成功");
		} catch (Exception e) {
			j.setMsg("系统错误，删除失败");
			logger.error(e.getMessage(),e);
		}
		super.writeJson(j);
	}

	public void edit() {
		Json j = new Json();
		try {
			User u = userService.edit(user);
			if (u != null) {
				j.setSuccess(true);
				j.setMsg("编辑成功");
				j.setObj(u);
			} else {
				j.setMsg("编辑失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			j.setMsg(e.getMessage());
		}
		super.writeJson(j);
	}

}
