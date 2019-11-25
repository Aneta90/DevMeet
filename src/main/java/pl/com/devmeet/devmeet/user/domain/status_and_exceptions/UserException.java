package pl.com.devmeet.devmeet.user.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.AppLogicException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 16.11.2019
 * Time: 11:50
 */
public class UserException extends AppLogicException {
    public UserException(String message) {
        super(message);
    }
}
