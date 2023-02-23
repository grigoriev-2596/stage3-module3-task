package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.mapstruct.Mapping;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class NewsMapper {

    public NewsModel dtoRequestToModel(NewsDtoRequest dto) {
        List<TagModel> tags = dto.getTagIds().stream().map(id -> new TagModel(id, "")).toList();
        return new NewsModel(dto.getId(), dto.getTitle(), dto.getContent(), null,
                null, new AuthorModel(dto.getAuthorId(), "", null, null), tags);
    }

    public NewsDtoResponse modelToDtoResponse(NewsModel model) {
        List<Long> tagIds = model.getTags().stream().map(TagModel::getId).toList();
        return new NewsDtoResponse(model.getId(), model.getTitle(), model.getContent(),
                model.getCreationDate(), model.getLastUpdateDate(), model.getAuthor().getId(), tagIds);
    }

    public abstract List<NewsDtoResponse> listOfModelsToListOfResponses(List<NewsModel> modelList);
}
