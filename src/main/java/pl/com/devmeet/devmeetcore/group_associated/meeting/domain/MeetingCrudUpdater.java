package pl.com.devmeet.devmeetcore.group_associated.meeting.domain;

import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityUpdater;
import pl.com.devmeet.devmeetcore.group_associated.meeting.domain.status_and_exceptions.MeetingNotFoundException;

public class MeetingCrudUpdater implements CrudEntityUpdater<MeetingDto, MeetingEntity> {

    private MeetingCrudFinder meetingCrudFinder;
    private MeetingCrudSaver meetingCrudSaver;

    public MeetingCrudUpdater(MeetingCrudRepository meetingCrudRepository) {
        this.meetingCrudSaver = new MeetingCrudSaver(meetingCrudRepository);
        this.meetingCrudFinder = new MeetingCrudFinder(meetingCrudRepository);
    }

    @Override
    public MeetingEntity updateEntity(MeetingDto oldDto, MeetingDto newDto) throws MeetingNotFoundException {

        MeetingEntity foundOldEntity = meetingCrudFinder.findEntity(oldDto);
        if (foundOldEntity != null) {
            updateAllParameters(foundOldEntity, mapToEntity(newDto));
        } else {
            throw new MeetingNotFoundException("Meeting has been not found in our database");
        }

        return meetingCrudSaver.saveEntity(updateAllParameters(foundOldEntity, mapToEntity(newDto)));
    }

    private MeetingEntity mapToEntity(MeetingDto meetingDto) {
        return MeetingCrudFacade.mapToEntity(meetingDto);
    }

    private MeetingEntity updateAllParameters(MeetingEntity oldMeetingEntity, MeetingEntity newMeetingEntity) {

        oldMeetingEntity.setMeetingNumber(oldMeetingEntity.getMeetingNumber());
        oldMeetingEntity.setPlace(newMeetingEntity.getPlace());
        oldMeetingEntity.setGroup(newMeetingEntity.getGroup());
        oldMeetingEntity.setActive(newMeetingEntity.isActive());
        oldMeetingEntity.setBeginTime(newMeetingEntity.getBeginTime());
        oldMeetingEntity.setCreationTime(newMeetingEntity.getCreationTime());
        oldMeetingEntity.setEndTime(newMeetingEntity.getEndTime());
        return oldMeetingEntity;
    }
}