package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 15.11.2019
 * Time: 23:52
 */
public class AvailabilityVoteNotFoundException extends CrudException {
    public AvailabilityVoteNotFoundException(String message) {
        super(message);
    }
}
