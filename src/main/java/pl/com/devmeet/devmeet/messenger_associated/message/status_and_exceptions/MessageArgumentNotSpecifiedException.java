package pl.com.devmeet.devmeet.messenger_associated.message.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 05.12.2019
 * Time: 01:25
 */
public class MessageArgumentNotSpecifiedException extends CrudException {
    public MessageArgumentNotSpecifiedException(String message) {
        super(message);
    }
}
