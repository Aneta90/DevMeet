package pl.com.devmeet.devmeet.group_associated.group.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupCrudStatusEnum;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupFoundButNotActiveException;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class GroupCrudDeleter implements CrudEntityDeleter<GroupDto, GroupEntity> {

    private GroupCrudSaver groupCrudSaver;
    private GroupCrudFinder groupCrudFinder;

    @Override
    public GroupEntity deleteEntity(GroupDto dto) throws GroupFoundButNotActiveException, GroupNotFoundException {
        GroupEntity group = groupCrudFinder.findEntityByGroup(dto);

        boolean groupActivity = group.isActive();

        if (groupActivity) {
            group.setActive(false);
            group.setModificationTime(DateTime.now());

            return groupCrudSaver.saveEntity(group);
        }

        throw new GroupFoundButNotActiveException(GroupCrudStatusEnum.GROUP_FOUND_BUT_NOT_ACTIVE.toString());
    }
}
