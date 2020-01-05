package pl.com.devmeet.devmeetcore.member_associated.availability.domain.status_and_exceptions;

import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 16.11.2019
 * Time: 11:08
 */
public class AvailabilityAlreadyExistsException extends CrudException {
    public AvailabilityAlreadyExistsException(String message) {
        super(message);
    }
}
