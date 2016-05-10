package yu.service.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import yu.service.util.MysqlData;
import yu.service.vo.ListProduct;
import yu.service.vo.ShouyeGuanggao;

public class Shop {
	//得到商城首页产品列表
	public ArrayList<ListProduct> getShouyeProducts(){
        try {
        	MysqlData.getConnection();
        	Statement statement = MysqlData.statement;
            //要执行的SQL语句
            String sql = "select id,name,price,pic,introduction from shouye_products";
            //ResultSet类，用来存放获取的结果集！！
            ResultSet rs = statement.executeQuery(sql);
            ArrayList<ListProduct> arrayList = new ArrayList<ListProduct>();
            while(rs.next()){
            	ListProduct listProduct = new ListProduct(rs.getString("id"), rs.getString("name"),
            			rs.getString("price"), rs.getString("pic"), rs.getString("introduction"));
                //首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
                //然后使用GB2312字符集解码指定的字节数组。
//              name = new String(name.getBytes("ISO-8859-1"),"gb2312");
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
	//得到首页广告
	public ArrayList<ShouyeGuanggao> getShouyeGuangao(){
		try {
        	MysqlData.getConnection();
        	Statement statement = MysqlData.statement;
            //要执行的SQL语句
            String sql = "select * from shouye_guanggao";
            //ResultSet类，用来存放获取的结果集！！
            ResultSet rs = statement.executeQuery(sql);
            ArrayList<ShouyeGuanggao> arrayList = new ArrayList<ShouyeGuanggao>();
            while(rs.next()){
//            	System.out.println(rs.getString("id")+"  "+rs.getString("shopid")+"  "+rs.getString("imgurl"));
            	ShouyeGuanggao listProduct = new ShouyeGuanggao(rs.getString("id"),
            			rs.getString("shopid"),rs.getString("imgurl"));
                //首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
                //然后使用GB2312字符集解码指定的字节数组。
//                name = new String(name.getBytes("ISO-8859-1"),"gb2312");
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
	//得到商城首页产品列表
	public ArrayList<ListProduct> getSearchResult(String name){
        try {
        	MysqlData.getConnection();
        	Statement statement = MysqlData.statement;
            //要执行的SQL语句
            String sql = "SELECT * FROM shouye_products t WHERE t.`name` LIKE '%"+name+"%'";
            System.out.println(sql);
            //ResultSet类，用来存放获取的结果集！！
            ResultSet rs = statement.executeQuery(sql);
            ArrayList<ListProduct> arrayList = new ArrayList<ListProduct>();
            while(rs.next()){
            	ListProduct listProduct = new ListProduct(rs.getString("id"), rs.getString("name"),
            			rs.getString("price"), rs.getString("pic"), rs.getString("introduction"));
            	
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
}
