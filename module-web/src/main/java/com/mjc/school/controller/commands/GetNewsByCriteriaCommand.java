package com.mjc.school.controller.commands;

import com.mjc.school.controller.Command;
import com.mjc.school.controller.implementation.NewsController;

import java.util.List;

public class GetNewsByCriteriaCommand implements Command {

    private final NewsController newsController;
    private final List<String> tagNames;
    private final List<Long> tagIds;
    private final String authorName;
    private final String title;
    private final String content;

    public GetNewsByCriteriaCommand(NewsController newsController, List<String> tagNames, List<Long> tagIds,
                                    String authorName, String title, String content) {
        this.newsController = newsController;
        this.tagNames = tagNames;
        this.tagIds = tagIds;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
    }

    @Override
    public Object execute() {
        return newsController.getNewsByCriteria(tagNames, tagIds, authorName, title, content);
    }
}
