package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain.status_and_exceptions;

import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

public class PlaceVoteNotFoundException extends CrudException {
    public PlaceVoteNotFoundException(String message) {
        super(message);
    }
}
