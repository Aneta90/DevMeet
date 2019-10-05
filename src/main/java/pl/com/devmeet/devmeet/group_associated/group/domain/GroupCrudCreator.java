package pl.com.devmeet.devmeet.group_associated.group.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class GroupCrudCreator {

    private GroupCrudSaver groupCrudSaver;

    public GroupCrudCreator(GroupCrudRepository repository) {
        this.groupCrudSaver = new GroupCrudSaver(repository);
    }
}
