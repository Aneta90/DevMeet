package pl.com.devmeet.devmeetcore.group_associated.permission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeetcore.domain_utils.CrudErrorEnum;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeetcore.group_associated.permission.domain.status_and_exceptions.PermissionCrudStatusEnum;
import pl.com.devmeet.devmeetcore.group_associated.permission.domain.status_and_exceptions.PermissionMethodNotImplemented;
import pl.com.devmeet.devmeetcore.group_associated.permission.domain.status_and_exceptions.PermissionNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

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
    public PermissionEntity findEntity(PermissionDto dto) throws PermissionNotFoundException, MemberNotFoundException, UserNotFoundException, GroupNotFoundException {
        Optional<PermissionEntity> permission = findPermission(dto);

        if (permission.isPresent())
            return permission.get();
        else
            throw new PermissionNotFoundException(PermissionCrudStatusEnum.PERMISSION_NOT_FOUND.toString());
    }

    private MemberEntity findMemberEntity(MemberDto member) throws MemberNotFoundException, UserNotFoundException {
        return memberFinder.findMember(member);
    }

    private GroupEntity findGroupEntity(GroupDto group) throws GroupNotFoundException {
        return groupFinder.findGroup(group);
    }

    private Optional<PermissionEntity> findPermission(PermissionDto dto) throws MemberNotFoundException, UserNotFoundException, GroupNotFoundException {
        MemberEntity member = findMemberEntity(dto.getMember());
        GroupEntity group = findGroupEntity(dto.getGroup());

        return permissionRepository.findByMemberAndGroup(member, group);
    }

    @Override
    public List<PermissionEntity> findEntities(PermissionDto dto) throws PermissionMethodNotImplemented {
        throw new PermissionMethodNotImplemented(CrudErrorEnum.METHOD_NOT_IMPLEMENTED.toString());
    }

    @Override
    public boolean isExist(PermissionDto dto) {
        try {
            return findPermission(dto).isPresent();
        } catch (MemberNotFoundException | UserNotFoundException | GroupNotFoundException e) {
            return false;
        }
    }
}
