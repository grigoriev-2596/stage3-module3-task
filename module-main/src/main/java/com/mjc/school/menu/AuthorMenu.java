package com.mjc.school.menu;

import com.mjc.school.controller.commands.handler.CommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorMenu extends Menu {

    private final CommandFactory commandFactory;

    @Autowired
    public AuthorMenu(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void create() {
        System.out.print(Constant.ENTER_AUTHOR_NAME);
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
        System.out.print(Constant.ENTER_AUTHOR_ID);
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
        System.out.print(Constant.ENTER_AUTHOR_ID);
        authorId = readId();
        System.out.print(Constant.ENTER_AUTHOR_NAME);
        authorName = scanner.nextLine();

        Object result = commandFactory
                .getUpdateAuthorCommand(authorId, authorName)
                .execute();
        System.out.println(result);
    }

    @Override
    public void delete() {
        System.out.print(Constant.ENTER_AUTHOR_ID);
        long authorId = readId();

        Object result = commandFactory
                .getDeleteAuthorCommand(authorId)
                .execute();
        System.out.println(result);
    }

    public void getAuthorByNewsId() {
        System.out.print(Constant.ENTER_NEWS_ID);
        long newsId = readId();

        Object result = commandFactory.
                getAuthorByNewsIdCommand(newsId)
                .execute();
        System.out.println(result);
    }
}
