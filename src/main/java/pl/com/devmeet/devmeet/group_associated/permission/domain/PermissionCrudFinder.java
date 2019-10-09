package pl.com.devmeet.devmeet.group_associated.permission.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;

import java.util.List;

class PermissionCrudFinder implements CrudEntityFinder<PermissionDto, PermissionEntity> {
    @Override
    public PermissionEntity findEntity(PermissionDto dto) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<PermissionEntity> findEntities(PermissionDto dto) throws IllegalArgumentException {
        return null;
    }

    @Override
    public boolean isExist(PermissionDto dto) {
        return false;
    }
}
