package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    UserDao userDao = getUserDao(true);

    private UserDao getUserDao(boolean isHibernate) {
        if (isHibernate) {
            return new UserDaoHibernateImpl();
        } else {
            return new UserDaoJDBCImpl();
        }
    }

    public void createUsersTable() {
        userDao.createUsersTable();
        System.out.println("Users table created");
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
        System.out.println("Users table dropped");
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
        System.out.println("User " + name + " saved");
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
        System.out.println("User " + id + " removed");
    }

    public List<User> getAllUsers() {
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        return users;
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
        System.out.println("Users table cleaned");

    }
}
