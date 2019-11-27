package pl.com.devmeet.devmeet.group_associated.meeting.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;

public class MeetingCrudCreator implements CrudEntityCreator<MeetingDto, MeetingEntity> {

    private MeetingCrudFinder meetingCrudFinder;
    private MeetingCrudSaver meetingCrudSaver;

    public MeetingCrudCreator(MeetingCrudFinder meetingCrudFinder, MeetingCrudSaver meetingCrudSaver) {
        this.meetingCrudFinder = meetingCrudFinder;
        this.meetingCrudSaver = meetingCrudSaver;
    }

    @Override
    public MeetingEntity createEntity(MeetingDto dto) throws CrudException {

        MeetingEntity meetingEntity = meetingCrudFinder.findEntity(dto);

        if (meetingEntity.isActive()) {
            throw new EntityAlreadyExistsException("Meeting already exists");
        } else {
            meetingCrudSaver.saveEntity(meetingEntity);
        }

        throw new EntityNotFoundException("Entity has been not found"); //??
    }
}
