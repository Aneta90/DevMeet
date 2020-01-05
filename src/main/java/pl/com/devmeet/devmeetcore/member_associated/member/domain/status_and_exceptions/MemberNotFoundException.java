package pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions;

import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

public class MemberNotFoundException extends CrudException {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
