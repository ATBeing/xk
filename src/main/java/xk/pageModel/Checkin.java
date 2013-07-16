package xk.pageModel;

import java.util.Date;

public class Checkin {

	private String id;
	private String uid;
	private String cid;
	private String ip;
	private String state; // 是否代签
	private String classSeq; // 第几次课的签到
	private Date checkintime;

	private String ids;// 页面传过来删除对象的主键值数组字符串
	private String stuname; // 给老师使用的变量，学生名
	private String cname;
	private String username; // 老师的姓名

	private int page;
	private int rows;
	private String sort;
	private String order;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getClassSeq() {
		return classSeq;
	}

	public void setClassSeq(String classSeq) {
		this.classSeq = classSeq;
	}

	public Date getCheckintime() {
		return checkintime;
	}

	public void setChenkintime(Date checkintime) {
		this.checkintime = checkintime;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getStuname() {
		return stuname;
	}

	public void setStuname(String stuname) {
		this.stuname = stuname;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
