package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
	

	
	public UserDaoJDBCImpl() {
	
	}
	
	public void createUsersTable() {
		try(Connection connection = getConnection();
			Statement statement = connection.createStatement()) {
			statement.execute("CREATE TABLE IF NOT EXISTS kata.users ( " +
					"id BIGINT NOT NULL AUTO_INCREMENT, " +
					"name VARCHAR(50) NOT NULL, " +
					"lastName VARCHAR(50) NOT NULL, " +
					"age TINYINT NOT NULL, " +
					"PRIMARY KEY (id)); ");
			connection.commit();
		} catch(Exception e) {
			System.out.println(e.getMessage()+"createUsersTable");
		}
		
	}
	
	public void dropUsersTable() {
		try(Connection connection = getConnection();
			Statement statement = connection.createStatement()) {
			statement.execute("DROP TABLE IF EXISTS kata.users;");
			connection.commit();
		} catch(Exception e) {
			System.out.println(e.getMessage()+"dropUsersTable");
		}
	}
	
	public void saveUser(String name, String lastName, byte age) {
		try(Connection connection = getConnection();
			PreparedStatement preparedStmt = connection.prepareStatement("INSERT INTO kata.users (name, lastName, age) values (?,?,?);")) {
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, lastName);
			preparedStmt.setInt(3, age);
			preparedStmt.execute();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void removeUserById(long id) {
		try(Connection connection = getConnection();
			Statement statement = connection.createStatement()) {
			statement.execute("DELETE FROM kata.users WHERE ID=" + id + ";"); //preparedStatement
			connection.commit();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public List<User> getAllUsers() {
	
		List<User> users = new ArrayList<>();
		try(Connection connection = getConnection();
			Statement statement = connection.createStatement()) {
			String sql = "SELECT * FROM kata.users";
			ResultSet resultSet = statement.executeQuery(sql);
			connection.commit();
			while(resultSet.next()) {
				long id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String lastName = resultSet.getString("lastName");
				byte age = (byte) resultSet.getInt("age");
				User user = new User(name, lastName, age);
				user.setId(id);
				users.add(user);
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return users;
	}
	
	public void cleanUsersTable() {
		try(Connection connection = getConnection();
			Statement statement = connection.createStatement()) {
			statement.execute("TRUNCATE TABLE kata.users;");
			connection.commit();
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
