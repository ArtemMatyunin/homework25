package ru.inno.matyunin.dao;

import ru.inno.matyunin.pojo.Mobile;
import ru.inno.matyunin.pojo.User;

import javax.ejb.EJB;
import java.util.List;


public interface UserDao {

    int addUser(User user);

    User getUser(User user);

    User getUserById(Integer id);

    void createTable();

    User getUserByName(String name);

    boolean updateUser(User user);

    boolean deleteUserById(Integer userId);
}
