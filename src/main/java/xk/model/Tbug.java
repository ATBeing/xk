package xk.model;

import java.sql.Clob;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tbug", catalog = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tbug implements java.io.Serializable {

	public Tbug(String id, String bname, Date bcreatetime) {
		this.id = id;
		this.bname = bname;
		this.bcreatetime = bcreatetime;
	}
	
	private static final long serialVersionUID = 2382897822980023395L;
	private String id;
	private Clob bdesc;
	private String bname;
	private Date bcreatetime;

	// Constructors

	/** default constructor */
	public Tbug() {
	}

	/** minimal constructor */
	public Tbug(String id, String bname) {
		this.id = id;
		this.bname = bname;
	}

	/** full constructor */
	public Tbug(String id, Clob bdesc, String bname, Date bcreatetime) {
		this.id = id;
		this.bdesc = bdesc;
		this.bname = bname;
		this.bcreatetime = bcreatetime;
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

	@Column(name = "bdesc")
	public Clob getBdesc() {
		return this.bdesc;
	}

	public void setBdesc(Clob bdesc) {
		this.bdesc = bdesc;
	}

	@Column(name = "bname", nullable = false, length = 50)
	public String getBname() {
		return this.bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "bcreatetime", length = 10)
	public Date getBcreatetime() {
		return this.bcreatetime;
	}

	public void setBcreatetime(Date bcreatetime) {
		this.bcreatetime = bcreatetime;
	}

}