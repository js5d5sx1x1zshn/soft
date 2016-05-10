package yu.service.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlData {
	//声明Connection对象
	private static Connection con;
	//声明Statement对象
	public static Statement statement;
    //驱动程序名
    private static final String driver = "com.mysql.jdbc.Driver";
    //URL指向要访问的数据库名mydata
    private static final String url = "jdbc:mysql://localhost:3306/shop?useUnicode=true&characterEncoding=UTF-8";
    //MySQL配置时的用户名
    private static final String user = "root";
    //MySQL配置时的密码
    private static final String password = "123";
    /**连接数据库*/
    public static void getConnection(){
    	try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url,user,password);
            //2.创建statement类对象，用来执行SQL语句！！
            statement = con.createStatement();
            
    	}catch (Exception e) {
    		 System.out.println(e.toString());
		}
    }
    
    public static void closeConnection(){
    	try {
    		if(con!=null){
    			con.close();
    		}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
    }
}
