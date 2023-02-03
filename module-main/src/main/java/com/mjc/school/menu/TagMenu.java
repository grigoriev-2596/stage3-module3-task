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
        System.out.println(commandFactory.
                getCreateTagCommand(name)
                .execute());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getAll() {
        List<Object> tagsList = Collections.unmodifiableList((List<Object>) commandFactory.
                getReadAllTagsCommand().
                execute());
        tagsList.forEach(System.out::println);
    }

    @Override
    public void getById() {
        System.out.print("Enter tag id:\n>>");
        long id = readId();

        System.out.println(commandFactory.
                getReadTagByIdCommand(id).
                execute());
    }

    @Override
    public void update() {
        String name;
        long id;
        System.out.print("Enter tag id:\n>>");
        id = readId();
        System.out.print("Enter tag name:\n>>");
        name = scanner.nextLine();

        System.out.println(commandFactory.
                getUpdateTagCommand(id, name).
                execute());
    }

    @Override
    public void delete() {
        System.out.print("Enter tag id:\n>>");
        long id = readId();

        System.out.println(commandFactory.
                getDeleteTagCommand(id).
                execute());
    }

    public void getTagsByNewsId() {
        System.out.print("Enter news id:\n>>");
        long newsId = readId();

        System.out.println(commandFactory.
                getTagsByNewsIdCommand(newsId)
                .execute());
    }
}
