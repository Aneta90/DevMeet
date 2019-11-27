package pl.com.devmeet.devmeet.group_associated.meeting.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

public class MeetingCrudDeleter implements CrudEntityDeleter<MeetingDto,MeetingEntity> {
    @Override
    public MeetingEntity deleteEntity(MeetingDto dto) throws CrudException {
        return null;
    }
}