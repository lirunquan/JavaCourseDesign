package tencent;
import java.sql.*;
public class MysqlOp {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://172.17.175.102:3306/?useSSL=false";
	static final String USER = "root";
	static final String PASSWORD = "123456.";
	
	public void Operation(String operation) {
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			statement = connection.createStatement();
			statement.executeUpdate(operation);
		} catch (SQLException e) {
			e.printStackTrace();// TODO: handle exception
		}catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}finally {
			try {
				if(statement!=null) {
					statement.close();
				}
			} catch (SQLException sException2) {
				 
			}
			try {
				if(connection!=null) {
					connection.close();
				}
			} catch (SQLException sException) {
				 
				sException.printStackTrace();
			}
		}
	}
	public void TableOperation(String databaseName, String operation) {
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			String sql = "USE "+databaseName;
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			
			statement = connection.createStatement();
			statement.executeUpdate(operation);
		} catch (SQLException e) {
			e.printStackTrace();// TODO: handle exception
		}catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}finally {
			try {
				if(statement!=null) {
					statement.close();
				}
			} catch (SQLException sException2) {
				 
			}
			try {
				if(connection!=null) {
					connection.close();
				}
			} catch (SQLException sException) {
				 
				sException.printStackTrace();
			}
		}
	}
	public boolean isDataExists(String databaseName, String operation, String data, String list) {
		Connection connection = null;
		Statement statement = null;
		String data_ = "";
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			String sql = "USE "+databaseName;
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			
			statement = connection.createStatement();
			ResultSet rSet = statement.executeQuery(operation);
			while (rSet.next()) {
				data_ = rSet.getString(list);
				if(data_.equals(data)) {
					break;
				}
			}
			rSet.close();
		} catch (SQLException e) {
			e.printStackTrace();// TODO: handle exception
		}catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}finally {
			try {
				if(statement!=null) {
					statement.close();
				}
			} catch (SQLException sException2) {
				 
			}
			try {
				if(connection!=null) {
					connection.close();
				}
			} catch (SQLException sException) {
				 
				sException.printStackTrace();
			}
		}
		return data.equals(data_);
	}
}
