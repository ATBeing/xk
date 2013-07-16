package xk.service;

import xk.pageModel.Checkin;
import xk.pageModel.DataGrid;

public interface CheckinServiceI {
	public DataGrid tDataGrid(Checkin checkin);
	
	public DataGrid sDataGrid(Checkin checkin);
	
	public boolean sign(String uid,String cname,String ip);
}
