package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Корвус", "Коракс", (byte) 120);
        userService.saveUser("Рогал", "Дорн", (byte) 120);
        userService.saveUser("Кайафас", "Каин", (byte) 105);
        userService.saveUser("Ферик", "Юрген", (byte) 85);
        userService.getAllUsers();
        userService.removeUserById(2);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
