package pl.com.devmeet.devmeet.group_associated.permission.domain;

import lombok.AllArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

@AllArgsConstructor
class PermissionCrudDeleter implements CrudEntityDeleter<PermissionDto, PermissionEntity> {

    private PermissionCrudRepository repository;

    @Override
    public PermissionEntity deleteEntity(PermissionDto dto) throws EntityNotFoundException {
        return null;
    }
}
