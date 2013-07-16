package xk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import xk.dao.UserDaoI;
import xk.dao.base.BaseDaoI;
import xk.model.Tcheckin;
import xk.model.Tcourse;
import xk.model.Tuser;
import xk.model.Tusertcourse;
import xk.pageModel.Course;
import xk.pageModel.DataGrid;
import xk.service.CourseServiceI;
import xk.service.StudentServiceI;

@Service("studentService")
public class StudentServiceImpl implements StudentServiceI {

	private UserDaoI userDao;
	private BaseDaoI<Tusertcourse> tusertcourseDao;
	private BaseDaoI<Tcourse> courseDao;
	private CourseServiceI courseService;
	private BaseDaoI<Tcheckin> checkinDao;

	public BaseDaoI<Tcourse> getCourseDao() {
		return courseDao;
	}

	@Autowired
	@Qualifier("baseDao")
	public void setCourseDao(BaseDaoI<Tcourse> courseDao) {
		this.courseDao = courseDao;
	}

	public BaseDaoI<Tusertcourse> getTusertcourseDaoI() {
		return tusertcourseDao;
	}

	@Autowired
	@Qualifier("baseDao")
	public void setTusertcourseDaoI(BaseDaoI<Tusertcourse> tusertcourseDao) {
		this.tusertcourseDao = tusertcourseDao;
	}

	public UserDaoI getUserDao() {
		return userDao;
	}

	@Autowired
	@Qualifier("userDao")
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	public BaseDaoI<Tusertcourse> getTusertcourseDao() {
		return tusertcourseDao;
	}

	@Autowired
	@Qualifier("baseDao")
	public void setTusertcourseDao(BaseDaoI<Tusertcourse> tusertcourseDao) {
		this.tusertcourseDao = tusertcourseDao;
	}

	public BaseDaoI<Tcheckin> getCheckinDao() {
		return checkinDao;
	}

	@Autowired
	@Qualifier("baseDao")
	public void setCheckinDao(BaseDaoI<Tcheckin> checkinDao) {
		this.checkinDao = checkinDao;
	}

	public CourseServiceI getCourseService() {
		return courseService;
	}

	@Autowired
	public void setCourseService(CourseServiceI courseService) {
		this.courseService = courseService;
	}

	@Override
	public DataGrid datagrid(Course course) {
		DataGrid dataGrid = new DataGrid();
		List<Course> lCourses = this.changeModel(this.find(course), course.getUsername());
		if (lCourses != null) {
			dataGrid.setRows(lCourses);
			dataGrid.setTotal(this.total(course));
			return dataGrid;
		}
		return null;
	}

	private Long total(Course course) {
		String hqlString = "select count(distinct tc) from Tcourse tc,Tusertcourse tuc,Tuser tu where tuc.tcourse=tc and tuc.tuser=tu ";
		Map<String, Object> map = new HashMap<String, Object>();
		hqlString = this.addWhere(course, hqlString, map);
		return courseDao.count(hqlString, map);
	}

	@Override
	public boolean choose(String ids, String username) {
		if (ids != null) {
			Tuser tuser = userDao.getTuserByName(username);
			for (String id : ids.split(",")) {
				Tcourse tcourse = courseDao.get(Tcourse.class, id);
				if (tcourse != null) {
					if (getExitTusertcourse(tcourse, tuser)) {
						return true;
					} else {
						Tusertcourse tuc = new Tusertcourse(UUID.randomUUID().toString(),
								tuser, tcourse);
						tusertcourseDao.save(tuc);
					}

				}
			}
			return true;
		}
		return false;
	}

	private List<Tcourse> find(Course course) {
		String hqlString = "select distinct tc from Tcourse tc,Tusertcourse tuc where tuc.tcourse=tc ";
		Map<String, Object> map = new HashMap<String, Object>();
		hqlString = addWhere(course, hqlString, map);

		if (course.getSort() != null && course.getOrder() != null) {
			hqlString += " order by " + course.getSort() + " " + course.getOrder();
		}
		return courseDao.find(hqlString, map, course.getPage(), course.getRows());
	}

	private String addWhere(Course course, String hqlString, Map<String, Object> map) {
		if (course.getCname() != null && !course.getCname().trim().equals("")) {
			hqlString += " and tc.cname like :cname ";
			map.put("cname", "%%" + course.getCname().trim() + "%%");
		}
		// hqlString+=" and tu.uname=:username";
		// map.put("username", course.getUsername());

		hqlString += " and tc.reviewed=:reviewed";
		map.put("reviewed", "1");
		return hqlString;
	}

	private List<Course> changeModel(List<Tcourse> tcourses, String username) {
		List<Course> lCourses = new ArrayList<Course>();
		if (tcourses != null && tcourses.size() > 0) {
			for (Tcourse tcourse : tcourses) {
				Course c = new Course();
				// 只拷贝常用的属性
				BeanUtils.copyProperties(tcourse, c);

				if (getXkstate(username, tcourse.getCname())) {
					c.setXkstate("已选");
				} else {
					c.setXkstate("未选");
				}
				lCourses.add(c);
			}
			return lCourses;
		} else {
			return null;
		}
	}

	private boolean getXkstate(String username, String cname) {
		Tuser tuser = userDao.getTuserByName(username);
		if (tuser != null) {
			String hql = "select distinct tc from Tusertcourse tuc,Tuser tu,Tcourse tc where tuc.tuser=tu and tuc.tcourse= tc and tu.uname=:username and tc.cname=:cname ";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("username", username);
			params.put("cname", cname);
			if (tusertcourseDao.get(hql, params) != null) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	private boolean getExitTusertcourse(Tcourse tcourses, Tuser tusers) {
		String hqlString = "select distinct tuc from Tusertcourse tuc ,Tuser tu,Tcourse tc where tuc.tuser=tu and tuc.tcourse=tc and tu=:tusers and tc=:tcourses";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tusers", tusers);
		map.put("tcourses", tcourses);
		if (tusertcourseDao.get(hqlString, map) != null) {
			return true;
		}
		return false;
	}

	
}
