package pl.com.devmeet.devmeet.group_associated.group.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityUpdater;

class GroupCrudUpdater implements CrudEntityUpdater<GroupDto, GroupEntity> {

    private GroupCrudSaver groupCrudSaver;

    public GroupCrudUpdater(GroupCrudRepository repository) {
        this.groupCrudSaver = new GroupCrudSaver(repository);
    }

    @Override
    public GroupEntity updateEntity(GroupDto oldDto, GroupDto newDto) {
        return null;
    }
}
