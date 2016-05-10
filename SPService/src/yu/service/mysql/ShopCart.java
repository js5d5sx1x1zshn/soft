package yu.service.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.fabric.xmlrpc.base.Param;

import yu.service.util.MysqlData;
import yu.service.vo.VOShopCart;

public class ShopCart {
	/**
	 * add to shop cart
	 * o 成功
	 * 1 失败
	 * 2 库存不足
	 * */
	public int addToCart(String zhishi,String color,int number,String shopid,String userid){
		 try {
	        	MysqlData.getConnection();
	        	Statement statement = MysqlData.statement;
	        	//判断数量是否超过库存
	        	
	        	//判断是增加数量 还是增加商品
	        	 String sql1 = "select number from shop_cart where shopid = "+shopid+" AND color='"+color+"' AND zhishi='"+zhishi+"' AND userid='"+userid+"'";
	        	 ResultSet rs = statement.executeQuery(sql1);
	        	 if(rs.next()){
	        		 System.out.println("1");
	        		 //要执行的SQL语句
	        		 int num = Integer.parseInt(rs.getString("number"))+number; 
	        		 String sql3 = "UPDATE shop_cart SET number = "+num+" where shopid = "+shopid+" AND color='"+color+"' AND zhishi='"+zhishi+"' AND userid='"+userid+"'";
	        		 System.out.println(sql3);
	 	             int execut = statement.executeUpdate(sql3);
	 	             if(execut>0){
	 	            	 return 0;
	 	             }
	        	 }else{
	        		 System.out.println("2");
	        		 //增加商品的数量
	        		 String sql2 = "INSERT INTO shop_cart VALUE('"+shopid+"',"+number+",'"+color+"','"+zhishi+"','"+userid+"')";
	        		 System.out.println(sql2);
	 	             int execut = statement.executeUpdate(sql2);
	 	             if(execut>0){
	 	             return 0;
	 	             }
	        	 }
	           
	            //ResultSet类，用来存放获取的结果集！！
	            
	            
	        } catch (Exception e) {
	            	
	            e.printStackTrace();
	            
	        }finally{
	        	
	        	MysqlData.closeConnection();
	        	
	        }
			return 1;
	}
	//得到购物车
	public ArrayList<VOShopCart> showShowCart(String userid){
		try {
        	MysqlData.getConnection();
        	Statement statement = MysqlData.statement;
            //要执行的SQL语句
            String sql = "SELECT shop_cart.zhishi,shop_cart.color,pic,shopid,shop_cart.number,price,imginfo FROM shop_cart INNER JOIN shouye_products table2 ON shop_cart.`shopid`=table2.`id` WHERE userid = '"+userid+"'";
            //ResultSet类，用来存放获取的结果集！！
            ResultSet rs = statement.executeQuery(sql);
            ArrayList<VOShopCart> arrayList = new ArrayList<VOShopCart>();
            while(rs.next()){
            	VOShopCart shopCart = new VOShopCart(rs.getString("shopid"), rs.getString("shop_cart.number"), 
                    	rs.getString("price"), rs.getString("imginfo"), rs.getString("shop_cart.zhishi"), 
                    	rs.getString("shop_cart.color"),rs.getString("pic"));
            	arrayList.add(shopCart);
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
	//删除购物车里的数据
	public boolean deleteCart(String zhishi,String color,String shopid,String userid){
		 try {
	        	MysqlData.getConnection();
	        	Statement statement = MysqlData.statement;
       		 	String sql3 = "DELETE FROM shop_cart WHERE shopid = "+shopid+" AND color='"+color+"' AND zhishi='"+zhishi+"' AND userid='"+userid+"'";
       		 	System.out.println(sql3);
	            int execut = statement.executeUpdate(sql3);
	            if(execut>0){
	            	return true;
	            }
	        } catch (Exception e) {
	            	
	            e.printStackTrace();
	            
	        }finally{
	        	MysqlData.closeConnection();
	        }
			return false;
	}
}
