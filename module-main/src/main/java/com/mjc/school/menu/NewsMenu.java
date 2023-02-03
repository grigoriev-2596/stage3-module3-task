package com.mjc.school.menu;

import com.mjc.school.controller.commands.handler.CommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class NewsMenu extends Menu {
    private final CommandFactory commandFactory;

    @Autowired
    public NewsMenu(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void create() {
        String title, content;
        long authorId;
        List<Long> tagIds;
        System.out.print("Enter news title:\n>>");
        title = scanner.nextLine();
        System.out.print("Enter news content:\n>>");
        content = scanner.nextLine();
        System.out.print("Enter author id:\n>>");
        authorId = readId();
        System.out.println("Enter tag ids (example: 1 3 7; can be empty):");
        tagIds = readTagIds();
        System.out.println(commandFactory
                .getCreateNewsCommand(title, content, authorId, tagIds)
                .execute());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getAll() {
        List<Object> newsList = Collections.unmodifiableList((List<Object>) commandFactory
                .getReadAllNewsCommand()
                .execute());
        newsList.forEach(System.out::println);
    }

    @Override
    public void getById() {
        System.out.print("Enter news id:\n>>");
        long newsId = readId();

        System.out.println(commandFactory
                .getReadNewsByIdCommand(newsId)
                .execute());
    }

    @Override
    public void update() {
        String title, content;
        long authorId, newsId;
        List<Long> tagIds;
        System.out.print("Enter news id:\n>>");
        newsId = readId();
        System.out.print("Enter news title:\n>>");
        title = scanner.nextLine();
        System.out.print("Enter news content:\n>>");
        content = scanner.nextLine();
        System.out.print("Enter author id:\n>>");
        authorId = readId();
        System.out.println("Enter tag ids (example: 1 3 7; can be empty):");
        tagIds = readTagIds();
        System.out.println(commandFactory
                .getUpdateNewsCommand(newsId, title, content, authorId, tagIds)
                .execute());
    }

    @Override
    public void delete() {
        System.out.print("Enter news id:\n>>");
        long newsId = readId();

        System.out.println(commandFactory
                .getDeleteNewsCommand(newsId)
                .execute());
    }

    public void getNewsByCriteria() {
        String title, content, authorName;
        List<String> tagNames;
        List<Long> tagIds;
        System.out.print("Enter tag ids (example: 1 3 7):\n>>");
        tagIds = readTagIds();
        System.out.print("Enter tag names (example: weather politics films)\n>>");
        tagNames = readStrings();
        System.out.print("Enter news title:\n>>");
        title = scanner.nextLine();
        System.out.print("Enter author name:\n>>");
        authorName = scanner.nextLine();
        System.out.print("Enter news content:\n>>");
        content = scanner.nextLine();

        System.out.println(commandFactory
                .getNewsByCriteriaCommand(tagNames, tagIds, authorName, title, content)
                .execute());
    }

    private List<Long> readTagIds() {
        try {
            return readStrings().stream().map(Long::parseLong).toList();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Tag id must be an integer");
        }
    }

    private List<String> readStrings() {
        String input = scanner.nextLine();
        if (input.isBlank()) {
            return new ArrayList<>();
        }
        return Arrays.stream(input.split(" ")).toList();
    }
}
