package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.AuthorMapper;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exceptions.ErrorCode;
import com.mjc.school.service.exceptions.ServiceException;
import com.mjc.school.service.validation.NewsManagementValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService implements BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> {
    private final BaseRepository<AuthorModel, Long> authorRepository;
    private final NewsManagementValidator validator;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorService(BaseRepository<AuthorModel, Long> authorRepository, NewsManagementValidator authorValidator, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.validator = authorValidator;
        this.authorMapper = authorMapper;
    }

    @Override
    public List<AuthorDtoResponse> readAll() {
        return authorMapper.listOfModelsToListOfResponses(authorRepository.readAll());
    }

    @Override
    public AuthorDtoResponse readById(Long id) {
        validator.validateId(id);
        Optional<AuthorModel> maybeNullModel = authorRepository.readById(id);
        if (maybeNullModel.isEmpty()) {
            throw new ServiceException(String.format(ErrorCode.AUTHOR_DOES_NOT_EXIST.toString(), id));
        }
        return authorMapper.modelToDtoResponse(maybeNullModel.get());
    }

    @Override
    public AuthorDtoResponse create(AuthorDtoRequest createRequest) {
        validator.validateAuthorRequest(createRequest);
        AuthorModel model = authorMapper.dtoRequestToModel(createRequest);
        model = authorRepository.create(model);
        return authorMapper.modelToDtoResponse(model);
    }

    @Override
    public AuthorDtoResponse update(AuthorDtoRequest updateRequest) {
        validator.validateAuthorRequest(updateRequest);
        validator.validateId(updateRequest.getId());
        AuthorModel authorToUpdate = authorMapper.dtoRequestToModel(updateRequest);
        AuthorModel updatedAuthor = authorRepository.update(authorToUpdate);
        if (updatedAuthor == null) {
            throw new ServiceException(String.format(ErrorCode.AUTHOR_DOES_NOT_EXIST.toString(), updateRequest.getId()));
        }
        return authorMapper.modelToDtoResponse(updatedAuthor);
    }


    public AuthorDtoResponse getAuthorByNewsId(Long id) {
        validator.validateId(id);
        AuthorModel authorModel = ((AuthorRepository) authorRepository).getAuthorByNewsId(id);
        if (authorModel == null) {
            throw new ServiceException(String.format(ErrorCode.NEWS_DOES_NOT_EXIST.toString(), id));
        }
        return authorMapper.modelToDtoResponse(authorModel);
    }

    @Override
    public boolean deleteById(Long id) {
        validator.validateId(id);
        return authorRepository.deleteById(id);
    }
}
