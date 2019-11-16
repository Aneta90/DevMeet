package pl.com.devmeet.devmeet.poll_associated.poll.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.AppLogicException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 16.11.2019
 * Time: 10:52
 */
public class PollUnsupportedOperationException extends AppLogicException {
    public PollUnsupportedOperationException(String message) {
        super(message);
    }
}
