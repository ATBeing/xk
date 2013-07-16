package xk.service.impl;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import xk.dao.base.BaseDaoI;
import xk.model.Tmenu;
import xk.pageModel.Menu;
import xk.pageModel.SessionInfo;
import xk.service.MenuServiceI;
import xk.util.ResourceUtil;


@Service("menuService")
public class MenuServiceImpl implements MenuServiceI {
	private static final Logger logger = Logger.getLogger(MenuServiceImpl.class);

	private BaseDaoI<Tmenu> menuDao;

	public BaseDaoI<Tmenu> getMenuDao() {
		return menuDao;
	}

	public static String getSessionInfo() {
		SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest()
				.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		String sessionInfoUsername = sessionInfo.getLoginName();
		return sessionInfoUsername;
	}
	
	@Autowired
	@Qualifier("baseDao")
	public void setMenuDao( BaseDaoI<Tmenu> menuDao) {
		this.menuDao = menuDao;
	}

	@Override
	public List<Menu> getTree(String id, String role) {
		String sessionInfoUsername = getSessionInfo(); 
		List<Menu> nlist = new ArrayList<Menu>();
		String hql = null;
		Map<String, Object> params = new HashMap<String, Object>();
		if (id == null || id.equals("")) {
			// 查询所有根节点
			hql = "from Tmenu t where t.tmenu is null";
		} else {
			// 异步加载当前id下的子节点
			if (role.equals("admin")) {
				//如果是管理员，则全部子节点加载出来，升序
				hql = "from Tmenu t where t.tmenu.id=:id order by t.tmenu.seq asc";
				params.put("id", id);
			} else {
				if(id.equals("0")){    //如果id为0，加载根节点之下的节点
					hql = "from Tmenu t where t.tmenu.id=:id order by t.tmenu.seq asc";
					params.put("id", id);
				}else {
					//如果是其他人员且id不为0，则加载角色的子节点出来，升序
					hql = "from Tmenu t where t.tmenu.id=:id and t.role=:role order by t.seq asc";
					params.put("id", id);
					params.put("role", role);
				}
				
			}
		}
		
		List<Tmenu> list = menuDao.find(hql, params);

		if (list != null && list.size() > 0) {
			for (Tmenu t : list) {
				Menu m = new Menu();
				BeanUtils.copyProperties(t, m);
				Map<String, Object> attributes = new HashMap<String, Object>();
				// easyui的树,url是放在attributes下的，所以要在这里特意赋值
				attributes.put("url", t.getUrl());
				m.setAttributes(attributes);
				Set<Tmenu> set = t.getTmenus();
				if (set != null && !set.isEmpty()) {
					m.setState("closed");// 节点以文件夹形式体现
				} else {
					m.setState("open");// 节点以文件形式体现
				}
				nlist.add(m);
			}
			return nlist;
		}else {
			logger.error(sessionInfoUsername+"getTree() with Trees is null");
		}
		return null;
	}

	@Override
	public List<Menu> getTree(String id) {
		String sessionInfoUsername = getSessionInfo(); 
		logger.debug(sessionInfoUsername+"getTree() start");
		List<Menu> nlist = new ArrayList<Menu>();
		String hql = null;
		Map<String, Object> params = new HashMap<String, Object>();
		if (id == null || id.equals("")) {
			// 查询所有根节点
			hql = "from Tmenu t where t.tmenu is null";
		} else {
			// 异步加载当前id下的子节点
			hql = "from Tmenu t where t.tmenu.id=:id order by t.mseq asc";
			params.put("id", id);
		}
		
		List<Tmenu> list = menuDao.find(hql, params);

		if (list != null && list.size() > 0) {
			for (Tmenu t : list) {
				Menu m = new Menu();
				BeanUtils.copyProperties(t, m);
				Map<String, Object> attributes = new HashMap<String, Object>();
				// easyui的树,url是放在attributes下的，所以要在这里特意赋值
				attributes.put("url", t.getUrl());
				m.setAttributes(attributes);
				Set<Tmenu> set = t.getTmenus();
				if (set != null && !set.isEmpty()) {
					m.setState("closed");// 节点以文件夹形式体现
				} else {
					m.setState("open");// 节点以文件形式体现
				}
				nlist.add(m);
			}
			return nlist;
		}else {
			logger.error(sessionInfoUsername+"getTree() with Trees is null");
		}
		return null;
	}

}
