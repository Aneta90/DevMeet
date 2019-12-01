package pl.com.devmeet.devmeet.messenger_associated.message.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 15.11.2019
 * Time: 23:11
 */
public class MemberFoundButNotActiveException extends CrudException {
    public MemberFoundButNotActiveException(String message) {
        super(message);
    }
}
