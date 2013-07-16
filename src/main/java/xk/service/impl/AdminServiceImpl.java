package xk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import xk.dao.base.BaseDaoI;
import xk.model.Tcourse;
import xk.pageModel.Course;
import xk.pageModel.DataGrid;
import xk.service.AdminServiceI;

@Service("adminService")
public class AdminServiceImpl implements AdminServiceI {
	private static final Logger logger = Logger.getLogger(AdminServiceImpl.class);

	private BaseDaoI<Tcourse> courseDao;

	public BaseDaoI<Tcourse> getCourseDao() {
		return courseDao;
	}

	@Autowired
	@Qualifier("baseDao")
	public void setCourseDao(BaseDaoI<Tcourse> courseDao) {
		this.courseDao = courseDao;
	}

	@Override
	public boolean editState(Course course) {
		Tcourse tcourse = courseDao.get(Tcourse.class, course.getId());
		if (tcourse != null) {
			if (course.getLocked() != null) {
				tcourse.setLocked(course.getLocked());
			}
			if (course.getReviewed() != null) {
				tcourse.setReviewed(course.getReviewed());
			}
			courseDao.update(tcourse);
			return true;
		} else {
			logger.error("admin changed course`s state is failure");
		}
		return false;

	}

	@Override
	public DataGrid datagrid(Course course) {
		DataGrid dataGrid = new DataGrid();
		List<Course> lCourses = this.changeModel(this.find(course));
		if(lCourses!=null){
			dataGrid.setRows(lCourses);
			dataGrid.setTotal(this.total(course));
			return dataGrid;
		}
		return null;
	}
	
	public List<Tcourse> find(Course course) {
		String hqlString = "select tc from Tcourse tc where 1=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		hqlString = addWhere(course, hqlString, map);

		if (course.getSort() != null && course.getOrder() != null) {
			hqlString += " order by " + course.getSort() + " " + course.getOrder();
		}
		return courseDao.find(hqlString, map, course.getPage(), course.getRows());
	}
	
	private String addWhere(Course course, String hqlString, Map<String, Object> map) {
		if (course.getCname() != null && !course.getCname().trim().equals("")) {
			hqlString += " and tc.cname like :cname";
			map.put("cname", "%%" + course.getCname().trim() + "%%");
		}
		return hqlString;
	}
	
	private List<Course> changeModel(List<Tcourse> tcourses) {
		List<Course> lCourses = new ArrayList<Course>();
		if (tcourses != null && tcourses.size() > 0) {
			for (Tcourse tcourse : tcourses) {
				Course course = new Course();
				// 只拷贝常用的属性
				BeanUtils.copyProperties(tcourse, course);
				
				if(tcourse.getReviewed()!=null&&tcourse.getReviewed().equals("1")){
					course.setReviewed("已审核");
				}else {
					course.setReviewed("未审核");
				}
				if(tcourse.getLocked()!=null&&tcourse.getLocked().equals("0")){
					course.setLocked("未锁定");
				}else {
					course.setLocked("已锁定");
				}
				
				lCourses.add(course);
			}
			return lCourses;
		} else {
			return null;
		}
	}
	
	private Long total(Course course) {
		String hqlString = "select count(distinct tc) from Tcourse tc where 1=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		hqlString = addWhere(course, hqlString, map);
		return courseDao.count(hqlString, map);
	}
}
