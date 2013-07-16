package xk.service.impl;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
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
import xk.model.Profile;
import xk.model.Tuser;
import xk.model.Tusertcourse;
import xk.pageModel.DataGrid;
import xk.pageModel.User;
import xk.service.UserServiceI;
import xk.util.Encrypt;

@Service("userService")
public class UserServiceImpl implements UserServiceI {
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

	private UserDaoI userDao;
	private BaseDaoI<Tusertcourse> tusertcourseDao;

	public UserDaoI getUserDao() {
		return userDao;
	}

	public BaseDaoI<Tusertcourse> getTusertcourseDaoI() {
		return tusertcourseDao;
	}

	@Autowired
	@Qualifier("baseDao")
	public void setTusertcourseDaoI(BaseDaoI<Tusertcourse> tusertcourseDao) {
		this.tusertcourseDao = tusertcourseDao;
	}

	@Autowired
	@Qualifier("userDao")
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	@Override
	public User reg(User user) {
		Tuser t = new Tuser();
		BeanUtils.copyProperties(user, t, new String[] { "pwd" });/* 将源对象的属性拷贝到目标对象的属性中去 */
		Profile profile = new Profile();
		BeanUtils.copyProperties(user, profile);
		
		String uuid = UUID.randomUUID().toString();
		t.setId(uuid);
		t.setPwd(Encrypt.e(user.getPwd()));/* MD5加密为32位，所以数据库密码长度至少为32 */
		t.setCreatetime(new Date());
		t.setProfile(profile);
		userDao.save(t);
		user.setId(uuid);
	//	BeanUtils.copyProperties(t, user);
		logger.info(user.getUname() + "增加成功");
		return user;
	}

	@Override
	public User login(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uname", user.getUname());
		params.put("pwd", Encrypt.e(user.getPwd()));
		Tuser t = userDao.get("from Tuser t where t.uname=:uname and t.pwd=:pwd", params);
		if (t != null) {
			BeanUtils.copyProperties(t, user);
			logger.info(user.getUname() + " login success");
			return user;
		} else {
			logger.error(" login user is null");
		}
		return null;
	}

	@Override
	public DataGrid datagrid(User user) {
		DataGrid dg = new DataGrid();
		String hql = " from Tuser t where 1=1";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(user, hql, params);
		hql = addOrder(user, hql);
		List<Tuser> lTusers = userDao.find(hql, params, user.getPage(), user.getRows());// 分页使用的方法
		if(lTusers!=null){
			List<User> lUsers = new ArrayList<User>();
			changeModel(lTusers, lUsers);
			dg.setTotal(total(user));
			dg.setRows(lUsers);
			return dg;
		}
		return null;
	}

	public void changeModel(List<Tuser> lTusers, List<User> lUsers) {
		if (lTusers != null && lTusers.size() > 0) {
			for (Tuser t : lTusers) {
				User u = new User();
				BeanUtils.copyProperties(t, u);
				if (t.getProfile() != null) {
					BeanUtils.copyProperties(t.getProfile(), u);
				}

				lUsers.add(u);
			}
		}
	}

	private String addOrder(User user, String hql) {
		if (user.getSort() != null) {
			hql += " order by " + user.getSort() + " " + user.getOrder();
		}
		return hql;
	}

	private String addWhere(User user, String hql, Map<String, Object> params) {
		if (user.getUname() != null && !user.getUname().trim().equals("")) {
			hql += " and t.uname like:uname ";
			params.put("uname", "%%" + user.getUname().trim() + "%%");
		}
		return hql;
	}

	@Override
	public void remove(String ids) {
		if (ids != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			for (String id : ids.split(",")) {
				if (!id.trim().equals("0")) {
					Tuser tu = userDao.get(Tuser.class, id);
					if (tu != null) {
						params.put("tu", tu);
						/**教师：这里只是删除老师建立的课程，而没有删除所建立课程与学生之间的联系
						 * 学生：删除他上过所有的课
						 * 签到：所有签到记录都要删除
						 */
						tusertcourseDao.executeHql("delete Tusertcourse t where t.tuser=:tu ", params);
						userDao.delete(tu);
					}
				}
			}
		}

		logger.info("delete users success");
	}

	/**
	 * 管理员修改用户信息，同时也可以修改用户密码 开启事务之后，对象作出修改。 当事务结束时，hibernate自动发送语句，所以这里不需要显式更新（update）对象数据
	 */
	public User edit(User user) {
		Tuser t = userDao.get(Tuser.class, user.getId());
		if (t != null) {
			BeanUtils.copyProperties(user, t, new String[] { "idd", "id", "pwd",
					"createtime", "modifytime", "role" });
			if (t.getProfile() != null) {
				BeanUtils.copyProperties(user, t.getProfile());
			} else {
				Profile profile = new Profile();
				profile.setEmail(user.getEmail());
				profile.setPhone(user.getPhone());
				t.setProfile(profile);
			}

			// t.setPwd(Encrypt.e(user.getPwd()));/* MD5加密为32位，所以数据库密码长度至少为32 */
			t.setModifytime(new Date());
			return user;
		} else {
			logger.error("user is not find can not edit");
		}
		return null;
	}

	private Long total(User user) {
		String hqlString = "select count(distinct t) from Tuser t where 1=1 ";
		Map<String, Object> params = new HashMap<String, Object>();
		hqlString = addWhere(user, hqlString, params);
		return userDao.count(hqlString, params);
	}

	public void editUserInfo(User user) {
		if (user.getId() != null && !user.getId().trim().equals("")) {
			Tuser t = userDao.get(Tuser.class, user.getId());
			if (user.getPwd() != null && !user.getPwd().trim().equals("")) {
				t.setPwd(Encrypt.e(user.getPwd()));
			}
			BeanUtils.copyProperties(user, t, new String[] { "idd", "pwd", "id",
					"createtime", "modifytime" });
			logger.info(user.getUname() + "edit personal information success");
			userDao.saveOrUpdate(t);
		} else {
			logger.error("no such a user");
		}
	}

	public Long total(String rolename, User user) {
		String hqlStringCount = "select count( distinct tu ) from Tuser tu where 1=1 ";
		Map<String, Object> params = new HashMap<String, Object>();
		if (rolename != null && !rolename.trim().equals("")) {
			hqlStringCount += " and tr.rolename=:rolename";
			params.put("rolename", rolename);
		}
		if (user.getQ() != null && !user.getQ().trim().equals("")) {
			hqlStringCount += " and tu.uname like :q ";
			params.put("q", "%%" + user.getQ().trim() + "%%");
		}
		return userDao.count(hqlStringCount, params);
	}
}
