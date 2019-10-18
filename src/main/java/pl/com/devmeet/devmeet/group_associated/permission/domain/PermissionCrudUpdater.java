package pl.com.devmeet.devmeet.group_associated.permission.domain;

import lombok.AllArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityUpdater;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

@AllArgsConstructor
class PermissionCrudUpdater implements CrudEntityUpdater<PermissionDto, PermissionEntity> {

    private PermissionCrudRepository repository;

    @Override
    public PermissionEntity updateEntity(PermissionDto oldDto, PermissionDto newDto) throws EntityNotFoundException {
        return null;
    }
}
