package pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.AppLogicException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 16.11.2019
 * Time: 11:32
 */
public class PermissionException extends AppLogicException {
    public PermissionException(String message) {
        super(message);
    }
}
