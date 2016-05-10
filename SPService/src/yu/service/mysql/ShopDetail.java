package yu.service.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import yu.service.util.MysqlData;
import yu.service.vo.Product;
import yu.service.vo.ShouyeGuanggao;

public class ShopDetail {
	//得到首页广告
		public ArrayList<ShouyeGuanggao> getDetailGuangao(String id){
			try {
	        	MysqlData.getConnection();
	        	Statement statement = MysqlData.statement;
	            //要执行的SQL语句
	            String sql = "select * from detail_guanggao where shopid = '"+id+"'";
	            //ResultSet类，用来存放获取的结果集！！
	            ResultSet rs = statement.executeQuery(sql);
	            ArrayList<ShouyeGuanggao> arrayList = new ArrayList<ShouyeGuanggao>();
	            while(rs.next()){
	            	ShouyeGuanggao listProduct = new ShouyeGuanggao(rs.getString("id"),
	            			rs.getString("shopid"),rs.getString("imgurl"));
	            	arrayList.add(listProduct);
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
		//得到信息
		public Product getDetailContent(String id){
			try {
	        	MysqlData.getConnection();
	        	Statement statement = MysqlData.statement;
	            //要执行的SQL语句
	            String sql = "select * from shouye_products where id="+id+"";
	            //ResultSet类，用来存放获取的结果集！！
	            ResultSet rs = statement.executeQuery(sql);
	            if(rs.next()){
	            	 Product product = new Product(rs.getString("prodNum"), rs.getString("id"), 
	             			rs.getString("name"), rs.getString("price"), rs.getString("number"), 
	             			rs.getString("uplimit"), rs.getString("pic"), rs.getString("introduction"),
	             			rs.getString("imginfo"));
	            	 product.setZhishi(rs.getString("zhishi"));
	            	 product.setColor(rs.getString("color"));
	            	 return product;
	            }
	            
	        } catch(SQLException e) {
	        	
	            e.printStackTrace();  
	            
	            }catch (Exception e) {
	            	
	            e.printStackTrace();
	            
	        }finally{
	        	
	        	MysqlData.closeConnection();
	        	
	        }
			return null;
		}
}
