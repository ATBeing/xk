package xk.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tauth", catalog = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tauth implements java.io.Serializable {

	private static final long serialVersionUID = 1968947366120061260L;
	private String id;
	private Tauth tauth;
	private String aname;
	private String adesc;
	private BigDecimal aseq;
	private String aurl;
	private Set<Tauth> tauths = new HashSet<Tauth>(0);
	private Set<Troletauth> troletauths = new HashSet<Troletauth>(0);

	// Constructors

	/** default constructor */
	public Tauth() {
	}

	/** minimal constructor */
	public Tauth(String id) {
		this.id = id;
	}

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
	@JoinColumn(name = "apid")
	public Tauth getTauth() {
		return this.tauth;
	}

	public void setTauth(Tauth tauth) {
		this.tauth = tauth;
	}

	@Column(name = "aname", length = 50)
	public String getAname() {
		return this.aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	@Column(name = "adesc", length = 100)
	public String getAdesc() {
		return this.adesc;
	}

	public void setAdesc(String adesc) {
		this.adesc = adesc;
	}

	@Column(name = "aseq", precision = 22, scale = 0)
	public BigDecimal getAseq() {
		return this.aseq;
	}

	public void setAseq(BigDecimal aseq) {
		this.aseq = aseq;
	}

	@Column(name = "aurl", length = 200)
	public String getAurl() {
		return this.aurl;
	}

	public void setAurl(String aurl) {
		this.aurl = aurl;
	}

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY, mappedBy = "tauth")
	public Set<Tauth> getTauths() {
		return this.tauths;
	}

	public void setTauths(Set<Tauth> tauths) {
		this.tauths = tauths;
	}

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY, mappedBy = "tauth")
	public Set<Troletauth> getTroletauths() {
		return this.troletauths;
	}

	public void setTroletauths(Set<Troletauth> troletauths) {
		this.troletauths = troletauths;
	}

}