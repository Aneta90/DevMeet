package pl.com.devmeet.devmeet.user.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 15.11.2019
 * Time: 22:58
 */
public class UserNotFoundException extends CrudException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
