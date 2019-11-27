package pl.com.devmeet.devmeet.group_associated.meeting.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityUpdater;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

public class MeetingCrudUpdater implements CrudEntityUpdater<MeetingDto,MeetingEntity> {
    @Override
    public MeetingEntity updateEntity(MeetingDto oldDto, MeetingDto newDto) throws CrudException {
        return null;
    }
}
