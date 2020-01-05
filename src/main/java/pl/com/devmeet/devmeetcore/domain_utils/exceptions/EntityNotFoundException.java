package pl.com.devmeet.devmeetcore.domain_utils.exceptions;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends CrudException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}