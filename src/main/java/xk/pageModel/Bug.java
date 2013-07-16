package xk.pageModel;

import java.sql.Clob;
import java.util.Date;

public class Bug {

	private String id;
	private String bname;
	private Clob bdesc;
	private Date bcreatetime;
	
	private int page;// 当前页
	private int rows;// 每页显示记录数
	private String sort;// 排序字段名
	private String order;// 按什么排序(asc,desc)
	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
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

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public Clob getBdesc() {
		return bdesc;
	}

	public void setBdesc(Clob bdesc) {
		this.bdesc = bdesc;
	}

	public Date getBcreatetime() {
		return bcreatetime;
	}

	public void setBcreatetime(Date bcreatetime) {
		this.bcreatetime = bcreatetime;
	}



}
