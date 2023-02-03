package com.mjc.school.service.dto;

public class TagDtoRequest {
    private final Long id;
    private final String name;

    public TagDtoRequest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
