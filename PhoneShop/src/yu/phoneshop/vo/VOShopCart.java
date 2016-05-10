package yu.phoneshop.vo;

import java.io.Serializable;

public class VOShopCart implements Serializable{
	private static final long serialVersionUID = 1785005108476140101L;
	private String shopid;
	private String number;
	private String price;
	private String imginfo;
	private String zhishi;
	private String color;
	private String pic;
	private String userid;
	public String getZhishi() {
		return zhishi;
	}
	public void setZhishi(String zhishi) {
		this.zhishi = zhishi;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getShopid() {
		return shopid;
	}
	public void setShopid(String shopid) {
		this.shopid = shopid;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getImginfo() {
		return imginfo;
	}
	public void setImginfo(String imginfo) {
		this.imginfo = imginfo;
	}
	
	public VOShopCart(String shopid, String number, String price,
			String imginfo, String zhishi, String color, String pic) {
		super();
		this.shopid = shopid;
		this.number = number;
		this.price = price;
		this.imginfo = imginfo;
		this.zhishi = zhishi;
		this.color = color;
		this.pic = pic;
	}
	public VOShopCart() {
		// TODO Auto-generated constructor stub
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}