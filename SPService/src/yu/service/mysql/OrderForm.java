package yu.service.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import yu.service.util.MysqlData;

public class OrderForm {
	//得到信息
	public List<yu.service.vo.OrderForm> getAdressList(String id){
		List<yu.service.vo.OrderForm> forms = new ArrayList<yu.service.vo.OrderForm>();
		try {
        	MysqlData.getConnection();
        	Statement statement = MysqlData.statement;
            //要执行的SQL语句
            String sql = "SELECT * FROM user_adress WHERE userid = '"+id+"'";
            //ResultSet类，用来存放获取的结果集！！
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
            	yu.service.vo.OrderForm form = new yu.service.vo.OrderForm(rs.getString("userid"),
            			rs.getString("reciever"),rs.getString("phone"), 
            			rs.getString("adress"),rs.getString("isdefault"),
            			rs.getInt("id"));
            	forms.add(form);
            }
            rs.close();
        } catch(SQLException e) {
        	
            e.printStackTrace();  
            
            }catch (Exception e) {
            	
            e.printStackTrace();
            
        }finally{
        	
        	MysqlData.closeConnection();
        	
        }
		return forms;
	}
	//修改默认地址
	public boolean updateAdress(int id,String userid){
		 try {
	        	MysqlData.getConnection();
	        	Statement statement = MysqlData.statement;
	        	String sql1 = "UPDATE user_adress a SET a.isdefault = 0 WHERE userid = '1'";
	        	statement.executeUpdate(sql1);
      		 	String sql3 = "UPDATE user_adress a SET a.isdefault = 1 WHERE id = "+id+" AND userid = '1'";
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
