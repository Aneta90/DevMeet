package pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 16.11.2019
 * Time: 10:31
 */
@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class GroupFoundButNotActiveException extends CrudException {
    public GroupFoundButNotActiveException(String message) {
        super(message);
    }
}
