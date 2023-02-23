package com.mjc.school.implementation;

import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.implementation.TagRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.implementation.NewsServiceImpl;
import com.mjc.school.service.validation.NewsManagementValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {
    @Mock
    private NewsRepository newsRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private NewsMapper newsMapper;

    @Mock
    private NewsManagementValidator validator;

    private NewsServiceImpl newsService;

    private NewsModel newsModel;
    private NewsDtoRequest newsDtoRequest;

    @BeforeEach
    public void setup() {
        newsService = new NewsServiceImpl(newsRepository, authorRepository, tagRepository, validator, newsMapper);

        LocalDateTime now = LocalDateTime.now();
        TagModel tagModel = new TagModel(1L, "climate");
        AuthorModel authorModel = new AuthorModel(1L, "Grigoriev Egor", now, now);

        newsDtoRequest = new NewsDtoRequest(2L, "title", "content", authorModel.getId(), List.of(tagModel.getId()));
        newsModel = new NewsModel(newsDtoRequest.getId(), newsDtoRequest.getTitle(), newsDtoRequest.getContent(), now, now, authorModel);
        newsModel.setTags(List.of(tagModel));
    }

    @Test
    void getById() {
        given(newsRepository.readById(newsModel.getId())).willReturn(Optional.of(newsModel));
        newsService.readById(newsModel.getId());

        verify(validator, times(1)).validateId(newsModel.getId());
        verify(newsRepository, times(1)).readById(newsModel.getId());
        verify(newsMapper, times(1)).modelToDtoResponse(newsModel);
    }

    @Test
    void create() {
        given(newsMapper.dtoRequestToModel(newsDtoRequest)).willReturn(newsModel);
        given(newsRepository.create(newsModel)).willReturn(newsModel);
        given(authorRepository.existById(newsDtoRequest.getAuthorId())).willReturn(true);
        given(tagRepository.existById(any(Long.class))).willReturn(true);
        newsService.create(newsDtoRequest);

        verify(validator, times(1)).validateNewsRequestWithoutId(newsDtoRequest);
        verify(newsMapper, times(1)).dtoRequestToModel(newsDtoRequest);
        verify(newsRepository, times(1)).create(newsModel);
        verify(newsMapper, times(1)).modelToDtoResponse(newsModel);
    }

    @Test
    void getAll() {
        List<NewsModel> listOfNews = List.of(this.newsModel);
        given(newsRepository.readAll()).willReturn(listOfNews);
        newsService.readAll();

        verify(newsRepository, times(1)).readAll();
        verify(newsMapper, times(1)).listOfModelsToListOfResponses(listOfNews);
    }

    @Test
    void update() {
        given(newsMapper.dtoRequestToModel(newsDtoRequest)).willReturn(newsModel);
        given(newsRepository.update(newsModel)).willReturn(newsModel);
        given(newsRepository.existById(newsDtoRequest.getId())).willReturn(true);
        given(authorRepository.existById(newsDtoRequest.getAuthorId())).willReturn(true);
        given(tagRepository.existById(any(Long.class))).willReturn(true);
        newsService.update(newsDtoRequest);

        verify(validator, times(1)).validateNewsRequest(newsDtoRequest);
        verify(newsMapper, times(1)).dtoRequestToModel(newsDtoRequest);
        verify(newsRepository, times(1)).update(newsModel);
        verify(newsMapper, times(1)).modelToDtoResponse(newsModel);
    }

    @Test
    void delete() {
        given(newsRepository.existById(newsDtoRequest.getId())).willReturn(true);
        Long id = newsDtoRequest.getId();
        newsService.deleteById(id);

        verify(validator, times(1)).validateId(id);
        verify(newsRepository, times(1)).deleteById(id);
    }

    @Test
    void getNewsByCriteria() {
        given(newsRepository.readAll()).willReturn(List.of(newsModel));
        newsService.getNewsByCriteria(null, newsDtoRequest.getTagIds(), "Grigoriev Egor", null, "");

        verify(newsRepository, times(1)).readAll();
        verify(newsMapper, times(1)).listOfModelsToListOfResponses(List.of(newsModel));
    }

}