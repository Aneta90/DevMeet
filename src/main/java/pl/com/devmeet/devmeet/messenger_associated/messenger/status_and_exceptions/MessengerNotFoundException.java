package pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: kamil
 * Date: 29.11.2019
 * Time: 18:37
 */
public class MessengerNotFoundException extends CrudException {
    public MessengerNotFoundException(String message) {
        super(message);
    }
}
