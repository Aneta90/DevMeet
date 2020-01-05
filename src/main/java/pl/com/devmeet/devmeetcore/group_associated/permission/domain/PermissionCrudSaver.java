package pl.com.devmeet.devmeetcore.group_associated.permission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeetcore.domain_utils.CrudEntitySaver;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

@Builder
@AllArgsConstructor
@NoArgsConstructor
class PermissionCrudSaver implements CrudEntitySaver<PermissionEntity, PermissionEntity> {

    private PermissionCrudRepository permissionCrudRepository;
    private PermissionGroupFinder groupFinder;
    private PermissionMemberFinder memberFinder;

    @Override
    public PermissionEntity saveEntity(PermissionEntity entity) throws GroupNotFoundException, MemberNotFoundException, UserNotFoundException {
        return permissionCrudRepository
                .save(connectPermissionWithMember(
                        connectPermissionWithGroup(entity)));
    }

    private PermissionEntity connectPermissionWithGroup(PermissionEntity permissionEntity) throws GroupNotFoundException {
        GroupEntity groupEntity = permissionEntity.getGroup();

        if (groupEntity.getId() == null)
            groupEntity = groupFinder.findGroup(GroupCrudFacade.map(permissionEntity.getGroup()));

        permissionEntity.setGroup(groupEntity);
        return permissionEntity;
    }

    private PermissionEntity connectPermissionWithMember(PermissionEntity permissionEntity) throws MemberNotFoundException, UserNotFoundException {
        MemberEntity memberEntity = permissionEntity.getMember();

        if (memberEntity.getId() == null)
            memberEntity = memberFinder.findMember(MemberCrudFacade.map(permissionEntity.getMember()));

        permissionEntity.setMember(memberEntity);
        return permissionEntity;
    }
}
