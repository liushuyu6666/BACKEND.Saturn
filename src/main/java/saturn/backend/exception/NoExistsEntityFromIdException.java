package saturn.backend.exception;

public class NoExistsEntityFromIdException extends Exception{

    public NoExistsEntityFromIdException(String message) {
        super("from the id we can't get this " + message);
    }
}
