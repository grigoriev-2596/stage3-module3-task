package com.mjc.school.menu;

import com.mjc.school.controller.commands.handler.CommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TagMenu extends Menu {
    private final CommandFactory commandFactory;

    @Autowired
    public TagMenu(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void create() {
        System.out.print("Enter tag name:\n>>");
        String name = scanner.nextLine();

        Object result = commandFactory.
                getCreateTagCommand(name)
                .execute();
        System.out.println(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getAll() {
        Object result = commandFactory.
                getReadAllTagsCommand().
                execute();
        ((Iterable) result).forEach(System.out::println);
    }

    @Override
    public void getById() {
        System.out.print("Enter tag id:\n>>");
        long id = readId();

        Object result = commandFactory.
                getReadTagByIdCommand(id).
                execute();
        System.out.println(result);
    }

    @Override
    public void update() {
        String name;
        long id;
        System.out.print("Enter tag id:\n>>");
        id = readId();
        System.out.print("Enter tag name:\n>>");
        name = scanner.nextLine();

        Object result = commandFactory.
                getUpdateTagCommand(id, name).
                execute();
        System.out.println(result);
    }

    @Override
    public void delete() {
        System.out.print("Enter tag id:\n>>");
        long id = readId();

        Object result = commandFactory.
                getDeleteTagCommand(id).
                execute();
        System.out.println(result);
    }

    public void getTagsByNewsId() {
        System.out.print("Enter news id:\n>>");
        long newsId = readId();

        Object result = commandFactory.
                getTagsByNewsIdCommand(newsId)
                .execute();
        System.out.println(result);
    }
}
