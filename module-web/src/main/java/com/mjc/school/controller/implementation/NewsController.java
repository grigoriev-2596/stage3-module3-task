package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.implementation.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NewsController implements BaseController<NewsDtoRequest, NewsDtoResponse, Long> {
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    public List<NewsDtoResponse> readAll() {
        return newsService.readAll();
    }

    @Override
    public NewsDtoResponse readById(Long id) {
        return newsService.readById(id);
    }

    @Override
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        return newsService.create(createRequest);
    }

    @Override
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        return newsService.update(updateRequest);
    }

    @Override
    public boolean deleteById(Long id) {
        return newsService.deleteById(id);
    }

    public List<NewsDtoResponse> getNewsByCriteria(List<String> tagNames, List<Long> tagIds, String authorName,
                                                   String title, String content) {
        return newsService.getNewsByCriteria(tagNames, tagIds, authorName, title, content);
    }

}
