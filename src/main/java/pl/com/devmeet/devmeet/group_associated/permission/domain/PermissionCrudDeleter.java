package pl.com.devmeet.devmeet.group_associated.permission.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;

class PermissionCrudDeleter implements CrudEntityDeleter<PermissionDto, PermissionEntity> {
    @Override
    public PermissionEntity deleteEntity(PermissionDto dto) throws IllegalArgumentException {
        return null;
    }
}
