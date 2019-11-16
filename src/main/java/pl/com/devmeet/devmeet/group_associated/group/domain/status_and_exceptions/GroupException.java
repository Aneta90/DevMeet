package pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.AppLogicException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 16.11.2019
 * Time: 10:35
 */
public class GroupException extends AppLogicException {
    public GroupException(String message) {
        super(message);
    }
}
