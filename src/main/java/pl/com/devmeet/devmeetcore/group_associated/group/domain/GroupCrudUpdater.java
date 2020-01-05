package pl.com.devmeet.devmeetcore.group_associated.group.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityUpdater;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupCrudStatusEnum;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupException;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupFoundButNotActiveException;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class GroupCrudUpdater implements CrudEntityUpdater<GroupDto, GroupEntity> {

    private GroupCrudSaver groupCrudSaver;
    private GroupCrudFinder groupCrudFinder;

    @Override
    public GroupEntity updateEntity(GroupDto oldDto, GroupDto newDto) throws GroupException, GroupNotFoundException, GroupFoundButNotActiveException {
        GroupEntity foundOldGroup = checkIsOldGroupActive(groupCrudFinder.findEntityByGroup(oldDto));

        GroupEntity newGroup = mapDtoToEntity(checkIsNewGroupHasAName(newDto, foundOldGroup));

        return groupCrudSaver.saveEntity(updateAllowedParameters(foundOldGroup, newGroup));
    }

    public GroupEntity updateEntity(GroupDto oldDto, String groupName, String website, String description) throws GroupException, GroupNotFoundException, GroupFoundButNotActiveException {
        return updateEntity(oldDto, GroupDto.builder()
                .groupName(groupName)
                .website(website)
                .description(description)
                .build());
    }

    private GroupEntity checkIsOldGroupActive(GroupEntity oldGroup) throws GroupFoundButNotActiveException {
        if (oldGroup.isActive())
            return oldGroup;
        else
            throw new GroupFoundButNotActiveException(GroupCrudStatusEnum.GROUP_FOUND_BUT_NOT_ACTIVE.toString());
    }

    private GroupDto checkIsNewGroupHasAName(GroupDto newGroup, GroupEntity oldGroup) throws GroupException {
        if (newGroup.getGroupName().equals(oldGroup.getGroupName()))
            return newGroup;

        throw new GroupException(GroupCrudStatusEnum.GROUP_INCORRECT_VALUES.toString());
    }

    private GroupEntity mapDtoToEntity(GroupDto dto) {
        return GroupCrudFacade.map(dto);
    }

    private GroupEntity updateAllowedParameters(GroupEntity oldEntity, GroupEntity newEntity) {
        oldEntity.setGroupName(newEntity.getGroupName());

        oldEntity.setWebsite(newEntity.getWebsite());
        oldEntity.setDescription(newEntity.getDescription());
        oldEntity.setMembersLimit(newEntity.getMembersLimit());

        oldEntity.setModificationTime(DateTime.now());
        return oldEntity;
    }
}
