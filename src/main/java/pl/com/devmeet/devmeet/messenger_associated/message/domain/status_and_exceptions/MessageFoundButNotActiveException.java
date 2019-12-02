package pl.com.devmeet.devmeet.messenger_associated.message.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 02.12.2019
 * Time: 17:36
 */

public class MessageFoundButNotActiveException extends CrudException {
    public MessageFoundButNotActiveException(String message) {
        super(message);
    }
}
