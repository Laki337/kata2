package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
	
	Session session = Util.getSessionFactory().openSession();
	Transaction transaction = null;
	
	public UserDaoHibernateImpl() {
	
	}
	
	
	@Override
	public void createUsersTable() {
		try {
			transaction = session.beginTransaction();
			
			session.createNativeQuery("CREATE TABLE IF NOT EXISTS kata.users ( " +
					"id BIGINT NOT NULL AUTO_INCREMENT, " +
					"name VARCHAR(50) NOT NULL, " +
					"lastName VARCHAR(50) NOT NULL, " +
					"age TINYINT NOT NULL, " +
					"PRIMARY KEY (id)) ").executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			transaction.rollback();
		}
	}
	
	
	
	@Override
	public void dropUsersTable() {
		try {
			transaction = session.beginTransaction();
			String sql = "DROP TABLE IF EXISTS kata.users;";
			session.createNativeQuery(sql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			transaction.rollback();
		}
	}
	
	@Override
	public void saveUser(String name, String lastName, byte age) {
		try {
			
			
			transaction = session.beginTransaction();
			User user = new User(name, lastName, age);
			session.save(user);
			session.getTransaction().commit();
		}
		catch(Exception e){
			transaction.rollback();
		}
	}
	
	@Override
	public void removeUserById(long id) {
		try {
			transaction = session.beginTransaction();
			String sql = "DELETE FROM kata.users WHERE ID=" + id + ";";
			session.createNativeQuery(sql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			transaction.rollback();
		}
	}
	
	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		try {
			transaction = session.beginTransaction();
			List<Object[]> rows = session.createNativeQuery("SELECT * FROM kata.users").list();
			for(Object[] row : rows) {
				long id = ((BigInteger) row[0]).longValue();
				String name = (String) row[1];
				String lastName = (String) row[2];
				byte age = (byte) row[3];
				User user = new User(name, lastName, age);
				user.setId(id);
				users.add(user);
			}
			
			session.getTransaction().commit();
		}
		catch(Exception e){
			transaction.rollback();
		}
		return users;
	}
	
	@Override
	public void cleanUsersTable() {
		try {
			transaction = session.beginTransaction();
			String sql = "TRUNCATE TABLE kata.users;";
			session.createNativeQuery(sql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			transaction.rollback();
		}
	}
}
