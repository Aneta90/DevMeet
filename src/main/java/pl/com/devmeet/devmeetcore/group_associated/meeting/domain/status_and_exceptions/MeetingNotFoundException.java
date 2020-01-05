package pl.com.devmeet.devmeetcore.group_associated.meeting.domain.status_and_exceptions;

import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

public class MeetingNotFoundException extends CrudException {
    public MeetingNotFoundException(String message) {
        super(message);
    }
}
