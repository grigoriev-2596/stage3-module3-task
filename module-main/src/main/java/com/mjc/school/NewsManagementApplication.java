package com.mjc.school;


import com.mjc.school.menu.NewsManagementMenu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class NewsManagementApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(NewsManagementApplication.class, args);
        NewsManagementMenu menu = context.getBean(NewsManagementMenu.class);
        menu.setScanner(new Scanner(System.in));
        menu.runMenu();
    }


}

