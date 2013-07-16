package xk.action;

import org.apache.log4j.Logger;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import xk.pageModel.Checkin;
import xk.pageModel.DataGrid;
import xk.pageModel.Json;
import xk.pageModel.SessionInfo;
import xk.service.CheckinServiceI;
import xk.util.ResourceUtil;

import com.opensymphony.xwork2.ModelDriven;
import com.sun.javadoc.SeeTag;

@ParentPackage(value = "basePackage")
@Namespace(value = "/checkin")
@Action(value = "checkinAction", results = {
		@Result(name = "teacherCheckin", location = "/teacher/teacherCheckin.jsp"),
		@Result(name = "studentCheckin", location = "/student/studentCheckin.jsp"),
		@Result(name = "userEdit", location = "/admin/userEdit.jsp") })
public class CheckinAction extends BaseAction implements ModelDriven<Checkin> {
	private static final Logger logger = Logger.getLogger(CheckinAction.class);

	private Checkin checkin = new Checkin();
	private CheckinServiceI checkinService;

	@Override
	public Checkin getModel() {
		return checkin;
	}

	public CheckinServiceI getCheckinService() {
		return checkinService;
	}

	@Autowired
	public void setCheckinService(CheckinServiceI checkinService) {
		this.checkinService = checkinService;
	}

	public String teacherCheckin() {
		return "teacherCheckin";
	}

	public String studentCheckin() {
		return "studentCheckin";
	}

	public void tDatagrid() {
		SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest()
				.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		checkin.setUsername(sessionInfo.getLoginName());
		try {
			DataGrid dataGrid = checkinService.tDataGrid(checkin);
			writeJson(dataGrid);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void sDatagrid() {
		try {
			DataGrid dataGrid = checkinService.sDataGrid(checkin);
			writeJson(dataGrid);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void sign(){
		Json json = new Json();
		SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest()
				.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		try {
			if(checkinService.sign(sessionInfo.getUserId(),checkin.getCid(),sessionInfo.getIp())){
				json.setSuccess(true);
				json.setMsg("签到成功");
			}else {
				json.setSuccess(true);
				json.setMsg("请不要重复签到");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json.setMsg("系统故障，请重新签到");
		}
		writeJson(json);
	}
}
