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
@Table(name = "troletauth", catalog = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Troletauth implements java.io.Serializable {
	private static final long serialVersionUID = -7037018671318077824L;
	private String id;
	private Trole trole;
	private Tauth tauth;

	// Constructors

	/** default constructor */
	public Troletauth() {
	}

	/** minimal constructor */
	public Troletauth(String id) {
		this.id = id;
	}

	/** full constructor */
	public Troletauth(String id, Trole trole, Tauth tauth) {
		this.id = id;
		this.trole = trole;
		this.tauth = tauth;
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
	@JoinColumn(name = "rid")
	public Trole getTrole() {
		return this.trole;
	}

	public void setTrole(Trole trole) {
		this.trole = trole;
	}

	@ManyToOne(fetch = FetchType.LAZY,cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "aid")
	public Tauth getTauth() {
		return this.tauth;
	}

	public void setTauth(Tauth tauth) {
		this.tauth = tauth;
	}

}