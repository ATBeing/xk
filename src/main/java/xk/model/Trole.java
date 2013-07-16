package xk.model;

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
@Table(name = "trole", catalog = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Trole implements java.io.Serializable {

	private static final long serialVersionUID = 2100062063434386016L;
	private String id;
	private Trole trole;
	private String rame;
	private String rdesc;
	private Set<Tusertrole> tusertroles = new HashSet<Tusertrole>(0);
	private Set<Troletauth> troletauths = new HashSet<Troletauth>(0);
	private Set<Trole> troles = new HashSet<Trole>(0);

	// Constructors

	/** default constructor */
	public Trole() {
	}

	/** minimal constructor */
	public Trole(String id) {
		this.id = id;
	}

	/** full constructor */
	public Trole(String id, Trole trole, String rame, String rdesc,
			Set<Tusertrole> tusertroles, Set<Troletauth> troletauths, Set<Trole> troles) {
		this.id = id;
		this.trole = trole;
		this.rame = rame;
		this.rdesc = rdesc;
		this.tusertroles = tusertroles;
		this.troletauths = troletauths;
		this.troles = troles;
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

	@ManyToOne(fetch = FetchType.LAZY,cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "rpid")
	public Trole getTrole() {
		return this.trole;
	}

	public void setTrole(Trole trole) {
		this.trole = trole;
	}

	@Column(name = "rame", length = 50)
	public String getRame() {
		return this.rame;
	}

	public void setRame(String rame) {
		this.rame = rame;
	}

	@Column(name = "rdesc", length = 50)
	public String getRdesc() {
		return this.rdesc;
	}

	public void setRdesc(String rdesc) {
		this.rdesc = rdesc;
	}

	@OneToMany(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY, mappedBy = "trole")
	public Set<Tusertrole> getTusertroles() {
		return this.tusertroles;
	}

	public void setTusertroles(Set<Tusertrole> tusertroles) {
		this.tusertroles = tusertroles;
	}

	@OneToMany(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY, mappedBy = "trole")
	public Set<Troletauth> getTroletauths() {
		return this.troletauths;
	}

	public void setTroletauths(Set<Troletauth> troletauths) {
		this.troletauths = troletauths;
	}

	@OneToMany(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY, mappedBy = "trole")
	public Set<Trole> getTroles() {
		return this.troles;
	}

	public void setTroles(Set<Trole> troles) {
		this.troles = troles;
	}

}