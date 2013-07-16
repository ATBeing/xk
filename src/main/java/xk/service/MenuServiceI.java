package xk.service;

import java.util.List;

import xk.pageModel.Menu;


public interface MenuServiceI {

	public List<Menu> getTree(String id,String role);
	
	public List<Menu> getTree(String id);
}
