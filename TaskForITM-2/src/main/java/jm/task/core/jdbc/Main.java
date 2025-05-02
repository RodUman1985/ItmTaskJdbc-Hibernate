package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
       userService.cleanUsersTable();
        userService.saveUser("Корвус", "Коракс", (byte) 210);
        userService.saveUser("Рогал", "Дорн", (byte) 210);
        userService.saveUser("Кайафас", "Каин", (byte) 110);
        userService.saveUser("Ферик", "Юрген", (byte) 100);
        userService.getAllUsers();
        userService.removeUserById(2);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
