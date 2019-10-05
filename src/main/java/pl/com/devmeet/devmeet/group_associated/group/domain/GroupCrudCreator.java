package pl.com.devmeet.devmeet.group_associated.group.domain;

import lombok.AllArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;

@AllArgsConstructor
class GroupCrudCreator implements CrudEntityCreator<GroupDto, GroupEntity> {

    private GroupCrudSaver groupCrudSaver;

    public GroupCrudCreator(GroupCrudRepository repository) {
        this.groupCrudSaver = new GroupCrudSaver(repository);
    }

    @Override
    public GroupEntity createEntity(GroupDto dto) {
        return null;
    }
}
