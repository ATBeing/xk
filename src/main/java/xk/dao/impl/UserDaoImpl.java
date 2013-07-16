package xk.dao.impl;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import xk.dao.UserDaoI;
import xk.dao.base.impl.BaseDaoImpl;
import xk.model.Tuser;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<Tuser> implements UserDaoI {
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

	@Override
	public Tuser getTuserByName(String username) {
		if (username != null && !username.equals("")) {
			String hqlString = "from Tuser t where 1=1 ";
			Tuser tu = new Tuser();
			Map<String, Object> map = new HashMap<String, Object>();
			hqlString += " and t.uname=:username";
			map.put("username", username);
			tu = super.get(hqlString, map);
			if (tu != null) {
				return tu;
			} else {
				return null;
			}
		} else {
			logger.error("getTuserByName() with tusername is null");
		}
		return null;
	}

	@Override
	public Tuser getTuserByIdd(String idd) {
		if(idd!=null&&!idd.trim().equals("")){
			String hqlString = "from Tuser t where 1=1 ";
			Map<String, Object> map = new HashMap<String, Object>();
			hqlString += " and t.idd=:idd";
			map.put("idd", idd);
			Tuser tu= super.get(hqlString, map);
			if(tu!=null){
				return tu;
			}else {
				return null;
			}
		}
		return null;
	}

}
