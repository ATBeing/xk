package xk.pageModel;

import java.math.BigDecimal;
import java.util.Map;

public class Menu {
	
	private String id;
	private String text;
	private String name;
	private String pid;     //父节点id
	private String ptext;   //父节点的内容

	private String iconCls;// 前面的小图标样式
	private String url;
	private BigDecimal mseq;
	private String role;
	private String state = "open";// 是否展开(open,closed);
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public BigDecimal getMseq() {
		return mseq;
	}

	public void setMseq(BigDecimal mseq) {
		this.mseq = mseq;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	private Map<String,Object> attributes;

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPtext() {
		return ptext;
	}

	public void setPtext(String ptext) {
		this.ptext = ptext;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}


}
