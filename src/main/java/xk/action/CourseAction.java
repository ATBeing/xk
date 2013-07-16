package xk.action;

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
import xk.service.CourseServiceI;
import xk.util.ResourceUtil;

import com.opensymphony.xwork2.ModelDriven;

@ParentPackage(value = "basePackage")
@Namespace(value = "/course")
@Action(value = "courseAction", results = {
		@Result(name = "course", location = "/teacher/course.jsp"),
		@Result(name="showCdesc",location="/teacher/courseFullInfor.jsp"),
		@Result(name = "courseAdd", location = "/teacher/courseAdd.jsp"),
		@Result(name = "courseEdit", location = "/teacher/courseEdit.jsp") })
public class CourseAction extends BaseAction implements ModelDriven<Course> {
	
	private static final Logger logger = Logger.getLogger(CourseAction.class);

	private Course course = new Course();

	@Override
	public Course getModel() {
		return course;
	}
	
	private CourseServiceI courseService;
	
	public CourseServiceI getCourseService() {
		return courseService;
	}

	@Autowired
	public void setCourseService(CourseServiceI courseService) {
		this.courseService = courseService;
	}

	public String course() {
		return "course";
	}

	public String courseAdd() {
		return "courseAdd";
	}

	public String courseEdit() {
		return "courseEdit";
	}
	
	public void add(){
		Json json = new Json();
		try {
			SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest()
					.getSession().getAttribute(ResourceUtil.getSessionInfoName());
			course.setUsername(sessionInfo.getLoginName());
			if(courseService.add(course)!=null){
				json.setSuccess(true);
				json.setMsg("添加课程成功，等待审核");
			}else {
				json.setMsg("失败的添加，请重新添加");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json.setMsg("系统故障，添加失败");
		}
		writeJson(json);
	}
	
	public void edit(){
		Json json = new Json();
		try {
			if(courseService.edit(course)!=null){
				json.setSuccess(true);
				json.setMsg("编辑成功");
			}else {
				json.setMsg("失败编辑，请重新编辑");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json.setMsg("系统故障，编辑失败");
		}
		writeJson(json);
	}
	
	public void datagrid(){
		try {
			SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest()
					.getSession().getAttribute(ResourceUtil.getSessionInfoName());
			course.setUsername(sessionInfo.getLoginName());
			DataGrid dGrid = courseService.datagrid(course);
			writeJson(dGrid);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void remove(){
		Json json = new Json();
		try {
			courseService.remove(course.getIds());
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			json.setMsg("系统故障，失败的删除，请重试");
			logger.error(e.getMessage(), e);
		}
		writeJson(json);
	}
	
	public String showCdesc() {
		ServletActionContext.getRequest().setAttribute("desc",
				courseService.getDescById(course.getId()));
		return "showCdesc";
	}
}
