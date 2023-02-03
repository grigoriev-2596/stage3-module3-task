package com.mjc.school.repository.model;

import com.mjc.school.repository.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "news")
public class NewsModel implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_generator")
    @SequenceGenerator(name = "news_generator", sequenceName = "news_seq", allocationSize = 1)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorModel author;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="news_tags",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<TagModel> tags = new ArrayList<>();

    public NewsModel(Long id, String title, String content, LocalDateTime creationDate, LocalDateTime lastUpdateDate, AuthorModel author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.author = author;
    }

    public NewsModel() {

    }

    @PrePersist
    public void setDates() {
        LocalDateTime now = LocalDateTime.now();
        setCreationDate(now);
        setLastUpdateDate(now);
    }

    @PreUpdate
    public void setLastUpdateDate() {
        setLastUpdateDate(LocalDateTime.now());
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public AuthorModel getAuthor() {
        return author;
    }

    public void setAuthor(AuthorModel author) {
        this.author = author;
    }

    public List<TagModel> getTags() {
        return tags;
    }

    public void setTags(List<TagModel> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsModel newsModel = (NewsModel) o;
        return Objects.equals(id, newsModel.id) && Objects.equals(title, newsModel.title) && Objects.equals(content, newsModel.content) && Objects.equals(creationDate, newsModel.creationDate) && Objects.equals(lastUpdateDate, newsModel.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, creationDate, lastUpdateDate);
    }

    @Override
    public String toString() {
        return "NewsModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creationDate=" + creationDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
