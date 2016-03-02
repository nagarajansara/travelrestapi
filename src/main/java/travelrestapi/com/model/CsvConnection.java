package travelrestapi.com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CsvConnection
{

	ResultSet results = null;
	Connection conn = null;
	Statement statement = null;

	public Connection getCsvConnection(String csvPath) throws Exception
	{
		try
		{
			Class.forName("org.relique.jdbc.csv.CsvDriver");
			conn = DriverManager.getConnection("jdbc:relique:csv:" + csvPath);
		} catch (Exception ex)
		{
			System.out.println("getCsvConnection :" + ex.getMessage());
			throw ex;
		}
		return conn;
	}

	public void closeCSVConnection(Connection connection, Statement statement,
			ResultSet resultSet) throws SQLException
	{
		if (connection != null && !connection.isClosed())
		{
			connection.close();
		}
		if (resultSet != null && !resultSet.isClosed())
		{
			resultSet.close();
		}
		if (statement != null && !statement.isClosed())
		{
			statement.close();
		}
	}

}
