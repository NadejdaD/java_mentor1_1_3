package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Ludovik", "Bethoven", (byte) 57);
        System.out.println("User с именем - Ludovik добавлен в базу данных");
        userService.saveUser("Iogann", "Bakh", (byte) 65);
        System.out.println("User с именем - Iogann добавлен в базу данных");
        userService.saveUser("Volvgang", "Mozart", (byte) 35);
        System.out.println("User с именем - Volvgang добавлен в базу данных");
        userService.saveUser("Rihard", "Vagner", (byte) 70);
        System.out.println("User с именем - Rihard добавлен в базу данных");

        List<User> list = userService.getAllUsers();
        for(User e: list) {
            System.out.println(e);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
