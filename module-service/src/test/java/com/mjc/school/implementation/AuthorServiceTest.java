package com.mjc.school.implementation;

import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.AuthorMapper;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.implementation.AuthorService;
import com.mjc.school.service.validation.NewsManagementValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
    @Mock
    AuthorRepository authorRepository;
    @Mock
    AuthorMapper authorMapper;
    @Mock
    NewsManagementValidator validator;

    @InjectMocks
    AuthorService authorService;

    private AuthorDtoRequest authorDtoRequest;
    private AuthorModel authorModel;

    @BeforeEach
    public void setup() {
        LocalDateTime now = LocalDateTime.now();
        authorDtoRequest = new AuthorDtoRequest(1L, "Grigoriev Egor");
        authorModel = new AuthorModel(authorDtoRequest.getId(), authorDtoRequest.getName(), now, now);
    }

    @Test
    void getById() {
        given(authorRepository.readById(authorDtoRequest.getId())).willReturn(Optional.of(authorModel));
        authorService.readById(authorDtoRequest.getId());

        verify(validator, times(1)).validateId(authorDtoRequest.getId());
        verify(authorMapper, times(1)).modelToDtoResponse(authorModel);
    }

    @Test
    void create() {
        given(authorMapper.dtoRequestToModel(authorDtoRequest)).willReturn(authorModel);
        given(authorRepository.create(authorModel)).willReturn(authorModel);
        authorService.create(authorDtoRequest);

        verify(validator, times(1)).validateAuthorRequest(authorDtoRequest);
        verify(authorMapper, times(1)).dtoRequestToModel(authorDtoRequest);
        verify(authorRepository, times(1)).create(authorModel);
        verify(authorMapper, times(1)).modelToDtoResponse(authorModel);
    }

    @Test
    void getAll() {
        given(authorRepository.readAll()).willReturn(List.of(authorModel));
        authorService.readAll();

        verify(authorRepository, times(1)).readAll();
        verify(authorMapper, times(1)).listOfModelsToListOfResponses(List.of(authorModel));
    }

    @Test
    void update() {
        given(authorMapper.dtoRequestToModel(authorDtoRequest)).willReturn(authorModel);
        given(authorRepository.update(authorModel)).willReturn(authorModel);
        authorService.update(authorDtoRequest);

        verify(validator, times(1)).validateAuthorRequest(authorDtoRequest);
        verify(validator, times(1)).validateId(authorDtoRequest.getId());
        verify(authorMapper, times(1)).dtoRequestToModel(authorDtoRequest);
        verify(authorRepository, times(1)).update(authorModel);
        verify(authorMapper, times(1)).modelToDtoResponse(authorModel);
    }

    @Test
    void delete() {
        Long id = authorDtoRequest.getId();
        authorService.deleteById(id);

        verify(validator, times(1)).validateId(id);
        verify(authorRepository, times(1)).deleteById(id);
    }

    @Test
    void getAuthorByNewsId() {
        final Long newsId = 2L;
        given(authorRepository.getAuthorByNewsId(newsId)).willReturn(authorModel);
        authorService.getAuthorByNewsId(newsId);

        verify(validator, times(1)).validateId(newsId);
        verify(authorRepository, times(1)).getAuthorByNewsId(newsId);
        verify(authorMapper, times(1)).modelToDtoResponse(authorModel);
    }

}