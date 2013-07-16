package xk.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tcheckin", catalog = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tcheckin implements java.io.Serializable {

	private static final long serialVersionUID = -4354313941150919189L;
	private String id;
	private Tcourse tcourse;
	private Tuser tuser;
	private String ip;
	private String state;
	private String classSeq;
	private Date chenkintime;

	// Constructors

	/** default constructor */
	public Tcheckin() {
	}

	/** minimal constructor */
	public Tcheckin(String id, Tuser tuser, Tcourse tcourse) {
		this.id = id;
		this.tcourse = tcourse;
		this.tuser = tuser;
	}

	/** full constructor */
	public Tcheckin(String id, Tcourse tcourse, Tuser tuser, String ip, String state,
			String classSeq, Date chenkintime) {
		this.id = id;
		this.tcourse = tcourse;
		this.tuser = tuser;
		this.ip = ip;
		this.state = state;
		this.classSeq = classSeq;
		this.chenkintime = chenkintime;
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
	@JoinColumn(name = "cid", nullable = false)
	public Tcourse getTcourse() {
		return this.tcourse;
	}

	public void setTcourse(Tcourse tcourse) {
		this.tcourse = tcourse;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "uid", nullable = false)
	public Tuser getTuser() {
		return this.tuser;
	}

	public void setTuser(Tuser tuser) {
		this.tuser = tuser;
	}

	@Column(name = "ip", length = 100)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "state", length = 5)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "classSeq", length = 5)
	public String getClassSeq() {
		return this.classSeq;
	}

	public void setClassSeq(String classSeq) {
		this.classSeq = classSeq;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "chenkintime", length = 10)
	public Date getChenkintime() {
		return this.chenkintime;
	}

	public void setChenkintime(Date chenkintime) {
		this.chenkintime = chenkintime;
	}

}