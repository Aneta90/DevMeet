package pl.com.devmeet.devmeet.group_associated.permission.domain;

import lombok.AllArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntitySaver;

@AllArgsConstructor
class PermissionCrudSaver implements CrudEntitySaver<PermissionEntity, PermissionEntity> {

    private PermissionCrudRepository repository;

    @Override
    public PermissionEntity saveEntity(PermissionEntity entity) {
        return repository.save(entity);
    }
}
