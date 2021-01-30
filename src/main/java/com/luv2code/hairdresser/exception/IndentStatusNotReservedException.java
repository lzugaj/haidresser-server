package com.luv2code.hairdresser.exception;

public class IndentStatusNotReservedException extends AbstractEntityException {

    private static final long serialVersionUID = 1L;

    public IndentStatusNotReservedException(final String entityName,
                                            final String fieldName,
                                            final String fieldValue) {
        this(entityName, fieldName, fieldValue, null);
    }

    public IndentStatusNotReservedException(final String entityName,
                                            final String fieldName,
                                            final String fieldValue,
                                            final Throwable cause) {
        super(entityName, fieldName, fieldValue, createMessage(entityName, fieldName, fieldValue), cause);
    }

    private static String createMessage(final String entityName, final String fieldName, final String fieldValue) {
        return String.format("Entity '%s' with '%s' value '%s' has not reserved status so the requested action cannot be performed.",
                entityName, fieldName, fieldValue);
    }
}
