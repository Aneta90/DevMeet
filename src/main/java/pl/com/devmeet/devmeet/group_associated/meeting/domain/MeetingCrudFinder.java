package pl.com.devmeet.devmeet.group_associated.meeting.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

import java.util.List;

public class MeetingCrudFinder implements CrudEntityFinder<MeetingDto, MeetingEntity> {


    @Override
    public MeetingEntity findEntity(MeetingDto dto) throws CrudException {
        return null;
    }

    @Override
    public List<MeetingEntity> findEntities(MeetingDto dto) throws CrudException {
        return null;
    }

    @Override
    public boolean isExist(MeetingDto dto) throws CrudException {
        return false;
    }
}
