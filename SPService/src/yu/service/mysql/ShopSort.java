package yu.service.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import yu.service.util.MysqlData;
import yu.service.vo.VOShopSort;

public class ShopSort {
	
	//显示商品分类列表
	public List<VOShopSort> getShopSorts(){
		
		try {
        	MysqlData.getConnection();
        	Statement statement = MysqlData.statement;
            //一级分类
            String sql = "SELECT * FROM shop_sort WHERE parentid = 0";
            ResultSet rs = statement.executeQuery(sql);
            ArrayList<VOShopSort> arrayList = new ArrayList<VOShopSort>();
            while(rs.next()){
            	VOShopSort shopSort = new VOShopSort(rs.getString("sortid"), rs.getString("id"), 
            			rs.getString("parentid"), rs.getString("path"),"","");
            	arrayList.add(shopSort);
            }
          
            //二级分类商品信息
            String sql2 = "SELECT t1.`id`,t1.`sortid`,parentid,path,t2.`name`,t2.`pic` FROM shop_sort t1 INNER JOIN shouye_products t2 ON t1.`id` = t2.`id` WHERE parentid = 1";
            rs = statement.executeQuery(sql2);
            while(rs.next()){
            	VOShopSort shopSort = new VOShopSort(rs.getString("sortid"), rs.getString("id"), 
            			rs.getString("parentid"), rs.getString("path"), rs.getString("name"),rs.getString("pic"));
            	arrayList.add(shopSort);
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
	
}
