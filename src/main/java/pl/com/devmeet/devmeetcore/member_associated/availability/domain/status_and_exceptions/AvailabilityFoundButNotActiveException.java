package pl.com.devmeet.devmeetcore.member_associated.availability.domain.status_and_exceptions;

import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 15.11.2019
 * Time: 23:48
 */
public class AvailabilityFoundButNotActiveException extends CrudException {
    public AvailabilityFoundButNotActiveException(String message) {
        super(message);
    }
}
