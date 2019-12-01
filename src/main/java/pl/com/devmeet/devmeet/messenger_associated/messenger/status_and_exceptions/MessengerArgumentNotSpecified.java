package pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 01.12.2019
 * Time: 14:28
 */

public class MessengerArgumentNotSpecified extends CrudException {
    public MessengerArgumentNotSpecified(String message) {
        super(message);
    }
}
