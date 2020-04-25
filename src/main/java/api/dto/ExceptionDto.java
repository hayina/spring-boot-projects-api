package api.dto;

import java.util.ArrayList;
import java.util.List;

public class ExceptionDto {

    public int status;
    public String datestamp;
    public List<Error> errors = new ArrayList<>();

    public ExceptionDto(int status, String datestamp, List<Error> errors) {
        this.status = status;
        this.datestamp = datestamp;
        this.errors = errors;
    }

    public static class Error {
        public String field;
        public String message;

        public Error(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
