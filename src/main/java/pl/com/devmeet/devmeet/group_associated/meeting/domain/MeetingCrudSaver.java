package pl.com.devmeet.devmeet.group_associated.meeting.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntitySaver;

public class MeetingCrudSaver implements CrudEntitySaver<MeetingEntity, MeetingEntity> {

    MeetingCrudRepository meetingCrudRepository;

    public MeetingCrudSaver(MeetingCrudRepository meetingCrudRepository) {
        this.meetingCrudRepository = meetingCrudRepository;
    }

    @Override
    public MeetingEntity saveEntity(MeetingEntity entity) {
        return meetingCrudRepository.save(entity);
    }
}
