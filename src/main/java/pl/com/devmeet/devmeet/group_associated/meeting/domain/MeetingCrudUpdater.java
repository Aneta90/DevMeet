package pl.com.devmeet.devmeet.group_associated.meeting.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityUpdater;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;

public class MeetingCrudUpdater implements CrudEntityUpdater<MeetingDto, MeetingEntity> {

    private MeetingCrudFinder meetingCrudFinder;
    private MeetingCrudSaver meetingCrudSaver;

    public MeetingCrudUpdater(MeetingCrudRepository meetingCrudRepository) {
        this.meetingCrudSaver = new MeetingCrudSaver(meetingCrudRepository);
        this.meetingCrudFinder = new MeetingCrudFinder(meetingCrudRepository);
    }

    @Override
    public MeetingEntity updateEntity(MeetingDto oldDto, MeetingDto newDto) throws EntityNotFoundException {

        MeetingEntity foundOldEntity = meetingCrudFinder.findEntity(oldDto);
        if (foundOldEntity != null) {
            updateAllParameters(foundOldEntity, mapToEntity(newDto));
        } else {
            throw new EntityNotFoundException("Meeting has been not found in our database");
        }

        return meetingCrudSaver.saveEntity(updateAllParameters(foundOldEntity, mapToEntity(newDto))); //?? check if works !!!
    }

    private MeetingEntity mapToEntity(MeetingDto meetingDto) {
        return MeetingCrudFacade.maptoEntity(meetingDto);
    }

    private MeetingEntity updateAllParameters(MeetingEntity oldMeetingEntity, MeetingEntity newMeetingEntity) {

        oldMeetingEntity.setMeetingNumber(newMeetingEntity.getMeetingNumber());
        oldMeetingEntity.setPlace(newMeetingEntity.getPlace());
        oldMeetingEntity.setGroup(newMeetingEntity.getGroup());
        oldMeetingEntity.setActive(newMeetingEntity.isActive());
        oldMeetingEntity.setBeginTime(newMeetingEntity.getBeginTime());
        oldMeetingEntity.setCreationTime(newMeetingEntity.getCreationTime());
        oldMeetingEntity.setEndTime(newMeetingEntity.getEndTime());
        return oldMeetingEntity;
    }
}