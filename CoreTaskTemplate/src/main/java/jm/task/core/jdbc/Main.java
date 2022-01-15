package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
    
        userService.createUsersTable();
    
        userService.saveUser("raz", "razov", (byte) 15);
        userService.saveUser("dva", "dvavov", (byte) 20);
        userService.saveUser("tri", "trivov", (byte) 25);
        userService.saveUser("chetire", "chetirev", (byte) 30);
    
       userService.getAllUsers();
    
       userService.cleanUsersTable();
       userService.dropUsersTable();
    }
}
