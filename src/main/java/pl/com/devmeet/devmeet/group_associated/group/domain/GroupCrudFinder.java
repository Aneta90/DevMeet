package pl.com.devmeet.devmeet.group_associated.group.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;

import java.util.List;

class GroupCrudFinder implements CrudEntityFinder<GroupDto, GroupEntity> {

    private GroupCrudSaver groupCrudSaver;

    public GroupCrudFinder(GroupCrudRepository repository) {
        this.groupCrudSaver = new GroupCrudSaver(repository);
    }

    @Override
    public GroupEntity findEntity(GroupDto dto) {
        return null;
    }

    @Override
    public List<GroupEntity> findEntities(GroupDto dto) {
        return null;
    }

    @Override
    public boolean isExist(GroupDto dto) {
        return false;
    }
}
