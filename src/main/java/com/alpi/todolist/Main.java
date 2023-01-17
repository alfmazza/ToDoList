package com.alpi.todolist;

import com.alpi.todolist.gui.Login;

public class Main {

    public static void main(String[] args) {
       
        Login login = new Login();
        login.setVisible(true);
        login.setLocationRelativeTo(null);
        login.setResizable(false);
    }
}
