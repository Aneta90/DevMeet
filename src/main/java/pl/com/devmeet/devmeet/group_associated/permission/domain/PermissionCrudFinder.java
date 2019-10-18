package pl.com.devmeet.devmeet.group_associated.permission.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.domain_utils.CrudErrorEnum;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions.PermissionCrudStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;

import java.util.List;
import java.util.Optional;

class PermissionCrudFinder implements CrudEntityFinder<PermissionDto, PermissionEntity> {

    private PermissionCrudRepository permissionRepository;
    private GroupCrudRepository groupRepository;
    private MemberRepository memberRepository;

    public PermissionCrudFinder(PermissionCrudRepository permissionRepository, GroupCrudRepository groupRepository, MemberRepository memberRepository) {
        this.permissionRepository = permissionRepository;
        this.groupRepository = groupRepository;
        this.memberRepository = memberRepository;
    }

    private MemberEntity findMemberEntity(MemberDto member) throws EntityNotFoundException {
        return new MemberCrudFacade(memberRepository).findEntity(member);
    }

    private GroupEntity findGroupEntity(GroupDto group) throws EntityNotFoundException {
        return new GroupCrudFacade(groupRepository).findEntity(group);
    }

    @Override
    public PermissionEntity findEntity(PermissionDto dto) throws EntityNotFoundException {
        Optional<PermissionEntity> permission = findPermission(dto);

        if (permission.isPresent())
            return permission.get();
        else
            throw new EntityNotFoundException(PermissionCrudStatusEnum.PERMISSION_NOT_FOUND.toString());
    }

    private Optional<PermissionEntity> findPermission(PermissionDto dto) throws EntityNotFoundException {
        MemberEntity member;
        GroupEntity group;
        try {
            member = findMemberEntity(dto.getMember());
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(PermissionCrudStatusEnum.PERMISSION_MEMBER_NOT_FOUND.toString());
        }
        try {
            group = findGroupEntity(dto.getGroup());
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(PermissionCrudStatusEnum.PERMISSION_GROUP_NOT_FOUND.toString());
        }

        return permissionRepository.findByMemberAndGroup(member, group);
    }

    @Override
    public List<PermissionEntity> findEntities(PermissionDto dto) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(CrudErrorEnum.METHOD_NOT_IMPLEMENTED.toString());
    }

    @Override
    public boolean isExist(PermissionDto dto) {
        try {
            return findPermission(dto).isPresent();
        } catch (EntityNotFoundException e) {
            return false;
        }
    }
}
