package xk.model;

import java.sql.Clob;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
@Embeddable
@DynamicInsert(true)
@DynamicUpdate(true)
public class Coursefile {
	private Clob appRecord;
	private String tid;
	private String opentype;
	private String exname;
	private String telphone;
	private String extype;
	private Integer extime;
	private Integer exnum;
	private Integer expernum;
	private String resultType;
	private Double materFare;
	private Double workFare;

	@Column(name = "appRecord")
	public Clob getAppRecord() {
		return this.appRecord;
	}

	public void setAppRecord(Clob appRecord) {
		this.appRecord = appRecord;
	}

	@Column(name = "tid", length = 36)
	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}


	@Column(name = "opentype", length = 10)
	public String getOpentype() {
		return this.opentype;
	}

	public void setOpentype(String opentype) {
		this.opentype = opentype;
	}

	@Column(name = "exname", length = 50)
	public String getExname() {
		return this.exname;
	}

	public void setExname(String exname) {
		this.exname = exname;
	}

	@Column(name = "telphone", length = 20)
	public String getTelphone() {
		return this.telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	@Column(name = "extype", length = 50)
	public String getExtype() {
		return this.extype;
	}

	public void setExtype(String extype) {
		this.extype = extype;
	}

	@Column(name = "extime")
	public Integer getExtime() {
		return this.extime;
	}

	public void setExtime(Integer extime) {
		this.extime = extime;
	}

	@Column(name = "exnum")
	public Integer getExnum() {
		return this.exnum;
	}

	public void setExnum(Integer exnum) {
		this.exnum = exnum;
	}

	@Column(name = "expernum")
	public Integer getExpernum() {
		return this.expernum;
	}

	public void setExpernum(Integer expernum) {
		this.expernum = expernum;
	}

	@Column(name = "resultType", length = 50)
	public String getResultType() {
		return this.resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	@Column(name = "materFare", precision = 10, scale = 4)
	public Double getMaterFare() {
		return this.materFare;
	}

	public void setMaterFare(Double materFare) {
		this.materFare = materFare;
	}

	@Column(name = "workFare", precision = 10, scale = 4)
	public Double getWorkFare() {
		return this.workFare;
	}

	public void setWorkFare(Double workFare) {
		this.workFare = workFare;
	}

}
