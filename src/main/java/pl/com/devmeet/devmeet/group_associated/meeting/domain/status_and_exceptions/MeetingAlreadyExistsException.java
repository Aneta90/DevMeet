package pl.com.devmeet.devmeet.group_associated.meeting.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

public class MeetingAlreadyExistsException extends CrudException {
    public MeetingAlreadyExistsException(String message) {
        super(message);
    }
}
