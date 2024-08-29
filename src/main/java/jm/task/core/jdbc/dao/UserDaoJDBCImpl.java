package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS `mydbtest`.`kata_users` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(35) NOT NULL,\n" +
            "  `last_name` VARCHAR(45) NOT NULL,\n" +
            "  `age` INT NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)";
    private static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS kata_users";
    private static final String SAVE_USER_QUERY = "INSERT INTO kata_users (name, last_name, age) VALUES (?, ?, ?)";
    private static final String REMOVE_USER_BY_ID_QUERY = "DELETE FROM kata_users WHERE id = ?";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM kata_users";
    private static final String REMOVE_ALL_USERS_QUERY = "DELETE FROM kata_users";

    private static final String ID_COLUMN_LABEL = "id";
    private static final String NAME_COLUMN_LABEL = "name";
    private static final String LAST_NAME_COLUMN_LABEL = "last_name";
    private static final String AGE_COLUMN_LABEL = "age";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        utilTableWork(CREATE_TABLE_QUERY);
    }

    public void dropUsersTable() {
        utilTableWork(DROP_TABLE_QUERY);
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnectionJDBC();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_QUERY)) {

            Class.forName(DRIVER);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);

            preparedStatement.execute();
            System.out.println("User с именем — " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnectionJDBC();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER_BY_ID_QUERY)) {

            Class.forName(DRIVER);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnectionJDBC();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_USERS_QUERY);
            while (resultSet.next()) {
                long id = resultSet.getLong(ID_COLUMN_LABEL);
                String name = resultSet.getString(NAME_COLUMN_LABEL);
                String lastName = resultSet.getString(LAST_NAME_COLUMN_LABEL);
                Byte age = resultSet.getByte(AGE_COLUMN_LABEL);

                User user = new User(name, lastName, age);
                user.setId(id);

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        utilTableWork(REMOVE_ALL_USERS_QUERY);
    }

    private static void utilTableWork(String sql) {
        try (Connection connection = Util.getConnectionJDBC();
             Statement statement = connection.createStatement()) {
            Class.forName(DRIVER);

            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
