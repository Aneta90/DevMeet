package pl.com.devmeet.devmeet.group_associated.permission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntitySaver;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;

@Builder
@AllArgsConstructor
@NoArgsConstructor
class PermissionCrudSaver implements CrudEntitySaver<PermissionEntity, PermissionEntity> {

    private PermissionCrudRepository permissionCrudRepository;
    private PermissionGroupFinder groupFinder;
    private PermissionMemberFinder memberFinder;

    @Override
    public PermissionEntity saveEntity(PermissionEntity entity) throws EntityNotFoundException {
        return permissionCrudRepository
                .save(connectPermissionWithMember(
                        connectPermissionWithGroup(entity)));
    }

    private PermissionEntity connectPermissionWithGroup(PermissionEntity permissionEntity) throws EntityNotFoundException {
        GroupEntity groupEntity = permissionEntity.getGroup();

        if (groupEntity.getId() == null)
            groupEntity = groupFinder.findGroup(GroupCrudFacade.map(permissionEntity.getGroup()));

        permissionEntity.setGroup(groupEntity);
        return permissionEntity;
    }

    private PermissionEntity connectPermissionWithMember(PermissionEntity permissionEntity) throws EntityNotFoundException {
        MemberEntity memberEntity = permissionEntity.getMember();

        if (memberEntity.getId() == null)
            memberEntity = memberFinder.findMember(MemberCrudFacade.map(permissionEntity.getMember()));

        permissionEntity.setMember(memberEntity);
        return permissionEntity;
    }
}
