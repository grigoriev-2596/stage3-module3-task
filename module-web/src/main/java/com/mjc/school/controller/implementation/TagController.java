package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TagController implements BaseController<TagDtoRequest, TagDtoResponse, Long> {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public List<TagDtoResponse> readAll() {
        return tagService.readAll();
    }

    @Override
    public TagDtoResponse readById(Long id) {
        return tagService.readById(id);
    }

    @Override
    public TagDtoResponse create(TagDtoRequest createRequest) {
        return tagService.create(createRequest);
    }

    @Override
    public TagDtoResponse update(TagDtoRequest updateRequest) {
        return tagService.update(updateRequest);
    }

    @Override
    public boolean deleteById(Long id) {
        return tagService.deleteById(id);
    }

    public List<TagDtoResponse> getTagByNewsId(Long id) {
        return tagService.getTagsByNewsId(id);
    }
}
