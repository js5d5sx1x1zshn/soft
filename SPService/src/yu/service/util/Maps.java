package yu.service.util;

import java.util.HashMap;
import java.util.Map;

import yu.service.vo.ListProduct;
import yu.service.vo.Product;

import com.itheima.redbaby.config.Constant;

public class Maps {
	/**基本商品的存储单位*/
//	public  static Map<String, Product> prouducts = new HashMap<String, Product>();
//	static {
//		prouducts.put("101", new Product("2000", "101", "荣耀6 Plus套餐版", "2899.00", "100",
//				"10", Constant.pic_url.concat("huawei_phone1.png"), "陶瓷后壳，共享专利注凝技术"));
//		prouducts.put("102", new Product("2000", "102", "华为 Mate7", "3699.00", "100", 
//				"10", Constant.pic_url.concat("huawei_phone2.png"), "Live once. Live life."));
//		prouducts.put("103", new Product("2000", "103", "荣耀路由", "188", "100", 
//				"10", Constant.pic_url.concat("huaweiluyou.png"), "引领品质Wi-Fi优雅生活"));
//		prouducts.put("104", new Product("2000", "104", "荣耀智能手环", "888", "100", 
//				"10", Constant.pic_url.concat("huaweishouhuan.png"), "能通话的健康手环"));
//		prouducts.put("105", new Product("2000", "105", "小米电视2", "3399", "100", 
//				"10", Constant.pic_url.concat("xiaomiTV2.jpg"), "顶配49英寸4K电视，性价比之王"));
//		prouducts.put("106", new Product("2000", "106", "小米平板", "1299", "100", 
//				"10", Constant.pic_url.concat("xiaomipad.jpg"), "全球首款搭载 NVIDIA Tegra K1 处理器平板"));
//		prouducts.put("107", new Product("2000", "107", "小米移动电源16000mAh", "129", "100", 
//				"10", Constant.pic_url.concat("xiaomidianyuan.jpg"), "双USB输出 高品质电芯 全铝合金金属外壳"));
//		prouducts.put("108", new Product("2000", "108", "小米头戴式耳机", "499", "100", 
//				"10", Constant.pic_url.concat("xiaomierji.jpg"), "独家音质优化专利，金属复合振膜技术"));
//	}
	public  static Map<String, ListProduct> prouducts = new HashMap<String, ListProduct>();
	static {
		prouducts.put("101", new ListProduct("101", "荣耀6 Plus套餐版", "2899.00", 
				 Constant.pic_url.concat("huawei_phone1.png"), "陶瓷后壳，共享专利注凝技术"));
		prouducts.put("102", new ListProduct("102", "华为 Mate7", "3699.00",
				 Constant.pic_url.concat("huawei_phone2.png"), "Live once. Live life."));
		prouducts.put("103", new ListProduct("103", "荣耀路由", "188",
				 Constant.pic_url.concat("huaweiluyou.png"), "引领品质Wi-Fi优雅生活"));
		prouducts.put("104", new ListProduct("104", "荣耀智能手环", "888", 
				 Constant.pic_url.concat("huaweishouhuan.png"), "能通话的健康手环"));
		prouducts.put("105", new ListProduct("105", "小米电视2", "3399",
				 Constant.pic_url.concat("xiaomiTV2.jpg"), "顶配49英寸4K电视，性价比之王"));
		prouducts.put("106", new ListProduct("106", "小米平板", "1299",  
				 Constant.pic_url.concat("xiaomipad.jpg"), "搭载 NVIDIA Tegra K1 处理器平板"));
		prouducts.put("107", new ListProduct("107", "小米移动电源16000mAh", "129", 
				 Constant.pic_url.concat("xiaomidianyuan.jpg"), "双USB输出 高品质电芯 "));
		prouducts.put("108", new ListProduct("108", "小米头戴式耳机", "499", 
				 Constant.pic_url.concat("xiaomierji.jpg"), "独家音质优化专利"));
	}
//	//商品详情的书局
//	public static Map<String, Product> prouducts_detail = new HashMap<String, Product>();
//	static {
//		prouducts_detail.put("101", new Product("2000", "101", "荣耀6 Plus套餐版", "2899.00", "3000", 
//				"999", null, "双眼看世界，荣耀独创后置平行双800万像素镜头设计，前置800万像素拍照神器！"));
//		prouducts_detail.put("102", new Product("2000", "102", "华为 Mate7", "3699.00", "3000", 
//				"999", null, "购机即送莫塞尔红酒VIP金卡、华为手机会员VIP金卡、华为手机延保卡，全球独家首发。"));
//		prouducts_detail.put("103", new Product("2000", "103", "荣耀路由", "188.00", "3000", 
//				"999", null, "业界首创分布式Wi-Fi方案，双路由一键组网完美覆盖大户豪宅。精致外观设计，11AC 1167Mbps无线，双频巴伦内置天线信号更强，双核CPU快一倍，防暴力破解安全防护，单路由也好用。支持迅雷远程下载~"));
//		prouducts_detail.put("104", new Product("2000", "104", "荣耀智能手环", "888.00", "3000", 
//				"999", null, "亦言亦行——IP57专业级防尘防水，微信连接，运动分享，能通话的健康智能手环！"));
//		prouducts_detail.put("105", new Product("2000", "105", "小米电视2", "1999.00", "3000", 
//				"999", null, "SDP X-Gen超晶屏具有5000:1的超高静态对比度和6毫秒的超快响应速度，搭配瑞仪光电的顶级背光模组，组成一个完美的屏幕模组，超凡画质由此开始。"));
//		prouducts_detail.put("106", new Product("2000", "106", "小米平板", "1299.00", "3000", 
//				"999", null, "全球首款搭载 NVIDIA Tegra K1 192 核 PC 架构的 GPU / 夏普、友达 7.9 英寸全贴合视网膜屏"));
//		prouducts_detail.put("107", new Product("2000", "107", "小米移动电源16000mAh", "129.00", "3000", 
//				"999", null, "双USB输出 高品质电芯 全铝合金金属外壳"));
//		prouducts_detail.put("108", new Product("2000", "108", "小米头戴式耳机", "499.00", "3000", 
//				"999", null, "50mm大尺寸航天金属振膜手机直推高保真音质"));
//	}
}

