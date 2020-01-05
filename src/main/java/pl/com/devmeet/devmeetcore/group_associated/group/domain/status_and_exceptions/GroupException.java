package pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.com.devmeet.devmeetcore.domain_utils.exceptions.AppLogicException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 16.11.2019
 * Time: 10:35
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class GroupException extends AppLogicException {
    public GroupException(String message) {
        super(message);
    }
}
