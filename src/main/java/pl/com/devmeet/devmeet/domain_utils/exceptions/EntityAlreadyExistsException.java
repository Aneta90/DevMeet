package pl.com.devmeet.devmeet.domain_utils.exceptions;

public class EntityAlreadyExistsException extends CrudException {

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}