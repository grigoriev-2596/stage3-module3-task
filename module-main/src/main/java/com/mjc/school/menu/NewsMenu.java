package com.mjc.school.menu;

import com.mjc.school.controller.commands.handler.CommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
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
        System.out.print(Constant.ENTER_NEWS_TITLE);
        title = scanner.nextLine();
        System.out.print(Constant.ENTER_NEWS_CONTENT);
        content = scanner.nextLine();
        System.out.print(Constant.ENTER_AUTHOR_ID);
        authorId = readId();
        System.out.println(Constant.ENTER_TAG_IDS);
        tagIds = readTagIds();

        Object result = commandFactory
                .getCreateNewsCommand(title, content, authorId, tagIds)
                .execute();
        System.out.println(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getAll() {
        Object result = commandFactory
                .getReadAllNewsCommand()
                .execute();
        ((Iterable) result).forEach(System.out::println);
    }

    @Override
    public void getById() {
        System.out.print(Constant.ENTER_NEWS_ID);
        long newsId = readId();

        Object result = commandFactory
                .getReadNewsByIdCommand(newsId)
                .execute();
        System.out.println(result);
    }

    @Override
    public void update() {
        String title, content;
        long authorId, newsId;
        List<Long> tagIds;
        System.out.print(Constant.ENTER_NEWS_ID);
        newsId = readId();
        System.out.print(Constant.ENTER_NEWS_TITLE);
        title = scanner.nextLine();
        System.out.print(Constant.ENTER_NEWS_CONTENT);
        content = scanner.nextLine();
        System.out.print(Constant.ENTER_AUTHOR_ID);
        authorId = readId();
        System.out.println(Constant.ENTER_TAG_IDS);
        tagIds = readTagIds();

        Object result = commandFactory
                .getUpdateNewsCommand(newsId, title, content, authorId, tagIds)
                .execute();
        System.out.println(result);
    }

    @Override
    public void delete() {
        System.out.print(Constant.ENTER_NEWS_ID);
        long newsId = readId();

        Object result = commandFactory
                .getDeleteNewsCommand(newsId)
                .execute();
        System.out.println(result);
    }

    @SuppressWarnings("unchecked")
    public void getNewsByCriteria() {
        String title, content, authorName;
        List<String> tagNames;
        List<Long> tagIds;
        System.out.print(Constant.ENTER_TAG_IDS);
        tagIds = readTagIds();
        System.out.print(Constant.ENTER_TAG_NAMES);
        tagNames = readStrings();
        System.out.print(Constant.ENTER_NEWS_TITLE);
        title = scanner.nextLine();
        System.out.print(Constant.ENTER_AUTHOR_NAME);
        authorName = scanner.nextLine();
        System.out.print(Constant.ENTER_NEWS_CONTENT);
        content = scanner.nextLine();

        Object result = commandFactory
                .getNewsByCriteriaCommand(tagNames, tagIds, authorName, title, content)
                .execute();
        ((Iterable) result).forEach(System.out::println);
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
