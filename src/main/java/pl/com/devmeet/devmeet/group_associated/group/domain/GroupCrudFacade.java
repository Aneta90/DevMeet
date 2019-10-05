package pl.com.devmeet.devmeet.group_associated.group.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudInterface;

import java.util.List;

@Service
public class GroupCrudFacade implements CrudInterface<GroupDto, GroupEntity> {

    private GroupCrudRepository repository;

    @Autowired
    public GroupCrudFacade(GroupCrudRepository repository) {
        this.repository = repository;
    }

    private GroupCrudCreator initCreator(){return new GroupCrudCreator(repository);}
    private GroupCrudFinder initFinder(){return new GroupCrudFinder(repository);}
    private GroupCrudUpdater initUpdater(){return new GroupCrudUpdater(repository);}
    private GroupCrudDeleter initDeleter(){return new GroupCrudDeleter(repository);}

    @Override
    public GroupDto create(GroupDto dto) { return null;}

    @Override
    public GroupDto read(GroupDto dto) {
        return null;
    }

    @Override
    public List<GroupDto> readAll(GroupDto dto) {
        return null;
    }

    @Override
    public GroupDto update(GroupDto oldDto, GroupDto newDto) {
        return null;
    }

    @Override
    public GroupDto delete(GroupDto dto) {
        return null;
    }

    @Override
    public GroupEntity findEntity(GroupDto dto) {
        return null;
    }

    @Override
    public List<GroupEntity> findEntities(GroupDto dto) {
        return null;
    }
}
