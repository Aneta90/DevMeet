package pl.com.devmeet.devmeet.group_associated.permission.domain;

import lombok.AllArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityUpdater;

@AllArgsConstructor
class PermissionCrudUpdater implements CrudEntityUpdater<PermissionDto, PermissionEntity> {

    private PermissionCrudRepository repository;

    @Override
    public PermissionEntity updateEntity(PermissionDto oldDto, PermissionDto newDto) throws IllegalArgumentException {
        return null;
    }
}
