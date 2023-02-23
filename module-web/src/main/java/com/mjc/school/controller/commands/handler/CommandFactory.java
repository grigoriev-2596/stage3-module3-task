package com.mjc.school.controller.commands.handler;

import com.mjc.school.controller.Command;
import com.mjc.school.controller.commands.*;
import com.mjc.school.controller.implementation.AuthorController;
import com.mjc.school.controller.implementation.NewsController;
import com.mjc.school.controller.implementation.TagController;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.TagDtoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandFactory {

    private final NewsController newsController;
    private final AuthorController authorController;
    private final TagController tagController;

    @Autowired
    public CommandFactory(NewsController newsController, AuthorController authorController, TagController tagController) {
        this.newsController = newsController;
        this.authorController = authorController;
        this.tagController = tagController;
    }

    public Command getCreateNewsCommand(String title, String content, Long authorId, List<Long> tagIds) {
        return new CreateCommand<>(newsController, new NewsDtoRequest(null, title, content, authorId, tagIds));
    }

    public Command getReadAllNewsCommand() {
        return new ReadAllCommand<>(newsController);
    }

    public Command getReadNewsByIdCommand(Long id) {
        return new GetByIdCommand<>(newsController, id);
    }

    public Command getUpdateNewsCommand(Long id, String name, String content, Long authorId, List<Long> tagIds) {
        return new UpdateCommand<>(newsController, new NewsDtoRequest(id, name, content, authorId, tagIds));
    }

    public Command getDeleteNewsCommand(Long id) {
        return new DeleteCommand<>(newsController, id);
    }

    public Command getNewsByCriteriaCommand(List<String> tagNames, List<Long> tagIds,
                                            String authorName, String title, String content) {
        return new GetNewsByCriteriaCommand(newsController, tagNames, tagIds, authorName, title, content);
    }


    public Command getCreateAuthorCommand(String name) {
        return new CreateCommand<>(authorController, new AuthorDtoRequest(null, name));
    }

    public Command getReadAllAuthorsCommand() {
        return new ReadAllCommand<>(authorController);
    }

    public Command getReadAuthorByIdCommand(Long id) {
        return new GetByIdCommand<>(authorController, id);
    }

    public Command getUpdateAuthorCommand(Long id, String name) {
        return new UpdateCommand<>(authorController, new AuthorDtoRequest(id, name));
    }

    public Command getDeleteAuthorCommand(Long id) {
        return new DeleteCommand<>(authorController, id);
    }

    public Command getAuthorByNewsIdCommand(Long id) {
        return new GetAuthorByNewsIdCommand(authorController, id);
    }


    public Command getCreateTagCommand(String name) {
        return new CreateCommand<>(tagController, new TagDtoRequest(null, name));
    }

    public Command getReadAllTagsCommand() {
        return new ReadAllCommand<>(tagController);
    }

    public Command getReadTagByIdCommand(Long id) {
        return new GetByIdCommand<>(tagController, id);
    }

    public Command getUpdateTagCommand(Long id, String name) {
        return new UpdateCommand<>(tagController, new TagDtoRequest(id, name));
    }

    public Command getDeleteTagCommand(Long id) {
        return new DeleteCommand<>(tagController, id);
    }

    public Command getTagsByNewsIdCommand(Long id) {
        return new GetTagsByNewsIdCommand(tagController, id);
    }
}
