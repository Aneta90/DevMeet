package pl.com.devmeet.devmeet.member_associated.member.domain;

import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;

public class MemberAlreadyExistsException extends EntityAlreadyExistsException {

    public MemberAlreadyExistsException(String message) {
        super(message);
    }
}
