package pl.com.devmeet.devmeetcore.member_associated.availability.domain.status_and_exceptions;

import pl.com.devmeet.devmeetcore.domain_utils.exceptions.AppLogicException;

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
