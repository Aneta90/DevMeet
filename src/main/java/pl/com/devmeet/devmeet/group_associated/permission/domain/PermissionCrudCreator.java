package pl.com.devmeet.devmeet.group_associated.permission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions.PermissionAlreadyExistsException;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions.PermissionCrudStatusEnum;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions.PermissionNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PermissionCrudCreator implements CrudEntityCreator<PermissionDto, PermissionEntity> {

    private PermissionCrudSaver permissionCrudSaver;
    private PermissionCrudFinder permissionCrudFinder;

    @Override
    public PermissionEntity createEntity(PermissionDto dto) throws PermissionAlreadyExistsException, GroupNotFoundException, MemberNotFoundException, UserNotFoundException {
        PermissionEntity permission;

        try {
            permission = permissionCrudFinder.findEntity(dto);

            if (!permission.isActive() && permission.getModificationTime() != null) {
                return permissionCrudSaver.saveEntity(setDefaultValuesWhenPermissionExist(permission));
            }

        } catch (PermissionNotFoundException e) {
            permission = setDefaultValuesWhenPermissionNotExist(PermissionCrudFacade.map(dto));
            return permissionCrudSaver.saveEntity(permission);
        }

        throw new PermissionAlreadyExistsException(PermissionCrudStatusEnum.PERMISSION_ALREADY_EXISTS.toString());
    }

    private PermissionEntity setDefaultValuesWhenPermissionNotExist(PermissionEntity permission) {
        permission.setActive(true);
        permission.setCreationTime(DateTime.now());
        return permission;
    }

    private PermissionEntity setDefaultValuesWhenPermissionExist(PermissionEntity permission) {
        permission.setActive(true);
        permission.setModificationTime(DateTime.now());
        return permission;
    }
}
