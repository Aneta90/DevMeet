package pl.com.devmeet.devmeet.group_associated.permission.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudFacadeInterface;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions.*;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.user.domain.UserRepository;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;

@Service
public class PermissionCrudFacade implements CrudFacadeInterface<PermissionDto, PermissionEntity> {

    private PermissionCrudRepository permissionRepository;
    private GroupCrudRepository groupRepository;
    private MemberRepository memberRepository;
    private UserRepository userRepository;

    @Autowired
    public PermissionCrudFacade(PermissionCrudRepository permissionRepository, GroupCrudRepository groupRepository, MemberRepository memberRepository, UserRepository userRepository) {
        this.permissionRepository = permissionRepository;
        this.groupRepository = groupRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    private PermissionGroupFinder initGroupFinder() {
        return new PermissionGroupFinder().builder()
                .groupCrudFacade(new GroupCrudFacade(groupRepository, memberRepository, userRepository))
                .build();
    }

    private PermissionMemberFinder initMemberFinder() {
        return new PermissionMemberFinder().builder()
                .memberCrudFacade(new MemberCrudFacade(memberRepository, userRepository))
                .build();
    }

    private PermissionCrudSaver initSaver() {
        return new PermissionCrudSaver().builder()
                .permissionCrudRepository(permissionRepository)
                .groupFinder(initGroupFinder())
                .memberFinder(initMemberFinder())
                .build();
    }

    private PermissionCrudFinder initFinder() {
        return new PermissionCrudFinder().builder()
                .permissionRepository(permissionRepository)
                .groupFinder(initGroupFinder())
                .memberFinder(initMemberFinder())
                .build();
    }

    private PermissionCrudCreator initCreator() {
        return new PermissionCrudCreator().builder()
                .permissionCrudFinder(initFinder())
                .permissionCrudSaver(initSaver())
                .build();
    }

    private PermissionCrudUpdater initUpdater() {
        return new PermissionCrudUpdater().builder()
                .permissionCrudFinder(initFinder())
                .permissionCrudSaver(initSaver())
                .build();
    }

    private PermissionCrudDeleter initDeleter() {
        return new PermissionCrudDeleter().builder()
                .permissionCrudFinder(initFinder())
                .permissionCrudSaver(initSaver())
                .build();
    }

    @Override
    public PermissionDto add(PermissionDto dto) throws UserNotFoundException, MemberNotFoundException, PermissionAlreadyExistsException, GroupNotFoundException {
        return map(initCreator().createEntity(dto));
    }

    public PermissionDto find(PermissionDto dto) throws UserNotFoundException, MemberNotFoundException, GroupNotFoundException, PermissionNotFoundException {
        return map(findEntity(dto));
    }

    public List<PermissionDto> findAll(PermissionDto dto) throws PermissionMethodNotImplemented {
        throw new PermissionMethodNotImplemented(PermissionCrudStatusEnum.METHOD_NOT_IMPLEMENTED.toString());
    }

    @Override
    public PermissionDto update(PermissionDto oldDto, PermissionDto newDto) throws UserNotFoundException, MemberNotFoundException, GroupNotFoundException, PermissionException, PermissionNotFoundException {
        return map(initUpdater().updateEntity(oldDto, newDto));
    }

    @Override
    public PermissionDto delete(PermissionDto dto) throws UserNotFoundException, MemberNotFoundException, PermissionAlreadyExistsException, GroupNotFoundException, PermissionNotFoundException {
        return map(initDeleter().deleteEntity(dto));
    }

    public PermissionEntity findEntity(PermissionDto dto) throws UserNotFoundException, GroupNotFoundException, MemberNotFoundException, PermissionNotFoundException {
        return initFinder().findEntity(dto);
    }

    public List<PermissionEntity> findEntities(PermissionDto dto) throws PermissionMethodNotImplemented {
        return initFinder().findEntities(dto);
    }

    public static PermissionDto map(PermissionEntity entity) {
        return PermissionCrudMapper.map(entity);
    }

    public static PermissionEntity map(PermissionDto dto) {
        return PermissionCrudMapper.map(dto);
    }
}
