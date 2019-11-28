package pl.com.devmeet.devmeet.group_associated.meeting.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;

import java.util.List;
import java.util.Optional;

public class MeetingCrudFinder implements CrudEntityFinder<MeetingDto, MeetingEntity> {


    private MeetingCrudRepository meetingRepository;

    public MeetingCrudFinder(MeetingCrudRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Override
    public MeetingEntity findEntity(MeetingDto dto) throws EntityNotFoundException {
        Optional<MeetingEntity> meetingEntity = meetingRepository.findMeetingByMeetingNumber(dto.getMeetingNumber());
        if (meetingEntity.isPresent()) {
            return meetingEntity.get();
        }

        throw new EntityNotFoundException("Meeting is not found in our database");
    }

    @Override
    public List<MeetingEntity> findEntities(MeetingDto dto) throws CrudException {
        return null;  // do sprawdzenia jak wyciągać listę spotkań
    }

    public List<MeetingEntity> findEntities(GroupDto dto) throws EntityNotFoundException {
        Optional<List<MeetingEntity>> meetingEntityList = meetingRepository.findMeetingByGroup(dto.getGroupName());
        if (meetingEntityList.isPresent()) {
            return meetingEntityList.get();
        }

        throw new EntityNotFoundException("Meeting is not found in our databse");
    }

    @Override
    public boolean isExist(MeetingDto dto) throws CrudException {

        Optional<MeetingEntity> meetingEntity = meetingRepository.findMeetingByMeetingNumber(dto.getMeetingNumber());
        return meetingEntity.get().isActive();
    }
}
