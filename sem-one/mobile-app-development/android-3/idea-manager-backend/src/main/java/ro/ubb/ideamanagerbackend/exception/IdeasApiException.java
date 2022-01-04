package ro.ubb.ideamanagerbackend.exception;

public class IdeasApiException extends RuntimeException{
    public IdeasApiException(String message){
        super(message);
    }
}
