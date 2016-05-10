package yu.service.vo;

public class OrderForm {
	private String userid;
	private String reciever;
	private String phone;
	private String adress;
	private String isDefault;
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getReciever() {
		return reciever;
	}
	public void setReciever(String reciever) {
		this.reciever = reciever;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public OrderForm(String userid, String reciever, String phone,
			String adress, String isDefault, int id) {
		super();
		this.userid = userid;
		this.reciever = reciever;
		this.phone = phone;
		this.adress = adress;
		this.isDefault = isDefault;
		this.id = id;
	}
	
}
