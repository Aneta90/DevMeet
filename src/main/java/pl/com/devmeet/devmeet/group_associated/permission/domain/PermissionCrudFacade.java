package pl.com.devmeet.devmeet.group_associated.permission.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudErrorEnum;
import pl.com.devmeet.devmeet.domain_utils.CrudInterface;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions.PermissionCrudStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;

import java.util.List;

@Service
public class PermissionCrudFacade implements CrudInterface<PermissionDto, PermissionEntity> {

    private PermissionCrudRepository repository;
    private GroupCrudRepository groupRepository;
    private MemberRepository memberRepository;

    @Autowired
    public PermissionCrudFacade(PermissionCrudRepository repository, GroupCrudRepository groupRepository, MemberRepository memberRepository) {
        this.repository = repository;
        this.groupRepository = groupRepository;
        this.memberRepository = memberRepository;
    }

    private PermissionCrudCreator initCreator() {
        return new PermissionCrudCreator(repository, groupRepository, memberRepository);
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
    public PermissionDto create(PermissionDto dto) throws EntityAlreadyExistsException {
        return map(initCreator().createEntity(dto));
    }

    @Override
    public PermissionDto read(PermissionDto dto) throws EntityNotFoundException {
        return map(initFinder().findEntity(dto));
    }

    @Override
    public List<PermissionDto> readAll(PermissionDto dto) throws UnsupportedOperationException {
        throw  new UnsupportedOperationException(PermissionCrudStatusEnum.METHOD_NOT_IMPLEMENTED.toString());
    }

    @Override
    public PermissionDto update(PermissionDto oldDto, PermissionDto newDto) throws EntityNotFoundException {
        return map(initUpdater().updateEntity(oldDto, newDto));
    }

    @Override
    public PermissionDto delete(PermissionDto dto) throws EntityNotFoundException {
        return map(initDeleter().deleteEntity(dto));
    }

    @Override
    public PermissionEntity findEntity(PermissionDto dto) throws EntityNotFoundException {
        return initFinder().findEntity(dto);
    }

    @Override
    public List<PermissionEntity> findEntities(PermissionDto dto) throws UnsupportedOperationException {
        return initFinder().findEntities(dto);
    }

    public static PermissionDto map(PermissionEntity entity) {
        return PermissionCrudMapper.map(entity);
    }

    public static PermissionEntity map(PermissionDto dto) {
        return PermissionCrudMapper.map(dto);
    }
}
