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
import xk.model.Coursefile;
import xk.model.Tcourse;
import xk.model.Tuser;
import xk.model.Tusertcourse;
import xk.pageModel.Course;
import xk.pageModel.DataGrid;
import xk.service.CourseServiceI;

@Service("courseService")
public class CourseServiceImpl implements CourseServiceI {

	private UserDaoI userDao;
	private BaseDaoI<Tcourse> courseDao;
	private BaseDaoI<Tusertcourse> tusertcourseDao;

	public BaseDaoI<Tcourse> getCourseDao() {
		return courseDao;
	}

	public BaseDaoI<Tusertcourse> getTusertcourseDao() {
		return tusertcourseDao;
	}

	
	public UserDaoI getUserDao() {
		return userDao;
	}

	@Autowired
	@Qualifier("userDao")
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	@Autowired
	@Qualifier("baseDao")
	public void setTusertcourseDao(BaseDaoI<Tusertcourse> tusertcourseDao) {
		this.tusertcourseDao = tusertcourseDao;
	}

	@Autowired
	@Qualifier("baseDao")
	public void setCourseDao(BaseDaoI<Tcourse> courseDao) {
		this.courseDao = courseDao;
	}

	

	@Override
	public void remove(String ids) {
		if(ids!=null){
			Map<String, Object> params = new HashMap<String, Object>();
			for (String id : ids.split(",")) {
				Tcourse tcourse = courseDao.get(Tcourse.class, id);
				if(tcourse!=null){
					params.put("tc", tcourse);
					/**
					 * 这里删除很彻底，把要删除课程的所有联系都删除，包括老师与学生
					 * 删除课程包括删除签到记录
					 */
					tusertcourseDao.executeHql("delete Tusertcourse t where t.tcourse=:tc ", params);
					courseDao.delete(tcourse);
				}
			}
		}

	}

	@Override
	public Course edit(Course course) {
		Tcourse tcourse = courseDao.get(Tcourse.class, course.getId());
		if(tcourse!=null){
			BeanUtils.copyProperties(course, tcourse);
			if(tcourse.getCoursefile()!=null){
				BeanUtils.copyProperties(course, tcourse.getCoursefile());
			}else {
				Coursefile coursefile = new Coursefile();
				BeanUtils.copyProperties(course, coursefile);
				tcourse.setCoursefile(coursefile);
			}
			courseDao.update(tcourse);
			return course;
		}
		return null;
	}

	@Override
	public Course add(Course course) {
		Tcourse tcourse = new Tcourse();
		Coursefile coursefile = new Coursefile();
		String uuid = UUID.randomUUID().toString();
		tcourse.setId(uuid);

		BeanUtils.copyProperties(course, tcourse, new String[] { "id" });
		BeanUtils.copyProperties(course, coursefile);

		tcourse.setOpencheckin("0");   //一开始不允许签到
		tcourse.setCheckinSeq(0);      //一开始的签到课程为  0，代表未能签到
		tcourse.setCoursefile(coursefile);

		tcourse.setReviewed("0");  //一开始未审阅
		tcourse.setLocked("0");    //一开始为锁定
		courseDao.save(tcourse);
		
		//保存老师创建的课程
		Tuser tuser = userDao.getTuserByName(course.getUsername());
		Tusertcourse tuc = new Tusertcourse(UUID.randomUUID().toString(),tuser,tcourse);
		tusertcourseDao.save(tuc);
		
		course.setId(uuid);

		return course;
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
		String hqlString = "select distinct tc from Tcourse tc,Tusertcourse tuc,Tuser tu where tuc.tcourse=tc and tuc.tuser=tu ";
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
		hqlString+=" and tu.uname=:username";
		map.put("username", course.getUsername());
		return hqlString;
	}

	private List<Course> changeModel(List<Tcourse> tcourses) {
		List<Course> lCourses = new ArrayList<Course>();
		if (tcourses != null && tcourses.size() > 0) {
			for (Tcourse tcourse : tcourses) {
				Course course = new Course();
				// 只拷贝常用的属性
				BeanUtils.copyProperties(tcourse, course);
				
				if(tcourse.getReviewed().equals("1")){
					course.setReviewed("已审核");
				}else {
					course.setReviewed("未审核");
				}
				
				lCourses.add(course);
			}
			return lCourses;
		} else {
			return null;
		}
	}

	private Long total(Course course) {
		String hqlString = "select count(distinct tc) from Tcourse tc,Tusertcourse tuc,Tuser tu where tuc.tcourse=tc and tuc.tuser=tu ";
		Map<String, Object> map = new HashMap<String, Object>();
		hqlString = addWhere(course, hqlString, map);
		return courseDao.count(hqlString, map);
	}

	@Override
	public String getDescById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
