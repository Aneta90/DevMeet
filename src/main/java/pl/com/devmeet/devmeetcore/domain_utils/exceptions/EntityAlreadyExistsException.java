package pl.com.devmeet.devmeetcore.domain_utils.exceptions;

public class EntityAlreadyExistsException extends CrudException {

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}