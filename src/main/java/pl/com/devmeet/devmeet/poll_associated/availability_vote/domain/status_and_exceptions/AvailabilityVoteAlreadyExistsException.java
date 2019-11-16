package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 16.11.2019
 * Time: 11:18
 */
public class AvailabilityVoteAlreadyExistsException extends CrudException {
    public AvailabilityVoteAlreadyExistsException(String message) {
        super(message);
    }
}
