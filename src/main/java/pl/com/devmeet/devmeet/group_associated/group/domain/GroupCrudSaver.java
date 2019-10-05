package pl.com.devmeet.devmeet.group_associated.group.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntitySaver;

class GroupCrudSaver implements CrudEntitySaver<GroupEntity, GroupEntity> {

    private GroupCrudRepository repository;

    public GroupCrudSaver(GroupCrudRepository repository) {
        this.repository = repository;
    }

    @Override
    public GroupEntity saveEntity(GroupEntity entity) {
        return repository.save(entity);
    }
}
