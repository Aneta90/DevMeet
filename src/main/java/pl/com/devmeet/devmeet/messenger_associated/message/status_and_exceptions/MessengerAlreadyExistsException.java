package pl.com.devmeet.devmeet.messenger_associated.message.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 02.12.2019
 * Time: 17:32
 */

public class MessengerAlreadyExistsException extends CrudException {
    public MessengerAlreadyExistsException(String message) {
        super(message);
    }
}
