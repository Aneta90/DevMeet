package pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions;

import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 15.11.2019
 * Time: 22:58
 */
public class UserAlreadyExistsException extends CrudException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
