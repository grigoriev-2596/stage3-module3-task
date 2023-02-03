package com.mjc.school.service;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class NewsMapper {
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "tags", ignore = true)
    public abstract NewsModel dtoRequestToModel(NewsDtoRequest dto);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "tags", ignore = true)
    public abstract NewsModel dtoResponseToModel(NewsDtoResponse dto);

    @Mapping(source = "author", target = "authorId")
    @Mapping(source = "tags", target = "tagIds")
    public NewsDtoResponse modelToDtoResponse(NewsModel model) {
        List<Long> tagIds = model.getTags().stream().map(TagModel::getId).collect(Collectors.toList());
        return new NewsDtoResponse(model.getId(), model.getTitle(), model.getContent(),
                model.getCreationDate(), model.getLastUpdateDate(), model.getAuthor().getId(), tagIds);
    }

    public abstract List<NewsDtoResponse> listOfModelsToListOfResponses(List<NewsModel> modelList);

    public abstract List<NewsModel> listOfResponsesToListOfModel(List<NewsDtoResponse> responseList);
}
