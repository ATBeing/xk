package xk.pageModel;

import java.sql.Clob;

public class Course {
	
	private String ids;// 页面传过来删除对象的主键值数组字符串
	private int page;
	private int rows;
	private String sort;
	private String order;
	private String username;

	private String q; // param for combogrid search
	
	private String id;
	private String cid;
	private String cname;
	private Clob cdesc;
	private Double ctime;
	private Integer maxnum;
	private Integer exitnum;
	private String locked;
	
	private Clob appRecord;
	private String tid;
	private String reviewed;
	private String opentype;
	private String exname;
	private String telphone;
	private String extype;
	private Integer extime;
	private Integer exnum;
	private Integer expernum;
	private String resultType;
	private Integer materFare;
	private Integer workFare;
	
	private String xkstate;
	
	
	public String getXkstate() {
		return xkstate;
	}
	public void setXkstate(String xkstate) {
		this.xkstate = xkstate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
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
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Clob getCdesc() {
		return cdesc;
	}
	public void setCdesc(Clob cdesc) {
		this.cdesc = cdesc;
	}
	public Double getCtime() {
		return ctime;
	}
	public void setCtime(Double ctime) {
		this.ctime = ctime;
	}
	public Integer getMaxnum() {
		return maxnum;
	}
	public void setMaxnum(Integer maxnum) {
		this.maxnum = maxnum;
	}
	public Integer getExitnum() {
		return exitnum;
	}
	public void setExitnum(Integer exitnum) {
		this.exitnum = exitnum;
	}
	public String getLocked() {
		return locked;
	}
	public void setLocked(String locked) {
		this.locked = locked;
	}
	public Clob getAppRecord() {
		return appRecord;
	}
	public void setAppRecord(Clob appRecord) {
		this.appRecord = appRecord;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getReviewed() {
		return reviewed;
	}
	public void setReviewed(String reviewed) {
		this.reviewed = reviewed;
	}
	public String getOpentype() {
		return opentype;
	}
	public void setOpentype(String opentype) {
		this.opentype = opentype;
	}
	public String getExname() {
		return exname;
	}
	public void setExname(String exname) {
		this.exname = exname;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getExtype() {
		return extype;
	}
	public void setExtype(String extype) {
		this.extype = extype;
	}
	public Integer getExtime() {
		return extime;
	}
	public void setExtime(Integer extime) {
		this.extime = extime;
	}
	public Integer getExnum() {
		return exnum;
	}
	public void setExnum(Integer exnum) {
		this.exnum = exnum;
	}
	public Integer getExpernum() {
		return expernum;
	}
	public void setExpernum(Integer expernum) {
		this.expernum = expernum;
	}
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	public Integer getMaterFare() {
		return materFare;
	}
	public void setMaterFare(Integer materFare) {
		this.materFare = materFare;
	}
	public Integer getWorkFare() {
		return workFare;
	}
	public void setWorkFare(Integer workFare) {
		this.workFare = workFare;
	}
	
	
	
}
