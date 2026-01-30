package com;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeCrud {
	// MySQL -> CREATE DATABASE employees;
	static final String URL = "jdbc:mysql://localhost:3306/employees?createDatabaseIfNotExist=true";
	static final String USERNAME = "root";
	static final String PASSWORD = "root";

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		createTable();
		
		insert();
	
		readAll();
		readById(1);
	}

	public static void createTable() throws SQLException {
		Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		Statement statement = connection.createStatement();

		statement.execute("CREATE TABLE IF NOT EXISTS employee (id INT AUTO_INCREMENT PRIMARY KEY, ename VARCHAR(20) NOT NULL, job VARCHAR(20) NOT NULL, salary BIGINT NOT NULL, hiredate DATE);");

		statement.close();
		connection.close();
	}

	public static void insert() throws SQLException {
		Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		Statement statement = connection.createStatement();

		statement.execute("INSERT INTO employee (ename, job, salary, hiredate) VALUES ('smith','clerk',800, '1982-11-10')");
		statement.execute("INSERT INTO employee (ename, job, salary, hiredate) VALUES ('allen','clerk',1200, '1982-07-21')");
		statement.execute("INSERT INTO employee (ename, job, salary, hiredate) VALUES ('wards','salesman',1600, '1981-01-19')");
		statement.execute("INSERT INTO employee (ename, job, salary, hiredate) VALUES ('martin','manager',3600, '1980-09-30')");

		System.out.println("inserted successfully");

		statement.close();
		connection.close();
	}

	public static void readAll() throws SQLException {
		Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		Statement statement = connection.createStatement();

		ResultSet resultset = statement.executeQuery("SELECT * FROM employee");

		while (resultset.next()) {
			System.out.println("ID       : " + resultset.getInt("id"));
			System.out.println("Name     : " + resultset.getString("ename"));
			System.out.println("Job      : " + resultset.getString("job"));
			System.out.println("Salary   : " + resultset.getLong("salary"));
			System.out.println("Hiredate : " + resultset.getDate("hiredate"));
		}

		System.out.println("fetched successfully");

		resultset.close();
		statement.close();
		connection.close();
	}

	public static void readById(int id) throws SQLException {
		Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		Statement statement = connection.createStatement();

		ResultSet resultset = statement.executeQuery("SELECT * FROM employee WHERE id=" + id);

		if (resultset.next()) {
			System.out.println("ID       : " + resultset.getInt("id"));
			System.out.println("Name     : " + resultset.getString("ename"));
			System.out.println("Job      : " + resultset.getString("job"));
			System.out.println("Salary   : " + resultset.getLong("salary"));
			System.out.println("Hiredate : " + resultset.getDate("hiredate"));
		} else {
			System.out.println("Employee not found");
		}
		resultset.close();
		statement.close();
		connection.close();
	}

	public static void update(int id, long salary) throws SQLException {
		Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		Statement statement = connection.createStatement();

		statement.execute("UPDATE employee SET salary=" + salary + " WHERE id=" + id);

		System.out.println("Updated");

		statement.close();
		connection.close();
	}

	public static void delete(int id) throws SQLException {
		Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		Statement statement = connection.createStatement();

		statement.execute("DELETE FROM employee WHERE id=" + id);

		System.out.println("Deleted");

		statement.close();
		connection.close();
	}
}
