package pl.com.devmeet.devmeet.messenger_associated.message.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

public class MemberNotFoundException extends CrudException {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
