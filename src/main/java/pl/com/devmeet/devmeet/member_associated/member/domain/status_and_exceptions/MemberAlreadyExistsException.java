package pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

public class MemberAlreadyExistsException extends CrudException {
    public MemberAlreadyExistsException(String message) {
        super(message);
    }
}
