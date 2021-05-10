package ru.inno.matyunin.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inno.matyunin.pojo.User;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import static java.sql.Statement.RETURN_GENERATED_KEYS;

@EJB
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @Resource(name = "jdbc/postgres")
    private DataSource ds;

    private static final String INSERT_INTO_USERS = "INSERT INTO users (user_name, user_pass, user_prompt) values (?, ?, ?)";
    private static final String SELECT_FROM_USERS = "SELECT * FROM users WHERE user_name = ? and user_pass = ?";
    private static final String SELECT_FROM_USERS_BY_ID = "SELECT * FROM users WHERE user_id = ?";
    private static final String SELECT_USER_BY_NAME = "SELECT * FROM users WHERE user_name = ?";
    private static final String UPDATE_USER = "UPDATE users SET (user_name, user_pass, user_prompt)=(?,?,?) WHERE user_id=?";
    private static final String DELETE_USER = "DELETE FROM users WHERE user_id=?";

    public static final String CREATE_TABLE_USERS
            = "DROP TABLE if exists users;" +
            " create table users\n" +
            "(\n" +
            "    user_id     serial      not null\n" +
            "        constraint users_pk\n" +
            "            primary key,\n" +
            "    user_name   varchar(10) not null,\n" +
            "    user_pass   varchar(10),\n" +
            "    user_prompt text\n" +
            ");\n" +
            "\n" +
            "alter table users\n" +
            "    owner to postgres;\n" +
            "\n" +
            "create unique index users_user_id_uindex\n" +
            "    on users (user_id);\n" +
            "\n" +
            "create unique index users_user_name_uindex\n" +
            "    on users (user_name);";


    @Override
    public int addUser(User user) {
        int result = 0;
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_USERS, RETURN_GENERATED_KEYS);

        ) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPass());
            preparedStatement.setString(3, user.getPrompt());
            int add = preparedStatement.executeUpdate();
            if (add == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet!=null&&resultSet.next()) {
                    result = resultSet.getInt(1);
                    resultSet.close();
                }

            }

        } catch (SQLException e) {
            LOGGER.error("Not added {}", e.getMessage());
        }
        return result;
    }

    @Override
    public User getUser(User user) {
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_USERS)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPass());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            "",
                            "");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Some thing wrong in getMobileById method", e);
        }
        return null;
    }

    @Override
    public User getUserById(Integer id) {
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_USERS_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Some thing wrong in getMobileById method", e);
        }
        return null;
    }

    @Override
    public void createTable() {
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_USERS)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("Some thing wrong in createTable method", e);
        }
    }

    @Override
    public User getUserByName(String name) {
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_USERS_BY_ID)) {
            preparedStatement.setString(2, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Some thing wrong in getMobileById method", e);
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        if (user == null) {
            return false;
        }
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPass());
            preparedStatement.setString(3, user.getPrompt());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Some thing wrong in addUser method", e);
            return false;
        }

        return true;

    }

    @Override
    public boolean deleteUserById(Integer userId) {
        if (userId == null) {
            return false;
        }
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Some thing wrong in addMobile method", e);
            return false;
        }

        return true;
    }
}
