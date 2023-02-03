package com.mjc.school.menu;

import com.mjc.school.controller.commands.handler.CommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class AuthorMenu extends Menu {
    private final CommandFactory commandFactory;

    @Autowired
    public AuthorMenu(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void create() {
        System.out.print("Enter author name:\n>>");
        String name = scanner.nextLine();

        System.out.println(commandFactory
                .getCreateAuthorCommand(name)
                .execute());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getAll() {
        List<Object> authorList = Collections.unmodifiableList((List<Object>) commandFactory
                .getReadAllAuthorsCommand()
                .execute());
        authorList.forEach(System.out::println);
    }

    @Override
    public void getById() {
        System.out.print("Enter news id:\n>>");
        long authorId = readId();

        System.out.println(commandFactory
                .getReadAuthorByIdCommand(authorId)
                .execute());
    }

    @Override
    public void update() {
        String authorName;
        long authorId;
        System.out.print("Enter author id:\n>>");
        authorId = readId();
        System.out.print("Enter author name:\n>>");
        authorName = scanner.nextLine();

        System.out.println(commandFactory
                .getUpdateAuthorCommand(authorId, authorName)
                .execute());
    }

    @Override
    public void delete() {
        System.out.print("Enter author id:\n>>");
        long authorId = readId();

        System.out.println(commandFactory
                .getDeleteAuthorCommand(authorId)
                .execute());
    }

    public void getAuthorByNewsId() {
        System.out.print("Enter news id:\n>>");
        long newsId = readId();

        System.out.println(commandFactory.
                getAuthorByNewsIdCommand(newsId)
                .execute());
    }
}
