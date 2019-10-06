package pl.com.devmeet.devmeet.group_associated.group.domain;

import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityUpdater;
import pl.com.devmeet.devmeet.group_associated.group.domain.status.GroupCrudInfoStatusEnum;

class GroupCrudUpdater implements CrudEntityUpdater<GroupDto, GroupEntity> {

    private GroupCrudSaver groupCrudSaver;
    private GroupCrudFinder groupCrudFinder;

    public GroupCrudUpdater(GroupCrudRepository repository) {
        this.groupCrudSaver = new GroupCrudSaver(repository);
        this.groupCrudFinder = new GroupCrudFinder(repository);
    }

    @Override
    public GroupEntity updateEntity(GroupDto oldDto, GroupDto newDto) throws IllegalArgumentException {
        GroupEntity foundOldGroup = checkIsOldGroupActive(groupCrudFinder.findEntity(oldDto));
        GroupEntity newGroup = mapDtoToEntity(checkIsNewGroupHasAName(newDto, foundOldGroup));

        return groupCrudSaver.saveEntity(updateValues(foundOldGroup, newGroup));
    }

    private GroupEntity checkIsOldGroupActive(GroupEntity oldGroup){
        if (oldGroup.isActive())
            return oldGroup;
        else
            throw new IllegalArgumentException(GroupCrudInfoStatusEnum
                    .GROUP_FOUND_BUT_NOT_ACTIVE.toString());
    }

    private GroupDto checkIsNewGroupHasAName(GroupDto newGroup, GroupEntity oldGroup) {
        if (newGroup.getGroupName().equals(oldGroup.getGroupName()))
            return newGroup;

        throw new IllegalArgumentException(GroupCrudInfoStatusEnum
                .GROUP_INCORRECT_VALUES.toString());
    }

    private GroupEntity mapDtoToEntity(GroupDto dto) {
        return GroupCrudFacade.map(dto);
    }

    private GroupEntity updateValues(GroupEntity oldEntity, GroupEntity newEntity) {
        newEntity.setId(oldEntity.getId());
        newEntity.setModificationTime(DateTime.now());

        return newEntity;
    }
}
