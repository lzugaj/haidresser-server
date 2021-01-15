package com.luv2code.hairdresser.controller.handler;

import com.luv2code.hairdresser.controller.handler.response.ApiResponse;
import com.luv2code.hairdresser.exception.EntityNotFoundException;
import com.luv2code.hairdresser.exception.UserAlreadyHasReservedIndentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ResponseEntityExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFoundRequestException(final EntityNotFoundException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        return createResponseMessage(notFound, exception, httpServletRequest);
    }

    @ExceptionHandler(value = UserAlreadyHasReservedIndentException.class)
    public ResponseEntity<ApiResponse> handleNotFoundRequestException(final UserAlreadyHasReservedIndentException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus conflict = HttpStatus.CONFLICT;
        return createResponseMessage(conflict, exception, httpServletRequest);
    }

    private ResponseEntity<ApiResponse> createResponseMessage(final HttpStatus httpStatus, final Exception exception, final HttpServletRequest httpServletRequest) {
        final ApiResponse apiResponse = ApiResponse.builder()
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .httpStatusCode(httpStatus.value())
                .httpStatus(httpStatus)
                .message(exception.getMessage())
                .path(httpServletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(apiResponse, httpStatus);
    }
}
