package Exceptions;

public class InvalidMessageTypeException extends RuntimeException { 
    
    public InvalidMessageTypeException() {
        super();
    }

    public InvalidMessageTypeException(String str) {
        super(str);
    }
}