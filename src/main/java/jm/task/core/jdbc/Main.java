package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Ivan", "Ivanov", (byte) 13);
        userService.saveUser("Sasha", "Sashkova", (byte) 15);
        userService.saveUser("Sonya", "Sonechkina", (byte) 14);
        userService.saveUser("Rolls", "Roller", (byte) 16);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
