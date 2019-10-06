package pl.com.devmeet.devmeet.group_associated.group.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudFacadeMode;
import pl.com.devmeet.devmeet.domain_utils.CrudInterface;

import java.util.List;

@Service
public class GroupCrudFacade implements CrudInterface<GroupDto, GroupEntity> {

    private GroupCrudRepository repository;
    private CrudFacadeMode facadeMode;

    @Autowired
    public GroupCrudFacade(GroupCrudRepository repository) {
        this.repository = repository;
    }

    private GroupCrudCreator initCreator() {
        return new GroupCrudCreator(repository);
    }

    private GroupCrudFinder initFinder() {
        return new GroupCrudFinder(repository);
    }

    private GroupCrudUpdater initUpdater() {
        return new GroupCrudUpdater(repository);
    }

    private GroupCrudDeleter initDeleter() {
        return new GroupCrudDeleter(repository);
    }

    @Override
    public GroupDto create(GroupDto dto) {
        return map(initCreator().createEntity(dto));
    }

    @Override
    public GroupDto read(GroupDto dto) throws IllegalArgumentException {
        return map(initFinder().findEntity(dto));
    }

    @Override
    public List<GroupDto> readAll(GroupDto dto) throws IllegalArgumentException {
        return mapDtoList(initFinder().findEntities(dto));
    }

    @Override
    public GroupDto update(GroupDto oldDto, GroupDto newDto) throws IllegalArgumentException {
        return map(initUpdater().updateEntity(oldDto, newDto));
    }

    @Override
    public GroupDto delete(GroupDto dto) throws IllegalArgumentException {
        return map(initDeleter().deleteEntity(dto));
    }

    @Override
    public GroupEntity findEntity(GroupDto dto) throws IllegalArgumentException {
        return initFinder().findEntity(dto);
    }

    @Override
    public List<GroupEntity> findEntities(GroupDto dto) throws IllegalArgumentException {
        return initFinder().findEntities(dto);
    }

    public static GroupDto map(GroupEntity entity) {
        return GroupCrudMapper.map(entity);
    }

    public static List<GroupDto> mapDtoList(List<GroupEntity> entities) {
        return GroupCrudMapper.mapDtoList(entities);
    }

    public static GroupEntity map(GroupDto dto) {
        return GroupCrudMapper.map(dto);
    }

    public static List<GroupEntity> mapEntityList(List<GroupDto> dtos) {
        return GroupCrudMapper.mapEntityList(dtos);
    }
}
