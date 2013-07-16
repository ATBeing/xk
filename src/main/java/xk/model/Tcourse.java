package xk.model;

import java.sql.Clob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tcourse", catalog = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tcourse implements java.io.Serializable {

	private static final long serialVersionUID = -7759170467912443924L;
	private String id;
	private String cid;
	private String cname;
	private Clob cdesc;
	private Date ctime;
	private Integer maxnum;
	private Integer exitnum;
	private String locked;
	private String reviewed;

	private Coursefile coursefile;

	private String opencheckin;
	private Integer checkinSeq;
	private Set<Tusertcourse> tusertcourses = new HashSet<Tusertcourse>(0);

	// Constructors

	/** default constructor */
	public Tcourse() {
	}

	/** minimal constructor */
	public Tcourse(String id, String cid, String cname, Clob cdesc, Date ctime,
			Integer maxnum, Integer exitnum, String locked, String reviewed,
			String opencheckin, Integer checkinSeq) {
		this.id = id;
		this.cid = cid;
		this.cname = cname;
		this.cdesc = cdesc;
		this.ctime = ctime;
		this.maxnum = maxnum;
		this.exitnum = exitnum;
		this.locked = locked;
		this.reviewed = reviewed;
		this.opencheckin = opencheckin;
		this.checkinSeq = checkinSeq;
	}

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "appRecord", column = @Column(name = "appRecord")),
			@AttributeOverride(name = "tid", column = @Column(name = "tid")),
			@AttributeOverride(name = "opentype", column = @Column(name = "opentype")),
			@AttributeOverride(name = "exname", column = @Column(name = "exname")),
			@AttributeOverride(name = "telphone", column = @Column(name = "telphone")),
			@AttributeOverride(name = "extype", column = @Column(name = "extype")),
			@AttributeOverride(name = "extime", column = @Column(name = "extime")),
			@AttributeOverride(name = "exnum", column = @Column(name = "exnum")),
			@AttributeOverride(name = "expernum", column = @Column(name = "expernum")),
			@AttributeOverride(name = "resultType", column = @Column(name = "resultType")),
			@AttributeOverride(name = "materFare", column = @Column(name = "materFare")),
			@AttributeOverride(name = "workFare", column = @Column(name = "workFare")) })
	public Coursefile getCoursefile() {
		return coursefile;
	}

	public void setCoursefile(Coursefile coursefile) {
		this.coursefile = coursefile;
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

	@Column(name = "cid", nullable = false, length = 50)
	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Column(name = "cname", nullable = false, length = 50)
	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Column(name = "cdesc")
	public Clob getCdesc() {
		return this.cdesc;
	}

	public void setCdesc(Clob cdesc) {
		this.cdesc = cdesc;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ctime", length = 10)
	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	@Column(name = "maxnum")
	public Integer getMaxnum() {
		return this.maxnum;
	}

	public void setMaxnum(Integer maxnum) {
		this.maxnum = maxnum;
	}

	@Column(name = "exitnum")
	public Integer getExitnum() {
		return this.exitnum;
	}

	public void setExitnum(Integer exitnum) {
		this.exitnum = exitnum;
	}

	@Column(name = "locked", length = 10)
	public String getLocked() {
		return this.locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	@Column(name = "reviewed", length = 5)
	public String getReviewed() {
		return this.reviewed;
	}

	public void setReviewed(String reviewed) {
		this.reviewed = reviewed;
	}

	@Column(name = "opencheckin", length = 5)
	public String getOpencheckin() {
		return this.opencheckin;
	}

	public void setOpencheckin(String opencheckin) {
		this.opencheckin = opencheckin;
	}

	@Column(name = "checkinSeq")
	public Integer getCheckinSeq() {
		return this.checkinSeq;
	}

	public void setCheckinSeq(Integer checkinSeq) {
		this.checkinSeq = checkinSeq;
	}

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY, mappedBy = "tcourse")
	public Set<Tusertcourse> getTusertcourses() {
		return this.tusertcourses;
	}

	public void setTusertcourses(Set<Tusertcourse> tusertcourses) {
		this.tusertcourses = tusertcourses;
	}

}