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
import xk.model.Tbug;
import xk.pageModel.Bug;
import xk.pageModel.DataGrid;
import xk.service.BugServiceI;
import xk.util.ClobUtil;


@Service("demoBugService")
public class BugServiceImpl implements BugServiceI {

	private BaseDaoI<Tbug> bugDao;

	public BaseDaoI<Tbug> getBugDao() {
		return bugDao;
	}

	@Autowired
	@Qualifier("baseDao")
	public void setBugDao(BaseDaoI<Tbug> bugDao) {
		this.bugDao = bugDao;
	}

	public DataGrid datagrid(Bug bug) {
		DataGrid dataGrid = new DataGrid();
		List<Bug> lBugs = changeModel(find(bug));
		if(lBugs!=null){
			dataGrid.setRows(lBugs);
			dataGrid.setTotal(total(bug));
			return dataGrid;
		}
		return null;
	}

	private List<Bug> changeModel(List<Tbug> tbugs) {
		List<Bug> bugs = new ArrayList<Bug>();
		if (tbugs != null && tbugs.size() > 0) {
			for (Tbug tb : tbugs) {
				Bug b = new Bug();
				BeanUtils.copyProperties(tb, b);
				bugs.add(b);
			}
		}
		return bugs;
	}

	private List<Tbug> find(Bug bug) {
		String hql = "select new Tbug(t.id,t.bname,t.bcreatetime) from Tbug t where 1=1 ";

		Map<String ,Object> map = new HashMap<String, Object>();
		hql = addWhere(bug, hql, map);

		if (bug.getSort() != null && bug.getOrder() != null) {
			hql += " order by " + bug.getSort() + " " + bug.getOrder();
		}
		return bugDao.find(hql, map, bug.getPage(), bug.getRows());
	}

	private Long total(Bug bug) {
		String hql = "select count(*) from Tbug t where 1=1 ";
		Map<String ,Object> map = new HashMap<String, Object>();
		hql = addWhere(bug, hql, map);
		return bugDao.count(hql, map);
	}

	private String addWhere(Bug bug, String hql, Map<String, Object> map) {
		if(bug.getBname()!=null&&!bug.getBname().trim().equals("")){
			hql += " and t.bname like:bname ";
			map.put("bname", "%%" + bug.getBname().trim() + "%%");
		}
		return hql;
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				Tbug t = bugDao.get(Tbug.class, id);
				if (t != null) {
					bugDao.delete(t);
				}
			}
		}
	}

	public void add(Bug bug) {
		if (bug.getBcreatetime() == null) {
			bug.setBcreatetime(new Date());
		}
		Tbug t = new Tbug();
		BeanUtils.copyProperties(bug, t);
		t.setId(UUID.randomUUID().toString());
		bugDao.save(t);
	}

	public String getDescById(String cid) {
		Tbug t = bugDao.get(Tbug.class, cid);
		return ClobUtil.getString(t.getBdesc());
	}

	public void update(Bug bug) {
		Tbug t = bugDao.get(Tbug.class, bug.getId());
		if (t != null) {
			t.setBdesc(bug.getBdesc());
			t.setBname(bug.getBname());
			t.setBcreatetime(new Date());
		}
	}

}
