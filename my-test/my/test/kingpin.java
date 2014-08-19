package my.test;
// 用来测试 git for windows 是否能用

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class kingpin {
	public static void main(String[] args) throws Exception{
		Class.forName("org.h2.Driver");
		
		Connection conn = DriverManager.getConnection("jdbc:h2:tcp://10.11.1.13:9092/mydb", "sa", "");
		Statement stmt = conn.createStatement();
		
		Statement stmt2 = conn.createStatement();
		stmt2.executeUpdate("DROP TABLE IF EXISTS my_table");
		stmt2.executeUpdate("CREATE TABLE IF NOT EXISTS my_table(name varchar(20))");
		stmt2.executeUpdate("INSERT INTO my_table(name) VALUES('zhh')");

		ResultSet rs = stmt2.executeQuery("SELECT name FROM my_table");
		rs.next();
		System.out.println(rs.getString(1));

		stmt2.close();
		conn.close();
	}
}
