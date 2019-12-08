package pl.com.devmeet.devmeet.group_associated.group.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupAlreadyExistsException;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupCrudStatusEnum;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class GroupCrudCreator implements CrudEntityCreator<GroupDto, GroupEntity> {

    private GroupCrudSaver groupCrudSaver;
    private GroupCrudFinder groupCrudFinder;

    @Override
    public GroupEntity createEntity(GroupDto dto) throws GroupAlreadyExistsException {
        GroupEntity group;

        try {
            group = groupCrudFinder.findEntityByGroup(dto);

            if (!group.isActive() && group.getModificationTime() != null)
                return groupCrudSaver.saveEntity(setDefaultValuesWhenGroupExists(group));

        } catch (GroupNotFoundException e) {
            return groupCrudSaver.saveEntity(setDefaultValuesWhenGroupNotExists(GroupCrudFacade.map(dto)));
        }
        
        throw new GroupAlreadyExistsException(GroupCrudStatusEnum.GROUP_ALREADY_EXISTS.toString());
    }


    private GroupEntity setDefaultValuesWhenGroupNotExists(GroupEntity group) {
        group.setCreationTime(DateTime.now());
        group.setActive(true);
        return group;
    }

    private GroupEntity setDefaultValuesWhenGroupExists(GroupEntity entity) {
        entity.setModificationTime(DateTime.now());
        entity.setActive(true);
        return entity;
    }
}
