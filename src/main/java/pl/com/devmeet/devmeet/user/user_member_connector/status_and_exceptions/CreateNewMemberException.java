package pl.com.devmeet.devmeet.user.user_member_connector.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.AppLogicException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 05.12.2019
 * Time: 23:42
 */

public class CreateNewMemberException extends AppLogicException {
    public CreateNewMemberException(String message) {
        super(message);
    }
}
