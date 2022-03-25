package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*класс выполняет функционал таблицы*/
public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    /*чтобы не дублировать коннект в каждом методе*/
    public void tableConnection(String x) {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
             statement.executeUpdate(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*создание таблицы*/
    public void createUsersTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS User (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), lastName VARCHAR(45), age INT)";
        tableConnection(createTable);
    }

    /*удаление таблицы*/
    public void dropUsersTable() {
        String dropTable = "DROP TABLE IF EXISTS User";
        tableConnection(dropTable);
    }

    /*сохранение пользователя*/
    public void saveUser(String name, String lastName, byte age) {
        String saveUser = "INSERT INTO User (name, lastName, age) VALUES (' " + name + "', '" + lastName + "', " + age + ")";
        tableConnection(saveUser);
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    /*удаление пользователя по id*/
    public void removeUserById(long id) {
        String removeUser = "DELETE From User WHERE id=" + id;
        tableConnection(removeUser);
    }

    /*вывод в консоль всех пользователей*/
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
             ResultSet resultSet = statement.executeQuery("select * from user");
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
        String cleanTable = "TRUNCATE TABLE User";
        tableConnection(cleanTable);
    }
}
