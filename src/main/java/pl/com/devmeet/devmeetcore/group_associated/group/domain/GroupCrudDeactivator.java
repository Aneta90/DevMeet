package pl.com.devmeet.devmeetcore.group_associated.group.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupCrudStatusEnum;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupFoundButNotActiveException;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerDto;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerAlreadyExistsException;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class GroupCrudDeactivator implements CrudEntityDeleter<GroupDto, GroupEntity> {

    private GroupCrudSaver groupCrudSaver;
    private GroupCrudFinder groupCrudFinder;
    private GroupMessengerDeactivator groupMessengerDeactivator;

    @Override
    public GroupEntity deleteEntity(GroupDto dto) throws GroupFoundButNotActiveException, GroupNotFoundException, UserNotFoundException, MessengerNotFoundException, MemberNotFoundException, MessengerAlreadyExistsException {
        GroupEntity group = groupCrudFinder.findEntityByGroup(dto);

        boolean groupActivity = group.isActive();

        if (groupActivity) {
            return groupCrudSaver.saveEntity(
                    setDefaultValues(group));
        }

        throw new GroupFoundButNotActiveException(GroupCrudStatusEnum.GROUP_FOUND_BUT_NOT_ACTIVE.toString());
    }

    private GroupEntity setDefaultValues(GroupEntity groupEntity) throws UserNotFoundException, MemberNotFoundException, MessengerAlreadyExistsException, MessengerNotFoundException, GroupNotFoundException {
        groupEntity.setActive(false);
        groupEntity.setModificationTime(DateTime.now());

        deactivateGroupMessenger(groupEntity);

        return groupEntity;
    }

    private MessengerDto deactivateGroupMessenger(GroupEntity groupEntity) throws UserNotFoundException, MessengerNotFoundException, MemberNotFoundException, GroupNotFoundException, MessengerAlreadyExistsException {
        return groupMessengerDeactivator.deactivateMessenger(groupEntity);
    }
}
