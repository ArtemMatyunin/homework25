package ru.inno.matyunin.dao;

import ru.inno.matyunin.pojo.Mobile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@EJB
public class MobileDaoJdbcImpl implements MobileDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MobileDaoJdbcImpl.class);

    @Resource(name = "jdbc/postgres")
    private DataSource ds;

    private static final String INSERT_INTO_MOBILE = "INSERT INTO mobile values (DEFAULT, ?, ?, ?)";
    private static final String SELECT_FROM_MOBILE = "SELECT * FROM mobile WHERE id = ?";
    private static final String SELECT_ALL_FROM_MOBILE = "SELECT * FROM mobile ORDER BY id";
    private static final String UPDATE_MOBILE = "UPDATE mobile SET (model, price, manufacturer)=(?,?,?) WHERE id=?";
    private static final String DELETE_MOBILE = "DELETE FROM mobile WHERE id=?";

    public static final String CREATE_TABLE_MOBILE
            = "DROP TABLE IF EXISTS mobile;\n"
            + "create table mobile\n"
            + "(\n"
            + "    id bigserial not null\n"
            + "        constraint mobile_pkey\n"
            + "            primary key,\n"
            + "    model varchar(100) not null,\n"
            + "    price integer not null,\n"
            + "    manufacturer varchar(100) not null\n"
            + ");\n"
            + "\n"
            + "alter table mobile owner to postgres;";


    @Override
    public boolean addMobile(Mobile mobile) {
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_MOBILE)) {
            preparedStatement.setString(1, mobile.getModel());
            preparedStatement.setInt(2, mobile.getPrice());
            preparedStatement.setString(3, mobile.getManufacturer());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("Some thing wrong in addMobile method", e);
            return false;
        }
        return true;
    }

    @Override
    public Mobile getMobileById(Integer id) {
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_MOBILE)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Mobile(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
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
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_MOBILE)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("Some thing wrong in createTable method", e);
        }
    }


    @Override
    public List<Mobile> getAllMobile() {
        List<Mobile> lstmb = new ArrayList<>();
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FROM_MOBILE);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                lstmb.add(new Mobile(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4)));
            }
            return lstmb;
        } catch (SQLException e) {
            LOGGER.error("Some thing wrong in getMobileById method", e);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean updateMobile(Mobile mobile) {
        if (mobile == null) {
            return false;
        }
        {
            try (Connection connection = ds.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MOBILE)) {
                preparedStatement.setString(1, mobile.getModel());
                preparedStatement.setInt(2, mobile.getPrice());
                preparedStatement.setString(3, mobile.getManufacturer());
                preparedStatement.setInt(4, mobile.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Some thing wrong in addMobile method", e);
                return false;
            }

            return true;
        }
    }

    @Override
    public boolean deleteMobileById(Integer id) {
        if (id == null) {
            return false;
        }
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MOBILE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Some thing wrong in addMobile method", e);
            return false;
        }

        return true;
    }

}
