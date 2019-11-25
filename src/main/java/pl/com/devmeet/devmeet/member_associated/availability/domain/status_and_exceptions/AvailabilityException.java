package pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.AppLogicException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 16.11.2019
 * Time: 11:03
 */
public class AvailabilityException extends AppLogicException {
    public AvailabilityException(String message) {
        super(message);
    }
}
