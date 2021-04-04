package saturn.backend.exception;

import saturn.backend.payload.response.ResponseBody;
import org.springframework.http.ResponseEntity;

public class RedundantIssueException extends Exception{
    public RedundantIssueException(String message){
        super("Redundant Error: " + message);
    }

    public static ResponseEntity<ResponseBody> ok(String message){
        Exception e = new RedundantIssueException(message);
        return ResponseEntity
                .ok(new ResponseBody(null,
                        e.getMessage(),
                        e));
    }
}
