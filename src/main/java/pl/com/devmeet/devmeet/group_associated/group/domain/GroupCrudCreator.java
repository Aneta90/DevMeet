package pl.com.devmeet.devmeet.group_associated.group.domain;

import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.status.GroupCrudStatusEnum;

@AllArgsConstructor
class GroupCrudCreator implements CrudEntityCreator<GroupDto, GroupEntity> {

    private GroupCrudSaver saver;
    private GroupCrudFinder finder;

    public GroupCrudCreator(GroupCrudRepository repository) {
        this.saver = new GroupCrudSaver(repository);
        this.finder = new GroupCrudFinder(repository);
    }

    @Override
    public GroupEntity createEntity(GroupDto dto) throws EntityAlreadyExistsException {
        GroupEntity group;

        try {
            group = finder.findEntity(dto);

            if (!group.isActive() && group.getModificationTime() != null)
                return saver.saveEntity(setDefaultValuesWhenGroupExists(group));

        } catch (EntityNotFoundException e) {
            return saver.saveEntity(setDefaultValuesWhenGroupNotExists(GroupCrudFacade.map(dto)));
        }
        
        throw new EntityAlreadyExistsException(GroupCrudStatusEnum.GROUP_ALREADY_EXISTS.toString());
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
