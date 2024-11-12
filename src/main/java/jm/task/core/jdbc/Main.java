package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();

        userService.tableExist();
        userService.createUsersTable();
        userService.tableExist();
        userService.createUsersTable();
        userService.tableExist();

        userService.saveUser("Oleg", "Pinkov", (byte) 57);
        userService.saveUser("Alex", "Newman", (byte) 33);
        userService.saveUser("John", "Goodman", (byte) 44);
        userService.saveUser("Stepan", "Percev", (byte) 99);

        List<User> usersList = userService.getAllUsers();
        for (User user : usersList) {
            System.out.println(user);
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();
        userService.dropUsersTable();
    }
}
