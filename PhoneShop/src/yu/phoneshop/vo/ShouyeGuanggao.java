package yu.phoneshop.vo;

public class ShouyeGuanggao {
	private String id;
	private String shopid;
	private String imgurl;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShopid() {
		return shopid;
	}
	public void setShopid(String shopid) {
		this.shopid = shopid;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public ShouyeGuanggao(String id, String shopid, String imgurl) {
		super();
		this.id = id;
		this.shopid = shopid;
		this.imgurl = imgurl;
	}
	
}
