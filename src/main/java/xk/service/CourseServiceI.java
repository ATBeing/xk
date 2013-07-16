package xk.service;

import java.util.List;

import xk.model.Tcourse;
import xk.pageModel.Course;
import xk.pageModel.DataGrid;

public interface CourseServiceI {
	public DataGrid datagrid(Course course);

	public void remove(String ids);

	public Course edit(Course course);
	
	public Course add(Course course);
	
	public String getDescById(String id);
	
	public List<Tcourse> find(Course course);
	
	
}
