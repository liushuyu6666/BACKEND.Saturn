package jupiter.backend.exception;

import jupiter.backend.payload.response.ResponseBody;
import org.springframework.http.ResponseEntity;

public class IllegalFormat extends Exception{
    public IllegalFormat(String message) {
        super("submit format is illegal: " + message);
    }

    public static ResponseEntity<ResponseBody> badRequest(String message){
        Exception e = new IllegalFormat(message);
        return ResponseEntity
                .badRequest()
                .body(new ResponseBody(null,
                        e.getMessage(),
                        e));
    }
}