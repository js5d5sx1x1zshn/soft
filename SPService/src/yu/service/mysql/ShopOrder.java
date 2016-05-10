package yu.service.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.sf.json.JSONArray;

import yu.service.util.MysqlData;
import yu.service.vo.ListProduct;
import yu.service.vo.Order;

public class ShopOrder {
	public int creatOrder(String adressid,String shoplist,String money,String userid){
		 try {
	        	MysqlData.getConnection();
	        	Statement statement = MysqlData.statement;
	        	//新建订单
	        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsssss");
	        	StringBuilder out = new StringBuilder(dateFormat.format(new Date()));
	        	dateFormat.applyPattern("yyyyMMddHHmmss");
	        	StringBuilder time = new StringBuilder(dateFormat.format(new Date()));
	        	System.out.println(time.toString());
	        	String sql2 = "INSERT shop_order VALUE('"+out.toString()+"','0','"+money+"'," +
	        			"'"+time+"','"+adressid+"','"+shoplist+"','"+userid+"')";
	        	System.out.println(sql2);
	            int execut = statement.executeUpdate(sql2);
	            JSONArray jsonArrayShopList = JSONArray.fromObject(shoplist);
	            if(execut>0){
	            	//成功后删除购物车
	            	int execut2 = 1;
	            	for(int i=0;i<jsonArrayShopList.size();i++){
		            	 String shopid = jsonArrayShopList.getJSONObject(i).getString("shopid");
		            	 String color = jsonArrayShopList.getJSONObject(i).getString("color");
		            	 String zhishi = jsonArrayShopList.getJSONObject(i).getString("zhishi");
		            	 String sql3 = "DELETE FROM shop_cart WHERE shopid = "+shopid+" AND color='"+color+"' AND zhishi='"+zhishi+"'";
		            	 System.out.println(sql3);
		            	 execut2 = statement.executeUpdate(sql3);
		            }
	            	if(execut2>0){
			          	return 0;
		            }
	            }else {
	            	//应该做删除回滚
	            }
	        	
	        } catch (Exception e) {
	            	
	            e.printStackTrace();
	            
	        }finally{
	        	
	        	MysqlData.closeConnection();
	        	
	        }
			return 1;
	}
	/**
	 * 产看全部订单
	 * */
	public ArrayList<Order> getWaitPayByOreder(String userid){
        try {
        	MysqlData.getConnection();
        	Statement statement = MysqlData.statement;
            //要执行的SQL语句
            String sql = "SELECT * FROM shop_order WHERE userid = '"+userid+"'";
            //ResultSet类，用来存放获取的结果集！！
            ResultSet rs = statement.executeQuery(sql);
            ArrayList<Order> arrayList = new ArrayList<Order>();
            while(rs.next()){
            	Order order = new Order(rs.getString("orderId") , rs.getString("status"), rs.getDouble("price"), rs.getString("time"),
            			rs.getString("adressId"), rs.getString("shoplist"), rs.getString("userid"));
            	arrayList.add(order);
            }
            rs.close();
            return arrayList;
        } catch(SQLException e) {
        	
            e.printStackTrace();  
            
            }catch (Exception e) {
            	
            e.printStackTrace();
            
        }finally{
        	
        	MysqlData.closeConnection();
        	
        }
		return null;
	}
	//取消订单
	public int cacnceOrder(String orderid){
        try {
        	MysqlData.getConnection();
        	Statement statement = MysqlData.statement;
        	 //要执行的SQL语句
	       	 String sql3 = "DELETE FROM shop_order WHERE orderid = '"+orderid+"'";
	       	 System.out.println(sql3);
	       	 int execut2 = statement.executeUpdate(sql3);
	       	System.out.println(execut2);
	       	 if(execut2>0){
	       		 String sql = "DELETE FROM order_jiaoyi_tijiaodan WHERE orderid = '"+orderid+"'";
		       	 System.out.println(sql);
		       	 int execut = statement.executeUpdate(sql);
	       		 return 1;
	       	 }
         return 0;
         
         } catch(SQLException e) {
        	
            e.printStackTrace();  
            
            }catch (Exception e) {
            	
            e.printStackTrace();
            
        }finally{
        	
        	MysqlData.closeConnection();
        	
        }
		return 0;
	}
	//产看待付款订单
	public ArrayList<Order> getWaitPayByOreder2(String userid,String status){
        try {
        	MysqlData.getConnection();
        	Statement statement = MysqlData.statement;
            //要执行的SQL语句
            String sql = "SELECT * FROM shop_order WHERE userid = '"+userid+"' AND status = '"+status+"'";
            //ResultSet类，用来存放获取的结果集！！
            ResultSet rs = statement.executeQuery(sql);
            ArrayList<Order> arrayList = new ArrayList<Order>();
            while(rs.next()){
            	Order order = new Order(rs.getString("orderId") , rs.getString("status"), rs.getDouble("price"), rs.getString("time"),
            			rs.getString("adressId"), rs.getString("shoplist"), rs.getString("userid"));
            	arrayList.add(order);
            }
            rs.close();
            return arrayList;
        } catch(SQLException e) {
        	
            e.printStackTrace();  
            
            }catch (Exception e) {
            	
            e.printStackTrace();
            
        }finally{
        	
        	MysqlData.closeConnection();
        	
        }
		return null;
	}
	/**
	 * 提交申请
	 * @param status
	 * @return
	 * 0失败
	 * 1成功
	 * 2订单待审核
	 */
	public int submitApply(String orderid,String status){
        try {
        	MysqlData.getConnection();
        	Statement statement = MysqlData.statement;
            //查找是否待审核的SQL语句
            String sql = "SELECT orderid FROM order_jiaoyi_tijiaodan WHERE orderid = '"+orderid+"'";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
            	rs.close();
            	return 2;
            }else{
            	String sql2 = "INSERT INTO order_jiaoyi_tijiaodan VALUE('"+orderid+"',"+status+")";
	        	System.out.println(sql2);
	            int execut = statement.executeUpdate(sql2);
	            if(execut>0){
	            	return 1;
	            }else{
	            	return 0;
	            }
            }
        } catch(SQLException e) {
        	
            e.printStackTrace();  
            
            }catch (Exception e) {
            	
            e.printStackTrace();
            
        }finally{
        	
        	MysqlData.closeConnection();
        	
        }
		return 0;
	}
}
