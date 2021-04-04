package saturn.backend.exception;

import saturn.backend.payload.response.ResponseBody;
import org.springframework.http.ResponseEntity;

public class FailToDeleteDocument extends Exception {
    public FailToDeleteDocument(String message) {
        super("submit format is illegal: " + message);
    }

    public static ResponseEntity<ResponseBody> ok(String message){
        Exception e = new IllegalFormat(message);
        return ResponseEntity
                .badRequest()
                .body(new ResponseBody(null,
                        e.getMessage(),
                        e));
    }
}