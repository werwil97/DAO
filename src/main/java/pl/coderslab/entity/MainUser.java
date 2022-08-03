package pl.coderslab.entity;

import java.sql.SQLException;

public class MainUser {
    public static void main(String[] args) throws SQLException {
        User user = new User();
        user.setUserName("Kacper");
        user.setEmail("utrata@coderslab.pl");
        user.setPassword("kdjddd");
        user.setId(3);
        UserDao userDao = new UserDao();
        userDao.create(user);
//        userDao.select(5);
//        userDao.update(user);
//        userDao.selectAll();
//        userDao.delete(2);

    }
}
