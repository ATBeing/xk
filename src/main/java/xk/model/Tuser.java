package xk.model;

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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tuser", catalog = "", uniqueConstraints = @UniqueConstraint(columnNames = "idd"))
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tuser implements java.io.Serializable {

	private static final long serialVersionUID = 612122473505395046L;
	private String id;
	private String idd;
	private String uname;
	private String pwd;
	private String role;

	private Profile profile;

	private Date createtime;
	private Date modifytime;
	private Set<Tusertrole> tusertroles = new HashSet<Tusertrole>(0);

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "email", column = @Column(name = "email")),
			@AttributeOverride(name = "phone", column = @Column(name = "phone")) })
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	/** default constructor */
	public Tuser() {
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "idd", unique = true, nullable = false, length = 50)
	public String getIdd() {
		return this.idd;
	}

	public void setIdd(String idd) {
		this.idd = idd;
	}

	@Column(name = "uname", nullable = false, length = 50)
	public String getUname() {
		return this.uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	@Column(name = "pwd", nullable = false, length = 32)
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "role", nullable = false, length = 20)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "createtime", length = 10)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "modifytime", length = 10)
	public Date getModifytime() {
		return this.modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY, mappedBy = "tuser")
	public Set<Tusertrole> getTusertroles() {
		return this.tusertroles;
	}

	public void setTusertroles(Set<Tusertrole> tusertroles) {
		this.tusertroles = tusertroles;
	}

}