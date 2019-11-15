package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 15.11.2019
 * Time: 23:53
 */
public class AvailabilityVoteFoundButNotActiveException extends CrudException {
    public AvailabilityVoteFoundButNotActiveException(String message) {
        super(message);
    }
}
