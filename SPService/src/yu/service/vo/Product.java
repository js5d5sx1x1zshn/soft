package yu.service.vo;

public class Product {
	/** 商品数量 */
	private String prodNum;
	/** ID */
	private String id;
	/** 商品名称 */
	private String name;
	/** 会员价 */
	private String price;
	/** 商品库存数量，0为缺货或下架 */
	private String number;
	/** 商品购买数量上限 */
	private String uplimit;
	/** 图片 */
	private String pic;
	/** 商品简介*/
	private String introduction;
	/** 商品具体规格*/
	private String imginfo;
	/** 商品制式*/
	private String zhishi;
	/** 商品颜色*/
	private String color;
	public String getProdNum() {
		return prodNum;
	}
	public void setProdNum(String prodNum) {
		this.prodNum = prodNum;
	}
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getUplimit() {
		return uplimit;
	}
	public void setUplimit(String uplimit) {
		this.uplimit = uplimit;
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
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Product(String prodNum, String id, String name, String price,
			String number, String uplimit, String pic, String introduction,
			String imginfo) {
		super();
		this.prodNum = prodNum;
		this.id = id;
		this.name = name;
		this.price = price;
		this.number = number;
		this.uplimit = uplimit;
		this.pic = pic;
		this.introduction = introduction;
		this.imginfo = imginfo;
	}
	public String getImginfo() {
		return imginfo;
	}
	public void setImginfo(String imginfo) {
		this.imginfo = imginfo;
	}
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
}
