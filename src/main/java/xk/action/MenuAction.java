package xk.action;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import xk.pageModel.Menu;
import xk.service.MenuServiceI;


import com.opensymphony.xwork2.ModelDriven;

@Namespace("/menu")
@Action(value = "menuAction")
public class MenuAction extends BaseAction implements ModelDriven<Menu>{

	private Menu menu = new Menu();
	@Override
	public Menu getModel() {
		return menu;
	}
	
	private MenuServiceI menuService;
	
	
	public MenuServiceI getMenuService() {
		return menuService;
	}

    @Autowired
	public void setMenuService(MenuServiceI menuService) {
		this.menuService = menuService;
	}


	public void getTreeNode(){
		super.writeJson(menuService.getTree(menu.getId()));
	}
	
}
