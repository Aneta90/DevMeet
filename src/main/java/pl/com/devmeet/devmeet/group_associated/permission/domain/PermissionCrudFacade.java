package pl.com.devmeet.devmeet.group_associated.permission.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudInterface;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;

import java.util.List;

@Service
public class PermissionCrudFacade implements CrudInterface<PermissionDto, PermissionEntity> {

    private PermissionCrudRepository repository;
    private GroupCrudRepository groupRepository;
    private MemberCrudRepository memberRepository;

    @Autowired
    public PermissionCrudFacade(PermissionCrudRepository repository) {
        this.repository = repository;
    }

    private PermissionCrudCreator initCreator() {
        return new PermissionCrudCreator(repository);
    }

    private PermissionCrudFinder initFinder() {
        return new PermissionCrudFinder(repository, groupRepository, memberRepository);
    }

    private PermissionCrudUpdater initUpdater() {
        return new PermissionCrudUpdater(repository);
    }

    private PermissionCrudDeleter initDeleter() {
        return new PermissionCrudDeleter(repository);
    }

    @Override
    public PermissionDto create(PermissionDto dto) {
        return map(initCreator().createEntity(dto));
    }

    @Override
    public PermissionDto read(PermissionDto dto) throws IllegalArgumentException {
        return map(initFinder().findEntity(dto));
    }

    @Override
    public List<PermissionDto> readAll(PermissionDto dto) throws IllegalArgumentException {
        return null;
    }

    @Override
    public PermissionDto update(PermissionDto oldDto, PermissionDto newDto) throws IllegalArgumentException {
        return map(initUpdater().updateEntity(oldDto, newDto));
    }

    @Override
    public PermissionDto delete(PermissionDto dto) throws IllegalArgumentException {
        return map(initDeleter().deleteEntity(dto));
    }

    @Override
    public PermissionEntity findEntity(PermissionDto dto) throws IllegalArgumentException {
        return initFinder().findEntity(dto);
    }

    @Override
    public List<PermissionEntity> findEntities(PermissionDto dto) throws IllegalArgumentException {
        return initFinder().findEntities(dto);
    }

    public static PermissionDto map(PermissionEntity entity) {
        return PermissionCrudMapper.map(entity);
    }

    public static PermissionEntity map(PermissionDto dto) {
        return PermissionCrudMapper.map(dto);
    }
}
