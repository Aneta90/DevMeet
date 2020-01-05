package pl.com.devmeet.devmeetcore.messenger_associated.message.status_and_exceptions;

import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

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
