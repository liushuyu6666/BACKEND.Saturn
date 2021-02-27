package jupiter.backend.exception;

public class NoExistsEntityFromNameException extends Exception{
    public NoExistsEntityFromNameException(String message) {
        super("from the name we can't get this " + message);
    }
}
