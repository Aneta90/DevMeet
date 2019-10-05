package pl.com.devmeet.devmeet.group_associated.group.domain;

class GroupCrudUpdater {

    private GroupCrudSaver groupCrudSaver;

    public GroupCrudUpdater(GroupCrudRepository repository) {
        this.groupCrudSaver = new GroupCrudSaver(repository);
    }
}
