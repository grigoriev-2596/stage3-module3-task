package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.exceptions.ErrorCode;
import com.mjc.school.service.exceptions.ServiceException;
import com.mjc.school.service.exceptions.ValidatorException;
import com.mjc.school.service.mapper.TagMapper;
import com.mjc.school.service.validation.NewsManagementValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final BaseRepository<TagModel, Long> tagRepository;
    private final BaseRepository<NewsModel, Long> newsRepository;

    private final NewsManagementValidator validator;
    private final TagMapper tagMapper;

    public TagServiceImpl(BaseRepository<TagModel, Long> tagRepository, BaseRepository<NewsModel, Long> newsRepository,
                          NewsManagementValidator validator, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.newsRepository = newsRepository;
        this.validator = validator;
        this.tagMapper = tagMapper;
    }

    @Override
    public List<TagDtoResponse> readAll() {
        return tagMapper.listOfModelsToListOfResponses(tagRepository.readAll());
    }

    @Override
    public TagDtoResponse readById(Long id) {
        validator.validateId(id);
        Optional<TagModel> maybeNullModel = tagRepository.readById(id);
        if (maybeNullModel.isEmpty()) {
            throw new ValidatorException(String.format(ErrorCode.TAG_DOES_NOT_EXIST.toString(), id));
        }
        return tagMapper.modelToDtoResponse(maybeNullModel.get());
    }

    @Override
    public TagDtoResponse create(TagDtoRequest createRequest) {
        validator.validateTagRequestWithoutId(createRequest);
        TagModel createdTag = tagRepository.create(tagMapper.dtoRequestToModel(createRequest));
        return tagMapper.modelToDtoResponse(createdTag);
    }

    @Override
    public TagDtoResponse update(TagDtoRequest updateRequest) {
        validator.validateTagRequest(updateRequest);
        tagExistsOrThrowException(updateRequest.getId());
        TagModel updateModel = tagRepository.update(tagMapper.dtoRequestToModel(updateRequest));
        return tagMapper.modelToDtoResponse(updateModel);
    }

    @Override
    public boolean deleteById(Long id) {
        validator.validateId(id);
        tagExistsOrThrowException(id);
        return tagRepository.deleteById(id);
    }

    public List<TagDtoResponse> getTagsByNewsId(Long id) {
        validator.validateId(id);
        Optional<NewsModel> maybeNullNews = newsRepository.readById(id);
        if (maybeNullNews.isEmpty()) {
            throw new ServiceException(String.format(ErrorCode.NEWS_DOES_NOT_EXIST.toString(), id));
        }
        return tagMapper.listOfModelsToListOfResponses(maybeNullNews.get().getTags());
    }

    private void tagExistsOrThrowException(Long id) {
        if (!tagRepository.existById(id)) {
            throw new ServiceException(String.format(ErrorCode.TAG_DOES_NOT_EXIST.toString(), id));
        }
    }
}
