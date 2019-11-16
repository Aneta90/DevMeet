package pl.com.devmeet.devmeet.poll_associated.poll.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 16.11.2019
 * Time: 09:12
 */
public class PollAlreadyExistsException extends CrudException {
    public PollAlreadyExistsException(String message) {
        super(message);
    }
}
