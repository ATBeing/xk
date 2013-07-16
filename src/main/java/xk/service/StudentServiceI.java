package xk.service;

import xk.pageModel.Course;
import xk.pageModel.DataGrid;

public interface StudentServiceI {
	public DataGrid datagrid(Course course);
	
	public boolean choose(String ids,String username);
	
}
