package xk.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import xk.dao.UserDaoI;
import xk.dao.base.BaseDaoI;
import xk.model.Tmenu;
import xk.model.Tuser;
import xk.service.RepairServiceI;
import xk.util.Encrypt;

@Service("repairService")
public class RepairServiceImpl implements RepairServiceI {
	private BaseDaoI<Tmenu> menuDao;
	private UserDaoI userDao;

	public BaseDaoI<Tmenu> getMenuDao() {
		return menuDao;
	}

	@Autowired
	@Qualifier("baseDao")
	public void setMenuDao(BaseDaoI<Tmenu> menuDao) {
		this.menuDao = menuDao;
	}

	public UserDaoI getUserDao() {
		return userDao;
	}

	@Autowired
	@Qualifier("userDao")
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	@Override
	public void repair() {
		repairMenu();
		// repairRole();
		repairUser();
	}

	private void repairMenu() {
		// 这里的修复不稳定，如果前台把“首页放到子节点下”，修复的时候会报错
		Tmenu root = new Tmenu();
		root.setId("0");
		root.setText("首页");
		root.setUrl("");
		menuDao.saveOrUpdate(root);
		
		Tmenu xtgl = new Tmenu();
		xtgl.setId("xtgl");
		xtgl.setText("系统管理");
		xtgl.setTmenu(root);
		xtgl.setMseq(BigDecimal.valueOf(1));
		menuDao.saveOrUpdate(xtgl);
		
		Tmenu yhgl = new Tmenu();
		yhgl.setId("yhgl");
		yhgl.setText("用户管理");
		yhgl.setUrl("/user/userAction!user.action");
		yhgl.setTmenu(xtgl);
		yhgl.setMseq(BigDecimal.valueOf(1));
		menuDao.saveOrUpdate(yhgl);
		
		Tmenu buggl = new Tmenu();
		buggl.setId("buggl");
		buggl.setText("Bug管理");
		buggl.setUrl("/admin/bugAction!bug.action");
		buggl.setTmenu(xtgl);
		buggl.setMseq(BigDecimal.valueOf(2));
		menuDao.saveOrUpdate(buggl);
		
		Tmenu kcztgl = new Tmenu();
		kcztgl.setId("kcztgl");
		kcztgl.setText("课程总管理");
		kcztgl.setUrl("/admin/adminAction!all.action");
		kcztgl.setTmenu(xtgl);
		kcztgl.setMseq(BigDecimal.valueOf(3));
		menuDao.saveOrUpdate(kcztgl);
		
		Tmenu drdc = new Tmenu();
		drdc.setId("drdc");
		drdc.setText("导入导出");
		drdc.setUrl("/admin/adminAction!adminIO.action");
		drdc.setTmenu(xtgl);
		drdc.setMseq(BigDecimal.valueOf(4));
		menuDao.saveOrUpdate(drdc);
		
		Tmenu js = new Tmenu();
		js.setId("js");
		js.setText("教师");
		js.setUrl("");
		js.setTmenu(root);
		js.setMseq(BigDecimal.valueOf(2));
		menuDao.saveOrUpdate(js);
		
		Tmenu kcgl = new Tmenu();
		kcgl.setId("kcgl");
		kcgl.setText("课程管理");
		kcgl.setUrl("/course/courseAction!course.action");
		kcgl.setTmenu(js);
		kcgl.setMseq(BigDecimal.valueOf(1));
		menuDao.saveOrUpdate(kcgl);
		
		Tmenu qdgl = new Tmenu();
		qdgl.setId("qdgl");
		qdgl.setText("签到管理");
		qdgl.setUrl("/checkin/checkinAction!teacherCheckin.action");
		qdgl.setTmenu(js);
		qdgl.setMseq(BigDecimal.valueOf(2));
		menuDao.saveOrUpdate(qdgl);
		
		Tmenu jszygl = new Tmenu();
		jszygl.setId("jszygl");
		jszygl.setText("教师作业管理");
		jszygl.setUrl("");
		jszygl.setTmenu(js);
		jszygl.setMseq(BigDecimal.valueOf(3));
		menuDao.saveOrUpdate(jszygl);
		
		Tmenu jszxyl = new Tmenu();
		jszxyl.setId("jszxyl");
		jszxyl.setText("教师在线阅览");
		jszxyl.setUrl("");
		jszxyl.setTmenu(js);
		jszxyl.setMseq(BigDecimal.valueOf(4));
		menuDao.saveOrUpdate(jszxyl);
		
		Tmenu xs = new Tmenu();
		xs.setId("xs");
		xs.setText("学生");
		xs.setUrl("");
		xs.setTmenu(root);
		xs.setMseq(BigDecimal.valueOf(3));
		menuDao.saveOrUpdate(xs);
		
		Tmenu xkgl = new Tmenu();
		xkgl.setId("xkgl");
		xkgl.setText("选课管理");
		xkgl.setUrl("/student/studentAction!student.action");
		xkgl.setTmenu(xs);
		xkgl.setMseq(BigDecimal.valueOf(1));
		menuDao.saveOrUpdate(xkgl);
		
		Tmenu xszygl = new Tmenu();
		xszygl.setId("xszygl");
		xszygl.setText("学生作业管理");
		xszygl.setUrl("");
		xszygl.setTmenu(xs);
		xszygl.setMseq(BigDecimal.valueOf(2));
		menuDao.saveOrUpdate(xszygl);
		
		Tmenu xszxyl = new Tmenu();
		xszxyl.setId("xszxyl");
		xszxyl.setText("学生在线阅览");
		xszxyl.setUrl("");
		xszxyl.setTmenu(xs);
		xszxyl.setMseq(BigDecimal.valueOf(3));
		menuDao.saveOrUpdate(xszxyl);
		
		Tmenu qdqk  = new Tmenu();
		qdqk.setId("qdqk");
		qdqk.setText("签到情况");
		qdqk.setUrl("/checkin/checkinAction!studentCheckin.action");
		qdqk.setTmenu(xs);
		qdqk.setMseq(BigDecimal.valueOf(4));
		menuDao.saveOrUpdate(qdqk);
		
		
		
	}

	/*
	 * private void repairRole() { }
	 */

	private void repairUser() {
		Map<String, Object> m = new HashMap<String, Object>();// 修改非法admin信息
		m.put("name", "admin");
		Tuser t = userDao.get("from Tuser t where t.uname=:name and t.id !='0'", m);
		if (t != null) {
			t.setUname(UUID.randomUUID().toString());
		}

		Date date = new Date();
		// 修复admin用户的信息
		Tuser admin = new Tuser();
		admin.setId("0");
		admin.setIdd("Root");
		admin.setUname("admin");
		admin.setPwd(Encrypt.e("admin"));
		admin.setRole("admin");
		admin.setModifytime(date);
		userDao.saveOrUpdate(admin);

		Tuser teacher = new Tuser();
		teacher.setId("1");
		teacher.setIdd("Teacher");
		teacher.setUname("teacher");
		teacher.setPwd(Encrypt.e("teacher0"));
		teacher.setRole("teacher");
		teacher.setModifytime(date);
		userDao.saveOrUpdate(teacher);
		
		Tuser student = new Tuser();
		student.setId("2");
		student.setIdd("Student");
		student.setUname("student");
		student.setPwd(Encrypt.e("student0"));
		student.setRole("student");
		student.setModifytime(date);
		userDao.saveOrUpdate(student);
		
		
	}
}
