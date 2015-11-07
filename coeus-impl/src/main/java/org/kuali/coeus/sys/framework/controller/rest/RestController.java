package org.kuali.coeus.sys.framework.controller.rest;

import org.kuali.coeus.sys.framework.rest.ResourceNotFoundException;
import org.kuali.coeus.sys.framework.rest.UnauthorizedAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class RestController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage validationError(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<String> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());
        String error;
        for (FieldError fieldError : fieldErrors) {
            error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
            errors.add(error);
        }
        return new ErrorMessage(errors);
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ErrorMessage resourceNotFoundError(ResourceNotFoundException ex) {
    	return generateSingleErrorFromExceptionMessage(ex);
    }
    
    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseBody
    public ErrorMessage unauthorizedError(UnauthorizedAccessException ex) {
    	return generateSingleErrorFromExceptionMessage(ex);
    }
    
    protected ErrorMessage generateSingleErrorFromExceptionMessage(Exception ex) {
    	return new ErrorMessage(Stream.of(ex.getMessage()).collect(Collectors.toList()));
    }

    
	@InitBinder
	public void initInstantBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Instant.class, new InstantCustomPropertyEditor());
	}
}