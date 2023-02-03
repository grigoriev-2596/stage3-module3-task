package com.mjc.school.implementation;

import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.implementation.TagRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.NewsMapper;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.implementation.NewsService;
import com.mjc.school.service.validation.NewsManagementValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    private TagRepository tagsRepository;
    @Mock
    private NewsMapper newsMapper;
    @Mock
    private NewsManagementValidator validator;

    @InjectMocks
    private NewsService newsService;

    private NewsModel newsModel;
    private NewsDtoRequest newsDtoRequest;
    private AuthorModel authorModel;
    private TagModel tagModel;

    @BeforeEach
    public void setup() {
        LocalDateTime now = LocalDateTime.now();
        tagModel = new TagModel(1L, "climate");
        authorModel = new AuthorModel(1L, "Grigoriev Egor", now, now);

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
        given(authorRepository.readById(newsDtoRequest.getAuthorId())).willReturn(Optional.of(authorModel));
        given(tagsRepository.readById(any(Long.class))).willReturn(Optional.of(tagModel));
        given(newsRepository.create(newsModel)).willReturn(newsModel);
        newsService.create(newsDtoRequest);

        verify(validator, times(1)).validateNewsRequest(newsDtoRequest);
        verify(validator, atLeast(2)).validateId(any(Long.class));
        verify(newsMapper, times(1)).dtoRequestToModel(newsDtoRequest);
        verify(newsMapper, times(1)).modelToDtoResponse(newsModel);
        verify(newsRepository, times(1)).create(newsModel);
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
        given(authorRepository.readById(newsDtoRequest.getAuthorId())).willReturn(Optional.of(authorModel));
        given(tagsRepository.readById(any(Long.class))).willReturn(Optional.of(tagModel));
        newsService.update(newsDtoRequest);

        verify(validator, times(1)).validateNewsRequest(newsDtoRequest);
        verify(validator, atLeast(2)).validateId(any(Long.class));
        verify(newsMapper, times(1)).dtoRequestToModel(newsDtoRequest);
        verify(newsMapper, times(1)).modelToDtoResponse(newsModel);
        verify(newsRepository, times(1)).update(newsModel);
    }

    @Test
    void delete() {
        Long id = newsDtoRequest.getId();
        newsService.deleteById(id);

        verify(validator, times(1)).validateId(id);
        verify(newsRepository,times(1)).deleteById(id);
    }

    @Test
    void getNewsByCriteria() {
        given(newsRepository.readAll()).willReturn(List.of(newsModel));
        newsService.getNewsByCriteria(null, newsDtoRequest.getTagIds(), "Grigoriev Egor", null, "");

        verify(newsRepository, times(1)).readAll();
        verify(newsMapper, times(1)).listOfModelsToListOfResponses(List.of(newsModel));
    }

}