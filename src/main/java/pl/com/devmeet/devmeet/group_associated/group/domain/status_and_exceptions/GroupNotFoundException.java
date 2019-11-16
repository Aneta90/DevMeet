package pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 16.11.2019
 * Time: 10:29
 */
public class GroupNotFoundException extends CrudException {
    public GroupNotFoundException(String message) {
        super(message);
    }
}
