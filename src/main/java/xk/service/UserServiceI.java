package xk.service;

import java.util.List;

import xk.model.Tuser;
import xk.pageModel.DataGrid;
import xk.pageModel.User;

public interface UserServiceI {

	public User reg(User user);

	public User login(User user);

	public DataGrid datagrid(User user);

	public void remove(String string);

	public User edit(User user);
	
	
	public void editUserInfo(User user);
	
	public void changeModel(List<Tuser> lTusers, List<User> lUsers);
	
}