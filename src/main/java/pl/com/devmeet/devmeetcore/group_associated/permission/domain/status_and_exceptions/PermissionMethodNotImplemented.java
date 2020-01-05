package pl.com.devmeet.devmeetcore.group_associated.permission.domain.status_and_exceptions;

import pl.com.devmeet.devmeetcore.domain_utils.exceptions.AppLogicException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 16.11.2019
 * Time: 11:34
 */
public class PermissionMethodNotImplemented extends AppLogicException {
    public PermissionMethodNotImplemented(String message) {
        super(message);
    }
}
