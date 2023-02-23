package com.mjc.school.service.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NewsDtoResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime creationDate;
    private final LocalDateTime lastUpdateDate;
    private final Long authorId;
    private final List<Long> tagIds = new ArrayList<>();

    public NewsDtoResponse(Long id, String title, String content, LocalDateTime creationDate,
                           LocalDateTime lastUpdateDate, Long authorId, List<Long> tagIds) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.authorId = authorId;
        this.tagIds.addAll(tagIds);
    }

    public NewsDtoResponse(Long id, String title, String content, LocalDateTime creationDate,
                           LocalDateTime lastUpdateDate, Long authorId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    @Override
    public String toString() {
        return "NewsDtoResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creationDate=" + creationDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", authorId=" + authorId +
                ", tagIds=" + tagIds +
                '}';
    }
}
