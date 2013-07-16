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
import xk.service.StudentServiceI;
import xk.util.ResourceUtil;

import com.opensymphony.xwork2.ModelDriven;

@ParentPackage(value = "basePackage")
@Namespace(value = "/student")
@Action(value = "studentAction", results = {
		@Result(name = "student", location = "/student/student.jsp"),
		@Result(name="showSdesc",location="/student/courseSFullInfor.jsp")
		})
public class StudentAction extends BaseAction implements ModelDriven<Course> {
	private static final Logger logger = Logger.getLogger(StudentAction.class);

	private Course course = new Course();
	
	@Override
	public Course getModel() {
		return course;
	}

	private CourseServiceI courseService;
	private StudentServiceI studentService;

	public CourseServiceI getCourseService() {
		return courseService;
	}

	@Autowired
	public void setCourseService(CourseServiceI courseService) {
		this.courseService = courseService;
	}

	public StudentServiceI getStudentService() {
		return studentService;
	}

	@Autowired
	public void setStudentService(StudentServiceI studentService) {
		this.studentService = studentService;
	}

	public String student(){
		return "student";
	}
	
	public void choose(){
		SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest()
				.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		Json json = new Json();
		try {
			boolean b = studentService.choose(course.getIds(),sessionInfo.getLoginName());
			if(b){
				json.setSuccess(true);
				json.setMsg("选课成功");
			}else {
				json.setMsg("失败的选课，请重新选择");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			json.setMsg("系统故障，选课失败");
		}
		writeJson(json);
	}
	
	public void datagrid(){
		try {
			SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest()
					.getSession().getAttribute(ResourceUtil.getSessionInfoName());
			course.setUsername(sessionInfo.getLoginName());
			DataGrid dGrid = studentService.datagrid(course);
			writeJson(dGrid);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
}
