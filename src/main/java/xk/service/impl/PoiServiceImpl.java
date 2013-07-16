package xk.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import xk.dao.UserDaoI;
import xk.model.Profile;
import xk.model.Tuser;
import xk.pageModel.User;
import xk.service.PoiServiceI;
import xk.service.UserServiceI;
import xk.util.Encrypt;
import xk.util.poi.ExcelUtils;
import xk.util.poi.JsGridReportBase;
import xk.util.poi.TableData;
import xk.util.poi.leoUtil;

import com.opensymphony.xwork2.ActionContext;

@Service("poiService")
public class PoiServiceImpl implements PoiServiceI {
	private static final Logger logger = Logger.getLogger(PoiServiceImpl.class);

	private UserServiceI userService;
	private FileInputStream fileStream;

	private UserDaoI userDao;

	public UserDaoI getUserDao() {
		return userDao;
	}

	@Autowired
	@Qualifier("userDao")
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setUserService(UserServiceI userService) {
		this.userService = userService;
	}

	@Override
	public void importUserExcel(File file) throws FileNotFoundException {
		logger.debug("importUserExcel() start");
		if (leoUtil.isExcel(file)) {
			try {
				fileStream = new FileInputStream(file);
				logger.info("打开新的文件流");
				Workbook book = WorkbookFactory.create(fileStream);
				Sheet sheet = book.getSheetAt(0);
				User user = new User();
				// 从0开始，第一行对应sheet.getRow(0)
				for (int i = 4; i <= sheet.getLastRowNum(); i++) {
					Row ros = sheet.getRow(i);
					Tuser tuser = userDao.getTuserByIdd(leoUtil.ConvertCellToStr(ros
							.getCell(0)));
					if (tuser != null) {
						tuser.setUname(leoUtil.ConvertCellToStr(ros.getCell(1)));
						tuser.setPwd(Encrypt.e(leoUtil.ConvertCellToStr(ros.getCell(2))));
						tuser.setModifytime(new Date());
						tuser.setRole(leoUtil.ConvertCellToStr(ros.getCell(5)));
						if (tuser.getProfile() != null) {
							tuser.getProfile().setPhone(
									leoUtil.ConvertCellToStr(ros.getCell(3)));
							tuser.getProfile().setEmail(
									leoUtil.ConvertCellToStr(ros.getCell(4)));
						} else {
							Profile profile = new Profile();
							profile.setPhone(leoUtil.ConvertCellToStr(ros.getCell(3)));
							profile.setEmail(leoUtil.ConvertCellToStr(ros.getCell(4)));
							tuser.setProfile(profile);
						}
						userDao.update(tuser);
					} else {
						// 还要做的工作，判断是否有数据，判断添加的信息是否unique（要求unique的），验证格式是否符合，
						logger.info("调用user的set方法");
						// user.setId()不需要，uesrService.reg(user) 后台自动添加了。
						user.setIdd(leoUtil.ConvertCellToStr(ros.getCell(0))); // 学号
						user.setUname(leoUtil.ConvertCellToStr(ros.getCell(1)));// 姓名
						user.setPwd(leoUtil.ConvertCellToStr(ros.getCell(2)));// 密码
						user.setPhone(leoUtil.ConvertCellToStr(ros.getCell(3)));// 手机号码
						user.setEmail(leoUtil.ConvertCellToStr(ros.getCell(4)));// 邮箱
						user.setRole(leoUtil.ConvertCellToStr(ros.getCell(5)));
						// logger.info(ros.getCell(3));
						// logger.info(leoUtil.ConvertCellToDate(ros.getCell(3)));
						// logger.info(ros.getCell(4));
						userService.reg(user);
					}

					logger.info("调用user的reg()");
				}

			} catch (InvalidFormatException e) {
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			} finally {
				logger.info("importUserExcel() into finally");
				if (fileStream != null) {
					try {
						fileStream.close();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				} else {
					logger.info("空Excel表");
				}
			}
		} else {
			logger.info("不是excel文件");
		}
		logger.debug("importUserExcel stop");
	}

	@Override
	public void exportUserModela() {
		logger.debug("exportUserModel() start");
		ActionContext context = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) context
				.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) context
				.get(ServletActionContext.HTTP_RESPONSE);
		response.setContentType("application/msexcel;charset=GBK");
		String title = "用户Excel表模版";
		String[] hearders = new String[] { "学号", "姓名", "密码", "手机", "邮箱", "角色" };// 表头数组
		String[] fields = new String[] { "idd", "uname", "pwd", "phone", "email", "role" };// People对象属性数组
		TableData td = ExcelUtils.createTableData(null,
				ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report;
		try {
			report = new JsGridReportBase(request, response);
			report.exportToExcel(title, "admin", td);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.debug("exportUserModel() stop");
	}

	@Override
	public void exportUserToExcela() {
		ActionContext context = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) context
				.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) context
				.get(ServletActionContext.HTTP_RESPONSE);
		response.setContentType("application/msexcel;charset=GBK");

		// List<Tuser> list = getBeanData();//获取数据
		List<Tuser> list = userDao.find("from Tuser");
		List<User> lUsers = new ArrayList<User>();
		userService.changeModel(list, lUsers);

		// System.out.print(list.size());
		String title = "普通Excel表";
		String[] hearders = new String[] { "编号", "学号", "姓名", "密码", "手机", "邮箱", "创建时间",
				"修改时间", "角色" };// 表头数组
		String[] fields = new String[] { "id", "idd", "uname", "pwd", "phone", "email",
				"createtime", "modifytime", "role" };// People对象属性数组
		TableData td = ExcelUtils.createTableData(lUsers,
				ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report;
		try {
			report = new JsGridReportBase(request, response);
			report.exportToExcel(title, "admin", td);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}
}
