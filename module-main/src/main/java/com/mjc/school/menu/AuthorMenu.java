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

        Object result = commandFactory
                .getCreateAuthorCommand(name)
                .execute();
        System.out.println(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getAll() {
        Object result = commandFactory
                .getReadAllAuthorsCommand()
                .execute();
        ((Iterable) result).forEach(System.out::println);
    }

    @Override
    public void getById() {
        System.out.print("Enter news id:\n>>");
        long authorId = readId();

        Object result = commandFactory
                .getReadAuthorByIdCommand(authorId)
                .execute();
        System.out.println(result);
    }

    @Override
    public void update() {
        String authorName;
        long authorId;
        System.out.print("Enter author id:\n>>");
        authorId = readId();
        System.out.print("Enter author name:\n>>");
        authorName = scanner.nextLine();

        Object result = commandFactory
                .getUpdateAuthorCommand(authorId, authorName)
                .execute();
        System.out.println(result);
    }

    @Override
    public void delete() {
        System.out.print("Enter author id:\n>>");
        long authorId = readId();

        Object result = commandFactory
                .getDeleteAuthorCommand(authorId)
                .execute();
        System.out.println(result);
    }

    public void getAuthorByNewsId() {
        System.out.print("Enter news id:\n>>");
        long newsId = readId();

        Object result = commandFactory.
                getAuthorByNewsIdCommand(newsId)
                .execute();
        System.out.println(result);
    }
}
