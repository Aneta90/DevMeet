package pl.com.devmeet.devmeet.group_associated.permission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions.PermissionAlreadyExistsException;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions.PermissionCrudStatusEnum;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions.PermissionNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PermissionCrudDeleter implements CrudEntityDeleter<PermissionDto, PermissionEntity> {

    private PermissionCrudFinder permissionCrudFinder;
    private PermissionCrudSaver permissionCrudSaver;

    @Override
    public PermissionEntity deleteEntity(PermissionDto dto) throws PermissionAlreadyExistsException, UserNotFoundException, GroupNotFoundException, MemberNotFoundException, PermissionNotFoundException {
        PermissionEntity permissionEntity = permissionCrudFinder.findEntity(dto);
        boolean permissionActivity = permissionEntity.isActive();

        if (permissionActivity) {
            permissionEntity.setActive(false);
            permissionEntity.setModificationTime(DateTime.now());

            return permissionCrudSaver.saveEntity(permissionEntity);
        }

        throw new PermissionAlreadyExistsException(PermissionCrudStatusEnum.PERMISSION_FOUND_BUT_NOT_ACTIVE.toString());
    }
}
