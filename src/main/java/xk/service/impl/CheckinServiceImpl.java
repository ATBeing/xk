package xk.service.impl;

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

import xk.dao.base.BaseDaoI;
import xk.model.Tcheckin;
import xk.model.Tcourse;
import xk.model.Tuser;
import xk.model.Tusertcourse;
import xk.pageModel.Checkin;
import xk.pageModel.DataGrid;
import xk.service.CheckinServiceI;

@Service("checkinService")
public class CheckinServiceImpl implements CheckinServiceI {

	private BaseDaoI<Tcheckin> checkinDao;
	private BaseDaoI<Tcourse> courseDao;
	private BaseDaoI<Tuser> userDao;
	private BaseDaoI<Tusertcourse> tusertcourseDao;

	public BaseDaoI<Tusertcourse> getTusertcourseDao() {
		return tusertcourseDao;
	}

	@Autowired
	@Qualifier("baseDao")
	public void setTusertcourseDao(BaseDaoI<Tusertcourse> tusertcourseDao) {
		this.tusertcourseDao = tusertcourseDao;
	}

	public BaseDaoI<Tuser> getUserDao() {
		return userDao;
	}

	@Autowired
	@Qualifier("baseDao")
	public void setUserDao(BaseDaoI<Tuser> userDao) {
		this.userDao = userDao;
	}

	public BaseDaoI<Tcourse> getCourseDao() {
		return courseDao;
	}

	@Autowired
	@Qualifier("baseDao")
	public void setCourseDao(BaseDaoI<Tcourse> courseDao) {
		this.courseDao = courseDao;
	}

	public BaseDaoI<Tcheckin> getCheckinDao() {
		return checkinDao;
	}

	@Autowired
	@Qualifier("baseDao")
	public void setCheckinDao(BaseDaoI<Tcheckin> checkinDao) {
		this.checkinDao = checkinDao;
	}

	@Override
	public DataGrid tDataGrid(Checkin checkin) {
		DataGrid dataGrid = new DataGrid();
		List<Checkin> lCheckins = changeModel(find(checkin));
		if (lCheckins != null) {
			dataGrid.setRows(lCheckins);
			dataGrid.setTotal(total(checkin));
			return dataGrid;
		}
		return null;
	}

	public List<Tcheckin> find(Checkin checkin) {
		String hqlString = "select distinct tc from Tcourse tc,Tusertcourse tuc,Tuser tu where tuc.tcourse=tc and tuc.tuser=tu ";
		Map<String, Object> map = new HashMap<String, Object>();
		hqlString = addWhere(checkin, hqlString, map);

//		if (checkin.getSort() != null && checkin.getOrder() != null) {
//			hqlString += " order by " + checkin.getSort() + " " + checkin.getOrder();
//		}
		List<Tcourse> lTcourses = courseDao.find(hqlString, map, checkin.getPage(),
				checkin.getRows());
		if (lTcourses != null && lTcourses.size() > 0) {
			List<Tcheckin> list = new ArrayList<Tcheckin>();
			Map<String, Object> params = new HashMap<String, Object>();
			for (Tcourse tcourse : lTcourses) {
				String hql = "from Tcheckin tck where tck.tcourse=:tcourse";
				params.put("tcourse", tcourse);
				// 找出这位老师上的课程对应的签到记录
				List<Tcheckin> lTcheckins = checkinDao.find(hql, params);
				if(lTcheckins!=null&&lTcheckins.size()>0){
					for (Tcheckin tcheckin : lTcheckins) {
						// 全部添加到一个集合里面
						list.add(tcheckin);
					}
				}
			}
			return list;
		}
		return null;

	}

	private String addWhere(Checkin checkin, String hqlString, Map<String, Object> map) {
		// 初始状态下，应该是查出老师对应课程
		if (checkin.getCname() != null && !checkin.getCname().trim().equals("")) {
			hqlString += " and tc.cname like :cname ";
			map.put("cname", "%%" + checkin.getCname().trim() + "%%");
		}
		hqlString += " and tu.uname=:username";
		map.put("username", checkin.getUsername());
		return hqlString;

	}

	private List<Checkin> changeModel(List<Tcheckin> lTcheckins) {
		if (lTcheckins != null) {
			List<Checkin> lCheckins = new ArrayList<Checkin>();
			for (Tcheckin tcheckin : lTcheckins) {
				Checkin checkin = new Checkin();
				BeanUtils.copyProperties(tcheckin, checkin);
				//获得学生名字与课程名称
				checkin.setStuname(tcheckin.getTuser().getUname());
				checkin.setCname(tcheckin.getTcourse().getCname());
				lCheckins.add(checkin);
			}
			return lCheckins;
		}
		return null;
	}

	private Long total(Checkin checkin) {
		String hqlString = "select count(distinct tck) from Tcourse tc,Tcheckin tck,Tuser tu where tck.tcourse=tc and tck.tuser=tu ";
		Map<String, Object> map = new HashMap<String, Object>();
		hqlString = addWhere(checkin, hqlString, map);
		return checkinDao.count(hqlString, map);
	}

	@Override
	public DataGrid sDataGrid(Checkin checkin) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean sign(String uid, String cid, String ip) {
		Tuser tuser = userDao.get(Tuser.class, uid);
		Tcourse tcourse = courseDao.get(Tcourse.class, cid);
		// 如果该学生有选修这门课，就允许签到
		if (getExitTusertcourse(tcourse, tuser)) {

			String opencheckin = tcourse.getOpencheckin(); // 是否开放签到

			if (opencheckin.equals("1")) {
				String checkinSeq = tcourse.getCheckinSeq().toString(); // 第几次课程的签到
				// 允许签到,接下来根据ip地址与该课的第几次签到数来判断是否代签
				if (getExitTcheckin(ip, checkinSeq, tcourse)) {
					Tcheckin tcheckin = new Tcheckin(UUID.randomUUID().toString(),
							tcourse, tuser, ip, "0", checkinSeq, new Date());
					checkinDao.save(tcheckin);
				} else {
					Tcheckin tcheckin = new Tcheckin(UUID.randomUUID().toString(),
							tcourse, tuser, ip, "1", checkinSeq, new Date());
					checkinDao.save(tcheckin);
				}
				return true;
			}
		}
		return false;
	}
	
	private boolean getExitTcheckin(String ip, String classSeq, Tcourse tcourse) {
		String hqlString = " from Tcheckin tck where tck.ip=:ip and tck.classSeq=:classSeq and tck.tcourse=:tcourse";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ip", ip);
		params.put("classSeq", classSeq);
		params.put("tcourse", tcourse);

		if (checkinDao.get(hqlString, params) != null) {
			return true;
		} else {
			return false;
		}
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
