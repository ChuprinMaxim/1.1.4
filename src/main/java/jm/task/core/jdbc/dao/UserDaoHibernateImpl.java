package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction;

    public UserDaoHibernateImpl() {
    }

    /*чтобы не дублировать запрос в каждом методе*/
    public void tableQuery(String x) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(x);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    @Override
    public void createUsersTable() {
        tableQuery("CREATE TABLE IF NOT EXISTS User (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), lastName VARCHAR(45), age INT(10))");
    }

    @Override
    public void dropUsersTable() {
        tableQuery("DROP TABLE IF EXISTS User");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        tableQuery("DELETE FROM User WHERE id=" + id);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM User");
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        tableQuery("DELETE FROM User");
    }
}
