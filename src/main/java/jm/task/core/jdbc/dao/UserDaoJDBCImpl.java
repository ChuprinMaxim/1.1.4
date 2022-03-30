package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*класс выполняет функционал таблицы*/
public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    /*чтобы не дублировать запрос в каждом методе*/
    public void tableQuery(String x) {
        try (PreparedStatement query = Util.getConnection().prepareStatement(x)) {
            query.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*создание таблицы*/
    public void createUsersTable() {
        tableQuery("CREATE TABLE IF NOT EXISTS User (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), lastName VARCHAR(45), age INT)");
    }

    /*удаление таблицы*/
    public void dropUsersTable() {
        tableQuery("DROP TABLE IF EXISTS User");
    }

    /*сохранение пользователя*/
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement query = Util.getConnection().prepareStatement("INSERT INTO User (name, lastName, age) VALUES (?, ?, ?)")) {
            query.setString(1, name);
            query.setString(2, lastName);
            query.setInt(3, age);
            query.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*удаление пользователя по id*/
    public void removeUserById(long id) {
        tableQuery("DELETE From User WHERE id=" + id);
    }

    /*вывод в консоль всех пользователей*/
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (PreparedStatement query = Util.getConnection().prepareStatement("select * from user")) {
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
                System.out.println(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /*стереть таблицу*/
    public void cleanUsersTable() {
        tableQuery("TRUNCATE TABLE User");
    }
}