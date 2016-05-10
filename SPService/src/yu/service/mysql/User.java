package yu.service.mysql;

import java.sql.ResultSet;
import java.sql.Statement;

import yu.service.util.MysqlData;
import yu.service.vo.OrderForm;

public class User {
	public boolean login(String name,String pwd){
		boolean execut = false;
		 try {
	        	MysqlData.getConnection();
	        	Statement statement = MysqlData.statement;
	        	 //要执行的SQL语句
       		 	String sql = "SELECT * FROM users WHERE userid = '"+name+"' AND userpwd = '"+pwd+"'";
       		 	System.out.println(sql);
       		    ResultSet resultSet = statement.executeQuery(sql);
	            if(resultSet.next()){
	            	execut = true;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally{
	        	MysqlData.closeConnection();
	        }
		    return execut;
	}	
	public int register(String name,String pwd){
		int execut = 0;
		try {
        	MysqlData.getConnection();
        	Statement statement = MysqlData.statement;
        	 //要执行的SQL语句
   		 	String sql = "SELECT * FROM users WHERE userid = '"+name+"'";
   		 	System.out.println(sql);
   		    ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
            	execut = 1;
            }else{
            	String sql2 = "INSERT INTO users(userid,userpwd) VALUE('"+name+"','"+pwd+"')";
            	int e = statement.executeUpdate(sql2);
            	if(e!=0){
            		execut = 2;
            	}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	MysqlData.closeConnection();
        }
		return execut;
	}
	
	public OrderForm getDefaultAdress(String id){
		OrderForm orderForm = null;
		try {
        	MysqlData.getConnection();
        	Statement statement = MysqlData.statement;
        	 //要执行的SQL语句
   		 	String sql = "SELECT * FROM user_adress WHERE isdefault = '1' AND userid ="+id+"";
   		 	System.out.println(sql);
   		    ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
            	//有已经设置好的默认地址
            	orderForm = new OrderForm(resultSet.getString("userid"), resultSet.getString("reciever"), 
            			resultSet.getString("phone"), resultSet.getString("adress"), 
            			resultSet.getString("isdefault"), resultSet.getInt("id"));
            	return orderForm;
            }else{
            	String sql2 = "SELECT * FROM user_adress WHERE  userid = '"+id+"'";
            	ResultSet rs = statement.executeQuery(sql2);
            	if(rs.next()){
            		//有已经设置好的默认地址
                	orderForm = new OrderForm(rs.getString("userid"), rs.getString("reciever"), 
                			rs.getString("phone"), rs.getString("adress"), 
                			rs.getString("isdefault"), rs.getInt("id"));
                	return orderForm;
            	}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	MysqlData.closeConnection();
        }
		return null;
	}
	public int updateAdress(String id,String reciever,String phone,String adress){
		int excute = 0;
		try {
        	MysqlData.getConnection();
        	Statement statement = MysqlData.statement;
        	 //要执行的SQL语句
   		 	String sql = "UPDATE user_adress SET reciever = '"+reciever+"' ,phone = '"+phone+"'," +
   		 			"adress = '"+adress+"' WHERE id = "+id+"";
   		 	System.out.println(sql);
   		    excute = statement.executeUpdate(sql);
   		    if(excute!=0){
   		    	excute = 1;
   		    }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	MysqlData.closeConnection();
        }
		return excute;
	}
	public int createAdress(String userid,String reciever,String phone,String adress){
		int excute = 0;
		try {
        	MysqlData.getConnection();
        	Statement statement = MysqlData.statement;
        	 //要执行的SQL语句
   		 	String sql = "INSERT user_adress(userid,reciever,phone,adress) VALUE('"+userid+"','"
        	 +reciever+"',"+phone+",'"+adress+"')";
   		 	System.out.println(sql);
   		    excute = statement.executeUpdate(sql);
   		    if(excute!=0){
   		    	excute = 1;
   		    }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	MysqlData.closeConnection();
        }
		return excute;
	}
}
