package pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

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
