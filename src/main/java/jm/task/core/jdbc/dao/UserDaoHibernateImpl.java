package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction t = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users" +
                    "(id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(45), " +
                    "lastname VARCHAR(45), " +
                    "age INT NOT NULL" +
                    ") ENGINE=InnoDB").executeUpdate();
            t.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction t = session.beginTransaction();
            session.createSQLQuery("DROP TABLE users").executeUpdate();
            t.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
       try ( Session session = Util.getSessionFactory().openSession()) {
        Transaction t = session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        t.commit();
    } catch (Exception e){
           System.out.println(e.getMessage());
           e.printStackTrace();
       }
    }


    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Query query = session.createQuery("delete  User WHERE id = :Id");
            query.setParameter("Id", id);
            query.executeUpdate();
            t.commit();
            session.close();
        }



    @Override
    public List<User> getAllUsers() {
        return (List<User>) Util.getSessionFactory().openSession().createQuery("FROM User ").list();


    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.createQuery("DELETE FROM User").executeUpdate();
        t.commit();
        session.close();


    }
}
