package common;
import java.awt.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MysqlOp {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://172.16.116.32:3306/?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
	static final String USER = "root";
	static final String PASSWORD = "201625010412";
	
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
	public ArrayList getInfo(String databaseName, String operation, String data) throws SQLException, ClassNotFoundException {
		
		ResultSet rSet = null;
		Connection connection = null;
		Statement statement = null;
		Class.forName(JDBC_DRIVER);
		connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		String sql = "USE "+databaseName;
		statement = connection.createStatement();
		statement.executeUpdate(sql);
			
		statement = connection.createStatement();
		rSet = statement.executeQuery(operation);
		connection.close();
		statement.close();
		return  convertList(rSet);
	}
	private static ArrayList convertList(ResultSet rSet) throws SQLException{
		ArrayList list = new ArrayList();
		ResultSetMetaData metaData = rSet.getMetaData();
		int column = metaData.getColumnCount();
		while(rSet.next()) {
			Map rowData = new HashMap();
			for(int i=1; i<=column; i++) {
				rowData.put(metaData.getColumnName(i), rSet.getObject(i));
			}
			list.add(rowData);
		}
		return list;
		
	}
}
