package pl.com.devmeet.devmeet.group_associated.group.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;

class GroupCrudDeleter implements CrudEntityDeleter<GroupDto, GroupEntity> {

    private GroupCrudSaver groupCrudSaver;
    private GroupCrudFinder groupCrudFinder;

    public GroupCrudDeleter(GroupCrudRepository repository) {
        this.groupCrudSaver = new GroupCrudSaver(repository);
        this.groupCrudFinder = new GroupCrudFinder(repository);
    }

    @Override
    public GroupEntity deleteEntity(GroupDto dto) {
        return null;
    }
}
