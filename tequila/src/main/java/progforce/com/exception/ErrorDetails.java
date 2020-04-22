package progforce.com.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
    private int errorCode;
    private HttpStatus httpStatus;

    public ErrorDetails(Date timestamp, String message, String details, HttpStatus httpStatus) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.errorCode = httpStatus.value();
        this.httpStatus = httpStatus;

    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
