package xk.model;

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
@Table(name = "tusertrole", catalog = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tusertrole implements java.io.Serializable {

	private static final long serialVersionUID = 6774913836220636418L;
	private String id;
	private Trole trole;
	private Tuser tuser;

	// Constructors

	/** default constructor */
	public Tusertrole() {
	}

	/** minimal constructor */
	public Tusertrole(String id) {
		this.id = id;
	}

	/** full constructor */
	public Tusertrole(String id, Trole trole, Tuser tuser) {
		this.id = id;
		this.trole = trole;
		this.tuser = tuser;
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
	@JoinColumn(name = "rid")
	public Trole getTrole() {
		return this.trole;
	}

	public void setTrole(Trole trole) {
		this.trole = trole;
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

}