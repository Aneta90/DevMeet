package pl.com.devmeet.devmeet.group_associated.permission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions.PermissionCrudStatusEnum;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PermissionCrudDeleter implements CrudEntityDeleter<PermissionDto, PermissionEntity> {

    private PermissionCrudFinder permissionCrudFinder;
    private PermissionCrudSaver permissionCrudSaver;

    @Override
    public PermissionEntity deleteEntity(PermissionDto dto) throws EntityNotFoundException, EntityAlreadyExistsException {
        PermissionEntity permissionEntity = permissionCrudFinder.findEntity(dto);
        boolean permissionActivity = permissionEntity.isActive();

        if (permissionActivity) {
            permissionEntity.setActive(false);
            permissionEntity.setModificationTime(DateTime.now());

            return permissionCrudSaver.saveEntity(permissionEntity);
        }

        throw new EntityAlreadyExistsException(PermissionCrudStatusEnum.PERMISSION_FOUND_BUT_NOT_ACTIVE.toString());
    }
}
