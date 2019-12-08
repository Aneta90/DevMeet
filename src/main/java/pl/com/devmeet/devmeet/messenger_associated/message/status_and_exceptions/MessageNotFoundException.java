package pl.com.devmeet.devmeet.messenger_associated.message.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 02.12.2019
 * Time: 17:31
 */

public class MessageNotFoundException extends CrudException {
    public MessageNotFoundException(String message) {
        super(message);
    }
}
