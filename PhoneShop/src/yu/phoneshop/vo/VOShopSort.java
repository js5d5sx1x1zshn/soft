package yu.phoneshop.vo;

public class VOShopSort {
	private String sortid;
	private String id;
	private String parentid;
	private String path;
	private String shopname;
	private String shopimg;
	
	public String getSortid() {
		return sortid;
	}
	public void setSortid(String sortid) {
		this.sortid = sortid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	
	public VOShopSort(String sortid, String id, String parentid, String path,
			String shopname, String shopimg) {
		super();
		this.sortid = sortid;
		this.id = id;
		this.parentid = parentid;
		this.path = path;
		this.shopname = shopname;
		this.shopimg = shopimg;
	}
	
	public String getShopimg() {
		return shopimg;
	}
	public void setShopimg(String shopimg) {
		this.shopimg = shopimg;
	}
	
}
