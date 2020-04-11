package api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import api.exceptions.AppExceptionModel;
import api.exceptions.ForbiddenException;

@ControllerAdvice
public class GlobalExceptionHandler  {
	
    @ExceptionHandler(ForbiddenException.class)
    @ResponseBody
    @ResponseStatus( HttpStatus.FORBIDDEN )
    public AppExceptionModel handleForbiddenException(ForbiddenException ex) {

    	ex.printStackTrace();
        return new AppExceptionModel(403, ex.getMessage());
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
