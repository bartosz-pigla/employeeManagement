package avra.hrsystem.employeemanagement.controller;

import avra.hrsystem.employeemanagement.exceptions.WrongFieldException;
import avra.hrsystem.employeemanagement.model.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(WrongFieldException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessage processWrongFieldException(WrongFieldException ex){
        return new ErrorMessage("wrong field",ex.getFieldName());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String processException(Exception ex){
        return "unknown error";
    }
}
