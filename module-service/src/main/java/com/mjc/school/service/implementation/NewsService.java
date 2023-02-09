package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.NewsMapper;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exceptions.ErrorCode;
import com.mjc.school.service.exceptions.ServiceException;
import com.mjc.school.service.validation.NewsManagementValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class NewsService implements BaseService<NewsDtoRequest, NewsDtoResponse, Long> {
    private final BaseRepository<NewsModel, Long> newsRepository;
    private final BaseRepository<AuthorModel, Long> authorRepository;
    private final BaseRepository<TagModel, Long> tagRepository;
    private final NewsManagementValidator validator;
    private final NewsMapper newsMapper;

    public NewsService(BaseRepository<NewsModel, Long> newsRepository, BaseRepository<AuthorModel, Long> authorRepository,
                       BaseRepository<TagModel, Long> tagRepository, NewsManagementValidator validator, NewsMapper newsMapper) {
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.validator = validator;
        this.newsMapper = newsMapper;
    }

    @Autowired


    @Override
    public List<NewsDtoResponse> readAll() {
        return newsMapper.listOfModelsToListOfResponses(newsRepository.readAll());
    }

    @Override
    public NewsDtoResponse readById(Long id) {
        validator.validateId(id);
        Optional<NewsModel> maybeNullModel = newsRepository.readById(id);
        if (maybeNullModel.isEmpty()) {
            throw new ServiceException(String.format(ErrorCode.NEWS_DOES_NOT_EXIST.toString(), id));
        }
        return newsMapper.modelToDtoResponse(maybeNullModel.get());
    }

    @Override
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        validator.validateNewsRequestWithoutId(createRequest);
        NewsModel model = newsMapper.dtoRequestToModel(createRequest);
        addAuthorToNewsModelByAuthorId(model, createRequest.getAuthorId());
        addTagsToNewsModelByTagId(model, createRequest.getTagIds());
        model = newsRepository.create(model);
        return newsMapper.modelToDtoResponse(model);
    }

    @Override
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        validator.validateNewsRequest(updateRequest);
        NewsModel model = newsMapper.dtoRequestToModel(updateRequest);
        addAuthorToNewsModelByAuthorId(model, updateRequest.getAuthorId());
        addTagsToNewsModelByTagId(model, updateRequest.getTagIds());
        NewsModel updateModel = newsRepository.update(model);
        if (updateModel == null) {
            throw new ServiceException(String.format(ErrorCode.NEWS_DOES_NOT_EXIST.toString(), updateRequest.getId()));
        }
        return newsMapper.modelToDtoResponse(updateModel);
    }

    @Override
    public boolean deleteById(Long id) {
        validator.validateId(id);
        return newsRepository.deleteById(id);
    }

    public List<NewsDtoResponse> getNewsByCriteria(List<String> tagNames, List<Long> tagIds,
                                                   String authorName, String title, String content) {
        List<NewsModel> newsModels = newsRepository.readAll()
                .stream()
                .filter(allNotNullParameterPredicate(tagNames, tagIds, authorName, title, content))
                .toList();
        return newsMapper.listOfModelsToListOfResponses(newsModels);
    }


    private void addTagsToNewsModelByTagId(NewsModel model, List<Long> tagIds) {
        List<TagModel> tags = tagIds.stream().map(tagId -> {
            validator.validateId(tagId);
            Optional<TagModel> maybeNullTag = tagRepository.readById(tagId);
            if (maybeNullTag.isEmpty()) {
                throw new ServiceException(String.format(ErrorCode.TAG_DOES_NOT_EXIST.toString(), tagId));
            }
            return maybeNullTag.get();
        }).toList();
        model.setTags(tags);
    }

    private void addAuthorToNewsModelByAuthorId(NewsModel model, Long authorId) {
        validator.validateId(authorId);
        Optional<AuthorModel> maybeNullAuthor = authorRepository.readById(authorId);
        if (maybeNullAuthor.isEmpty()) {
            throw new ServiceException(String.format(ErrorCode.AUTHOR_DOES_NOT_EXIST.toString(), authorId));
        }
        model.setAuthor(maybeNullAuthor.get());
    }

    private Predicate<NewsModel> allNotNullParameterPredicate(List<String> tagNames, List<Long> tagIds,
                                                              String authorName, String title, String content) {
        Predicate<NewsModel> newsPredicate = news -> true;
        if (tagNames != null && !tagNames.isEmpty()) {
            newsPredicate = newsPredicate.and(news -> new HashSet<>(news.getTags().stream().map(TagModel::getName).toList()).containsAll(tagNames));
        }
        if (tagIds != null && !tagIds.isEmpty()) {
            newsPredicate = newsPredicate.and(news -> new HashSet<>(news.getTags().stream().map(TagModel::getId).toList()).containsAll(tagIds));
        }
        if (authorName != null && !authorName.isBlank()) {
            newsPredicate = newsPredicate.and(news -> news.getAuthor().getName().equalsIgnoreCase(authorName));
        }
        if (title != null && !title.isBlank()) {
            newsPredicate = newsPredicate.and(news -> news.getTitle().toLowerCase().contains(title.toLowerCase()));
        }
        if (content != null && !content.isBlank()) {
            newsPredicate = newsPredicate.and(news -> news.getContent().toLowerCase().contains(content.toLowerCase()));
        }
        return newsPredicate;
    }

}
