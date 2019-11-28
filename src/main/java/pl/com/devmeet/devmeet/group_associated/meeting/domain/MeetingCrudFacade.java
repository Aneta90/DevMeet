package pl.com.devmeet.devmeet.group_associated.meeting.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudFacadeInterface;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;

import java.util.List;

import static pl.com.devmeet.devmeet.group_associated.meeting.domain.MeetingMapper.map;

@Service
public class MeetingCrudFacade implements CrudFacadeInterface<MeetingDto, MeetingEntity> {

    private MeetingCrudRepository repository;

    @Autowired
    public MeetingCrudFacade(MeetingCrudRepository repository) {
        this.repository = repository;
    }

    private MeetingCrudCreator initCreator() {
        return new MeetingCrudCreator(repository);
    }

    private MeetingCrudFinder initFinder() {
        return new MeetingCrudFinder(repository);
    }

    private MeetingCrudUpdater initUpdater() {
        return new MeetingCrudUpdater(repository);
    }

    private MeetingCrudDeleter initDeleter() {
        return new MeetingCrudDeleter(repository);
    }


    @Override
    public MeetingDto create(MeetingDto dto) throws CrudException {
        return mapToDto(initCreator().createEntity(dto));
    }

    @Override
    public MeetingDto read(MeetingDto dto) throws CrudException {
        return mapToDto(initFinder().findEntity(dto));
    }

   @Override
    public List<MeetingDto> readAll(MeetingDto dto) throws CrudException {
        return null; //do sprawdzenia !!!
    }

    public List<MeetingDto> readAll(GroupDto dto) throws CrudException {
        return mapToDtosList(findEntities(dto)); //do spr.
    }

    @Override
    public MeetingDto update(MeetingDto oldDto, MeetingDto newDto) throws CrudException {
        return map(initUpdater().updateEntity(oldDto, newDto));
    }

    @Override
    public MeetingDto delete(MeetingDto dto) throws EntityNotFoundException {
        return map(initDeleter().deleteEntity(dto));
    }

    @Override
    public MeetingEntity findEntity(MeetingDto dto) throws CrudException {
        return initFinder().findEntity(dto);
    }

    @Override
    public List<MeetingEntity> findEntities(MeetingDto dto) throws CrudException {
        return null;
    }

    public List<MeetingEntity> findEntities(GroupDto dto) throws CrudException {
        return initFinder().findEntities(dto);
    }


    public static List<MeetingEntity> mapToEntityList(List<MeetingDto> meetingDtosList) {
        return MeetingMapper.mapToEntities(meetingDtosList);
    }

    public static List<MeetingDto> mapToDtosList(List<MeetingEntity> meetingEntityList) {
        return MeetingMapper.mapToDtos(meetingEntityList);
    }

    public static MeetingEntity mapToEntity(MeetingDto meetingDto) {
        return MeetingMapper.map(meetingDto);
    }

    public static MeetingDto mapToDto(MeetingEntity meetingEntity) {
        return MeetingMapper.map(meetingEntity);
    }
}