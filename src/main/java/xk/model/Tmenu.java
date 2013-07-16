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
@Table(name = "tmenu", catalog = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tmenu implements java.io.Serializable {

	private static final long serialVersionUID = 8882014907396827300L;
	private String id;
	private Tmenu tmenu;
	private String iconcls;
	private String text;
	private String url;
	private BigDecimal mseq;
	private Set<Tmenu> tmenus = new HashSet<Tmenu>(0);

	// Constructors

	/** default constructor */
	public Tmenu() {
	}

	/** minimal constructor */
	public Tmenu(String id) {
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

	@ManyToOne(fetch = FetchType.LAZY,cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "mpid")
	public Tmenu getTmenu() {
		return this.tmenu;
	}

	public void setTmenu(Tmenu tmenu) {
		this.tmenu = tmenu;
	}

	@Column(name = "iconcls", length = 50)
	public String getIconcls() {
		return this.iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	@Column(name = "text", length = 100)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "url", length = 100)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "mseq", precision = 22, scale = 0)
	public BigDecimal getMseq() {
		return this.mseq;
	}

	public void setMseq(BigDecimal mseq) {
		this.mseq = mseq;
	}

	@OneToMany(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY, mappedBy = "tmenu")
	public Set<Tmenu> getTmenus() {
		return this.tmenus;
	}

	public void setTmenus(Set<Tmenu> tmenus) {
		this.tmenus = tmenus;
	}

}