package pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 15.11.2019
 * Time: 23:46
 */
public class AvailabilityNotFoundException extends CrudException {
    public AvailabilityNotFoundException(String message) {
        super(message);
    }
}
