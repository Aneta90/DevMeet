package pl.com.devmeet.devmeetcore.group_associated.group.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupAlreadyExistsException;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupCrudStatusEnum;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerAlreadyExistsException;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerArgumentNotSpecified;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class GroupCrudCreator implements CrudEntityCreator<GroupDto, GroupEntity> {

    private GroupCrudSaver groupCrudSaver;
    private GroupCrudFinder groupCrudFinder;
    private GroupMessengerCreator groupMessengerCreator;

    @Override
    public GroupEntity createEntity(GroupDto dto) throws GroupAlreadyExistsException, UserNotFoundException, GroupNotFoundException, MemberNotFoundException, MessengerAlreadyExistsException, MessengerArgumentNotSpecified {
        GroupEntity group;

        try {
            group = groupCrudFinder.findEntityByGroup(dto);

            if (!group.isActive() && group.getModificationTime() != null)
                return groupCrudSaver.saveEntity(setDefaultValuesWhenGroupExists(group));

        } catch (GroupNotFoundException e) {
            group = groupCrudSaver.saveEntity(
                    setDefaultValuesWhenGroupNotExists(GroupCrudFacade.map(dto))
            );
            createMessengerForGroup(dto);

            return group;
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

    private void createMessengerForGroup(GroupDto groupDto) throws UserNotFoundException, MemberNotFoundException, MessengerAlreadyExistsException, GroupNotFoundException, MessengerArgumentNotSpecified {
        groupMessengerCreator.createMessenger(groupDto);
    }
}
