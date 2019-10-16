package pl.com.devmeet.devmeet.domain_utils;

public class EntityAlreadyExistsException extends Exception {

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}