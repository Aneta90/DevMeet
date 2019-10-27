package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudErrorEnum;
import pl.com.devmeet.devmeet.domain_utils.CrudInterface;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;

import java.util.List;

@Service
public class PollCrudFacade implements CrudInterface<PollDto, PollEntity> {

    private GroupCrudRepository groupCrudRepository;
    private PollCrudRepository pollCrudRepository;

    @Autowired
    public PollCrudFacade(GroupCrudRepository groupCrudRepository, PollCrudRepository pollCrudRepository) {
        this.groupCrudRepository = groupCrudRepository;
        this.pollCrudRepository = pollCrudRepository;
    }

    private PollGroupFinder initGroupFinder() {
        return new PollGroupFinder().builder()
                .groupCrudFacade(new GroupCrudFacade(groupCrudRepository))
                .build();
    }

    private PollCrudSaver initSaver() {
        return new PollCrudSaver().builder()
                .pollCrudRepository(pollCrudRepository)
                .pollGroupFinder(initGroupFinder())
                .build();
    }

    private PollCrudFinder initFinder() {
        return new PollCrudFinder().builder()
                .pollCrudRepository(pollCrudRepository)
                .groupFinder(initGroupFinder())
                .build();
    }

    private PollCrudCreator initCreator() {
        return new PollCrudCreator().builder()
                .pollCrudSaver(initSaver())
                .pollCrudFinder(initFinder())
                .build();
    }

    private PollCrudDeleter initDeleter() {
        return new PollCrudDeleter().builder()
                .pollCrudSaver(initSaver())
                .pollCrudFinder(initFinder())
                .build();
    }

    @Override
    public PollDto create(PollDto dto) throws EntityNotFoundException, EntityAlreadyExistsException {
        return map(initCreator().createEntity(dto));
    }

    @Override
    public PollDto read(PollDto dto) throws IllegalArgumentException, EntityNotFoundException {
        return map(findEntity(dto));
    }

    @Override
    public List<PollDto> readAll(PollDto dto) throws IllegalArgumentException, UnsupportedOperationException {
        throw new UnsupportedOperationException(CrudErrorEnum.METHOD_NOT_IMPLEMENTED.toString());
    }

    @Override
    public PollDto update(PollDto oldDto, PollDto newDto) throws UnsupportedOperationException {
       throw new UnsupportedOperationException(CrudErrorEnum.METHOD_NOT_IMPLEMENTED.toString());
    }

    @Override
    public PollDto delete(PollDto dto) throws EntityNotFoundException, EntityAlreadyExistsException {
        return map(initDeleter().deleteEntity(dto));
    }

    @Override
    public PollEntity findEntity(PollDto dto) throws EntityNotFoundException {
        return initFinder().findEntity(dto);
    }

    @Override
    public List<PollEntity> findEntities(PollDto dto) throws IllegalArgumentException, EntityNotFoundException, UnsupportedOperationException {
        throw new UnsupportedOperationException(CrudErrorEnum.METHOD_NOT_IMPLEMENTED.toString());
    }

    public static PollDto map(PollEntity entity) {
        return PollCrudMapper.map(entity);
    }

    public static PollEntity map(PollDto dto) {
        return PollCrudMapper.map(dto);
    }

    public static List<PollDto> mapToDtos(List<PollEntity> entities){
        return PollCrudMapper.mapToDtos(entities);
    }

    public static List<PollEntity> mapToEntities(List<PollDto> dtos){
        return PollCrudMapper.mapToEntities(dtos);
    }
}
