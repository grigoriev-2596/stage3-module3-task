package com.mjc.school.service.validation;

import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.exceptions.ErrorCode;
import com.mjc.school.service.exceptions.ValidatorException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class NewsManagementValidator {
    private static final int NEWS_TITLE_MIN_LENGTH = 5;
    private static final int NEWS_TITLE_MAX_LENGTH = 30;
    private static final int NEWS_CONTENT_MIN_LENGTH = 5;
    private static final int NEWS_CONTENT_MAX_LENGTH = 255;
    private static final int AUTHOR_NAME_MIN_LENGTH = 3;
    private static final int AUTHOR_NAME_MAX_LENGTH = 15;
    private static final int TAG_NAME_MIN_LENGTH = 3;
    private static final int TAG_NAME_MAX_LENGTH = 15;

    public void validateNewsRequest(NewsDtoRequest request) {
        validateNewsRequestWithoutId(request);
        validateId(request.getId());
    }

    public void validateAuthorRequest(AuthorDtoRequest request) {
        validateAuthorRequestWithoutId(request);
        validateId(request.getId());
    }

    public void validateTagRequest(TagDtoRequest request) {
        validateTagRequestWithoutId(request);
        validateId(request.getId());
    }

    public void validateNewsRequestWithoutId(NewsDtoRequest request) {
        validateStringLength(request.getTitle(), "news title", NEWS_TITLE_MIN_LENGTH, NEWS_TITLE_MAX_LENGTH);
        validateStringLength(request.getContent(), "news content", NEWS_CONTENT_MIN_LENGTH, NEWS_CONTENT_MAX_LENGTH);
        validateId(request.getAuthorId());
        request.getTagIds().forEach(this::validateId);
    }

    public void validateAuthorRequestWithoutId(AuthorDtoRequest request) {
        validateStringLength(request.getName(), "author name", AUTHOR_NAME_MIN_LENGTH, AUTHOR_NAME_MAX_LENGTH);
    }

    public void validateTagRequestWithoutId(TagDtoRequest request) {
        validateStringLength(request.getName(), "tag name", TAG_NAME_MIN_LENGTH, TAG_NAME_MAX_LENGTH);
    }

    public void validateId(Long id) {
        if (id == null) {
            throw new ValidatorException(String.format(ErrorCode.ID_IS_NULL.toString()));
        }
        if (id < 1) {
            throw new ValidatorException(ErrorCode.ID_IS_INVALID.toString());
        }
    }

    private void validateStringLength(String str, String fieldName, int min, int max) {
        if (str == null) {
            throw new ValidatorException(String.format(ErrorCode.REQUIRED_FIELD_IS_NULL.toString(), fieldName));
        }
        if (str.length() < min || str.length() > max) {
            throw new ValidatorException(String.format(ErrorCode.STRING_LENGTH_IS_INVALID.toString(), fieldName, min, max));
        }
    }

}
