package pl.com.devmeet.devmeet.domain_utils.exceptions;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends CrudException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}