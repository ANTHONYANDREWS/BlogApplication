package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityTransaction;
import javax.transaction.SystemException;
import javax.transaction.Transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import model.User;
import oracle.net.aso.r;
import utility.ConnectionManager;
import utility.HibernateConnectionManager;

public class UserDao implements UserDaoInterface {
	
	private SessionFactory sessionFactory = HibernateConnectionManager.getSessionFactory();

//	@Override
	public int signup(User user) {
//		String INSERT_USERS_SQL = "INSERT INTO USERS(email,password)VALUES(?,?)";
//		int result =0;
//		try {
//			Connection con = ConnectionManager.getConnection();
//			PreparedStatement ps = con.prepareStatement(INSERT_USERS_SQL);
//			ps.setString(1, user.getEmail()); 
//			ps.setString(2, user.getPassword());
////			System.out.println(ps);
////			System.out.println(user.getEmail());
//			result= ps.executeUpdate();
////			 = 1; //We are changing it manually right sir?
////			return result;
//		}
//		catch(SQLException e) {
//			e.printStackTrace();
//		}
//		return result;
//		
		
		Session session = this.sessionFactory.openSession();
		org.hibernate.Transaction transaction = session.beginTransaction();
		if(session.save(user)!=null)
		{
			transaction.commit();
			session.close();
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean loginUser(User user) {
//		boolean status = false;
//		try {
//			Connection con = ConnectionManager.getConnection();
//			PreparedStatement ps = con.prepareStatement("select * from USERS where email =? and password=?");
//			ps.setString(1, user.getEmail());
//			ps.setString(2, user.getPassword());
//			System.out.println(ps);
//			ResultSet rs = ps.executeQuery();
//			status = rs.next();
//			
//		}
//		catch(SQLException e){
//			e.printStackTrace();
//		}
//		return status;
//	}
	Session session = this.sessionFactory.openSession();
	Transaction tx = null;
	
	try {
		tx=(Transaction) session.getTransaction();
		((EntityTransaction) tx).begin();
		Query query = session.createQuery("from User where email='"+user.getEmail()+"'"+"and password ='"+user.getPassword()+"'");
		user = (User)query.uniqueResult();
		tx.commit();
	}
	catch(Exception e) {
		if(tx!=null)
			try {
				tx.rollback();
			} catch (IllegalStateException | SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		e.printStackTrace();
	}
	finally {
		session.close();
	}
	return true;

}
}
