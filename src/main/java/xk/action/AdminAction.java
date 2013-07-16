package xk.action;

import java.io.File;

import org.apache.log4j.Logger;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import xk.pageModel.Course;
import xk.pageModel.DataGrid;
import xk.pageModel.Json;
import xk.pageModel.SessionInfo;
import xk.service.AdminServiceI;
import xk.service.CourseServiceI;
import xk.service.PoiServiceI;
import xk.util.ResourceUtil;

import com.opensymphony.xwork2.ModelDriven;

@ParentPackage(value = "basePackage")
@Namespace(value = "/admin")
@Action(value = "adminAction", results = {
		@Result(name = "state", location = "/admin/state.jsp"),
		@Result(name = "adminIO", location = "/admin/adminIO.jsp"),
		@Result(name = "all", location = "/admin/all.jsp")
		})
public class AdminAction extends BaseAction implements ModelDriven<Course>{
	private static final Logger logger = Logger.getLogger(AdminAction.class);

	private File file;
	private Course course = new Course();
	private CourseServiceI courseService;
	private AdminServiceI adminService;
	private PoiServiceI poiService;
	
	@Override
	public Course getModel() {
		return course;
	}

	public CourseServiceI getCourseService() {
		return courseService;
	}

	public PoiServiceI getPoiService() {
		return poiService;
	}

	@Autowired
	public void setPoiService(PoiServiceI poiService) {
		this.poiService = poiService;
	}

	@Autowired
	public void setCourseService(CourseServiceI courseService) {
		this.courseService = courseService;
	}
	
	public AdminServiceI getAdminService() {
		return adminService;
	}

	@Autowired
	public void setAdminService(AdminServiceI adminService) {
		this.adminService = adminService;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String all(){
		return "all";
	}
	
	public String state(){
		return "state";
	}
	
	public String adminIO(){
		return "adminIO";
	}
	public void editState(){
		Json json = new Json();
		try {
			if(adminService.editState(course)){
				json.setMsg("修改状态成功");
				json.setSuccess(true);
			}else {
				json.setMsg("失败的修改状态，请重新修改");
			}
		} catch (Exception e) {
			json.setMsg("系统故障，请重新修改课程状态");
			logger.error(e.getMessage(), e);
		}
		writeJson(json);
	}
	
	public void datagrid(){
		try {
			SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest()
					.getSession().getAttribute(ResourceUtil.getSessionInfoName());
			course.setUsername(sessionInfo.getLoginName());
			DataGrid dGrid = adminService.datagrid(course);
			writeJson(dGrid);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	} 
	
	public void exportUserModela(){
		Json j = new Json();
		try {
			poiService.exportUserModela();
			j.setSuccess(true);
			j.setMsg("导出Model成功");
		} catch (Exception e) {
			j.setMsg("导出Model失败，请重试");
			logger.error(e.getMessage(),e);
		}
		// super.writeJson(j);
	}
	
	public void exportUserToExcela() {
		Json j = new Json();
		try {
			poiService.exportUserToExcela();
			j.setSuccess(true);
			j.setMsg("导出成功");
		} catch (Exception e) {
			j.setMsg("导出失败，请重试");
			logger.error(e.getMessage(),e);
		}
		// super.writeJson(j);
	}
	
	public void importUserExcel() {
		Json j = new Json();
		try {
			poiService.importUserExcel(file);
			j.setSuccess(true);
			j.setMsg("导入成功");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		super.writeJson(j);
	}
}
