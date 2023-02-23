package com.mjc.school.service.dto;

public class AuthorDtoRequest {

    private final Long id;
    private final String name;

    public AuthorDtoRequest(Long id, String name) {
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
