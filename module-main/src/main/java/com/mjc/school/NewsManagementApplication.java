package com.mjc.school;


import com.mjc.school.menu.MenuRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Scanner;

@SpringBootApplication
public class NewsManagementApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(NewsManagementApplication.class, args);
        MenuRunner menu = context.getBean(MenuRunner.class);
        menu.setScanner(new Scanner(System.in));
        menu.runMenu();
    }


}

