package telran.advert.exceptions;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionsHandler {

	@ExceptionHandler(value = NotFoundException.class)
	ResponseEntity<String> notFoundHandler(NotFoundException e) {
		String errorMessage = e.getMessage();
		log.error(errorMessage);
		return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class) // pattern, not empty
	ResponseEntity<String> methodArgumentExceptionHandler(MethodArgumentNotValidException e) {
		String errorMessage = e.getAllErrors().stream().map(er -> er.getCodes()[0] + ": " + er.getDefaultMessage())
				.collect(Collectors.joining("; "));
		log.error(errorMessage);
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { HttpMessageNotReadableException.class }) // all fields with wrong names
	ResponseEntity<String> illegalArgumentHandler(RuntimeException e) {
		String errorMessage = e.getMessage();
		log.error(errorMessage);
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	ResponseEntity<String> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		String errorMessage = e.getMessage() + " (wrong path)";
		log.error(errorMessage);
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
	ResponseEntity<String> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		String errorMessage = e.getMessage();
		log.error(errorMessage);
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

}
