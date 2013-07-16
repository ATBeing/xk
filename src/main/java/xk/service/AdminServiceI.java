package xk.service;

import xk.pageModel.Course;
import xk.pageModel.DataGrid;

public interface AdminServiceI {

	public boolean editState(Course course);
	
	public DataGrid datagrid(Course course);
	
}
