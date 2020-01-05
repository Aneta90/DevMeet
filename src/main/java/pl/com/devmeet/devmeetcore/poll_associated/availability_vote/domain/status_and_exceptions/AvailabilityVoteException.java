package pl.com.devmeet.devmeetcore.poll_associated.availability_vote.domain.status_and_exceptions;

import pl.com.devmeet.devmeetcore.domain_utils.exceptions.AppLogicException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 16.11.2019
 * Time: 00:20
 */
public class AvailabilityVoteException extends AppLogicException {
    public AvailabilityVoteException(String message) {
        super(message);
    }
}
