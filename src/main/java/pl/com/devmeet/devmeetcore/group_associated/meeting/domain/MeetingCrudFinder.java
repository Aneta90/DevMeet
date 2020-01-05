package pl.com.devmeet.devmeetcore.group_associated.meeting.domain;

import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeetcore.group_associated.meeting.domain.status_and_exceptions.MeetingNotFoundException;

import java.util.List;
import java.util.Optional;

public class MeetingCrudFinder implements CrudEntityFinder<MeetingDto, MeetingEntity> {


    private MeetingCrudRepository meetingRepository;

    public MeetingCrudFinder(MeetingCrudRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Override
    public MeetingEntity findEntity(MeetingDto dto) throws MeetingNotFoundException {
        Optional<MeetingEntity> meetingEntity = meetingRepository.findMeetingByMeetingNumber(dto.getMeetingNumber());
        if (meetingEntity.isPresent()) {
            return meetingEntity.get();
        }

        throw new MeetingNotFoundException("Meeting is not found in our database");
    }

    @Override
    public List<MeetingEntity> findEntities(MeetingDto dto) {
        return null;  // do sprawdzenia jak wyciągać listę spotkań
    }

    public List<MeetingEntity> findEntities(GroupDto dto) throws MeetingNotFoundException {
        Optional<List<MeetingEntity>> meetingEntityList = meetingRepository.findMeetingByGroup(dto.getGroupName());
        if (meetingEntityList.isPresent()) {
            return meetingEntityList.get();
        }

        throw new MeetingNotFoundException("Meeting is not found in our database");
    }

    @Override
    public boolean isExist(MeetingDto dto) {

        Optional<MeetingEntity> meetingEntity = meetingRepository.findMeetingByMeetingNumber(dto.getMeetingNumber());

        return meetingEntity.isPresent();

    }
}