package com.mjc.school.menu;

import com.mjc.school.controller.commands.handler.CommandConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static java.lang.System.exit;

@Component
public class MenuRunner {
    private final TagMenu tagMenu;
    private final NewsMenu newsMenu;
    private final AuthorMenu authorMenu;

    private Scanner scanner;

    @Autowired
    public MenuRunner(TagMenu tagMenu, NewsMenu newsMenu, AuthorMenu authorMenu) {
        this.tagMenu = tagMenu;
        this.newsMenu = newsMenu;
        this.authorMenu = authorMenu;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
        authorMenu.setScanner(scanner);
        tagMenu.setScanner(scanner);
        newsMenu.setScanner(scanner);
    }

    private void printMenu() {
        CommandConstants[] commands = CommandConstants.values();
        for (CommandConstants c : commands) {
            System.out.println(c.getId() + " - " + c.getName());
        }
    }

    public void runMenu() {
        if (scanner == null) {
            throw new NullPointerException("Scanner must be initialized");
        }
        while (true) {
            try {
                printMenu();
                System.out.print(Constant.ENTER_NUMBER_OF_OPERATION);
                int commandId = readCommandId();
                CommandConstants commandConstant = CommandConstants.valueOf(commandId);
                System.out.println(Constant.OPERATION + commandConstant.getName());
                switch (commandConstant) {
                    case EXIT -> exit(0);
                    case CREATE_NEWS -> newsMenu.create();
                    case GET_ALL_NEWS -> newsMenu.getAll();
                    case GET_NEWS_BY_ID -> newsMenu.getById();
                    case UPDATE_NEWS -> newsMenu.update();
                    case DELETE_NEWS -> newsMenu.delete();
                    case CREATE_AUTHOR -> authorMenu.create();
                    case GET_ALL_AUTHORS -> authorMenu.getAll();
                    case GET_AUTHOR_BY_ID -> authorMenu.getById();
                    case UPDATE_AUTHOR -> authorMenu.update();
                    case DELETE_AUTHOR -> authorMenu.delete();
                    case CREATE_TAG -> tagMenu.create();
                    case GET_ALL_TAGS -> tagMenu.getAll();
                    case GET_TAG_BY_ID -> tagMenu.getById();
                    case UPDATE_TAG -> tagMenu.update();
                    case DELETE_TAG -> tagMenu.delete();
                    case GET_TAGS_BY_NEWS_ID -> tagMenu.getTagsByNewsId();
                    case GET_AUTHOR_BY_NEWS_ID -> authorMenu.getAuthorByNewsId();
                    case GET_NEWS_BY_CRITERIA -> newsMenu.getNewsByCriteria();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

    private int readCommandId() {
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            throw new IllegalArgumentException("Operation number must be an integer");
        }
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }

}
