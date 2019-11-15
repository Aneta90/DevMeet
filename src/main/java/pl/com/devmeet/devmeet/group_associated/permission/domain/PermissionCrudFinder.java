package pl.com.devmeet.devmeet.group_associated.permission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.domain_utils.CrudErrorEnum;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions.PermissionCrudStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PermissionCrudFinder implements CrudEntityFinder<PermissionDto, PermissionEntity> {

    private PermissionCrudRepository permissionRepository;
    private PermissionGroupFinder groupFinder;
    private PermissionMemberFinder memberFinder;

    @Override
    public PermissionEntity findEntity(PermissionDto dto) throws EntityNotFoundException {
        Optional<PermissionEntity> permission = findPermission(dto);

        if (permission.isPresent())
            return permission.get();
        else
            throw new EntityNotFoundException(PermissionCrudStatusEnum.PERMISSION_NOT_FOUND.toString());
    }

    private MemberEntity findMemberEntity(MemberDto member) throws EntityNotFoundException {
        return memberFinder.findMember(member);
    }

    private GroupEntity findGroupEntity(GroupDto group) throws EntityNotFoundException {
        return groupFinder.findGroup(group);
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
