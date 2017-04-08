package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {

	public static Connection conn = null;

	public static Connection getConnection() throws ClassNotFoundException {

		String db_connect_string = "jdbc:sqlserver://localhost:1433;databaseName=MagicMoments";
		String db_userid = "sa";
		String db_password = "ionuser@123";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(db_connect_string, db_userid, db_password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}

}
