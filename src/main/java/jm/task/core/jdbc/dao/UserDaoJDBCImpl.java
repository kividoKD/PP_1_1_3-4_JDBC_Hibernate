package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Util util = new Util();
    private String sql;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        sql = "CREATE TABLE users(" +
                "id BIGINT PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(45)," +
                "last_name VARCHAR(45)," +
                "age SMALLINT(3))";

        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            if (!tableExist()) {
                statement.executeUpdate(sql);
                System.out.println("Table Created");
            } else {
                System.out.println("Table 'users' already exists");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        sql = "DROP TABLE users";

        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (tableExist()) {
                preparedStatement.executeUpdate();
                System.out.println("Table Deleted");
            } else {
                System.out.println("Unknown table 'mydb.users'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        sql = "INSERT INTO users (name, last_name, age) VALUES(?, ?, ?)";

        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User Added");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.printf("User id = %d Deleted", id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        sql = "SELECT name, last_name, age FROM users";
        List<User> userList = new ArrayList<>();

        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        sql = "DELETE FROM users";

        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Table Cleaned");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public boolean tableExist() {
        try (Connection connection = util.getConnection()) {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables("mydb", null, "users", null);
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
