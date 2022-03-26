package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;
/*класс переиспользует методы класса UserDaoJBCLmpl*/
public class UserServiceImpl implements UserService {
    UserDaoJDBCImpl userDaoHibernate = new UserDaoJDBCImpl();

    public UserServiceImpl() {
    }

    public void createUsersTable() {
        userDaoHibernate.createUsersTable();
    }

    public void dropUsersTable() {
        userDaoHibernate.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        this.userDaoHibernate.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        userDaoHibernate.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return userDaoHibernate.getAllUsers();
    }

    public void cleanUsersTable() {
        userDaoHibernate.cleanUsersTable();
    }
}
