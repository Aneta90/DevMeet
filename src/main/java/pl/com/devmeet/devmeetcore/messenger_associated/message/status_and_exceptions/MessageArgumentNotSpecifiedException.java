package pl.com.devmeet.devmeetcore.messenger_associated.message.status_and_exceptions;

import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

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
