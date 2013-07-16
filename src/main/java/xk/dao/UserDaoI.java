package xk.dao;


import xk.dao.base.BaseDaoI;
import xk.model.Tuser;

public interface UserDaoI extends BaseDaoI<Tuser>{

	public Tuser getTuserByName(String tusername);
	
	public Tuser getTuserByIdd(String idd);
	
}
