package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/java_mentor?useSSL=false&serverTimezone=UTC";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable()  {
        final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS users" +
                " (user_id BIGINT NOT NULL AUTO_INCREMENT," +
                " user_firstName VARCHAR(30)," +
                " user_lastName VARCHAR(30)," +
                " user_age TINYINT(125) UNSIGNED NULL," +
                " PRIMARY KEY (user_id));";

        try (Connection conn = new Util().getConnection(URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement()) {
                statement.executeUpdate(CREATE_TABLE_USERS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        final String DROP_TABLE_USERS = "DROP TABLE IF EXISTS java_mentor.users";

        try (Connection conn = new Util().getConnection(URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement()) {
            statement.executeUpdate(DROP_TABLE_USERS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String SAVE_USER = "insert into java_mentor.users (user_firstName, user_lastName, user_age) values (?, ?, ?)";

        try (Connection conn = new Util().getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement prSt = conn.prepareStatement(SAVE_USER)) {
             prSt.setString(1, name);
             prSt.setString(2, lastName);
             prSt.setByte(3, age);
             prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        final String REMOVE_USER_BY_ID = "DELETE FROM  java_mentor.users WHERE user_id = ?";

        try (Connection conn = new Util().getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement prSt = conn.prepareStatement(REMOVE_USER_BY_ID)) {
            prSt.setLong(1, id);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        final String GET_ALL_USERS = "SELECT * FROM java_mentor.users";
        List<User> list = new ArrayList<User>();

        try (Connection conn = new Util().getConnection(URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement()) {
            ResultSet resSet = statement.executeQuery(GET_ALL_USERS);
            while(resSet.next()) {
                Long id;
                String name;
                String lastName;
                byte age;

                id = resSet.getLong("user_id");
                name = resSet.getString("user_firstName");
                lastName = resSet.getString("user_lastName");
                age = resSet.getByte("user_age");
                User user = new User(name, lastName, age);
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        final String CLEAN_USERS_TABLE = "DELETE FROM java_mentor.users";

        try(Connection conn = new Util().getConnection(URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement()) {
                statement.executeUpdate(CLEAN_USERS_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
