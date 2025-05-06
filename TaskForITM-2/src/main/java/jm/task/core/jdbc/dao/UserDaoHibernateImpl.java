package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getInstance().getSessionFactory();

    public UserDaoHibernateImpl() {


    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (id BIGSERIAL PRIMARY KEY," +
                "name VARCHAR(255)," +
                "last_name VARCHAR(255)," +
                "age INT)";
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            System.out.println("таблица созданна с помощью Hibernate");
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка при создании таблицы");
        }

    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            System.out.println("таблица удалена с помощью Hibernate");
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка при удалении таблицы");
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        if (name == null || lastName == null || age < 0) {
            throw new IllegalArgumentException("Некорректные входящие данные. Возраст не можеь быть меньше нуля," +
                    " а имя и фамилия не олжны быть пустыми");
        }
        User user = new User(name, lastName, age);
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(user);
            tx.commit();
            System.out.println("пользователь с именем " + name + " успешно добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка при добавлении пользователя");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ошибка при удалении пользователя");
        }

    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User");
            return query.list();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(" Ошибка при получении списка пользователей");
        }
     return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createSQLQuery("DELETE FROM users").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ошибка при очистки таблицы");
        }

    }
}
