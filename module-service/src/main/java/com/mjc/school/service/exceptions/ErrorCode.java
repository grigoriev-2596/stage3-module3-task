package com.mjc.school.service.exceptions;

public enum ErrorCode {

    STRING_LENGTH_IS_INVALID(1001, "%s field should have length from %s to %s."),
    REQUIRED_FIELD_IS_NULL(1002, "%s field must not be null"),
    ID_IS_INVALID(1003, "minimum id value must be 1"),
    NEWS_DOES_NOT_EXIST(1004, "news with id %s does not exist"),
    AUTHOR_DOES_NOT_EXIST(1005, "author with id %s does not exist"),
    ID_MUST_BE_AN_INTEGER(1006, "id field must be an integer"),
    ID_IS_NULL(1007, "id field is null"),
    TAG_DOES_NOT_EXIST(1008, "tag with id %s does not exist");

    private final int id;
    private final String message;

    ErrorCode(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ERROR_CODE: " + id + ", ERROR_MESSAGE: " + message;
    }
}
