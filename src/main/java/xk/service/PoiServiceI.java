package xk.service;

import java.io.File;

public interface PoiServiceI {

	// Excel数据导入到数据库中
	public void importUserExcel(File file) throws Exception;

	// 导出填写数据的Excel模版
	public void exportUserModela();

	// 从数据库中导出数据到excel中
	public void exportUserToExcela();

}
