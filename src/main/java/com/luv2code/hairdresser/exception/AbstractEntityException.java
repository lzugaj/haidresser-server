package com.luv2code.hairdresser.exception;

public abstract class AbstractEntityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String entityName;

    private final String fieldName;

    private final String fieldValue;

    public AbstractEntityException(final String entityName,
                                   final String fieldName,
                                   final String fieldValue,
                                   final String message) {
        this(entityName, fieldName, fieldValue, message, null);
    }

    public AbstractEntityException(final String entityName,
                                   final String fieldName,
                                   final String fieldValue,
                                   final String message,
                                   final Throwable cause) {
        super(message, cause);
        this.entityName = entityName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}
