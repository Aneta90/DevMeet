package pl.com.devmeet.devmeet.group_associated.meeting.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.group_associated.meeting.domain.status_and_exceptions.MeetingAlreadyExistsException;

public class MeetingCrudCreator implements CrudEntityCreator<MeetingDto, MeetingEntity> {

    private MeetingCrudFinder meetingCrudFinder;
    private MeetingCrudSaver meetingCrudSaver;

    public MeetingCrudCreator(MeetingCrudRepository meetingCrudRepository) {
        this.meetingCrudFinder = new MeetingCrudFinder(meetingCrudRepository);
        this.meetingCrudSaver = new MeetingCrudSaver(meetingCrudRepository);
    }

    @Override
    public MeetingEntity createEntity(MeetingDto dto) throws MeetingAlreadyExistsException {
        MeetingEntity meetingEntity;
        if (meetingCrudFinder.isExist(dto)) {
            throw new MeetingAlreadyExistsException("Meeting already exists");
        } else {
            meetingEntity = meetingCrudSaver.saveEntity(MeetingCrudFacade.mapToEntity(dto));
        }

        return meetingEntity;
    }
}
