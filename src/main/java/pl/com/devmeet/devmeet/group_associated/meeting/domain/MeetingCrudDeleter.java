package pl.com.devmeet.devmeet.group_associated.meeting.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeet.group_associated.meeting.domain.status_and_exceptions.MeetingNotFoundException;

public class MeetingCrudDeleter implements CrudEntityDeleter<MeetingDto, MeetingEntity> {

    private MeetingCrudFinder meetingCrudFinder;
    private MeetingCrudSaver meetingCrudSaver;

    public MeetingCrudDeleter(MeetingCrudRepository meetingCrudRepository) {
        this.meetingCrudFinder = new MeetingCrudFinder(meetingCrudRepository);
        this.meetingCrudSaver = new MeetingCrudSaver(meetingCrudRepository);
    }

    @Override
    public MeetingEntity deleteEntity(MeetingDto dto) throws MeetingNotFoundException {

        MeetingEntity meetingEntity = meetingCrudFinder.findEntity(dto);

        boolean isMeetingActive = meetingEntity.isActive();
        if (isMeetingActive) {
            meetingEntity.setActive(false);
            meetingEntity.setBeginTime(null);
            meetingEntity.setEndTime(null);

            return meetingCrudSaver.saveEntity(meetingEntity);
        }

        throw new MeetingNotFoundException("Meeting has been not found in our database");
    }
}