package xk.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletInputStream;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import xk.pageModel.Bug;
import xk.pageModel.Json;
import xk.service.BugServiceI;
import xk.util.ResourceUtil;
import xk.util.poi.ExceptionUtil;


import com.opensymphony.xwork2.ModelDriven;

@Namespace("/admin")
@Action(value = "bugAction", results = {
		@Result(name = "bug", location = "/admin/bug.jsp"),
		@Result(name = "bugAdd", location = "/admin/bugAdd.jsp"),
		@Result(name = "bugEdit", location = "/admin/bugEdit.jsp"),
		@Result(name = "showCdesc", location = "/admin/bugShowDesc.jsp") })
public class BugAction extends BaseAction implements ModelDriven<Bug> {

	private static final Logger logger = Logger.getLogger(BugAction.class);

	private BugServiceI demoBugService;

	public BugServiceI getDemoBugService() {
		return demoBugService;
	}

	@Autowired
	public void setDemoBugService(BugServiceI demoBugService) {
		this.demoBugService = demoBugService;
	}

	private Bug bug = new Bug();

	public Bug getModel() {
		return bug;
	}

	public String bug() {
		return "bug";
	}

	public String bugAdd() {
		return "bugAdd";
	}

	/** 功能：前台传进id，然后查出desc，为edit页面数据准备
	 *      前台只是用get方法传cid 后台果然能够接到cid
	 * @return edit`s page
	 */
	public String bugEdit() {
		ServletActionContext.getRequest().setAttribute("desc",
				demoBugService.getDescById(bug.getId()));
		return "bugEdit";
	}

	/**
	 * 获得bug数据表格
	 */
	public void datagrid() {
		super.writeJson(demoBugService.datagrid(bug));
	}

	public void delete() {
		Json j = new Json();
		try {
			demoBugService.delete(bug.getIds());
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			j.setMsg("系统故障,请重新删除");
		}
		
		super.writeJson(j);
	}

	public void add() {
		Json j = new Json();
		try {
			demoBugService.add(bug);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg("添加失败！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}
		super.writeJson(j);
	}

	public void edit() {
		Json j = new Json();
		//前台xheditor封装好desc，后台直接存desc内容，图片已经由xheditor上传了
		try {
			demoBugService.update(bug);
			j.setSuccess(true);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			j.setMsg("修改失败！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}
		super.writeJson(j);
	}

	public String showCdesc() {
		ServletActionContext.getRequest().setAttribute("desc",
				demoBugService.getDescById(bug.getId()));
		return "showCdesc";
	}

	/**
	 * 文件上传
	 */
	public void upload() {
		//获取attach的路径，保存到attach文件夹中
		String savePath = ServletActionContext.getServletContext().getRealPath("/")
				+ ResourceUtil.getUploadDirectory() + "/";// 文件保存目录路径
		
		String saveUrl = "/" + ResourceUtil.getUploadDirectory() + "/";// 文件保存目录URL

		String contentDisposition = ServletActionContext.getRequest().getHeader(
				"Content-Disposition");// 如果是HTML5上传文件，那么这里有响应头的

		if (contentDisposition != null) {// HTML5拖拽上传文件
			Long fileSize = Long.valueOf(ServletActionContext.getRequest().getHeader(
					"Content-Length"));// 上传的文件大小
			
			String fileName = contentDisposition.substring(contentDisposition
					.lastIndexOf("filename=\""));// 文件名称
			
			fileName = fileName.substring(fileName.indexOf("\"") + 1);
			
			fileName = fileName.substring(0, fileName.indexOf("\""));

			ServletInputStream inputStream;
			try {
				inputStream = ServletActionContext.getRequest().getInputStream();
			} catch (IOException e) {
				this.uploadError("上传文件出错！");
				ExceptionUtil.getExceptionMessage(e);
				return;
			}

			if (inputStream == null) {
				this.uploadError("您没有上传任何文件！");
				return;
			}

			if (fileSize > ResourceUtil.getUploadFileMaxSize()) {
				this.uploadError("上传文件超出限制大小！", fileName);
				return;
			}

			// 检查文件扩展名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1)
					.toLowerCase();
			if (!Arrays.<String> asList(ResourceUtil.getUploadFileExts().split(","))
					.contains(fileExt)) {
				this.uploadError("上传文件扩展名是不允许的扩展名。\n只允许"
						+ ResourceUtil.getUploadFileExts() + "格式！");
				return;
			}

			savePath += fileExt + "/";
			saveUrl += fileExt + "/";

			SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthDf = new SimpleDateFormat("MM");
			SimpleDateFormat dateDf = new SimpleDateFormat("dd");
			Date date = new Date();
			String ymd = yearDf.format(date) + "/" + monthDf.format(date) + "/"
					+ dateDf.format(date) + "/";
			savePath += ymd;
			saveUrl += ymd;

			File uploadDir = new File(savePath);// 创建要上传文件到指定的目录
			if (!uploadDir.exists()) { // 如果不存在，则创建目录
				//创建的目录层次为：文件后缀/年/月/日
				uploadDir.mkdirs();
			}

			//新名字为：将UUID里面的横杠全部替换掉，加点，再加文件后缀
			String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + "."
					+ fileExt;// 新的文件名称
			File uploadedFile = new File(savePath, newFileName);

			try {
				/** 这个是系统方法，copy文件内容
				 *  利用spring的文件工具上传
				 */
				FileCopyUtils.copy(inputStream, new FileOutputStream(uploadedFile));
			} catch (FileNotFoundException e) {
				this.uploadError("上传文件出错！");
				ExceptionUtil.getExceptionMessage(e);
				return;
			} catch (IOException e) {
				this.uploadError("上传文件出错！");
				ExceptionUtil.getExceptionMessage(e);
				return;
			}

			this.uploadSuccess(ServletActionContext.getRequest().getContextPath()
					+ saveUrl + newFileName, fileName, 0);// 文件上传成功

			return;
		}

		MultiPartRequestWrapper multiPartRequest = (MultiPartRequestWrapper) ServletActionContext
				.getRequest();// 由于struts2上传文件时自动使用了request封装
		File[] files = multiPartRequest.getFiles(ResourceUtil.getUploadFieldName());// 上传的文件集合
		String[] fileNames = multiPartRequest.getFileNames(ResourceUtil
				.getUploadFieldName());// 上传文件名称集合

		if (files == null || files.length < 1) {
			this.uploadError("您没有上传任何文件！");
			return;
		}

		for (int i = 0; i < files.length; i++) {// 循环所有文件
			File file = files[i];// 上传的文件(临时文件)
			String fileName = fileNames[i];// 上传文件名

			if (file.length() > ResourceUtil.getUploadFileMaxSize()) {
				this.uploadError("上传文件超出限制大小！", fileName);
				return;
			}

			// 检查文件扩展名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1)
					.toLowerCase();
			if (!Arrays.<String> asList(ResourceUtil.getUploadFileExts().split(","))
					.contains(fileExt)) {
				this.uploadError("上传文件扩展名是不允许的扩展名。\n只允许"
						+ ResourceUtil.getUploadFileExts() + "格式！");
				return;
			}

			savePath += fileExt + "/";
			saveUrl += fileExt + "/";

			SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthDf = new SimpleDateFormat("MM");
			SimpleDateFormat dateDf = new SimpleDateFormat("dd");
			Date date = new Date();
			String ymd = yearDf.format(date) + "/" + monthDf.format(date) + "/"
					+ dateDf.format(date) + "/";
			savePath += ymd;
			saveUrl += ymd;

			File uploadDir = new File(savePath);// 创建要上传文件到指定的目录
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + "."
					+ fileExt;// 新的文件名称
			File uploadedFile = new File(savePath, newFileName);

			try {
				FileCopyUtils.copy(file, uploadedFile);// 利用spring的文件工具上传
			} catch (Exception e) {
				this.uploadError("上传文件失败！", fileName);
				return;
			}

			this.uploadSuccess(ServletActionContext.getRequest().getContextPath()
					+ saveUrl + newFileName, fileName, i);// 文件上传成功

		}

	}

	//把错误信息写进变量里，用json返回
	private void uploadError(String err, String msg) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("err", err);
		m.put("msg", msg);
		super.writeJson(m);
	}

	//需要一个参数的uploadError会调用两个参数的uploadError
	private void uploadError(String err) {
		this.uploadError(err, "");
	}

	//将成功的信息写进变量里，用json返回
	private void uploadSuccess(String newFileName, String fileName, int id) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("err", "");
		
		Map<String, Object> nm = new HashMap<String, Object>();
		nm.put("url", newFileName);
		nm.put("localfile", fileName);
		nm.put("id", id);
		
		m.put("msg", nm);
		super.writeJson(m);
	}

}
