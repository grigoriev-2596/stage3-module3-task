package com.mjc.school.service;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.mapstruct.Mapping;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class AuthorMapper {
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    @Mapping(target = "news", ignore = true)
    public abstract AuthorModel dtoRequestToModel(AuthorDtoRequest dto);

    @Mapping(target = "news", ignore = true)
    public abstract AuthorModel dtoResponseToModel(AuthorDtoResponse dto);

    public abstract AuthorDtoResponse modelToDtoResponse(AuthorModel model);

    public abstract List<AuthorDtoResponse> listOfModelsToListOfResponses(List<AuthorModel> modelList);

    public abstract List<AuthorModel> listOfResponsesToListOfModel(List<AuthorDtoResponse> responseList);
}
