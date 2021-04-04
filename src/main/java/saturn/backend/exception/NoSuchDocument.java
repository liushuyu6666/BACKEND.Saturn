package saturn.backend.exception;

import saturn.backend.payload.response.ResponseBody;
import org.springframework.http.ResponseEntity;

public class NoSuchDocument extends Exception {

    public NoSuchDocument(String message) {
        super("No such document: " + message);
    }

    public static ResponseEntity<ResponseBody> ok(String message){
        Exception e = new NoSuchDocument(message);
        return ResponseEntity
                .ok(new ResponseBody(null,
                        e.getMessage(),
                        e));
    }
}

