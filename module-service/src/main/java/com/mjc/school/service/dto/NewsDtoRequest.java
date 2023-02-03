package com.mjc.school.service.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsDtoRequest {
    private final Long id;
    private final String title;
    private final String content;
    private final Long authorId;
    private final List<Long> tagIds = new ArrayList<>();

    public NewsDtoRequest(Long id, String title, String content, Long authorId, List<Long> tagIds) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.tagIds.addAll(tagIds);
    }

    public NewsDtoRequest(Long id, String title, String content, Long authorId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }
}
