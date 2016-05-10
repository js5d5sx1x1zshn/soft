package yu.phoneshop.vo;

public class ListProduct {
	/** ID */
	private String id;
	/** 商品名称 */
	private String name;
	/** 会员价 */
	private String price;
	/** 图片 */
	private String pic;
	/** 商品简介*/
	private String introduction;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public ListProduct() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ListProduct(String id, String name, String price, String pic,
			String introduction) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.pic = pic;
		this.introduction = introduction;
	}
}
