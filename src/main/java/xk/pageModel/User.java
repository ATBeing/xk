package xk.pageModel;

import java.util.Date;

public class User implements java.io.Serializable {/* 集群类要使用用Serializable */
	private static final long serialVersionUID = -7679873292373501022L;

	private String id;
	private String ids;// 页面传过来删除对象的主键值数组字符串
	private String uname;
	private String pwd;
	private String pid;

	private String idd; // 学生的学号，或者教师的职工号
	private String role; // 暂时使用的角色

	private String phone;
	private String email;
	private Date createtime;
	private Date modifytime;
	private String roleIds; // 角色id集
	private String roleNames; // 角色名称
	private String roleDescs;

	private int page;
	private int rows;
	private String sort;
	private String order;

	private String q; // param for combogrid search

	public String getIdd() {
		return idd;
	}

	public void setIdd(String idd) {
		this.idd = idd;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRoleDescs() {
		return roleDescs;
	}

	public void setRoleDescs(String roleDescs) {
		this.roleDescs = roleDescs;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
