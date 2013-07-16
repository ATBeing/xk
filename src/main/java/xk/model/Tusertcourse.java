package xk.model;

import java.sql.Clob;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tusertcourse", catalog = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tusertcourse implements java.io.Serializable {

	private static final long serialVersionUID = 7483536784307115916L;
	private String id;
	private Tcourse tcourse;
	private Tuser tuser;
	private String grade;
	private String group;
	private Clob report;
	private Integer checkinnum;

	// Constructors

	/** default constructor */
	public Tusertcourse() {
	}

	/** minimal constructor */
	public Tusertcourse(String id, Tuser tuser, Tcourse tcourse) {
		this.id = id;
		this.tuser = tuser;
		this.tcourse = tcourse;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "cid")
	public Tcourse getTcourse() {
		return this.tcourse;
	}

	public void setTcourse(Tcourse tcourse) {
		this.tcourse = tcourse;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "uid")
	public Tuser getTuser() {
		return this.tuser;
	}

	public void setTuser(Tuser tuser) {
		this.tuser = tuser;
	}

	@Column(name = "grade", length = 5)
	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Column(name = "group", length = 10)
	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Column(name = "report")
	public Clob getReport() {
		return this.report;
	}

	public void setReport(Clob report) {
		this.report = report;
	}

	@Column(name = "checkinnum")
	public Integer getCheckinnum() {
		return this.checkinnum;
	}

	public void setCheckinnum(Integer checkinnum) {
		this.checkinnum = checkinnum;
	}

}