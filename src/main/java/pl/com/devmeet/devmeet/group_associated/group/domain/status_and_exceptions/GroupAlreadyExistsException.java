package pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 16.11.2019
 * Time: 10:30
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class GroupAlreadyExistsException extends CrudException {
    public GroupAlreadyExistsException(String message) {
        super(message);
    }
}
