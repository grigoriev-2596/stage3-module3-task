package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exceptions.ErrorCode;
import com.mjc.school.service.exceptions.ServiceException;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.validation.NewsManagementValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class NewsServiceImpl implements NewsService {

    private final BaseRepository<NewsModel, Long> newsRepository;
    private final BaseRepository<AuthorModel, Long> authorRepository;
    private final BaseRepository<TagModel, Long> tagRepository;

    private final NewsManagementValidator validator;
    private final NewsMapper newsMapper;

    @Autowired
    public NewsServiceImpl(BaseRepository<NewsModel, Long> newsRepository, BaseRepository<AuthorModel, Long> authorRepository,
                           BaseRepository<TagModel, Long> tagRepository, NewsManagementValidator validator, NewsMapper newsMapper) {
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.validator = validator;
        this.newsMapper = newsMapper;
    }

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
        authorExistsOrThrowException(createRequest.getAuthorId());
        tagsExistOrThrowException(createRequest.getTagIds());
        NewsModel createdNews = newsRepository.create(newsMapper.dtoRequestToModel(createRequest));
        return newsMapper.modelToDtoResponse(createdNews);
    }

    @Override
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        validator.validateNewsRequest(updateRequest);
        newsExistsOrThrowException(updateRequest.getId());
        authorExistsOrThrowException(updateRequest.getAuthorId());
        tagsExistOrThrowException(updateRequest.getTagIds());
        NewsModel updatedNews = newsRepository.update(newsMapper.dtoRequestToModel(updateRequest));
        return newsMapper.modelToDtoResponse(updatedNews);
    }

    @Override
    public boolean deleteById(Long id) {
        validator.validateId(id);
        newsExistsOrThrowException(id);
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

    private void authorExistsOrThrowException(Long id) {
        if (!authorRepository.existById(id)) {
            throw new ServiceException(String.format(ErrorCode.AUTHOR_DOES_NOT_EXIST.toString(), id));
        }
    }

    private void newsExistsOrThrowException(Long id) {
        if (!newsRepository.existById(id)) {
            throw new ServiceException(String.format(ErrorCode.NEWS_DOES_NOT_EXIST.toString(), id));
        }
    }

    private void tagsExistOrThrowException(List<Long> ids) {
        ids.forEach(id -> {
            if (!tagRepository.existById(id)) {
                throw new ServiceException(String.format(ErrorCode.TAG_DOES_NOT_EXIST.toString(), id));
            }
        });
    }
}
