package com.mjc.school.implementation;

import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.implementation.TagRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.mapper.TagMapper;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.implementation.TagServiceImpl;
import com.mjc.school.service.validation.NewsManagementValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    @Mock
    TagRepository tagRepository;

    @Mock
    NewsRepository newsRepository;

    @Mock
    TagMapper tagMapper;

    @Mock
    NewsManagementValidator validator;

    TagServiceImpl tagService;

    private TagDtoRequest tagDtoRequest;
    private TagModel tagModel;
    private NewsModel newsModel;

    @BeforeEach
    public void setup() {
        tagService = new TagServiceImpl(tagRepository, newsRepository, validator, tagMapper);

        LocalDateTime now = LocalDateTime.now();
        tagDtoRequest = new TagDtoRequest(1L, "climate");
        tagModel = new TagModel(tagDtoRequest.getId(), tagDtoRequest.getName());
        newsModel = new NewsModel(2L, "title", "content", now, now, new AuthorModel(1L, "Egor", now, now));
        newsModel.setTags(List.of(tagModel));
    }

    @Test
    void getById() {
        Long id = tagDtoRequest.getId();
        given(tagRepository.readById(id)).willReturn(Optional.of(tagModel));
        tagService.readById(id);

        verify(validator, times(1)).validateId(id);
        verify(tagRepository, times(1)).readById(id);
        verify(tagMapper, times(1)).modelToDtoResponse(tagModel);
    }

    @Test
    void create() {
        given(tagMapper.dtoRequestToModel(tagDtoRequest)).willReturn(tagModel);
        given(tagRepository.create(tagModel)).willReturn(tagModel);
        tagService.create(tagDtoRequest);

        verify(validator, times(1)).validateTagRequestWithoutId(tagDtoRequest);
        verify(tagMapper, times(1)).dtoRequestToModel(tagDtoRequest);
        verify(tagRepository, times(1)).create(tagModel);
        verify(tagMapper, times(1)).modelToDtoResponse(tagModel);

    }

    @Test
    void getAll() {
        given(tagRepository.readAll()).willReturn(List.of(tagModel));
        tagService.readAll();

        verify(tagRepository, times(1)).readAll();
        verify(tagMapper, times(1)).listOfModelsToListOfResponses(List.of(tagModel));
    }

    @Test
    void update() {
        given(tagMapper.dtoRequestToModel(tagDtoRequest)).willReturn(tagModel);
        given(tagRepository.update(tagModel)).willReturn(tagModel);
        given(tagRepository.existById(tagDtoRequest.getId())).willReturn(true);
        tagService.update(tagDtoRequest);

        verify(validator, times(1)).validateTagRequest(tagDtoRequest);
        verify(tagMapper, times(1)).dtoRequestToModel(tagDtoRequest);
        verify(tagRepository, times(1)).update(tagModel);
        verify(tagMapper, times(1)).modelToDtoResponse(tagModel);
    }

    @Test
    void delete() {
        given(tagRepository.existById(tagDtoRequest.getId())).willReturn(true);
        Long id = tagDtoRequest.getId();
        tagService.deleteById(id);

        verify(validator, times(1)).validateId(id);
        verify(tagRepository, times(1)).deleteById(id);
    }

    @Test
    void getAuthorByNewsId() {
        Long newsId = tagDtoRequest.getId();
        given(newsRepository.readById(newsId)).willReturn(Optional.of(newsModel));
        tagService.getTagsByNewsId(newsId);

        verify(validator, times(1)).validateId(newsId);
        verify(newsRepository, times(1)).readById(newsId);
        verify(tagMapper, times(1)).listOfModelsToListOfResponses(List.of(tagModel));
    }

}