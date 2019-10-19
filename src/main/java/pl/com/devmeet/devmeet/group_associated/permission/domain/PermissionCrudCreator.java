package pl.com.devmeet.devmeet.group_associated.permission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions.PermissionCrudStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PermissionCrudCreator implements CrudEntityCreator<PermissionDto, PermissionEntity> {

    private PermissionCrudSaver permissionCrudSaver;
    private PermissionCrudFinder permissionCrudFinder;
    private GroupCrudFacade groupCrudFacade;
    private MemberCrudFacade memberCrudFacade;

    @Override
    public PermissionEntity createEntity(PermissionDto dto) throws EntityAlreadyExistsException, EntityNotFoundException {
        PermissionEntity permission;

        try {
            permission = permissionCrudFinder.findEntity(dto);

            if (!permission.isActive() && permission.getModificationTime() != null) {
                return permissionCrudSaver.saveEntity(setDefaultValuesWhenPermissionExist(permission));
            }

        } catch (EntityNotFoundException e) {
            permission = setDefaultValuesWhenPermissionNotExist(PermissionCrudFacade.map(dto));
            return permissionCrudSaver.saveEntity(permission);
        }

        throw new EntityAlreadyExistsException(PermissionCrudStatusEnum.PERMISSION_ALREADY_EXISTS.toString());
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
