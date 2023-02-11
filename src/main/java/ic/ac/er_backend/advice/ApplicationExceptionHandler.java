package ic.ac.er_backend.advice;

import ic.ac.er_backend.util.Result;
import ic.ac.er_backend.util.ResultState;
import io.github.MigadaTang.exception.ERException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleInvalidArgument(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldErrors().get(0);
        return Result.error(ResultState.INVALID_ARGUMENT, fieldError.getField() + ": " + fieldError.getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ERException.class)
    public Result handleERException(ERException ex) {
        return Result.error(ResultState.ER_API_ERROR, ex.getMessage());
    }
}