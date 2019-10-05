package pl.com.devmeet.devmeet.group_associated.group.domain;

class GroupCrudDeleter {

    private GroupCrudSaver groupCrudSaver;

    public GroupCrudDeleter(GroupCrudRepository repository) {
        this.groupCrudSaver = new GroupCrudSaver(repository);
    }
}
