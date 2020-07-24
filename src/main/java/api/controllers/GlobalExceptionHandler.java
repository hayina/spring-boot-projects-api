package api.controllers;

import api.dto.ExceptionDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import api.exceptions.AppExceptionModel;
import api.exceptions.ForbiddenException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler  {
	
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus( HttpStatus.FORBIDDEN )
    public ResponseEntity<AppExceptionModel> handleForbiddenException(ForbiddenException ex) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(
                    new AppExceptionModel(HttpStatus.FORBIDDEN.value(),
                            ex.getMessage()), headers,
                                HttpStatus.BAD_REQUEST
        );

//    	ex.printStackTrace();
//        return new AppExceptionModel(HttpStatus.FORBIDDEN.value(), ex.getMessage());
    }


//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, Object> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
////        BindingResult result = ex.getBindingResult();
////        List<FieldError> fieldErrors = result.getFieldErrors();
//
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
//        body.put("status", HttpStatus.BAD_REQUEST.value());
//
//        //Get all fields errors
//        body.put("errors", ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(x -> x.getField() + " : " + x.getDefaultMessage())
//                .collect(Collectors.toList()));
//        return body;
//
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionDto methodArgumentNotValidException(MethodArgumentNotValidException ex) {

        return new ExceptionDto(
                HttpStatus.BAD_REQUEST.value(),
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()),
                ex.getBindingResult().getFieldErrors()
                        .stream().map(x -> new ExceptionDto.Error(x.getField(), x.getDefaultMessage()))
                        .collect(Collectors.toList())
        );

    }

//
//    @ExceptionHandler(UnauthorizedException.class)
//    @ResponseBody
//    public AppException handleUnauthorizedException(AuthenticationException ex) {
//
//    	ex.printStackTrace();
//    	return new AppException(401, ex.getMessage());
//    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, Object>> handleException(Exception ex) throws JsonProcessingException {
//
//    	ex.printStackTrace();
//		Map<String, Object> jsonException = new HashMap<String, Object>();
//		jsonException.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
//		jsonException.put("messsage", ex.getMessage());
////		jsonException.put("time_stamp", new Date());
//
//        return new ResponseEntity<>(jsonException, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
