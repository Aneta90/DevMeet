package pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions;

import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 08.12.2019
 * Time: 14:14
 */
public class GroupArgumentsNotSpecifiedException extends CrudException {
    public GroupArgumentsNotSpecifiedException(String message) {
        super(message);
    }
}
