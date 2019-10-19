package pl.com.devmeet.devmeet.group_associated.permission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PermissionCrudDeleter implements CrudEntityDeleter<PermissionDto, PermissionEntity> {

    private PermissionCrudFinder permissionCrudFinder;
    private PermissionCrudSaver permissionCrudSaver;

    @Override
    public PermissionEntity deleteEntity(PermissionDto dto) throws EntityNotFoundException {
        return null;
    }
}
