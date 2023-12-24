package Exceptions;

public class InvalidCharFoundException extends RuntimeException { 
    
    public InvalidCharFoundException() {
        super();
    }

    public InvalidCharFoundException(String str) {
        super(str);
    }
}